package Servlets;

import DAOs.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "FreelancerServlet", urlPatterns = {"/FreelancerServlet"})
public class FreelancerServlet extends HttpServlet {

    private FreelancerDAO freelancerDao = new FreelancerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        Map<String, Object> datos = objectMapper.readValue(request.getInputStream(), Map.class);

        int id_usuario = ((Number) datos.get("id_usuario")).intValue();
        String biografia = ((String) datos.get("biografia"));
        String nivel_experiencia = ((String) datos.get("nivel_experiencia"));
        double tarifa_hora = ((Number) datos.get("sitio_web")).doubleValue();   

        if (!freelancerDao.registrarInformacionInicial(id_usuario, biografia, nivel_experiencia, tarifa_hora)) {

            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se pudo ingresar la informacion, intentelo de nuevo\"}");

        } else {
            response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"informacion registrada\"}");

        }
    }

}
