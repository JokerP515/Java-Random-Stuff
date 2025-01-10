package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.configuration.ApiKeyManager;
import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private ApiKeyManager apiKeyManager = ApiKeyManager.getInstance();
    private final String API_KEY = "&apikey=" + apiKeyManager.getApiKey();
    private ConvierteDatos conversor = new ConvierteDatos();
    // private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repositorio;

    private List<Serie> series;

    private Optional<Serie> serieBuscada;

    public Principal(SerieRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar series por titulo
                    5 - Top 5 series
                    6 - Buscar series por categoria
                    7 - Filtrar series por temporadas y evaluación
                    8 - Buscar episodios por nombre
                    9 - Top 5 episodios por serie

                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    buscarSeriesPorCategoria();
                    break;
                case 7:
                    filtrarSeriesPorTemporadasYEvaluacion();
                    break;
                case 8:
                    buscarEpisodioPorNombre();
                    break;
                case 9:
                    buscarTop5Episodios();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }

    }

    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie para buscar episodios: ");
        String nombreSerie = teclado.nextLine();

        Optional<Serie> serie = series.stream().filter(s -> s.getTitulo().equalsIgnoreCase(nombreSerie)).findFirst();

        if (serie.isPresent()) {
            Serie s = serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= s.getTotalTemporadas(); i++) {
                var json = consumoApi
                        .obtenerDatos(URL_BASE + s.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = new ArrayList<>();
            episodios = temporadas.stream().flatMap(d -> d.episodios().stream().map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            s.setEpisodios(episodios);
            repositorio.save(s);
        } else {
            System.out.println("No se encontró la serie");
        }

    }

    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        // System.out.println(datos);
        // if (datos != null) {
        // datosSeries.add(datos);
        // }

    }

    private void mostrarSeriesBuscadas() {

        // series = datosSeries.stream().map(Serie::new).collect(Collectors.toList());
        series = repositorio.findAll();

        series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escribe el nombre de la serie que deseas buscar: ");
        String nombreSerie = teclado.nextLine();

        serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);

        if (serieBuscada.isPresent()) {
            System.out.println("La serie buscada es: " + serieBuscada.get());
        } else {
            System.out.println("No se encontró la serie");
        }
    }

    private void buscarTop5Series() {
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s -> System.out.println(s.getTitulo() + " - " + s.getEvaluacion()));
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("Escribe el nombre de la categoria que deseas buscar: ");
        String nombreGenero = teclado.nextLine();
        Categoria categoria = Categoria.getCategoria(nombreGenero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);

        System.out.println("Series de la categoria " + nombreGenero);
        seriesPorCategoria.forEach(System.out::println);
    }

    public void filtrarSeriesPorTemporadasYEvaluacion() {
        System.out.println("Ingresa el número de temporadas: ");
        Integer totalTemporadas = teclado.nextInt();
        System.out.println("Ingresa la evaluación: ");
        Double evaluacion = teclado.nextDouble();
        teclado.nextLine();
        List<Serie> seriesFiltradas = repositorio.seriesPorTemporadaYEvaluacion(totalTemporadas, evaluacion);

        System.out.println("Series con " + totalTemporadas + " temporadas y evaluación mayor o igual a " + evaluacion);
        seriesFiltradas.forEach(s -> System.out.println(s.getTitulo() + " - evaluación: " + s.getEvaluacion()));
    }

    private void buscarEpisodioPorNombre() {
        System.out.println("Escribe el nombre del episodio que deseas buscar: ");
        String nombreEpisodio = teclado.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorNombre(nombreEpisodio);
        episodiosEncontrados.forEach(e -> System.out.printf("Serie: %s, Temporada: %s, Episodio: %s, Evaluacion: %s\n",
                e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));
    }

    private void buscarTop5Episodios(){
        buscarSeriePorTitulo();

        if(serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = repositorio.top5Episodios(serie);
            topEpisodios.forEach(e -> System.out.printf("Serie: %s, Temporada: %s, Episodio: %s, Evaluacion: %s\n",
                e.getSerie().getTitulo(), e.getTemporada(), e.getTitulo(), e.getEvaluacion()));
        }
    }
}
