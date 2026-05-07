package DAOs;

import Modelos.*;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportesFreelancerDAO {

    ConexionDB conexion = new ConexionDB();

    public ArrayList<HistorialContratosFreelancer> obtenerHistorialContratos(int id_usuario, Date fecha_inicio,
            Date fecha_fin) {

        ArrayList<HistorialContratosFreelancer> historial = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT \n"
                    + "    u_cliente.nombre_completo AS cliente,\n"
                    + "\n"
                    + "    pr.titulo AS proyecto,\n"
                    + "\n"
                    + "    (c.monto - ((c.monto * com.porcentaje) / 100)) AS monto_recibido,\n"
                    + "\n"
                    + "    cal.puntuacion,\n"
                    + "    cal.comentario,\n"
                    + "\n"
                    + "    c.fecha_fin\n"
                    + "\n"
                    + "FROM Freelancers f\n"
                    + "\n"
                    + "JOIN Propuestas p \n"
                    + "    ON f.id_freelancer = p.id_freelancer\n"
                    + "\n"
                    + "JOIN Contratos c \n"
                    + "    ON p.id_propuesta = c.id_propuesta\n"
                    + "\n"
                    + "JOIN Proyectos pr \n"
                    + "    ON p.id_proyecto = pr.id_proyecto\n"
                    + "\n"
                    + "JOIN Clientes cl \n"
                    + "    ON pr.id_cliente = cl.id_cliente\n"
                    + "\n"
                    + "JOIN Usuarios u_cliente \n"
                    + "    ON cl.id_usuario = u_cliente.id_usuario\n"
                    + "\n"
                    + "LEFT JOIN Calificaciones cal \n"
                    + "    ON c.id_contrato = cal.id_contrato\n"
                    + "\n"
                    + "JOIN Comisiones com \n"
                    + "    ON c.fecha_inicio >= com.fecha_inicio\n"
                    + "    AND (com.fecha_fin IS NULL OR c.fecha_inicio <= com.fecha_fin)\n"
                    + "\n"
                    + "WHERE f.id_freelancer = (SELECT id_freelancer FROM freelancers WHERE id_usuario = ?)   \n"
                    + "\n"
                    + "AND c.estado = 'COMPLETADO'\n"
                    + "\n"
                    + "\n"
                    + "AND c.fecha_fin BETWEEN ? AND ?\n"
                    + "\n"
                    + "ORDER BY c.fecha_fin DESC";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);
            stm.setDate(2, fecha_inicio);
            stm.setDate(3, fecha_fin);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                HistorialContratosFreelancer nuevo = new HistorialContratosFreelancer(
                        rs.getString("cliente"), rs.getString("proyecto"), rs.getDouble("monto_recibido"),
                        rs.getDouble("puntuacion"), rs.getString("comentario"), rs.getDate("fecha_fin"));

                historial.add(nuevo);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER EL HISTORIAL DE CONTRATOS DESDE DAO" + e.getMessage());
        }

        return historial;
    }

    public ArrayList<TopCategorias> obtenerTopCategoriasFreelancer(int id_usuario) {

        ArrayList<TopCategorias> historial = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT \n"
                    + "    cat.nombre AS nombre_categoria,\n"
                    + "\n"
                    + "    COUNT(c.id_contrato) AS cantidad_contratos,\n"
                    + "\n"
                    + "    SUM(\n"
                    + "        c.monto - ((c.monto * com.porcentaje) / 100)\n"
                    + "    ) AS total_ingresos\n"
                    + "\n"
                    + "FROM Freelancers f\n"
                    + "\n"
                    + "JOIN Propuestas p \n"
                    + "    ON f.id_freelancer = p.id_freelancer\n"
                    + "\n"
                    + "JOIN Contratos c \n"
                    + "    ON p.id_propuesta = c.id_propuesta\n"
                    + "\n"
                    + "JOIN Proyectos pr \n"
                    + "    ON p.id_proyecto = pr.id_proyecto\n"
                    + "\n"
                    + "JOIN Categorias cat \n"
                    + "    ON pr.id_categoria = cat.id_categoria\n"
                    + "\n"
                    + "JOIN Comisiones com \n"
                    + "    ON c.fecha_inicio >= com.fecha_inicio\n"
                    + "    AND (com.fecha_fin IS NULL OR c.fecha_inicio <= com.fecha_fin)\n"
                    + "\n"
                    + "WHERE f.id_freelancer = (SELECT id_freelancer FROM freelancers WHERE id_usuario = ?) \n"
                    + "\n"
                    + "AND c.estado = 'COMPLETADO'\n"
                    + "\n"
                    + "GROUP BY cat.id_categoria, cat.nombre\n"
                    + "\n"
                    + "ORDER BY total_ingresos DESC\n"
                    + "\n"
                    + "LIMIT 5";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                TopCategorias nuevo = new TopCategorias(
                        rs.getString("nombre_categoria"), rs.getInt("cantidad_contratos"),
                        rs.getDouble("total_ingresos"));

                historial.add(nuevo);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER TOP 5 CATEGORIAS DE FREELANCER DESDE DAO" + e.getMessage());
        }

        return historial;
    }

    public ArrayList<Propuesta> obtenerPropuestasFreelancer(int id_usuario, Date fecha_inicio,
            Date fecha_fin) {

        ArrayList<Propuesta> historial = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT \n"
                    + "    pr.titulo AS proyecto,\n"
                    + "\n"
                    + "    p.monto AS monto,\n"
                    + "\n"
                    + "    p.estado,\n"
                    + "\n"
                    + "    p.fecha\n"
                    + "\n"
                    + "FROM Propuestas p\n"
                    + "\n"
                    + "JOIN Proyectos pr \n"
                    + "    ON p.id_proyecto = pr.id_proyecto\n"
                    + "\n"
                    + "WHERE p.id_freelancer = (SELECT id_freelancer FROM freelancers WHERE id_usuario = ?) \n"
                    + "\n"
                    + "AND p.fecha BETWEEN ? AND ?\n"
                    + "\n"
                    + "ORDER BY p.fecha DESC;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);
            stm.setDate(2, fecha_inicio);
            stm.setDate(3, fecha_fin);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Propuesta nuevo = new Propuesta(rs.getDouble("monto"), rs.getString("estado"), rs.getDate("fecha"),
                rs.getString("proyecto"));

                historial.add(nuevo);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER PROPUESTAS ENVIADAS DE FREELANCER DESDE DAO" + e.getMessage());
        }

        return historial;
    }

}
