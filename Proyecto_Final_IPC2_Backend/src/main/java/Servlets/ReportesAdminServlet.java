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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "ReportesAdminServlet", urlPatterns = {"/ReportesAdminServlet"})
public class ReportesAdminServlet extends HttpServlet {

    private ReportesAdminDAO reportesDao = new ReportesAdminDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");

        ArrayList<HistorialPorcentajes> porcentajes = reportesDao.obtenerHistorialPorcentajes();

        if (porcentajes.isEmpty()) {
            //no hay porcentajes
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No hay porcentajes, ocurrio un error\"}");

        } else {

            String json = om.writeValueAsString(porcentajes);
            response.getWriter().print(json);
        }

    }

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

        String accionRecibida = request.getParameter("accion");

        if (accionRecibida == null) {
            response.getWriter().print("{\"error\": \"Acción no especificada\"}");
            return;
        }

        try {
            switch (accionRecibida) {

                case "topFreelancers":
                    mostrarTopFreelancers(request, response, om, fecha1, fecha2);
                    break;
                case "topCategorias":
                    mostrarTopCategorias(request, response, om, fecha1, fecha2);
                    break;

                case "totalIngresos":
                    mostrarTotalIngresos(request, response, om, fecha1, fecha2);
                    break;

                default:

                    response.getWriter().print("{\"error\": \"Acción no válida\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void mostrarTopFreelancers(HttpServletRequest request, HttpServletResponse response, ObjectMapper om,
            Date fecha1, Date fecha2) throws IOException {

        ArrayList<TopFreelancers> reportes = new ArrayList<>();

        reportes = reportesDao.obtenerTopFreelancers(fecha1, fecha2);

        if (reportes.isEmpty()) {
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se econtraron "
                    + "reportes de freelancers en el intervalo\"}");

        } else {

            String json = om.writeValueAsString(reportes);
            response.getWriter().print(json);

        }

    }

    private void mostrarTopCategorias(HttpServletRequest request, HttpServletResponse response, ObjectMapper om, Date fecha1, Date fecha2)
            throws IOException {

        ArrayList<TopCategorias> reportes = new ArrayList<>();

        reportes = reportesDao.obtenerTopCategorias(fecha1, fecha2);

        if (reportes.isEmpty()) {
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se econtraron "
                    + "reportes de categorias en el intervalo\"}");

        } else {

            String json = om.writeValueAsString(reportes);
            response.getWriter().print(json);

        }

    }

    private void mostrarTotalIngresos(HttpServletRequest request, HttpServletResponse response, ObjectMapper om, Date fecha1, Date fecha2)
            throws IOException {

        TotalIngresos reportes = new TotalIngresos();

        reportes = reportesDao.obtenerTotalIngresos(fecha1, fecha2);

        if (reportes == null) {
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se econtraron "
                    + "reportes de total de ingresos en el intervalo\"}");

        } else {

            String json = om.writeValueAsString(reportes);
            response.getWriter().print(json);

        }

    }

}
