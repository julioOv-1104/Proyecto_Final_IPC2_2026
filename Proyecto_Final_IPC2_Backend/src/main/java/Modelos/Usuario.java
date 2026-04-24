package Modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

public class Usuario {

    private int id_usuario;
    private String nombre_completo;
    private String username;
    private String password;
    private String correo;
    private String telefono;
    private String direccion;
    private String cui;
    private int rol;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fecha_nacimiento;
    private Boolean estado;
    private double saldo;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombreCompleto, String username, String password, String correo, String telefono, String direccion, String cui,
            Date fechaNacimiento, Boolean estado, double saldo, int rol) {
        this.id_usuario = idUsuario;
        this.nombre_completo = nombreCompleto;
        this.username = username;
        this.password = password;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.cui = cui;
        this.fecha_nacimiento = fechaNacimiento;
        this.estado = estado;
        this.saldo = saldo;
        this.rol = rol;
    }

    public Usuario(int id_usuario, String username, int rol, Boolean estado, double saldo) {
        this.id_usuario = id_usuario;
        this.username = username;
        this.rol = rol;
        this.estado = estado;
        this.saldo = saldo;
    }
    
    

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

}
