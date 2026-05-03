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

@WebServlet(name = "ReportesClienteServlet", urlPatterns = {"/ReportesClienteServlet"})
public class ReportesClienteServlet extends HttpServlet {

    private ReportesClienteDAO reportesDao = new ReportesClienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");
        om.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

        int id_usuario = ((Number) datos.get("id_usuario")).intValue();

        ArrayList<Recarga> recargas = reportesDao.obtenerHistorialRecargas(id_usuario);

        if (recargas.isEmpty()) {
            //no hay recargas
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No hay recargas de saldo\"}");

        } else {

            String json = om.writeValueAsString(recargas);
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
        int id_usuario = ((Number) datos.get("id_usuario")).intValue();

        String accionRecibida = request.getParameter("accion");

        if (accionRecibida == null) {
            response.getWriter().print("{\"error\": \"Acción no especificada\"}");
            return;
        }

        try {
            switch (accionRecibida) {

                case "historialProyectos":
                    mostrarHistorialProyectos(request, response, om, fecha1, fecha2, id_usuario);
                    break;
                case "gastos":
                    mostrarGastos(request, response, om, fecha1, fecha2, id_usuario);
                    break;

                default:

                    response.getWriter().print("{\"error\": \"Acción no válida\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void mostrarHistorialProyectos(HttpServletRequest request, HttpServletResponse response,
            ObjectMapper om, Date fecha1, Date fecha2, int id_usuario) {

        try {

            ArrayList<HistorialProyectosCliente> reportes = new ArrayList<>();

            reportes = reportesDao.obtenerHistorialProyectosCliente(id_usuario, fecha1, fecha2);

            if (reportes.isEmpty()) {
                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se econtraron "
                        + "reportes de proyectos en el intervalo\"}");

            } else {

                String json = om.writeValueAsString(reportes);
                response.getWriter().print(json);

            }

        } catch (Exception e) {
        }

    }

    private void mostrarGastos(HttpServletRequest request, HttpServletResponse response, ObjectMapper om, Date fecha1, Date fecha2, int id_usuario) {

         try {

            ArrayList<GastoPorCategoria> reportes = new ArrayList<>();

            reportes = reportesDao.obtenerHistorialGastos(id_usuario, fecha1, fecha2);

            if (reportes.isEmpty()) {
                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se econtraron "
                        + "reportes de gastos por categoria en el intervalo\"}");

            } else {

                String json = om.writeValueAsString(reportes);
                response.getWriter().print(json);

            }

        } catch (Exception e) {
        }

    }

}
