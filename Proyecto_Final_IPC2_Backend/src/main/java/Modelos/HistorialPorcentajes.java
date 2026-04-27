package Modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

public class HistorialPorcentajes {

    private double porcentaje;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fecha_inicio, fecha_fin;

    public HistorialPorcentajes() {
    }

    public HistorialPorcentajes(double porcentaje, Date fecha_inicio, Date fecha_fin) {
        this.porcentaje = porcentaje;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
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

}
