
package DAOs;

import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            }else{
            stm.setString(4, sitio_web);
            }


            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR INFORMACION INICIAL DE CLIENTE DESDE DAO " + e.getMessage());
        }

        return false;
    }
    
}
