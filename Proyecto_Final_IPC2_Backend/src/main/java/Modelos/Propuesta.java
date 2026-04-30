
package Modelos;

import java.sql.Date;


public class Propuesta {
    
    private Integer id_propuesta;
    private Integer id_proyecto;
    private Integer id_freelancer;
    private double monto;
    private Integer plazo_dias;
    private String descripcion;
    private String estado;
    private Date fecha;
    private String motivo_rechazo;
    private String nombre_freelancer;
    private double calificacion_promedio;

    public Propuesta() {
    }

    public Propuesta(Integer id_propuesta, Integer id_proyecto, Integer id_freelancer, double monto, Integer plazo_dias, String descripcion, String estado, Date fecha, String motivo_rechazo) {
        this.id_propuesta = id_propuesta;
        this.id_proyecto = id_proyecto;
        this.id_freelancer = id_freelancer;
        this.monto = monto;
        this.plazo_dias = plazo_dias;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fecha = fecha;
        this.motivo_rechazo = motivo_rechazo;
    }

    public Integer getId_propuesta() {
        return id_propuesta;
    }

    public void setId_propuesta(Integer id_propuesta) {
        this.id_propuesta = id_propuesta;
    }

    public Integer getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(Integer id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public Integer getId_freelancer() {
        return id_freelancer;
    }

    public void setId_freelancer(Integer id_freelancer) {
        this.id_freelancer = id_freelancer;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Integer getPlazo_dias() {
        return plazo_dias;
    }

    public void setPlazo_dias(Integer plazo_dias) {
        this.plazo_dias = plazo_dias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMotivo_rechazo() {
        return motivo_rechazo;
    }

    public void setMotivo_rechazo(String motivo_rechazo) {
        this.motivo_rechazo = motivo_rechazo;
    }

    public String getNombre_freelancer() {
        return nombre_freelancer;
    }

    public void setNombre_freelancer(String nombre_freelancer) {
        this.nombre_freelancer = nombre_freelancer;
    }

    public double getCalificacion_promedio() {
        return calificacion_promedio;
    }

    public void setCalificacion_promedio(double calificacion_promedio) {
        this.calificacion_promedio = calificacion_promedio;
    }
    
    
    
}
