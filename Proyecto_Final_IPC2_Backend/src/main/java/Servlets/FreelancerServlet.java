package Servlets;

import DAOs.*;
import Modelos.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "FreelancerServlet", urlPatterns = {"/FreelancerServlet"})
public class FreelancerServlet extends HttpServlet {

    private FreelancerDAO freelancerDao = new FreelancerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");

        //Accion define si se está creando editado cleinte o si se va a editar
        String accionRecibida = request.getParameter("accion");

        if (accionRecibida == null) {
            response.getWriter().print("{\"error\": \"Acción no especificada\"}");
            return;
        }

        try {
            switch (accionRecibida) {
                case "obtenerFreelancers":
                    obtenerFreelancers(request, response, om);
                    break;

                default:

                    response.getWriter().print("{\"error\": \"Acción no válida\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        //Accion define si se está creando editado cleinte o si se va a editar
        String accionRecibida = request.getParameter("accion");

        if (accionRecibida == null) {
            response.getWriter().print("{\"error\": \"Acción no especificada\"}");
            return;
        }

        try {
            switch (accionRecibida) {
                case "completar":
                    registrarInfo(request, response, om);
                    break;
                case "obtenerHabilidades":
                    obtenerHabilidadesDeFreelancer(request, response, om);
                    break;
                case "vincularDesvincular":
                    vincularHabilidadFreelancer(request, response, om);
                    break;
                case "solicitar":
                    solicitarHabilidad(request, response, om);
                    break;

                default:

                    response.getWriter().print("{\"error\": \"Acción no válida\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void registrarInfo(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_usuario = ((Number) datos.get("id_usuario")).intValue();
            String biografia = ((String) datos.get("biografia"));
            String nivel_experiencia = ((String) datos.get("nivel_experiencia"));
            double tarifa_hora = Double.parseDouble(datos.get("tarifa_hora").toString());

            if (!freelancerDao.registrarInformacionInicial(id_usuario, biografia, nivel_experiencia, tarifa_hora)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se pudo ingresar la informacion, intentelo de nuevo\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"informacion registrada\"}");

            }

        } catch (Exception e) {
        }

    }

    private void obtenerHabilidadesDeFreelancer(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_usuario = ((Number) datos.get("id_usuario")).intValue();

            ArrayList<Habilidad> habilidades = freelancerDao.obtenerHabilidadesDeFreelancer(id_usuario);

            if (habilidades == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al obtener las habilidades del freelancer\"}");

            } else {
                String json = om.writeValueAsString(habilidades);
                response.getWriter().print(json);
            }
        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER HABILIDADES DE FREELANCER DESDE SERVLET");
        }

    }

    private void obtenerFreelancers(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            ArrayList<Usuario> freelancers = freelancerDao.obtenerFreelancers();

            if (freelancers.isEmpty()) {
                //no hay freelancers
                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No hay freelancers\"}");

            } else {

                String json = om.writeValueAsString(freelancers);
                response.getWriter().print(json);
            }

        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER FREELANCERS DESDE SERVLET: " + e.getMessage());
        }

    }

    private void vincularHabilidadFreelancer(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_usuario = ((Number) datos.get("id_usuario")).intValue();
            int id_habilidad = ((Number) datos.get("id_habilidad")).intValue();
            int metodo = ((Number) datos.get("metodo")).intValue();//si es 1 vincula, si es 2 desvincula

            if (metodo == 1) {//vincula
                if (!freelancerDao.vincularFreelancerHabilidad(id_usuario, id_habilidad)) {

                    response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al vincular la habilidad y el freelancer\"}");

                } else {
                    response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Habilidad y freelancer vinculados exitosamente\"}");

                }

            } else {//desvincula

                if (!freelancerDao.desvincularFreelancerHabilidad(id_usuario, id_habilidad)) {

                    response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al desvincular la habilidad y el freelancer\"}");

                } else {
                    response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Habilidad y freelancer desvinculados exitosamente\"}");

                }

            }
        } catch (Exception e) {
            System.out.println("ERROR AL VINCULAR/DESVINCULAR HABILIDAD Y FREELANCER DESDE SERVLET");
        }

    }

    private void solicitarHabilidad(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {
       
        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_usuario = ((Number) datos.get("id_usuario")).intValue();
            String descripcion = ((String) datos.get("descripcion"));
            String nombre = ((String) datos.get("nombre"));

            if (!freelancerDao.registrarSolicitudHabilidad(id_usuario, descripcion, nombre)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se pudo registrar la solicitud, intentelo de nuevo\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Solicitud registrada con exito\"}");

            }

        } catch (Exception e) {
            System.out.println("ERROR AL REGISTRAR SOLICITUD DE HABILIDAD DESDE SERVLET: " +e.getMessage());
        }
        
    }

}
