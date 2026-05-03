
package Modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class Recarga {
    
    private double monto;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime fecha;

    public Recarga() {
    }

    public Recarga(double monto, LocalDateTime fecha) {
        this.monto = monto;
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
    
    
}
