
package Modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

public class Contrato {
    
    private int id_contrato;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fecha_inicio, fecha_fin;
    private String estado;
    private double monto;
    private String motivo_cancelacion;
    private double porcentaje;
    
    private int id_proyecto;

    public Contrato() {
    }

    public Contrato(int id_contrato, Date fecha_inicio, Date fecha_fin, String estado, double monto, 
            String motivo_cancelacion, double porcentaje, int id_proyecto) {
        this.id_contrato = id_contrato;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.estado = estado;
        this.monto = monto;
        this.motivo_cancelacion = motivo_cancelacion;
        this.porcentaje = porcentaje;
        this.id_proyecto = id_proyecto;
    }

    public int getId_contrato() {
        return id_contrato;
    }

    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMotivo_cancelacion() {
        return motivo_cancelacion;
    }

    public void setMotivo_cancelacion(String motivo_cancelacion) {
        this.motivo_cancelacion = motivo_cancelacion;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }
    
    
    
    
}
