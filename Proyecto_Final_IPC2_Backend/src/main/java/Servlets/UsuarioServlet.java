package Servlets;

import DAOs.UsuarioDAO;
import Modelos.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDao = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {//doPost para poder hacer el login de los usuarios

        ObjectMapper om = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        //Accion define si se está creando nuevo cleinte o si se va a editar
        String accionRecibida = request.getParameter("accion");

        if (accionRecibida == null) {
            response.getWriter().print("{\"error\": \"Acción no especificada\"}");
            return;
        }

        try {
            switch (accionRecibida) {
                case "login":
                    login(request, response, om);
                    break;

                case "comprobar":
                    comprobarUusarioCompleto(request, response, om);
                    break;
                case "admin":
                    registrarAdmin(request, response, om);
                    break;
                case "estado":
                    activarDesactivar(request, response, om);
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

        Usuario entrante = om.readValue(request.getInputStream(), Usuario.class);

        Usuario nuevo = usuarioDao.registrarUsuario(entrante);

        if (nuevo == null) {

            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Ocurrio un error al registrar usuario\"}");
            System.out.println("ERROR AL REGISTRAR USUARIO DESDE SERVLET");

        } else {

            String json = om.writeValueAsString(nuevo);
            response.getWriter().print(json);
        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response,
            ObjectMapper om) {

        try {
            Usuario entrante = om.readValue(request.getInputStream(), Usuario.class);

            Usuario usuario = usuarioDao.login(entrante.getUsername(), entrante.getPassword());

            if (usuario == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Credenciales incorrectas\"}");

            } else {
                String json = om.writeValueAsString(usuario);
                response.getWriter().print(json);
            }
        } catch (Exception e) {
        }
    }

    private void comprobarUusarioCompleto(HttpServletRequest request, HttpServletResponse response,
            ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String username = ((String) datos.get("username"));
            String tabla = ((String) datos.get("tabla"));

            if (!usuarioDao.comprobarUsuarioCompleto(tabla, username)) {

                response.getWriter().print("{\"status\":\"incompleto\",\"mensaje\":\"Debe completar su informacion\"}");

            } else {
                response.getWriter().print("{\"status\":\"completo\",\"mensaje\":\"Puede iniciar sesion\"}");

            }

        } catch (Exception e) {
        }

    }

    private void registrarAdmin(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_usuario = ((Number) datos.get("id_usuario")).intValue();

            if (!usuarioDao.registrarAdmin(id_usuario)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al vincual usuario a  administradores\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Administrador registrado con exito\"}");

            }

        } catch (Exception e) {
        }

    }

    private void activarDesactivar(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {
       
         try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String username = ((String) datos.get("username"));

            if (!usuarioDao.activarDesactivarUsuario(username)) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al activar/desactivar usuario\"}");

            } else {
                response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Usuario activado/desactivado con exito\"}");

            }

        } catch (Exception e) {
        }
    }

}
