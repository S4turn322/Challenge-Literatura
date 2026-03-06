package com.alura.challenge_literatura.repository;

import com.alura.challenge_literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);

//    @Query("SELECT l FROM Libro l JOIN l.autor a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombreAutor, '%'))")
//    List<Libro> buscarPorNombreAutor(@Param("nombreAutor") String nombreAutor);
//
    @Query("SELECT l FROM Libro l WHERE l.idioma = 'es'")
    List<Libro> listarLibrosEnEspaniol();

    @Query("SELECT l FROM Libro l WHERE l.idioma = 'en'")
    List<Libro> listarLibroEnIngles();

}
