
package DAOs;

import Modelos.Usuario;
import java.util.Base64;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsuarioDAO {
    
     ConexionDB conexion = new ConexionDB();

    public Usuario registrarUsuario(Usuario nuevo) {

        try (Connection conn = conexion.conectar()) {

            String sql = "INSERT INTO usuarios (nombre_completo, username, password, correo, telefono, "
                    + "direccion, cui, fecha_nacimiento, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(sql);
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

            stm.executeUpdate();

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
            System.out.println("ERROR AL HACER LOGIN DESDE DAO: "+e.getMessage());
        }
        return user;
    }
    
}
