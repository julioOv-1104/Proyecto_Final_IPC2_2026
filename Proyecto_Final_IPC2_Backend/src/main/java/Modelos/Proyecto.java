
package Modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

public class Proyecto {
    
    private int id_proyecto; 
    private int id_cliente;
    private int id_categoria;
    private String titulo;
    private String descripcion;
    private double presupuesto_max;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fecha_limite;
    private String estado;

    public Proyecto() {
    }

    public Proyecto(int idProyecto, int idCliente, int idCategoria, String titulo, String descripcion, 
            double presupuestoMax, Date fechaLimite, String estado) {
        this.id_proyecto = idProyecto;
        this.id_cliente = idCliente;
        this.id_categoria = idCategoria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.presupuesto_max = presupuestoMax;
        this.fecha_limite = fechaLimite;
        this.estado = estado;
    }

    public int getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPresupuesto_max() {
        return presupuesto_max;
    }

    public void setPresupuesto_max(double presupuesto_max) {
        this.presupuesto_max = presupuesto_max;
    }

    public Date getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(Date fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    
}
