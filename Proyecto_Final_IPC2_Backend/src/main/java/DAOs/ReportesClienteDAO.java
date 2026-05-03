package DAOs;

import Modelos.*;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReportesClienteDAO {

    ConexionDB conexion = new ConexionDB();

    public ArrayList<HistorialProyectosCliente> obtenerHistorialProyectosCliente(int id_usuario, Date fecha_inicio,
            Date fecha_fin) {

        ArrayList<HistorialProyectosCliente> historial = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT \n"
                    + "    pr.id_proyecto,\n"
                    + "    pr.titulo,\n"
                    + "    pr.estado AS estado_proyecto,\n"
                    + "    pr.fecha_creacion,\n"
                    + "\n"
                    + "    c.monto,\n"
                    + "\n"
                    + "    u.nombre_completo AS freelancer\n"
                    + "\n"
                    + "FROM Proyectos pr\n"
                    + "\n"
                    + "LEFT JOIN Propuestas p \n"
                    + "    ON pr.id_proyecto = p.id_proyecto\n"
                    + "\n"
                    + "LEFT JOIN Contratos c \n"
                    + "    ON p.id_propuesta = c.id_propuesta\n"
                    + "\n"
                    + "LEFT JOIN Freelancers f \n"
                    + "    ON p.id_freelancer = f.id_freelancer\n"
                    + "\n"
                    + "LEFT JOIN Usuarios u \n"
                    + "    ON f.id_usuario = u.id_usuario\n"
                    + "\n"
                    + "WHERE pr.id_cliente = (SELECT id_cliente FROM clientes WHERE id_usuario = ?)\n"
                    + "\n"
                    + "AND pr.fecha_creacion BETWEEN ? AND ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);
            stm.setDate(2, fecha_inicio);
            stm.setDate(3, fecha_fin);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                HistorialProyectosCliente nuevo = new HistorialProyectosCliente(rs.getString("titulo"),
                        rs.getString("estado_proyecto"), rs.getString("freelancer"), rs.getDouble("monto"),
                        rs.getDate("fecha_creacion"));

                historial.add(nuevo);

            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER HISTORIAL DE PROYECTOS DE CLIENTE DESDE DAO: " + e.getMessage());
        }

        return historial;
    }

    public ArrayList<Recarga> obtenerHistorialRecargas(int id_usuario) {

        ArrayList<Recarga> historial = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM recargas WHERE id_cliente = "
                    + "(SELECT id_cliente FROM clientes WHERE id_usuario = ?)";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Recarga nuevo = new Recarga(rs.getDouble("monto"), rs.getObject("fecha", LocalDateTime.class));

                historial.add(nuevo);

            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER HISTORIAL DE RECARGAS DE CLIENTE DESDE DAO: " + e.getMessage());
        }

        return historial;
    }

    public ArrayList<GastoPorCategoria> obtenerHistorialGastos(int id_usuario, Date fecha_inicio,
            Date fecha_fin) {

        ArrayList<GastoPorCategoria> historial = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT \n"
                    + "    cat.nombre AS categoria,\n"
                    + "\n"
                    + "    COUNT(c.id_contrato) AS cantidad_contratos,\n"
                    + "\n"
                    + "    SUM(c.monto) AS total_gastado\n"
                    + "\n"
                    + "FROM Proyectos pr\n"
                    + "\n"
                    + "JOIN Categorias cat \n"
                    + "    ON pr.id_categoria = cat.id_categoria\n"
                    + "\n"
                    + "JOIN Propuestas p \n"
                    + "    ON pr.id_proyecto = p.id_proyecto\n"
                    + "\n"
                    + "JOIN Contratos c \n"
                    + "    ON p.id_propuesta = c.id_propuesta\n"
                    + "\n"
                    + "WHERE pr.id_cliente = (SELECT id_cliente FROM clientes WHERE id_usuario = ?) \n"
                    + "\n"
                    + "AND c.estado = 'COMPLETADO'\n"
                    + "\n"
                    + "AND c.fecha_inicio BETWEEN ? AND ?\n"
                    + "\n"
                    + "GROUP BY cat.id_categoria, cat.nombre\n"
                    + "\n"
                    + "ORDER BY total_gastado DESC;";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);
            stm.setDate(2, fecha_inicio);
            stm.setDate(3, fecha_fin);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                GastoPorCategoria nuevo = new GastoPorCategoria(rs.getString("categoria"), rs.getInt("cantidad_contratos"),
                        rs.getDouble("total_gastado"));

                historial.add(nuevo);

            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER HISTORIAL DE GASTOS POR CATEGORIA CLIENTE DESDE DAO: " + e.getMessage());
        }

        return historial;
    }

}
