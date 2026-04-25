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

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        Map<String, Object> datos = objectMapper.readValue(request.getInputStream(), Map.class);

        int id_usuario = ((Number) datos.get("id_usuario")).intValue();
        String descripcion = ((String) datos.get("descripcion"));
        String sector = ((String) datos.get("sector"));
        String sitio_web = ((String) datos.get("sitio_web"));   

        if (!clienteDao.registrarInformacionInicial(id_usuario, descripcion, sector, sitio_web)) {

            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se pudo ingresar la informacion, intentelo de nuevo\"}");

        } else {
            response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"informacion registrada, iniciando sesion\"}");

        }
    }

}
