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

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        Usuario entrante = objectMapper.readValue(request.getInputStream(), Usuario.class);

         Usuario usuario = usuarioDao.login(entrante.getUsername(), entrante.getPassword());

        if (usuario == null) {

            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Credenciales incorrectas\"}");

        } else {
            String json = objectMapper.writeValueAsString(usuario);
            response.getWriter().print(json);
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

}
