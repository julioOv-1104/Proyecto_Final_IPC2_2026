
package DAOs;

import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FreelancerDAO {
    
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
    
}
