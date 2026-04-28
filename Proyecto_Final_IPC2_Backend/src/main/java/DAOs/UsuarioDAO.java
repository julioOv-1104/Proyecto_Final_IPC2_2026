package DAOs;

import Modelos.Usuario;
import java.util.Base64;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO {

    ConexionDB conexion = new ConexionDB();

    public Usuario registrarUsuario(Usuario nuevo) {

        int idGenerado = 0;

        try (Connection conn = conexion.conectar()) {

            String sql = "INSERT INTO usuarios (nombre_completo, username, password, correo, telefono, "
                    + "direccion, cui, fecha_nacimiento, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, nuevo.getNombre_completo());
            stm.setString(2, nuevo.getUsername());

            String contraEncriptada = Base64.getEncoder().encodeToString(nuevo.getPassword().getBytes());
            stm.setString(3, contraEncriptada);//se encripta la contraseña para guardarla asi en la DB

            stm.setString(4, nuevo.getCorreo());
            stm.setString(5, nuevo.getTelefono());
            stm.setString(6, nuevo.getDireccion());
            stm.setString(7, nuevo.getCui());
            stm.setDate(8, nuevo.getFecha_nacimiento());
            stm.setInt(9, nuevo.getRol());

            int filasAfectadas = stm.executeUpdate();

            if (filasAfectadas > 0) {
                // se obtiene el id del usuario que se acaba de crear
                try (ResultSet rs = stm.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                        System.out.println("ID del nuevo registro: " + idGenerado);
                        nuevo.setId_usuario(idGenerado);
                    }
                }
            }

            return nuevo;

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR USUARIO DESDE DAO " + e.getMessage());
        }

        return null;
    }

    public Usuario login(String username, String password) {

        String nombre = username.trim();//le quita los espacios sobrantes
        String contra = Base64.getEncoder().encodeToString(password.trim().getBytes());

        Usuario user = null;

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, nombre);
            stm.setString(2, contra);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                user = new Usuario(rs.getInt("id_usuario"), nombre, rs.getInt("rol"), rs.getBoolean("estado"),
                        rs.getDouble("saldo"));
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL HACER LOGIN DESDE DAO: " + e.getMessage());
        }
        return user;
    }

    public boolean comprobarUsuarioCompleto(String tabla, String username) {

        //comprueba que el usuario ya completo su informacion inicial
        try (Connection conn = conexion.conectar()) {

            String sql = " SELECT 1 FROM " + tabla + " JOIN usuarios ON " + tabla + ".id_usuario = usuarios.id_usuario "
                    + "WHERE usuarios.username = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL COMPROBAR USUARIO COMPLETO DESDE DAO: " + e.getMessage());
        }
        return false;
    }
    
    
    public boolean registrarAdmin(int id_usuario) {

        try (Connection conn = conexion.conectar()) {

            String sql = "INSERT INTO administradores (id_usuario) VALUES (?)";
            
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR ADMINSTRADOR DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public boolean activarDesactivarUsuario(String username) {


        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE usuarios SET estado = NOT estado WHERE username = ?";
            PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, username);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL ACTIVAR/DESACTIVAR USUARIO DESDE DAO " + e.getMessage());
        }

        return false;
    }
    
     public double obtenerSaldo(int id_usuario) {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT saldo FROM usuarios WHERE id_usuario = ?";//selecciona solo a los clientes
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getDouble("saldo");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER SALDO DE USUARIOS DESDE DAO" + e.getMessage());
        }

        return -1;
    }
    
}
