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
        
        //Accion define si se está creando nuevo cleinte o si se va a editar
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

        

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
                case "cambiarComision":
                    cambiarComision(request, response, om);
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
            Comision comision =adminDao.obtenerComisionActual();

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


}
