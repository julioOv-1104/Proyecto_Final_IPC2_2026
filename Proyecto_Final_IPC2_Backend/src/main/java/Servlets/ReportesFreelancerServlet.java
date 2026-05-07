package Servlets;

import DAOs.ReportesFreelancerDAO;
import Modelos.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "ReportesFreelancerServlet", urlPatterns = {"/ReportesFreelancerServlet"})
public class ReportesFreelancerServlet extends HttpServlet {

    private ReportesFreelancerDAO reportesDao = new ReportesFreelancerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { // doGet para ver los reportes del administrador

        ObjectMapper om = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");

        Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

        String sFecha1 = (String) datos.get("fecha1");
        java.sql.Date fecha1 = java.sql.Date.valueOf(sFecha1);
        String sFecha2 = (String) datos.get("fecha2");
        java.sql.Date fecha2 = java.sql.Date.valueOf(sFecha2);//obtiene las fechas para los intervalos
        int id_usuario = ((Number) datos.get("id_usuario")).intValue();

        String accionRecibida = request.getParameter("accion");

        if (accionRecibida == null) {
            response.getWriter().print("{\"error\": \"Acción no especificada\"}");
            return;
        }

        try {
            switch (accionRecibida) {

                case "historialContratos":
                    obtenerHistorialContratos(request, response, om, fecha1, fecha2, id_usuario);
                    break;
                case "topCategorias":
                    obtenerTopCategorias(request, response, om, fecha1, fecha2, id_usuario);
                    break;
                case "propuestasEnviadas":
                    obtenerPropuestasEnviadas(request, response, om, fecha1, fecha2, id_usuario);
                    break;

                default:

                    response.getWriter().print("{\"error\": \"Acción no válida\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void obtenerHistorialContratos(HttpServletRequest request, HttpServletResponse response,
            ObjectMapper om, Date fecha1, Date fecha2, int id_usuario) {

        try {

            ArrayList<HistorialContratosFreelancer> reportes = new ArrayList<>();

            reportes = reportesDao.obtenerHistorialContratos(id_usuario, fecha1, fecha2);

            if (reportes.isEmpty()) {
                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se econtraron "
                        + "reportes de contratos en el intervalo\"}");

            } else {

                String json = om.writeValueAsString(reportes);
                response.getWriter().print(json);

            }

        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER HISTORIAL DE CONTRATOS DE FREELANCER DESDE SERVLET");
        }

    }

    private void obtenerTopCategorias(HttpServletRequest request, HttpServletResponse response, ObjectMapper om, Date fecha1, Date fecha2, int id_usuario) {

        try {

            ArrayList<TopCategorias> reportes = new ArrayList<>();

            reportes = reportesDao.obtenerTopCategoriasFreelancer(id_usuario);

            if (reportes.isEmpty()) {
                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se econtraron "
                        + "top 5 de categorias\"}");

            } else {

                String json = om.writeValueAsString(reportes);
                response.getWriter().print(json);

            }

        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER TOP 5 CATEGORIAS DE FREELANCER DESDE SERVLET");
        }

    }

    private void obtenerPropuestasEnviadas(HttpServletRequest request, HttpServletResponse response, ObjectMapper om, Date fecha1, Date fecha2, int id_usuario) {
      
        try {

            ArrayList<Propuesta> reportes = new ArrayList<>();

            reportes = reportesDao.obtenerPropuestasFreelancer(id_usuario, fecha1, fecha2);

            if (reportes.isEmpty()) {
                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se econtraron "
                        + "propuestas enviadas en el intervalo\"}");

            } else {

                String json = om.writeValueAsString(reportes);
                response.getWriter().print(json);

            }

        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER RPROPUESTAS ENVIADAS DE FREELANCER DESDE SERVLET");
        }
        
    }

}
