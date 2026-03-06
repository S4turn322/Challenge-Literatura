package com.alura.challenge_literatura.main;

import com.alura.challenge_literatura.model.Autor;
import com.alura.challenge_literatura.model.DatosBusqueda;
import com.alura.challenge_literatura.model.DatosLibros;
import com.alura.challenge_literatura.model.Libro;
import com.alura.challenge_literatura.repository.AutorRepository;
import com.alura.challenge_literatura.repository.LibroRepository;
import com.alura.challenge_literatura.service.ConsumoAPI;
import com.alura.challenge_literatura.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;

public class Main {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio1;
    private AutorRepository repositorio2;
    private List<Libro> libros;
    private List<Autor> autores;

    public Main(LibroRepository repository, AutorRepository repository2) {

        this.repositorio1 = repository;
        this.repositorio2 = repository2;

    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título
                    2 - Listar libros registrados 
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma 
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnDeterminadoAnho();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }



    private DatosLibros getDatosLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        System.out.println(json);
        if (json == null || json.isEmpty()) {
            System.out.println("La respuesta vino vacía");
            return null;
        }
        DatosBusqueda datosBusqueda = conversor.obtenerDatos(json, DatosBusqueda.class);
//
//        DatosLibros datosLibro = datosBusqueda.results().get(0);
//        Libro libro = new Libro(datosLibro);
//        return datosLibro;

        return datosBusqueda.results().stream()
                .findFirst()
                .orElse(null);


    }

    private void buscarLibroPorTitulo() {

//        DatosLibros datosLibros = getDatosLibro();
//        Libro libro = new Libro(datosLibros);
//        repositorio1.save(libro);
//        System.out.println(datosLibros);
//
        DatosLibros datos = getDatosLibro();

        if (datos == null) {
            System.out.println("Libro no encontrado.");
            return;
        }
        if (repositorio1.existsById(datos.id())) {
            System.out.println("El libro ya está en la base de datos.");
        }
        else {
            repositorio1.save(new Libro(datos));
            System.out.println("Libro guardado con éxito.");
        }

    }

    private void listarLibrosRegistrados() {

        libros = repositorio1.findAll();
        libros.forEach(l -> System.out.printf(
                "----- LIBRO -----\nTitulo: %s\nAutor: %s\nIdioma: %s\nDescargas: %d\n-----------------\n",
                l.getTitulo(),
                l.getAutor() != null ? l.getAutor().getNombre() : "N/A",
                l.getIdioma(),
                l.getDescargas()
        ));
    }

    private void listarAutoresRegistrados() {

        autores = repositorio2.findAll();
        autores.forEach(a -> System.out.printf(
                "----- AUTOR -----\nNombre: %s\nFecha de nacimiento: %d\nFecha de fallecimiento: %d\n-----------------\n",
                a.getNombre(),
                a.getFechaDeNacimiento() != null ? a.getFechaDeNacimiento() : "N/D",
                a.getFechaDeFallecimiento() != null ? a.getFechaDeFallecimiento() : "Sigue vivo / Sin datos"
        ));
    }

    private void listarAutoresVivosEnDeterminadoAnho(){

        System.out.println("Ingrese el año de auto/es que desea verificar: ");
        var anio = teclado.nextInt();

        List<Autor> autoresEncontrados = repositorio2.listarAutoresVivosEnDeterminadoAnio(anio);

        if (autoresEncontrados.isEmpty()) {
            System.out.println("No se encontraron autores registrados vivos en el año " + anio);
        }
        else {
            autoresEncontrados.forEach(a -> {
                String titulosLibros = a.getLibros().stream()
                        .map(Libro::getTitulo)
                        .collect(java.util.stream.Collectors.joining(", "));

                System.out.printf(
                        "----- AUTOR -----\n" +
                                "Nombre: %s\n" +
                                "Nacimiento: %s\n" +
                                "Fallecimiento: %s\n" +
                                "Libros: [%s]\n" +
                                "-----------------\n",
                        a.getNombre(),
                        a.getFechaDeNacimiento(),
                        a.getFechaDeFallecimiento() != null ? a.getFechaDeFallecimiento() : "N/A",
                        titulosLibros

                );

            });
        }

    }
    private void listarLibrosPorIdioma(){

        var menu = """
                    Ingrese el idioma que desea buscar:
                    es - Español
                    en - Ingles
                    """;
        System.out.println(menu);
        var opcion = teclado.nextLine();

        switch (opcion) {

            case "es":
                    List<Libro> espaniol = repositorio1.listarLibrosEnEspaniol();
                    System.out.println("-----LIBROS EN ESPAÑOL-----");
                    espaniol.forEach(l -> System.out.println(l.getTitulo()));
                    break;
            case "en":
                    List<Libro> ingles = repositorio1.listarLibroEnIngles();
                    System.out.println("-----LIBROS EN INGLES-----");
                    ingles.forEach(l -> System.out.println(l.getTitulo()));
                    break;
            default:
                System.out.println("Opcion invalida");
                break;

        }
    }
}