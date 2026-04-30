
package Modelos;

import java.sql.Date;

public class Entrega {
    
    private int id_entrega;
    private int id_contrato;
    private String descripcion; 
    private String archivo_url;  
    private Date fecha;  
    private String estado;      
    private String motivo_rechazo;

    public Entrega() {
    }

    public Entrega(int id_entrega, int id_contrato, String descripcion, String archivo_url, Date fecha, String estado, String motivo_rechazo) {
        this.id_entrega = id_entrega;
        this.id_contrato = id_contrato;
        this.descripcion = descripcion;
        this.archivo_url = archivo_url;
        this.fecha = fecha;
        this.estado = estado;
        this.motivo_rechazo = motivo_rechazo;
    }

    public int getId_entrega() {
        return id_entrega;
    }

    public void setId_entrega(int id_entrega) {
        this.id_entrega = id_entrega;
    }

    public int getId_contrato() {
        return id_contrato;
    }

    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getArchivo_url() {
        return archivo_url;
    }

    public void setArchivo_url(String archivo_url) {
        this.archivo_url = archivo_url;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivo_rechazo() {
        return motivo_rechazo;
    }

    public void setMotivo_rechazo(String motivo_rechazo) {
        this.motivo_rechazo = motivo_rechazo;
    }
    
    
    
    
}
