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

@WebServlet(name = "ClienteServlet", urlPatterns = {"/ClienteServlet"})
public class ClienteServlet extends HttpServlet {

    private ClienteDAO clienteDao = new ClienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");

        ArrayList<Usuario> clientes = clienteDao.obtenerClientes();

        if (clientes.isEmpty()) {
            //no hay clientes
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No hay clientes\"}");

        } else {

            String json = om.writeValueAsString(clientes);
            response.getWriter().print(json);
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

}
