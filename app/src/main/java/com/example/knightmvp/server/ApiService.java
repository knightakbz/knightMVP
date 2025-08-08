package com.example.knightmvp.server;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("forecast")
    Call<String> getWeatherData();
    
    // Здесь можно добавить другие API методы
}
