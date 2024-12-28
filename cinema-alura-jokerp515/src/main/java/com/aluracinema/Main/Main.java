package com.aluracinema.Main;

import com.aluracinema.models.*;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aluracinema.calc.*;
import com.aluracinema.configuration.ApiKeyManager;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ApiKeyManager apiKeyManager = ApiKeyManager.getInstance();
        String API_KEY = apiKeyManager.getApiKey();
        // Movie myMovie = new Movie("Matrix",1998);

        // myMovie.note(10);
        // myMovie.note(8);
        // myMovie.note(9);

        // Serie mySerie = new Serie("Breaking Bad",2008);

        // mySerie.note(10);
        // mySerie.note(8);
        // mySerie.note(9);

        // // TimeCalc calc = new TimeCalc();
        // // calc.sumTime(myMovie);
        // // calc.sumTime(mySerie);
        // // System.out.println("Necessary time to see your movies/series:
        // "+calc.getTotalTime()+" Minutes");

        // // Filter filter = new Filter();
        // // filter.filter(myMovie);

        // Episode episode = new Episode();
        // episode.setNumEp(1);
        // episode.setName("Episode 1");
        // episode.setSerie(mySerie);
        // episode.setTotalVisualizations(300);

        // //filter.filter(episode);

        // ArrayList<Title> list = new ArrayList<>();

        // list.add(myMovie);
        // list.add(mySerie);

        // for(Title it : list){
        // System.out.println(it);
        // }

        Scanner sc = new Scanner(System.in);

        List<Title> Titles = new ArrayList<>();

        while (true) {
            System.out.println("Ingresa el nombre de una pelicula: ");
            String search = sc.nextLine();

            if(search.equalsIgnoreCase("exit")){
                break;
            }

            // search = search.replaceAll("\\s+","+");
            // search = search.replaceAll("&","%26");

            String adjustedSearch = URLEncoder.encode(search, "UTF-8");

            // System.out.println(adjustedSearch);

            String url = "https://www.omdbapi.com/?t=" + adjustedSearch + "&apikey="+API_KEY;

            HttpClient client = HttpClient.newHttpClient();
            // client.newCall(URI.create("https://www.alura.com.br/")).newBuilder
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();

            // System.out.println(response.body());

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            Omdb omdb = gson.fromJson(json, Omdb.class);
            // System.out.println(omdb);

            Title myTitle = new Title(omdb);
            System.out.println(myTitle);
            
            Titles.add(myTitle);
        }
        System.out.println("Lista de peliculas ingresadas: "+Titles);

        FileWriter writer = new FileWriter("titles.json");
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        gson2.toJson(Titles, writer);
        writer.close();

        sc.close();
        System.out.println("Final del programa");
    }
}