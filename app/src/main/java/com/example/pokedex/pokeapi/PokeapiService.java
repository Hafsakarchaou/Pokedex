package com.example.pokedex.pokeapi;

import com.example.pokedex.models.Pokemon;
import com.example.pokedex.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeapiService {

    @GET("pokemon")
    Call<PokemonResponse> GetListPokemon(@Query("limit") int limit, @Query("offset") int offset);

    //@GET("pokemon/{name}")
    //Call<Integer> getPokemonWeight(@Path("name") String name);

    @GET("pokemon/{name}")
    Call<Pokemon> getPokemonDetails(@Path("name") String name);

}
