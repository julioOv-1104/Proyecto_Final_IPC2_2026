package DAOs;

import Modelos.*;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO extends Usuario {

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

    public boolean comprobarSaldoCliente(int id_usuario, double monto) {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT 1 FROM usuarios WHERE id_usuario = ? AND saldo >= ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);
            stm.setDouble(2, monto);//comprueba que el usuario tenga suficiente saldo

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {

                return true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL COMPROBAR SALDO DE CLIENTE DESDE DAO" + e.getMessage());
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

    public boolean recargarSaldo(int id_usuario, double recarga) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE usuarios SET saldo = saldo + ? WHERE id_usuario = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setDouble(1, recarga);
            stm.setInt(2, id_usuario);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL RECARGAR SALDO DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public ArrayList<Proyecto> obtenerProyectosSinContrato(int id_usuario) {

        ArrayList<Proyecto> proyectos = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM proyectos WHERE id_cliente = (SELECT id_cliente FROM clientes "
                    + "WHERE id_usuario = ?) AND estado IN ('ABIERTO', 'EN_REVISION');";
            //obtiene solo los entregas del usuario que no tengan contrato
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Proyecto nuevo = new Proyecto(rs.getInt("id_proyecto"), rs.getInt("id_cliente"),
                        rs.getInt("id_categoria"), rs.getString("titulo"), rs.getString("descripcion"),
                        rs.getDouble("presupuesto_max"), rs.getDate("fecha_limite"), rs.getString("estado"));

                proyectos.add(nuevo);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER PROYECTOS SIN CONTRATO DESDE DAO" + e.getMessage());
        }

        return proyectos;
    }

    public ArrayList<Proyecto> obtenerProyectosConEntrega(int id_usuario) {

        ArrayList<Proyecto> proyectos = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM proyectos WHERE id_cliente = (SELECT id_cliente FROM clientes "
                    + "WHERE id_usuario = ?) AND estado IN ('ENTREGA_PENDIENTE')";
            //obtiene solo los entregas del usuario que ya tengan contrato y tengan entrega
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_usuario);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Proyecto nuevo = new Proyecto(rs.getInt("id_proyecto"), rs.getInt("id_cliente"),
                        rs.getInt("id_categoria"), rs.getString("titulo"), rs.getString("descripcion"),
                        rs.getDouble("presupuesto_max"), rs.getDate("fecha_limite"), rs.getString("estado"));

                proyectos.add(nuevo);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER PROYECTOS CON ENTREGAS DESDE DAO" + e.getMessage());
        }

        return proyectos;
    }

    public boolean registrarSolicitudCategoria(int id_usuario, String descripcion, String nombre) {

        try (Connection conn = conexion.conectar()) {

            String sql = "INSERT INTO solicitud_categoria (id_cliente, nombre, descripcion) SELECT id_cliente, "
                    + "?, ? FROM clientes WHERE id_usuario = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, nombre);
            stm.setString(2, descripcion);
            stm.setInt(3, id_usuario);//a partir del id_usuario encuentra el id_cliente

            System.out.println("SQL: " + stm);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR SOLICITUD DE CATEGORIA DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public boolean publicarProyecto(int id_usuario, int id_categoria, String titulo, String descripcion,
            double presupuesto, Date fecha_limite) {

        try (Connection conn = conexion.conectar()) {

            String sql = "INSERT INTO proyectos (id_cliente,  id_categoria, titulo, descripcion, presupuesto_max,"
                    + "fecha_limite) SELECT id_cliente, "
                    + "?, ?, ?, ?, ? FROM clientes WHERE id_usuario = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_categoria);
            stm.setString(2, titulo);
            stm.setString(3, descripcion);
            stm.setDouble(4, presupuesto);
            stm.setDate(5, fecha_limite);
            stm.setInt(6, id_usuario);//a partir del id_usuario encuentra el id_cliente

            System.out.println("SQL: " + stm);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL PUBLICAR PROYECTO DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public boolean editarProyecto(int id_proyecto, int id_categoria, String titulo, String descripcion,
            double presupuesto, Date fecha_limite) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE proyectos SET id_categoria = ?,titulo = ?, descripcion=?, presupuesto_max = ?, "
                    + "fecha_limite = ? WHERE id_proyecto = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_categoria);
            stm.setString(2, titulo);
            stm.setString(3, descripcion);
            stm.setDouble(4, presupuesto);
            stm.setDate(5, fecha_limite);
            stm.setInt(6, id_proyecto);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL EDITAR PROYECTO DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public String consultarEstadoProyecto(int id_proyecto) {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT estado FROM proyectos WHERE id_proyecto = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_proyecto);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {

                return rs.getString("estado");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER ESTADO DE PROYECTO DESDE DAO " + e.getMessage());
        }

        return "";
    }

    public boolean cancelarProyecto(int id_proyecto) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE proyectos SET estado = 'CANCELADO' WHERE id_proyecto = ?";
            //cancela el proyecto
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_proyecto);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL CANCELAR PROYECTO DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public ArrayList<Propuesta> obtenerPropuestas(int id_proyecto) {

        ArrayList<Propuesta> propuestas = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT \n"
                    + "    p.id_propuesta,\n"
                    + "    p.id_proyecto,\n"
                    + "    p.id_freelancer,\n"
                    + "    p.monto,\n"
                    + "    p.plazo_dias,\n"
                    + "    p.descripcion,\n"
                    + "    p.estado,\n"
                    + "    p.fecha,\n"
                    + "    p.motivo_rechazo,"
                    + "    u.nombre_completo AS nombre_freelancer,\n"
                    + "\n"
                    + "    \n"
                    + "    IFNULL(AVG(ca.puntuacion), 0) AS calificacion_promedio\n"
                    + "\n"
                    + "FROM Propuestas p\n"
                    + "\n"
                    + "JOIN Freelancers f \n"
                    + "    ON p.id_freelancer = f.id_freelancer\n"
                    + "\n"
                    + "JOIN Usuarios u \n"
                    + "    ON f.id_usuario = u.id_usuario\n"
                    + "\n"
                    + "LEFT JOIN Propuestas p2 \n"
                    + "    ON f.id_freelancer = p2.id_freelancer\n"
                    + "\n"
                    + "LEFT JOIN Contratos c \n"
                    + "    ON p2.id_propuesta = c.id_propuesta\n"
                    + "\n"
                    + "LEFT JOIN Calificaciones ca \n"
                    + "    ON c.id_contrato = ca.id_contrato\n"
                    + "\n"
                    + "WHERE p.id_proyecto = ? AND p.estado = 'PENDIENTE'\n"
                    + "\n"
                    + "GROUP BY p.id_propuesta, u.nombre_completo;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_proyecto);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Propuesta nuevo = new Propuesta(rs.getInt("id_propuesta"), rs.getInt("id_proyecto"),
                        rs.getInt("id_freelancer"), rs.getDouble("monto"), rs.getInt("plazo_dias"),
                        rs.getString("descripcion"), rs.getString("estado"), rs.getDate("fecha"),
                        rs.getString("motivo_rechazo"));

                nuevo.setNombre_freelancer(rs.getString("nombre_freelancer"));
                nuevo.setCalificacion_promedio(rs.getDouble("calificacion_promedio"));

                propuestas.add(nuevo);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER PROPUESTAS DESDE DAO" + e.getMessage());
        }

        return propuestas;
    }

    public Propuesta obtenerPropuestaEspecifica(int id_propuesta) {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM propuestas WHERE id_propuesta = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_propuesta);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {

                Propuesta nuevo = new Propuesta(rs.getInt("id_propuesta"), rs.getInt("id_proyecto"),
                        rs.getInt("id_freelancer"), rs.getDouble("monto"), rs.getInt("plazo_dias"),
                        rs.getString("descripcion"), rs.getString("estado"), rs.getDate("fecha"),
                        rs.getString("motivo_rechazo"));

                return nuevo;
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER PROPUESTA ESPECIFICA DESDE DAO" + e.getMessage());
        }

        return null;
    }

    public boolean aceptarPropuesta(int id_propuesta) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE propuestas SET estado = 'ACEPTADA' WHERE id_propuesta = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_propuesta);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL ACEPTAR PROPUESTA DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public boolean bloquearSaldoCliente(double monto, int id_usuario) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE usuarios SET saldo = saldo - ? WHERE id_usuario = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setDouble(1, monto);
            stm.setInt(2, id_usuario);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL BLOQUEAR SALDO DESDE DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public void cambiarEstadoProyecto(String estado, int id_proyecto) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE proyectos SET estado = ? WHERE id_proyecto = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, estado);
            stm.setInt(2, id_proyecto);

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERROR AL CAMBIAR EL ESTADO DEL PROYECTO A " + estado + " DESDE DAO " + e.getMessage());
        }

    }

    public boolean rechazarPropuesta(int id_propuesta, String motivo) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE propuestas SET estado = 'RECHAZADA', motivo_rechazo = ? WHERE id_propuesta = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, motivo);
            stm.setInt(2, id_propuesta);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL RECHAZAR PROPUESTA DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public boolean crearContrato(Propuesta propuesta) {

        try (Connection conn = conexion.conectar()) {

            String sql = "INSERT INTO contratos (id_propuesta, id_proyecto, monto) "
                    + "VALUES (?, ?, ?)";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, propuesta.getId_propuesta());
            stm.setInt(2, propuesta.getId_proyecto());
            stm.setDouble(3, propuesta.getMonto());

            System.out.println("SQL: " + stm);

            stm.executeUpdate();

            //cambia el estado del proyecto a EN_PROGRESO
            cambiarEstadoProyecto("EN_PROGRESO", propuesta.getId_proyecto());

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL CREAR CONTRATO DESDE DAO " + e.getMessage());
        }

        return false;
    }

    public ArrayList<Entrega> obtenerEntregas(int id_proyecto) {

        ArrayList<Entrega> entregas = new ArrayList<>();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT e.*\n"
                    + "FROM Entregas e\n"
                    + "JOIN Contratos c \n"
                    + "    ON e.id_contrato = c.id_contrato\n"
                    + "JOIN Propuestas p \n"
                    + "    ON c.id_propuesta = p.id_propuesta\n"
                    + "WHERE p.id_proyecto = ? \n"
                    + "AND e.estado = 'PENDIENTE';";
            //obtiene solo las entregas del proyecto que estan pendientes
            
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id_proyecto);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Entrega nueva = new Entrega(rs.getInt("id_entrega"), rs.getInt("id_contrato"),
                        rs.getString("descripcion"), rs.getString("archivo_url"), rs.getDate("fecha"),
                        rs.getString("estado"), rs.getString("motivo_rechazo"));

                entregas.add(nueva);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER ENTREGAS DESDE DAO" + e.getMessage());
        }

        return entregas;
    }
    
    public boolean rechazarEntrega(int id_entrega, String motivo) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE entregas SET estado = 'RECHAZADA', motivo_rechazo = ? WHERE id_entrega = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, motivo);
            stm.setInt(2, id_entrega);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL RECHAZAR ENTREGA DESDE DAO " + e.getMessage());
        }

        return false;
    }

}
