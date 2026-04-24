
package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

  private Connection conn;
    private String url = "jdbc:mysql://localhost:3306/connect_work";
    private String usuario = "root";
    private String contrasenna = "julioadmin";
    
    public Connection conectar(){
    
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, usuario, contrasenna);
            System.out.println("Conexion exitosa");
            return conn;
            
        } catch (SQLException e) {
            System.out.println("Error al conectar "+e.getMessage());
            return null;
        }catch(ClassNotFoundException e){
            System.out.println("ERROR EN LA CONEXION CON CONNECT_WORK"+e.getMessage());
            return null;
        }
    }
    

    
}


