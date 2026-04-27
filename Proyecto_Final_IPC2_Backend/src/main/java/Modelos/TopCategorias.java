
package Modelos;

public class TopCategorias {
    
    private String nombre_categoria;
    private int cantidad_contratos;
    private int total_comisiones;

    public TopCategorias() {
    }

    public TopCategorias(String nombre_categoria, int cantidad_contratos, int total_comisiones) {
        this.nombre_categoria = nombre_categoria;
        this.cantidad_contratos = cantidad_contratos;
        this.total_comisiones = total_comisiones;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }

    public int getCantidad_contratos() {
        return cantidad_contratos;
    }

    public void setCantidad_contratos(int cantidad_contratos) {
        this.cantidad_contratos = cantidad_contratos;
    }

    public int getTotal_comisiones() {
        return total_comisiones;
    }

    public void setTotal_comisiones(int total_comisiones) {
        this.total_comisiones = total_comisiones;
    }
    
    
    
}
