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

@WebServlet(name = "AdminServlet", urlPatterns = {"/AdminServlet"})
public class AdminServlet extends HttpServlet {

    private AdminDAO adminDao = new AdminDAO();

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
                case "comisionActual":
                    obtenerComisionActual(request, response, om);
                    break;
                case "categorias":
                    obtenerCategorias(request, response, om);
                    break;
                case "habilidades":
                    obtenerHabilidades(request, response, om);
                    break;
                case "solicitudesHabilidades":
                    obtenerSolicitudesHabilidades(request, response, om);
                    break;
                case "solicitudesCategorias":
                    obtenerSolicitudesCategorias(request, response, om);
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
            throws ServletException, IOException {//doPost para poder hacer el login de los usuarios

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
                case "editarCategoria":
                    editarCategoria(request, response, om);
                    break;
                case "editarHabilidad":
                    editarHabilidad(request, response, om);
                    break;
                case "activarDesactivarCategoria":
                    activarDesactivarCategoria(request, response, om);
                    break;
                case "activarDesactivarHabilidad":
                    activarDesactivarHabilidad(request, response, om);
                    break;

                default:

                    response.getWriter().print("{\"error\": \"Acción no válida\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
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
                case "cambiarComision":
                    cambiarComision(request, response, om);
                    break;
                case "crearCategoria":
                    crearCategoria(request, response, om);
                    break;
                case "crearHabilidad":
                    crearHabilidad(request, response, om);
                    break;
                case "aceptarSolicitudCategoria":
                    aceptarRechazarCategoria(request, response, om);
                    break;
                case "aceptarSolicitudHabilidad":
                    aceptarRechazarHabilidad(request, response, om);
                    break;

                default:

                    response.getWriter().print("{\"error\": \"Acción no válida\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void obtenerComisionActual(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {
            Comision comision = adminDao.obtenerComisionActual();

            if (comision == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al obtener la comision\"}");

            } else {
                String json = om.writeValueAsString(comision);
                response.getWriter().print(json);
            }
        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER COMISION DESDE SERVLET");
        }

    }

    private void cambiarComision(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            double porcentaje = ((Number) datos.get("porcentaje")).doubleValue();

            if (!adminDao.registrarNuevaComision(porcentaje)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al cambiar porcentaje de comision\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Porcentaje de comision cambiado con exiton\"}");

            }

        } catch (Exception e) {
        }

    }

    private void crearCategoria(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String nombre = ((String) datos.get("nombre"));
            String descripcion = ((String) datos.get("descripcion"));

            if (!adminDao.registrarCategoria(nombre, descripcion)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al crear categoria\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Categoria creada con exito\"}");

            }

        } catch (Exception e) {
        }

    }

    private void crearHabilidad(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_categoria = ((Number) datos.get("id_categoria")).intValue();
            String nombre = ((String) datos.get("nombre"));
            String descripcion = ((String) datos.get("descripcion"));

            if (!adminDao.registrarHabilidad(id_categoria, nombre, descripcion)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al crear habilidad\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Habilidad creada con exito\"}");

            }

        } catch (Exception e) {
        }

    }

    private void obtenerCategorias(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {
            ArrayList<Categoria> categorias = adminDao.obtenerCategorias();

            if (categorias == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al obtener las categorias\"}");

            } else {
                String json = om.writeValueAsString(categorias);
                response.getWriter().print(json);
            }
        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER CATEGORIAS DESDE SERVLET");
        }

    }

    private void obtenerHabilidades(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {
            ArrayList<Habilidad> habilidades = adminDao.obtenerHabilidades();

            if (habilidades == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al obtener las habilidades\"}");

            } else {
                String json = om.writeValueAsString(habilidades);
                response.getWriter().print(json);
            }
        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER HABILIDADES DESDE SERVLET");
        }

    }

    private void editarCategoria(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {
            Categoria entrante = om.readValue(request.getInputStream(), Categoria.class);
            Categoria editado = adminDao.editarCategoria(entrante);

            if (editado == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al editar categoria\"}");
                System.out.println("ERROR AL EDITAR CATEGORIA DESDE SERVLET");

            } else {

                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Categoria editada con exito\"}");

            }
        } catch (Exception e) {
        }

    }

    private void editarHabilidad(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {
            Habilidad entrante = om.readValue(request.getInputStream(), Habilidad.class);
            Habilidad editado = adminDao.editarHabilidad(entrante);

            if (editado == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al editar habilidad\"}");
                System.out.println("ERROR AL EDITAR HABILIDAD DESDE SERVLET");

            } else {

                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Habilidad editada con exito\"}");

            }
        } catch (Exception e) {
        }

    }

    private void activarDesactivarCategoria(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_categoria = ((Number) datos.get("id_categoria")).intValue();

            if (!adminDao.activarDesactivarCategoria(id_categoria)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al activar/desactivar categoria\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Categoria activada/desactivada con exito\"}");

            }

        } catch (Exception e) {
        }
    }

    private void activarDesactivarHabilidad(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_habilidad = ((Number) datos.get("id_habilidad")).intValue();

            if (!adminDao.activarDesactivarHabilidad(id_habilidad)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al activar/desactivar habilidad\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Habilidad activada/desactivada con exito\"}");

            }

        } catch (Exception e) {
        }
    }

    private void obtenerSolicitudesHabilidades(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {
            ArrayList<Solicitud> solicitudesHabilidades = adminDao.obtenerSolicitudes("solicitud_habilidad");

            if (solicitudesHabilidades == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al obtener las solicitudes de habilidades\"}");

            } else {
                String json = om.writeValueAsString(solicitudesHabilidades);
                response.getWriter().print(json);
            }
        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER SOLICITUD DE HABILIDADES DESDE SERVLET");
        }

    }

    private void obtenerSolicitudesCategorias(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {
            ArrayList<Solicitud> solicitudesCategorias = adminDao.obtenerSolicitudes("solicitud_categoria");

            if (solicitudesCategorias == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al obtener las solicitudes de categorias\"}");

            } else {
                String json = om.writeValueAsString(solicitudesCategorias);
                response.getWriter().print(json);
            }
        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER SOLICITUD DE CATEGORIAS DESDE SERVLET");
        }

    }

    private void aceptarRechazarCategoria(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_solicitud = ((Number) datos.get("id_solicitud")).intValue();
            String estado = ((String) datos.get("estado"));
            String nombre = ((String) datos.get("nombre"));
            String descripcion = ((String) datos.get("descripcion"));

            if (!adminDao.aceptarRechazarSolicitudCategoria(id_solicitud, estado, nombre, descripcion)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al "+estado+" solcitud de categoria\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Solicitud de categoria "+estado+" con exito\"}");

            }

        } catch (Exception e) {
        }

    }

    private void aceptarRechazarHabilidad(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {
     
        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_solicitud = ((Number) datos.get("id_solicitud")).intValue();
            String estado = ((String) datos.get("estado"));
            String nombre = ((String) datos.get("nombre"));
            String descripcion = ((String) datos.get("descripcion"));

            if (!adminDao.aceptarRechazarSolicitudHabilidad(id_solicitud, estado, nombre, descripcion)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al "+estado+" solcitud de habilidad\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Solicitud de habilidad "+estado+" con exito\"}");

            }

        } catch (Exception e) {
        }
        
    }

}
