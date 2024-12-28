package com.aluracursos.jokerp515.cinema;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aluracursos.jokerp515.cinema.configuration.ApiKeyManager;
import com.aluracursos.jokerp515.cinema.model.SeriesData;
import com.aluracursos.jokerp515.cinema.service.APIConsumption;
import com.aluracursos.jokerp515.cinema.service.ConvertData;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ApiKeyManager apiKeyManager = ApiKeyManager.getInstance();

		String APIKey = apiKeyManager.getApiKey();

		APIConsumption apiConsumption = new APIConsumption();
		String json = apiConsumption.obtenerDatos("https://www.omdbapi.com/?t=Game+Of+Thrones&apikey="+APIKey);
		
		// System.out.println(json);	

		ConvertData convertData = new ConvertData();

		var seriesData = convertData.getData(json, SeriesData.class);

		System.out.println(seriesData);

		json = apiConsumption.obtenerDatos("https://www.omdbapi.com/?t=Game+Of+Thrones&Season=1&episode=1&apikey="+APIKey);
	}

}
