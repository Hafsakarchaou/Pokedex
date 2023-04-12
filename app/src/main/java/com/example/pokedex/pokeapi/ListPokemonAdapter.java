package com.example.pokedex.pokeapi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.pokedex.DetailsActivity;
import com.example.pokedex.MainActivity;
import com.example.pokedex.R;
import com.example.pokedex.models.Pokemon;

import java.util.ArrayList;
public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder> {
    private ArrayList<Pokemon> dataset;
    private Context context;
    private OnItemClickListener listener;

    private String url = "\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/\" + p.getNumber() + \".png\"";

    public interface OnItemClickListener {
        void onItemClick(Pokemon pokemon);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public ListPokemonAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon p = dataset.get(position);
        holder.textViewName.setText(p.name);

        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumber() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageViewThumbnail);


    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void aditionaryListPokemon(ArrayList<Pokemon> listPokemon) {
        dataset.addAll(listPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewThumbnail;
        private TextView textViewName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewThumbnail = itemView.findViewById(R.id.fotoImageView);
            textViewName = itemView.findViewById(R.id.nombreTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Pokemon pokemon = dataset.get(position);
                        listener.onItemClick(pokemon);
                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra(MainActivity.POKI_NAME_PARAM, pokemon.name);
                        intent.putExtra(MainActivity.POKI_IMAGE_PARAM, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.getNumber() + ".png");
                        context.startActivity(intent);
                    }
                }
            });
        }
    }


}