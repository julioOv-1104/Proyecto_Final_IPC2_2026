package Servlets;

import DAOs.*;
import Modelos.*;
import Services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "ClienteServlet", urlPatterns = {"/ClienteServlet"})
public class ClienteServlet extends HttpServlet {

    private ClienteDAO clienteDao = new ClienteDAO();
    private ClienteService servicio = new ClienteService();

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
                case "proyectosSinContrato":
                    obtenerProyectosSinContrato(request, response, om);
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
                    completarInformacion(request, response, om);
                    break;
                case "solicitar":
                    solicitarCategoria(request, response, om);
                    break;
                case "publicar":
                    publicarProyecto(request, response, om);
                    break;
                case "editar":
                    editarProyecto(request, response, om);
                    break;
                case "proyectosSinContrato":
                    obtenerProyectosSinContrato(request, response, om);
                    break;
                case "proyectosConEntregas":
                    obtenerProyectosConEntregas(request, response, om);
                    break;
                case "obtenerPropuestas":
                    obtenerPropuestas(request, response, om);
                    break;
                case "obtenerEntregas":
                    obtenerEntregas(request, response, om);
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
                case "recargar":
                    recargarSaldo(request, response, om);
                    break;
                case "cancelarProyecto":
                    cancelarProyecto(request, response, om);
                    break;
                case "aceptarPropuesta":
                    aceptarPropuesta(request, response, om);
                    break;
                case "rechazarPropuesta":
                    rechazarPropuesta(request, response, om);
                    break;
                case "rechazarEntrega":
                    rechazarEntrega(request, response, om);
                    break;

                default:

                    response.getWriter().print("{\"error\": \"Acción no válida\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void recargarSaldo(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            double recarga = ((Number) datos.get("recarga")).doubleValue();
            int id_usuario = ((Number) datos.get("id_usuario")).intValue();

            if (!clienteDao.recargarSaldo(id_usuario, recarga)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al recargar saldo\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Saldo cargado con exito\"}");

            }

        } catch (Exception e) {
        }

    }

    private void completarInformacion(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_usuario = ((Number) datos.get("id_usuario")).intValue();
            String descripcion = ((String) datos.get("descripcion"));
            String sector = ((String) datos.get("sector"));
            String sitio_web = ((String) datos.get("sitio_web"));

            if (!clienteDao.registrarInformacionInicial(id_usuario, descripcion, sector, sitio_web)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se pudo ingresar la informacion, intentelo de nuevo\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"informacion registrada, iniciando sesion\"}");

            }

        } catch (Exception e) {
        }

    }

    private void solicitarCategoria(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_usuario = ((Number) datos.get("id_usuario")).intValue();
            String descripcion = ((String) datos.get("descripcion"));
            String nombre = ((String) datos.get("nombre"));

            if (!clienteDao.registrarSolicitudCategoria(id_usuario, descripcion, nombre)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se pudo registrar la solicitud, intentelo de nuevo\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Solicitud registrada con exito\"}");

            }

        } catch (Exception e) {
        }
    }

    private void publicarProyecto(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_usuario = ((Number) datos.get("id_usuario")).intValue();
            int id_categoria = ((Number) datos.get("id_categoria")).intValue();
            String titulo = ((String) datos.get("titulo"));
            String descripcion = ((String) datos.get("descripcion"));
            double presupuesto = ((Number) datos.get("presupuesto_max")).doubleValue();
            String sFecha = (String) datos.get("fecha_limite");
            java.sql.Date fecha_limite = java.sql.Date.valueOf(sFecha);

            if (!clienteDao.publicarProyecto(id_usuario, id_categoria, titulo, descripcion, presupuesto, fecha_limite)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al publicar proyecto, intente de nuevo\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Proyecto publicado con exito\"}");

            }

        } catch (Exception e) {

        }

    }

    private void editarProyecto(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_proyecto = ((Number) datos.get("id_proyecto")).intValue();
            int id_categoria = ((Number) datos.get("id_categoria")).intValue();
            String titulo = ((String) datos.get("titulo"));
            String descripcion = ((String) datos.get("descripcion"));
            double presupuesto = ((Number) datos.get("presupuesto_max")).doubleValue();
            String sFecha = (String) datos.get("fecha_limite");
            java.sql.Date fecha_limite = java.sql.Date.valueOf(sFecha);

            if (!clienteDao.editarProyecto(id_proyecto, id_categoria, titulo, descripcion, presupuesto, fecha_limite)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al editar proyecto, intente de nuevo\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Proyecto editado con exito\"}");

            }

        } catch (Exception e) {
            System.out.println("ERROR AL EDITAR PROYECTO DESDE SERVLET: " + e.getMessage());
        }

    }

    private void obtenerProyectosSinContrato(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_usuario = ((Number) datos.get("id_usuario")).intValue();

            ArrayList<Proyecto> proyectos = clienteDao.obtenerProyectosSinContrato(id_usuario);

            if (proyectos == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al obtener los proyectos sin contrato\"}");

            } else {
                String json = om.writeValueAsString(proyectos);
                response.getWriter().print(json);
            }
        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER PROYECTOS SIN CONTRATO DESDE SERVLET");
        }

    }

    private void obtenerProyectosConEntregas(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_usuario = ((Number) datos.get("id_usuario")).intValue();

            ArrayList<Proyecto> proyectos = clienteDao.obtenerProyectosConEntrega(id_usuario);

            if (proyectos == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al obtener los proyectos con entregas\"}");

            } else {
                String json = om.writeValueAsString(proyectos);
                response.getWriter().print(json);
            }
        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER PROYECTOS CON ENTRREGAS DESDE SERVLET");
        }

    }

    private void cancelarProyecto(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_proyecto = ((Number) datos.get("id_proyecto")).intValue();

            if (!servicio.cancelarProyecto(id_proyecto)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al cancelar proyecto, intente de nuevo\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Proyecto cancelado con exito\"}");

            }

        } catch (Exception e) {
            System.out.println("ERROR AL CANCELAR PROYECTO DESDE SERVLET: " + e.getMessage());
        }

    }

    private void obtenerPropuestas(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_proyecto = ((Number) datos.get("id_proyecto")).intValue();

            ArrayList<Propuesta> propuestas = clienteDao.obtenerPropuestas(id_proyecto);

            if (propuestas == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al obtener las propuestas\"}");

            } else {
                String json = om.writeValueAsString(propuestas);
                response.getWriter().print(json);
            }
        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER PROPUESTAS DESDE SERVLET");
        }

    }

    private void aceptarPropuesta(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_propuesta = ((Number) datos.get("id_propuesta")).intValue();
            int id_usuario = ((Number) datos.get("id_usuario")).intValue();

            if (!servicio.aceptarPropuesta(id_propuesta, id_usuario)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No tiene suficiente saldo para aceptar la propuesta\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Propuesta aceptada con exito. Se inició un contrato\"}");

            }

        } catch (Exception e) {
            System.out.println("ERROR AL ACEPTAR PROPUESTA DESDE SERVLET: " + e.getMessage());
        }

    }

    private void rechazarPropuesta(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_propuesta = ((Number) datos.get("id_propuesta")).intValue();
            String motivo = ((String) datos.get("motivo"));

            if (!clienteDao.rechazarPropuesta(id_propuesta, motivo)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al rechazar la propuesta\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Propuesta rechazada\"}");

            }

        } catch (Exception e) {
            System.out.println("ERROR AL RECHAZAR PROPUESTA DESDE SERVLET: " + e.getMessage());
        }

    }

    private void obtenerEntregas(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_proyecto = ((Number) datos.get("id_proyecto")).intValue();

            ArrayList<Entrega> entregas = clienteDao.obtenerEntregas(id_proyecto);

            if (entregas == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al obtener las entregas\"}");

            } else {
                String json = om.writeValueAsString(entregas);
                response.getWriter().print(json);
            }
        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER ENTREGAS DESDE SERVLET");
        }

    }

    private void rechazarEntrega(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_proyecto = ((Number) datos.get("id_proyecto")).intValue();
            int id_entrega = ((Number) datos.get("id_entrega")).intValue();
            String motivo = ((String) datos.get("motivo"));

            if (!servicio.rechazarEntrega(id_entrega, motivo, id_proyecto)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al rechazar la entrega\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Entrega rechazada\"}");

            }

        } catch (Exception e) {
            System.out.println("ERROR AL RECHAZAR ENTREGA DESDE SERVLET: " + e.getMessage());
        }

    }

}
