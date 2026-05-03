
package Modelos;

public class Habilidad {
    
    private int id_habilidad, id_categoria;
    private String nombre, descripcion;
    private boolean estado;
    private int id_proyecto;

    public Habilidad() {
    }

    public Habilidad(int id_habilidad, int id_categoria, String nombre, String descripcion, boolean estado) {
        this.id_habilidad = id_habilidad;
        this.id_categoria = id_categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }
    
    public Habilidad(int id_habilidad, int id_categoria, String nombre, String descripcion, boolean estado, int id_proyecto) {
        this.id_habilidad = id_habilidad;
        this.id_categoria = id_categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.id_proyecto = id_proyecto;
    }

    public int getId_habilidad() {
        return id_habilidad;
    }

    public void setId_habilidad(int id_habilidad) {
        this.id_habilidad = id_habilidad;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }
    
    
    
}
