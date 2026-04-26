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
        
        ArrayList<Usuario> freelancers = freelancerDao.obtenerFreelancers();

        if (freelancers.isEmpty()) {
            //no hay freelancers
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No hay freelancers\"}");

        } else {

            String json = om.writeValueAsString(freelancers);
            response.getWriter().print(json);
        }

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
        double tarifa_hora = Double.parseDouble(datos.get("tarifa_hora").toString());   

        if (!freelancerDao.registrarInformacionInicial(id_usuario, biografia, nivel_experiencia, tarifa_hora)) {

            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se pudo ingresar la informacion, intentelo de nuevo\"}");

        } else {
            response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"informacion registrada\"}");

        }
    }

}
