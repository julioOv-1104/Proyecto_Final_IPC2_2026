
package DAOs;

import Modelos.Usuario;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FreelancerDAO extends Usuario{
    
    ConexionDB conexion = new ConexionDB();
    
    public boolean registrarInformacionInicial(int id_usuario, String biografia, String nivel_experiencia, double tarifa_hora) {

        try (Connection conn = conexion.conectar()) {

            String sql = "INSERT INTO freelancers (id_usuario, biografia, nivel_experiencia, tarifa_hora)"
                    + " VALUES (?, ?, ?, ?)";
            
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);
            stm.setString(2, biografia);
            stm.setString(3, nivel_experiencia);  
            stm.setDouble(4, tarifa_hora);
            


            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR INFORMACION INICIAL DE FREELANCER DESDE DAO " + e.getMessage());
        }

        return false;
    }
    
    
    public ArrayList<Usuario> obtenerFreelancers() {

        ArrayList<Usuario> freelancers = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM usuarios WHERE rol = 3";//selecciona solo a los freelancers
            PreparedStatement stm = conn.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Usuario nuevo = new Usuario(rs.getString("nombre_completo"), rs.getString("username"),
                        rs.getString("correo"), rs.getString("telefono"), rs.getString("direccion"),
                        rs.getString("cui"), rs.getDate("fecha_nacimiento"), rs.getBoolean("estado"));

                freelancers.add(nuevo);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER FREELANCERS DESDE DAO" + e.getMessage());
        }

        return freelancers;
    }
    
}
