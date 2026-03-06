package com.alura.challenge_literatura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libro")

public class Libro {

    @Id
    private Long id;


    @Column(unique = true)
    private String titulo;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Relación correcta con la entidad Autor
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private Integer descargas;
    private String idioma;
    //private List<LibrosDTO> results;


    public Libro(){}

    public Libro(DatosLibros datosLibros){

        this.titulo = datosLibros.titulo();;
        this.id = datosLibros.id();
        this.descargas = datosLibros.descargas();


        if (datosLibros.autor() != null && !datosLibros.autor().isEmpty()) {
            this.autor = new Autor(datosLibros.autor().get(0)); // Usa el constructor de Autor(DatosAutor)
        }

        if (datosLibros.idioma() != null && !datosLibros.idioma().isEmpty()) {
            this.idioma = datosLibros.idioma().get(0);
        }

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutores(Autor autor) {
        this.autor = autor;
    }


    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

//    public void setAutores(List<Autor> autores) {
//        autores.forEach(a -> a.getLibros().add(this));
//        this.autores = autores;
//    }


//    public String getNombreAutor() {
//        return nombreAutor;
//    }
//
//    public void setNombreAutor(String nombreAutor) {
//        this.nombreAutor = nombreAutor;
//    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", descargas=" + descargas +
                ", idioma='" + idioma + '\'' +
                '}';
    }
}
