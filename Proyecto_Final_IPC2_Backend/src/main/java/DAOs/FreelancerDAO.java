package DAOs;

import Modelos.*;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FreelancerDAO extends Usuario {

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

    public ArrayList<Habilidad> obtenerHabilidadesDeFreelancer(int id_usuario) {

        ArrayList<Habilidad> habilidades = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM habilidades JOIN freelancer_habilidad ON"
                    + " habilidades.id_habilidad = freelancer_habilidad.id_habilidad WHERE id_freelancer ="
                    + "(SELECT id_freelancer FROM freelancers WHERE id_usuario = ?)";
            //selecciona las habilidades vinculadas a un freelancer

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);

            ResultSet rs = stm.executeQuery();

            System.out.println("SQL: " + stm);

            while (rs.next()) {

                Habilidad nuevo = new Habilidad(rs.getInt("id_habilidad"), rs.getInt("id_categoria"),
                        rs.getString("nombre"), rs.getString("descripcion"), rs.getBoolean("estado"));

                habilidades.add(nuevo);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER HABILIDADES DE FREELANCER DESDE DAO" + e.getMessage());
        }

        return habilidades;
    }
    
    public boolean vincularFreelancerHabilidad(int id_usuario, int id_habilidad) {

        try (Connection conn = conexion.conectar()) {

            String sql = "INSERT INTO freelancer_habilidad (id_freelancer, id_habilidad) "
                    + "VALUES ((SELECT id_freelancer FROM freelancers WHERE id_usuario = ?),?)";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);
            stm.setInt(2, id_habilidad);
            
             System.out.println("SQL: "+stm);

            stm.executeUpdate();
            
           

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL VINCULAR HABILIDAD Y FREELANCER DESDE DAO " + e.getMessage());
        }

        return false;
    }
    
    public boolean desvincularFreelancerHabilidad(int id_usuario, int id_habilidad) {

        try (Connection conn = conexion.conectar()) {

            String sql = "DELETE FROM freelancer_habilidad WHERE id_freelancer = "
                    + "(SELECT id_freelancer FROM freelancers WHERE id_usuario = ?) AND id_habilidad = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);
            stm.setInt(2, id_habilidad);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL DESVINCULAR HABILIDAD Y FREELANCER DESDE DAO " + e.getMessage());
        }

        return false;
    }
    
     public boolean registrarSolicitudHabilidad(int id_usuario, String descripcion, String nombre) {

        try (Connection conn = conexion.conectar()) {

            String sql = "INSERT INTO solicitud_habilidad (id_freelancer, nombre, descripcion) SELECT id_freelancer, "
                    + "?, ? FROM freelancers WHERE id_usuario = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, nombre);
            stm.setString(2, descripcion);
            stm.setInt(3, id_usuario);//a partir del id_usuario encuentra el id_freelancer

            System.out.println("SQL: " + stm);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR SOLICITUD DE HABILIDAD DESDE DAO " + e.getMessage());
        }

        return false;
    }

}
