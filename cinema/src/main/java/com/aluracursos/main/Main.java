package com.aluracursos.main;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.aluracursos.jokerp515.cinema.configuration.ApiKeyManager;
import com.aluracursos.jokerp515.cinema.model.SeasonsData;
import com.aluracursos.jokerp515.cinema.model.SeriesData;
import com.aluracursos.jokerp515.cinema.service.APIConsumption;
import com.aluracursos.jokerp515.cinema.service.ConvertData;

public class Main {
    private APIConsumption apiConsumption = new APIConsumption();
    private final String URL_BASE = "http://www.omdbapi.com/?t=";
    
    ApiKeyManager apiKeyManager = ApiKeyManager.getInstance();
    private final String APIKey = apiKeyManager.getApiKey();

    private Scanner sc = new Scanner(System.in);

    private ConvertData convertData = new ConvertData();

    public void displayMenu() throws UnsupportedEncodingException{
        System.out.println("Escribe el nombre de la pelicula: ");

        String search = sc.nextLine();

        String fixedSearch = URLEncoder.encode(search, "UTF-8");

        String json = apiConsumption.obtenerDatos(URL_BASE + fixedSearch + "&apikey=" + APIKey);

        //System.out.println(json);

        SeriesData data = convertData.getData(json, SeriesData.class);

        System.out.println(data);

        // Makes a range list for the seasons from 1 to the total number of seasons
        List<Integer> range = IntStream.rangeClosed(1, data.totalSeasons()).boxed().toList();

        // Gets the data of each season
		List<SeasonsData> seasonsData = range.stream()
				.map(season -> apiConsumption.obtenerDatos(URL_BASE+fixedSearch+"&Season="+season+"&apikey="+APIKey))
				.map(jsonSeason -> convertData.getData(jsonSeason, SeasonsData.class))
				.collect(Collectors.toList());

        // seasonsData.forEach(System.out::println);

        //Show only episodes titles of each season

        seasonsData.forEach(season -> {
            System.out.println("Season " + season.numSeason());
            season.episodes().forEach(episode -> System.out.println(episode.title()));
            System.out.println();
        });
    }
}
