
package Modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

public class HistorialProyectosCliente {
    
    private String titulo, estado_proyecto, freelancer;
    private double monto;
    
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fecha;

    public HistorialProyectosCliente() {
    }

    public HistorialProyectosCliente(String titulo, String estado_proyecto, String freelancer, double monto, Date fecha) {
        this.titulo = titulo;
        this.estado_proyecto = estado_proyecto;
        this.freelancer = freelancer;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstado_proyecto() {
        return estado_proyecto;
    }

    public void setEstado_proyecto(String estado_proyecto) {
        this.estado_proyecto = estado_proyecto;
    }

    public String getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(String freelancer) {
        this.freelancer = freelancer;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    
}
