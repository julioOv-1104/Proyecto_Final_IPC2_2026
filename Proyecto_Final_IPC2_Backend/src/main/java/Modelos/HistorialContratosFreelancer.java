
package Modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

public class HistorialContratosFreelancer {
    
    private String cliente;
    private String proyecto;
    private double monto_recibido;
    private double puntuacion;
    private String comentario;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fecha_fin;

    public HistorialContratosFreelancer() {
    }

    public HistorialContratosFreelancer(String cliente, String proyecto, double monto_recibido, double puntuacion, String comentario, Date fecha_fin) {
        this.cliente = cliente;
        this.proyecto = proyecto;
        this.monto_recibido = monto_recibido;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fecha_fin = fecha_fin;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public double getMonto_recibido() {
        return monto_recibido;
    }

    public void setMonto_recibido(double monto_recibido) {
        this.monto_recibido = monto_recibido;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }
    
    
}
