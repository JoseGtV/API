package com.example.api.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCliente {
    private static final String BASE_URL = "https://66453e05b8925626f8916046.mockapi.io/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static UsuarioService getUsuarioService()
    {
        return getClient().create(UsuarioService.class);
    }
}
