package DAOs;

import Modelos.*;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO {

    ConexionDB conexion = new ConexionDB();

    public boolean registrarInformacionInicial(int id_usuario, String descripcion, String sector, String sitio_web) {

        try (Connection conn = conexion.conectar()) {

            String sql = "INSERT INTO clientes (id_usuario, descripcion, sector, sitio_web)"
                    + " VALUES (?, ?, ?, ?)";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);
            stm.setString(2, descripcion);
            stm.setString(3, sector);//se encripta la contraseña para guardarla asi en la DB

            if (sitio_web.isBlank()) {
                stm.setString(4, null);
            } else {
                stm.setString(4, sitio_web);
            }

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR INFORMACION INICIAL DE CLIENTE DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public ArrayList<Usuario> obtenerClientes() {

        ArrayList<Usuario> clientes = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM usuarios WHERE rol = 2";//selecciona solo a los clientes
            PreparedStatement stm = conn.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Usuario nuevo = new Usuario(rs.getString("nombre_completo"), rs.getString("username"),
                        rs.getString("correo"), rs.getString("telefono"), rs.getString("direccion"),
                        rs.getString("cui"), rs.getDate("fecha_nacimiento"), rs.getBoolean("estado"));

                clientes.add(nuevo);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER CLIENTES DESDE DAO" + e.getMessage());
        }

        return clientes;
    }

}
