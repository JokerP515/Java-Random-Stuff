package com.aluracursos.jokerp515.cinema.configuration;
import io.github.cdimascio.dotenv.Dotenv;

public class ApiKeyManager {
    private static ApiKeyManager INSTANCE; // Singleton instance
    private String apiKey;

    private ApiKeyManager(){
        Dotenv dotenv = Dotenv.configure().load();
        this.apiKey = dotenv.get("API_KEY");
    }

    public static ApiKeyManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ApiKeyManager();
        }
        return INSTANCE;
    }

    public String getApiKey(){
        return this.apiKey;
    }
        
}
