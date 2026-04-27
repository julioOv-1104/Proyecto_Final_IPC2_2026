
package Modelos;

public class TopFreelancers {
    
    private String nombre_freelancer;
    private int cantidad_contratos;
    private double total_freelancer, total_comision;

    public TopFreelancers() {
    }

    public TopFreelancers(String nombre_freelancer, int cantidad_contratos, double total_freelancer, double total_comision) {
        this.nombre_freelancer = nombre_freelancer;
        this.cantidad_contratos = cantidad_contratos;
        this.total_freelancer = total_freelancer;
        this.total_comision = total_comision;
    }

    public String getNombre_freelancer() {
        return nombre_freelancer;
    }

    public void setNombre_freelancer(String nombre_freelancer) {
        this.nombre_freelancer = nombre_freelancer;
    }

    public int getCantidad_contratos() {
        return cantidad_contratos;
    }

    public void setCantidad_contratos(int cantidad_contratos) {
        this.cantidad_contratos = cantidad_contratos;
    }

    public double getTotal_freelancer() {
        return total_freelancer;
    }

    public void setTotal_freelancer(double total_freelancer) {
        this.total_freelancer = total_freelancer;
    }

    public double getTotal_comision() {
        return total_comision;
    }

    public void setTotal_comision(double total_comision) {
        this.total_comision = total_comision;
    }
    
    
    
}
