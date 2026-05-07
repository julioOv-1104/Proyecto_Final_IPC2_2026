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

    public boolean registrarInformacionInicial(int id_usuario, String biografia, String nivel_experiencia,
            double tarifa_hora) {

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
            //selecciona las calificaciones vinculadas a un freelancer

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

            System.out.println("SQL: " + stm);

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

    public ArrayList<Proyecto> obtenerProyectosAbiertos() {

        ArrayList<Proyecto> proyectos = new ArrayList<>();

        System.out.println("ENTRA AKI");

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM proyectos WHERE estado = 'ABIERTO' ";
            //obtiene todos los proyectos abiertos
            PreparedStatement stm = conn.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Proyecto nuevo = new Proyecto(rs.getInt("id_proyecto"), rs.getInt("id_cliente"),
                        rs.getInt("id_categoria"), rs.getString("titulo"), rs.getString("descripcion"),
                        rs.getDouble("presupuesto_max"), rs.getDate("fecha_limite"), rs.getString("estado"));

                proyectos.add(nuevo);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER TODOS LOS PROYECTOS ABIERTOS DESDE DAO" + e.getMessage());
        }

        return proyectos;
    }

    public boolean registrarPropuesta(int id_usuario, int id_proyecto, double monto, int plazo, String descripcion) {

        try (Connection conn = conexion.conectar()) {

            String sql = "INSERT INTO propuestas (id_proyecto, id_freelancer, monto, plazo_dias, descripcion) "
                    + "VALUES (?, (SELECT id_freelancer FROM freelancers WHERE id_usuario = ?),?, ?, ?)";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_proyecto);
            stm.setInt(2, id_usuario);
            stm.setDouble(3, monto);
            stm.setInt(4, plazo);
            stm.setString(5, descripcion);

            System.out.println("SQL: " + stm);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR PROPUESTA DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public boolean verificarPropuestaYaExistente(int id_usuario, int id_proyecto) {
        //verifica si un freelancer ya envio una propuesta a un proyecto

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT 1 FROM propuestas WHERE id_proyecto = ? AND id_freelancer ="
                    + " (SELECT id_freelancer FROM freelancers WHERE id_usuario = ?)";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_proyecto);
            stm.setInt(2, id_usuario);

            System.out.println("SQL: " + stm);

            ResultSet rs = stm.executeQuery();

            return rs.next();//si no ha enviado ninguna propuesta devuelve false

        } catch (SQLException e) {
            System.out.println("ERROR AL VERIFICAR SI FREELANCER YA ENVIO PROPUESTA DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public ArrayList<Propuesta> obtenerPropuestasFreelancer(int id_usuario) {

        ArrayList<Propuesta> propuestas = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM propuestas WHERE id_freelancer = "
                    + "(SELECT id_freelancer FROM freelancers WHERE id_usuario = ?)";
            PreparedStatement stm = conn.prepareStatement(sql);

            stm.setInt(1, id_usuario);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Propuesta nueva = new Propuesta(rs.getInt("id_propuesta"), rs.getInt("id_proyecto"), rs.getDouble("monto"),
                        rs.getInt("plazo_dias"), rs.getString("descripcion"), rs.getString("estado"),
                        rs.getDate("fecha"), rs.getString("motivo_rechazo"));

                propuestas.add(nueva);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER TODAS LA PROPUESTAS DE FREELANCER DESDE DAO" + e.getMessage());
        }

        return propuestas;
    }

    public boolean retirarPropuesta(int id_propuesta) {

        try (Connection conn = conexion.conectar()) {

            String sql = "DELETE FROM propuestas WHERE id_propuesta = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_propuesta);

            System.out.println("SQL: " + stm);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL RETIRAR PROPUESTA DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public ArrayList<Contrato> obtenerContratosFreelancer(int id_usuario) {

        ArrayList<Contrato> contratos = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = " SELECT c.id_contrato, c.fecha_inicio, c.fecha_fin,"
                    + "c.estado, c.monto, c.motivo_cancelacion, c.porcentaje, c.id_proyecto FROM contratos AS c JOIN propuestas AS pr "
                    + "ON c.id_propuesta = pr.id_propuesta WHERE pr.id_freelancer = "
                    + "(SELECT id_freelancer FROM freelancers WHERE id_usuario = ?)";
            PreparedStatement stm = conn.prepareStatement(sql);

            stm.setInt(1, id_usuario);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Contrato nueva = new Contrato(rs.getInt("id_contrato"), rs.getDate("fecha_inicio"), rs.getDate("fecha_fin"),
                        rs.getString("estado"), rs.getDouble("monto"), rs.getString("motivo_cancelacion"),
                        rs.getDouble("porcentaje"), rs.getInt("id_proyecto"));

                contratos.add(nueva);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER TODOS LOS CONTRATOS DE FREELANCER DESDE DAO" + e.getMessage());
        }

        return contratos;
    }

    public boolean registrarEntrega(int id_contrato, String descripcion, String url) {

        try (Connection conn = conexion.conectar()) {

            String sql = "INSERT INTO entregas (id_contrato, descripcion, archivo_url) VALUES"
                    + " (?,?,?)";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_contrato);
            stm.setString(2, descripcion);
            stm.setString(3, url);

            System.out.println("SQL: " + stm);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR ENTREGA DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public String consultarEstadoContrato(int id_contrato) {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT estado FROM contratos WHERE id_contrato = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_contrato);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {

                return rs.getString("estado");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER ESTADO DE CONTRATO DESDE DAO " + e.getMessage());
        }

        return "";
    }

    public ArrayList<Entrega> obtenerEntregasFreelancer(int id_usuario) {

        ArrayList<Entrega> entregas = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT e.descripcion, e.archivo_url, e.fecha, e.estado, e.motivo_rechazo "
                    + "FROM entregas AS e JOIN contratos ON e.id_contrato = contratos.id_contrato "
                    + "JOIN propuestas ON propuestas.id_propuesta = contratos.id_propuesta "
                    + "WHERE propuestas.id_freelancer = (SELECT id_freelancer FROM freelancers WHERE id_usuario = ?)";
            PreparedStatement stm = conn.prepareStatement(sql);

            stm.setInt(1, id_usuario);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Entrega nueva = new Entrega(rs.getString("descripcion"), rs.getString("archivo_url"),
                        rs.getDate("fecha"), rs.getString("estado"), rs.getString("motivo_rechazo"));

                entregas.add(nueva);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER TODAS LAS ENTREGAS DE FREELANCER DESDE DAO" + e.getMessage());
        }

        return entregas;
    }

    public ArrayList<Calificacion> obtenerCalificacionesDeFreelancer(int id_usuario) {

        ArrayList<Calificacion> calificaciones = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT \n"
                    + "    pr.titulo AS proyecto,\n"
                    + "\n"
                    + "    cal.puntuacion,\n"
                    + "\n"
                    + "    cal.comentario,\n"
                    + "\n"
                    + "    (\n"
                    + "        SELECT AVG(cal2.puntuacion)\n"
                    + "        FROM Calificaciones cal2\n"
                    + "        JOIN Contratos c2 \n"
                    + "            ON cal2.id_contrato = c2.id_contrato\n"
                    + "        JOIN Propuestas p2 \n"
                    + "            ON c2.id_propuesta = p2.id_propuesta\n"
                    + "        WHERE p2.id_freelancer = f.id_freelancer\n"
                    + "    ) AS calificacion_promedio\n"
                    + "\n"
                    + "FROM Freelancers f\n"
                    + "\n"
                    + "JOIN Propuestas p \n"
                    + "    ON f.id_freelancer = p.id_freelancer\n"
                    + "\n"
                    + "JOIN Contratos c \n"
                    + "    ON p.id_propuesta = c.id_propuesta\n"
                    + "\n"
                    + "JOIN Calificaciones cal \n"
                    + "    ON c.id_contrato = cal.id_contrato\n"
                    + "\n"
                    + "JOIN Proyectos pr \n"
                    + "    ON p.id_proyecto = pr.id_proyecto\n"
                    + "\n"
                    + "WHERE f.id_freelancer = (SELECT id_freelancer FROM freelancers WHERE id_usuario = ?)";
            //obtiene las calificaciones de un freelancer

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);

            ResultSet rs = stm.executeQuery();

            System.out.println("SQL: " + stm);

            while (rs.next()) {

                Calificacion nuevo = new Calificacion(rs.getString("proyecto"), rs.getDouble("puntuacion"),
                        rs.getString("comentario"), rs.getDouble("calificacion_promedio"));

                calificaciones.add(nuevo);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER CALIFICACIONES DE FREELANCER DESDE DAO" + e.getMessage());
        }

        return calificaciones;
    }

}
