package DAOs;

import Modelos.*;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public boolean registrarCategoria(String nombre, String descripcion) {

        try (Connection conn = conexion.conectar()) {

            String sql = "INSERT INTO categorias (nombre, descripcion) VALUES (?, ?)";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, nombre);
            stm.setString(2, descripcion);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR NUEVA CATEGORIA DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public ArrayList<Categoria> obtenerCategorias() {

        ArrayList<Categoria> categorias = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM categorias";//selecciona las solicitudes
            PreparedStatement stm = conn.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Categoria nuevo = new Categoria(rs.getInt("id_categoria"), rs.getString("nombre"),
                        rs.getString("descripcion"), rs.getBoolean("estado"));

                categorias.add(nuevo);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER CATEGORIAS DESDE DAO" + e.getMessage());
        }

        return categorias;
    }

    public boolean registrarHabilidad(int id_categoria, String nombre, String descripcion) {

        try (Connection conn = conexion.conectar()) {

            String sql = "INSERT INTO habilidades (id_categoria, nombre, descripcion) VALUES (?, ?, ?)";

            PreparedStatement stm = conn.prepareStatement(sql);
            
            if (id_categoria == 0) {//si es 0 es porque viene de una solicitud
                stm.setNull(1, java.sql.Types.INTEGER);
            }else{
                stm.setInt(1, id_categoria);
            }          
            
            stm.setString(2, nombre);
            stm.setString(3, descripcion);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR NUEVA HABILIDAD DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public ArrayList<Habilidad> obtenerHabilidades() {

        ArrayList<Habilidad> habilidades = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM habilidades";//selecciona las solicitudes
            PreparedStatement stm = conn.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Habilidad nuevo = new Habilidad(rs.getInt("id_habilidad"), rs.getInt("id_categoria"),
                        rs.getString("nombre"), rs.getString("descripcion"), rs.getBoolean("estado"));

                habilidades.add(nuevo);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER HABILIDADES DESDE DAO" + e.getMessage());
        }

        return habilidades;
    }

    public boolean activarDesactivarCategoria(int id_categoria) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE categorias SET estado = NOT estado WHERE id_categoria = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_categoria);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL ACTIVAR/DESACTIVAR CATEGORIA DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public boolean activarDesactivarHabilidad(int id_habilidad) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE habilidades SET estado = NOT estado WHERE id_habilidad = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_habilidad);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL ACTIVAR/DESACTIVAR HABILIDAD DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public Categoria editarCategoria(Categoria nueva) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE categorias SET nombre = ?, descripcion=? WHERE id_categoria = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, nueva.getNombre());
            stm.setString(2, nueva.getDescripcion());
            stm.setInt(3, nueva.getId_categoria());

            stm.executeUpdate();

            return nueva;

        } catch (SQLException e) {
            System.out.println("ERROR AL EDITAR CATEGORIA DESDE DAO " + e.getMessage());
        }

        return null;
    }

    public Habilidad editarHabilidad(Habilidad nueva) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE habilidades SET id_categoria = ?, nombre = ?, descripcion=? WHERE id_habilidad = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, nueva.getId_categoria());
            stm.setString(2, nueva.getNombre());
            stm.setString(3, nueva.getDescripcion());
            stm.setInt(4, nueva.getId_habilidad());

            stm.executeUpdate();

            return nueva;

        } catch (SQLException e) {
            System.out.println("ERROR AL EDITAR HABILIDAD DESDE DAO " + e.getMessage());
        }

        return null;
    }

    public ArrayList<Solicitud> obtenerSolicitudes(String tabla) {

        ArrayList<Solicitud> solicitudes = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM " + tabla + " WHERE estado = 'PENDIENTE'";//selecciona las solicitudes ya sea de categoria o habilidad
            PreparedStatement stm = conn.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            if (tabla.equals("solicitud_categoria")) {//si lo que pide son las solicitudes de categorias

                while (rs.next()) {

                    Solicitud nuevo = new Solicitud(rs.getInt("id_solicitud_categoria"), rs.getInt("id_cliente"),
                            rs.getString("nombre"), rs.getString("descripcion"), rs.getString("estado"), rs.getDate("fecha"));

                    solicitudes.add(nuevo);
                }

            } else if (tabla.equals("solicitud_habilidad")) {//si lo que pide son las solicitudes de habilidades

                while (rs.next()) {

                    Solicitud nuevo = new Solicitud(rs.getInt("id_solicitud_habilidad"), rs.getInt("id_freelancer"),
                            rs.getString("nombre"), rs.getString("descripcion"), rs.getString("estado"), rs.getDate("fecha"));

                    solicitudes.add(nuevo);
                }

            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER " + tabla + " DESDE DAO" + e.getMessage());
        }

        return solicitudes;
    }
    
    
    public boolean aceptarRechazarSolicitudCategoria(int id_solicitud, String estado, String nombre, String descripcion) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE solicitud_categoria SET estado = ? WHERE id_solicitud_categoria = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, estado);
            stm.setInt(2, id_solicitud);

            stm.executeUpdate();

            if (estado.equals("RECHAZADA")) {
                return true;
            }
            
            if (estado.equals("ACEPTADA")) {
                return registrarCategoria(nombre, descripcion);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL ACEPTAR/RECHAZAR SOLICITUD DE CATEGORIA DESDE DAO " + e.getMessage());
        }

        return false;
    }
    
    public boolean aceptarRechazarSolicitudHabilidad(int id_solicitud, String estado, String nombre, String descripcion) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE solicitud_habilidad SET estado = ? WHERE id_solicitud_habilidad = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, estado);
            stm.setInt(2, id_solicitud);

            stm.executeUpdate();

            if (estado.equals("RECHAZADA")) {
                return true;
            }
            
            if (estado.equals("ACEPTADA")) {
                return registrarHabilidad(0, nombre, descripcion);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL ACEPTAR/RECHAZAR SOLICITUD DE HABILIDAD DESDE DAO " + e.getMessage());
        }

        return false;
    }
    
}
