package com.sisterag.cambiarestado.modelos;

public class Usuarios {
    private int idusr;
    private String cliente, usuario, clave;
    private int nivel, estatus;
    private String nombres;
    private String apellidos;

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto() {
        this.nombrecompleto = getNombres() + " " + getApellidos();
    }

    private String nombrecompleto;

    public int getIdusr() {
        return idusr;
    }

    public void setIdusr(int idusr) {
        this.idusr = idusr;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
