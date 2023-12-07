package com.example.practicapokeapi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonViewModel extends ViewModel {
    private MutableLiveData<List<Pokemon>> pokemonLiveData = new MutableLiveData<>();
    private PokeApiService pokeApiService; // Inicializa esto con Retrofit

    public PokemonViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pokeApiService = retrofit.create(PokeApiService.class);
    }


    public LiveData<List<Pokemon>> getPokemonLiveData() {
        return pokemonLiveData;
    }

    public void fetchPokemonList(int limit, int offset) {
        pokeApiService.getPokemonList(limit, offset).enqueue(new Callback<PokemonList>() {
            @Override
            public void onResponse(Call<PokemonList> call, Response<PokemonList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pokemonLiveData.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<PokemonList> call, Throwable t) {
                // Manejar errores
            }
        });
    }
}



