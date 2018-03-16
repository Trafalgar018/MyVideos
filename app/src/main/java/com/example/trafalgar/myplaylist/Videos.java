package com.example.trafalgar.myplaylist;

/**
 * Created by trafalgar on 16/03/18.
 */

public class Videos {
    private String titulo;
    private String usuario;
    private String image;
    private String visitas;
    private String id;

    // Constructores

    public Videos() {
    }

    public Videos(String titulo, String usuario, String visitas) {
        this.titulo = titulo;
        this.usuario = usuario;
        this.visitas = visitas;
    }

    public Videos(String titulo, String usuario, String image, String visitas, String id) {
        this.titulo = titulo;
        this.usuario = usuario;
        this.image = image;
        this.visitas = visitas;
        this.id = id;
    }

    // Accesores

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVisitas() {
        return visitas;
    }

    public void setViews(String visitas) {
        this.visitas = visitas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getTitulo() + ": " + getUsuario() + ". " + getVisitas();
    }
}
