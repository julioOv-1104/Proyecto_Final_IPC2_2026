
package Modelos;

public class GastoPorCategoria {
    
    private String categoria;
    private int cantidad_contratos;
    private double total_gastado;

    public GastoPorCategoria() {
    }

    public GastoPorCategoria(String categoria, int cantidad_contratos, double total_gastado) {
        this.categoria = categoria;
        this.cantidad_contratos = cantidad_contratos;
        this.total_gastado = total_gastado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidad_contratos() {
        return cantidad_contratos;
    }

    public void setCantidad_contratos(int cantidad_contratos) {
        this.cantidad_contratos = cantidad_contratos;
    }

    public double getTotal_gastado() {
        return total_gastado;
    }

    public void setTotal_gastado(double total_gastado) {
        this.total_gastado = total_gastado;
    }
    
    
    
}
