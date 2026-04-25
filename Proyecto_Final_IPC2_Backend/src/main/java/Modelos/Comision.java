
package Modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;


public class Comision {
    
    private int id_comision;
    private double porcentaje;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fecha_inicio;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    
    private Date fecha_fin;     
    private boolean estado;

    public Comision() {
    }

    public Comision(double porcentaje, Date fecha_inicio, Date fecha_fin, boolean estado) {
        this.porcentaje = porcentaje;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.estado = estado;
    }
    
    

    public int getId_comision() {
        return id_comision;
    }

    public void setId_comision(int id_comision) {
        this.id_comision = id_comision;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    
    
}
