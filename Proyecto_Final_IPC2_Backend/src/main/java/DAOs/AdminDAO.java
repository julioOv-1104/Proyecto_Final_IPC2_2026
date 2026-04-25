package DAOs;

import Modelos.*;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDAO {

    ConexionDB conexion = new ConexionDB();

    public boolean registrarNuevaComision(double porcentaje) {

        if (desactivarComisionVieja()) {

            try (Connection conn = conexion.conectar()) {

                String sql = "INSERT INTO comisiones (porcentaje) VALUES (?)";

                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setDouble(1, porcentaje);

                stm.executeUpdate();

                return true;

            } catch (SQLException e) {
                System.out.println("ERROR AL REGISTRAR NUEVA COMISION DESDE DAO " + e.getMessage());
            }
        }

        return false;
    }

    private boolean desactivarComisionVieja() {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE comisiones SET fecha_fin = CURRENT_DATE, estado = false WHERE estado = true";

            PreparedStatement stm = conn.prepareStatement(sql);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR NUEVA COMISION DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public Comision obtenerComisionActual() {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM comisiones WHERE estado = true";//selecciona solo la comision activa
            PreparedStatement stm = conn.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Comision nueva = new Comision(rs.getDouble("porcentaje"), rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin"), rs.getBoolean("estado"));

                return nueva;
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER COMISION ACTUAL DESDE DAO" + e.getMessage());
        }

        return null;
    }

}
