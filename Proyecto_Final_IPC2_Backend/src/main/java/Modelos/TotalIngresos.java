
package Modelos;

public class TotalIngresos {
    
    private int cantidad_contratos;
    private double total_comisiones;

    public TotalIngresos() {
    }

    public TotalIngresos(int cantidad_contratos, double total_comisiones) {
        this.cantidad_contratos = cantidad_contratos;
        this.total_comisiones = total_comisiones;
    }

    public int getCantidad_contratos() {
        return cantidad_contratos;
    }

    public void setCantidad_contratos(int cantidad_contratos) {
        this.cantidad_contratos = cantidad_contratos;
    }

    public double getTotal_comisiones() {
        return total_comisiones;
    }

    public void setTotal_comisiones(double total_comisiones) {
        this.total_comisiones = total_comisiones;
    }
    
    
    
}
