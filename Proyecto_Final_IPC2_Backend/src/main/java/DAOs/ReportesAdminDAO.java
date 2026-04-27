package DAOs;

import Modelos.*;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportesAdminDAO {

    ConexionDB conexion = new ConexionDB();

    public ArrayList<HistorialPorcentajes> obtenerHistorialPorcentajes() {

        ArrayList<HistorialPorcentajes> historial = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = " SELECT * FROM comisiones";
            PreparedStatement stm = conn.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                HistorialPorcentajes nuevo = new HistorialPorcentajes(rs.getDouble("porcentaje"),
                        rs.getDate("fecha_inicio"), rs.getDate("fecha_fin"));

                historial.add(nuevo);

            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER HISTORIAL DE PORCENTAJES DESDE DAO: " + e.getMessage());
        }
        return historial;
    }

    public ArrayList<TopFreelancers> obtenerTopFreelancers(Date fecha_inicio, Date fecha_fin) {//obitne los 5 freelancers con mas ingresos

        ArrayList<TopFreelancers> topFreelancers = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT \n"
                    + "    u.nombre_completo AS nombre_freelancer,\n"
                    + "    COUNT(c.id_contrato) AS cantidad_contratos,\n"
                    + "    \n"
                    + "    SUM(c.monto - ((c.monto * com.porcentaje) / 100)) AS total_freelancer,    \n"
                    + "\n"
                    + "    SUM((c.monto * com.porcentaje) / 100) AS total_comision\n"
                    + "\n"
                    + "FROM Freelancers f\n"
                    + "JOIN Usuarios u \n"
                    + "    ON f.id_usuario = u.id_usuario\n"
                    + "\n"
                    + "JOIN Propuestas p \n"
                    + "    ON f.id_freelancer = p.id_freelancer\n"
                    + "\n"
                    + "JOIN Contratos c \n"
                    + "    ON p.id_propuesta = c.id_propuesta\n"
                    + "\n"
                    + "JOIN Comisiones com \n"
                    + "    ON c.fecha_inicio BETWEEN com.fecha_inicio \n"
                    + "    AND IFNULL(com.fecha_fin, NOW())\n"
                    + "\n"
                    + "WHERE c.estado = 'COMPLETADO'\n"
                    + "\n"
                    + "AND c.fecha_inicio BETWEEN ? AND ?\n"
                    + "\n"
                    + "GROUP BY f.id_freelancer, u.nombre_completo\n"
                    + "\n"
                    + "ORDER BY total_freelancer DESC LIMIT 5";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setDate(1, fecha_inicio);
            stm.setDate(2, fecha_fin);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                TopFreelancers nuevo = new TopFreelancers(rs.getString("nombre_freelancer"),
                        rs.getInt("cantidad_contratos"), rs.getDouble("total_freelancer"), rs.getDouble("total_comision"));

                topFreelancers.add(nuevo);

            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER TOP 5 FREELANCERS DESDE DAO: " + e.getMessage());
        }
        return topFreelancers;
    }

    public ArrayList<TopCategorias> obtenerTopCategorias(Date fecha_inicio, Date fecha_fin) {

        ArrayList<TopCategorias> topCategorias = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT \n"
                    + "    cat.nombre AS nombre_categoria,\n"
                    + "    \n"
                    + "    COUNT(c.id_contrato) AS cantidad_contratos,\n"
                    + "    \n"
                    + "\n"
                    + "    SUM((c.monto * com.porcentaje) / 100) AS total_comisiones\n"
                    + "\n"
                    + "FROM Categorias cat\n"
                    + "\n"
                    + "JOIN Proyectos pr \n"
                    + "    ON cat.id_categoria = pr.id_categoria\n"
                    + "\n"
                    + "JOIN Propuestas p \n"
                    + "    ON pr.id_proyecto = p.id_proyecto\n"
                    + "\n"
                    + "JOIN Contratos c \n"
                    + "    ON p.id_propuesta = c.id_propuesta\n"
                    + "\n"
                    + "JOIN Comisiones com \n"
                    + "    ON c.fecha_inicio >= com.fecha_inicio\n"
                    + "    AND (com.fecha_fin IS NULL OR c.fecha_inicio <= com.fecha_fin)\n"
                    + "\n"
                    + "WHERE c.estado = 'COMPLETADO'\n"
                    + "\n"
                    + "\n"
                    + "AND c.fecha_inicio BETWEEN ? AND ?\n"
                    + "\n"
                    + "GROUP BY cat.id_categoria, cat.nombre\n"
                    + "\n"
                    + "ORDER BY cantidad_contratos DESC\n"
                    + "\n"
                    + "LIMIT 5";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setDate(1, fecha_inicio);
            stm.setDate(2, fecha_fin);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                TopCategorias nuevo = new TopCategorias(rs.getString("nombre_categoria"),
                        rs.getInt("cantidad_contratos"), rs.getInt("total_comision"));

                topCategorias.add(nuevo);

            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER TOP 5 CATEGORIAS DESDE DAO: " + e.getMessage());
        }
        return topCategorias;
    }

    public TotalIngresos obtenerTotalIngresos(Date fecha_inicio, Date fecha_fin) {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT \n"
                    + "    COUNT(c.id_contrato) AS cantidad_contratos,\n"
                    + "    IFNULL(SUM((c.monto * com.porcentaje) / 100), 0) AS total_comisiones\n"
                    + "FROM Contratos c\n"
                    + "JOIN Comisiones com \n"
                    + "    ON c.fecha_inicio >= com.fecha_inicio\n"
                    + "    AND (com.fecha_fin IS NULL OR c.fecha_inicio <= com.fecha_fin)\n"
                    + "WHERE c.estado = 'COMPLETADO'\n"
                    + "AND c.fecha_inicio BETWEEN ? AND ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setDate(1, fecha_inicio);
            stm.setDate(2, fecha_fin);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                TotalIngresos totalIngresos = new TotalIngresos();
                totalIngresos.setCantidad_contratos(rs.getInt("cantidad_contratos"));
                totalIngresos.setTotal_comisiones(rs.getDouble("total_comisiones"));
                
                return totalIngresos;

            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER TOTAL DE INGRESOS DESDE DAO: " + e.getMessage());
        }
        return null;
    }

}
