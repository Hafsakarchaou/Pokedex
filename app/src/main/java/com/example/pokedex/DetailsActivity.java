package com.example.pokedex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pokedex.models.Pokemon;
import com.example.pokedex.pokeapi.PokeapiService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {
    private PokeapiService pokeapiService;
    private TextView textViewWeight;
    private TextView textViewHeight;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.POKI_NAME_PARAM);
        String imageUrl = intent.getStringExtra("pokemonImageUrl");
        setTitle("Pokemon infos");
        TextView textViewNameP = findViewById(R.id.textViewNameP);
        textViewNameP.setText(name);
        ImageView imageView=findViewById(R.id.imagePoki);
        Glide.with(this).load(imageUrl).into(imageView);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // Use the bitmap here
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                int color = palette.getDominantColor(ContextCompat.getColor(DetailsActivity.this, R.color.black));
                                CardView cardView = findViewById(R.id.d1);
                                cardView.setCardBackgroundColor(color);
                            }
                        });
                    }
                });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokeapiService pokeapiService = retrofit.create(PokeapiService.class);
        textViewWeight = findViewById(R.id.textViewWeight);
        textViewHeight = findViewById(R.id.textViewHeightP);

        Call<Pokemon> call = pokeapiService.getPokemonDetails(name);

        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful()) {

                    int weight = response.body().getWeight();
                    int height = response.body().getHeight();


                    textViewWeight.setText(String.format("%d", weight));
                    textViewHeight.setText(String.format("%d", height));
                } else {
                    Log.e("DetailsActivity", "API call failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("DetailsActivity", "API call failed: " + t.getMessage());
            }
        });

    }
}
