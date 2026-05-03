
package Modelos;

public class FreelancerCompletado {
    
    private String nombre_freelancer, titulo;
    private int id_contrato, id_freelancer;

    public FreelancerCompletado() {
    }

    public FreelancerCompletado(String nombre_freelancer, String titulo, int id_contrato, int id_freelancer) {
        this.nombre_freelancer = nombre_freelancer;
        this.titulo = titulo;
        this.id_contrato = id_contrato;
        this.id_freelancer = id_freelancer;
    }

    public String getNombre_freelancer() {
        return nombre_freelancer;
    }

    public void setNombre_freelancer(String nombre_freelancer) {
        this.nombre_freelancer = nombre_freelancer;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId_contrato() {
        return id_contrato;
    }

    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }

    public int getId_freelancer() {
        return id_freelancer;
    }

    public void setId_freelancer(int id_freelancer) {
        this.id_freelancer = id_freelancer;
    }
    
    
    
}
