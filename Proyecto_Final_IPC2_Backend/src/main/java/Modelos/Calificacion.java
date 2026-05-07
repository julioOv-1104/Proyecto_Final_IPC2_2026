
package Modelos;

public class Calificacion {
    
    private String proyecto;
    private double puntuacion;
    private String comentario;
    private double calificacion_promedio;

    public Calificacion() {
    }
    

    public Calificacion(String proyecto, double puntuacion, String comentario, double calificacion_promedio) {
        this.proyecto = proyecto;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.calificacion_promedio = calificacion_promedio;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getCalificacion_promedio() {
        return calificacion_promedio;
    }

    public void setCalificacion_promedio(double calificacion_promedio) {
        this.calificacion_promedio = calificacion_promedio;
    }
    
    
}
