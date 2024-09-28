package com.ultra.vision.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ultra.vision.movies.MovieActivity;
import com.ultra.vision.movies.database.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ultra.vision.R;
import com.ultra.vision.movies.object.Movie;

import java.lang.ref.WeakReference;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movieList;
    private WeakReference<Activity> activityReference;
    private String mode;

    public MovieAdapter(Activity activity, List<Movie> movieList, String mode) {
        this.movieList = movieList;
        this.activityReference = new WeakReference<>(activity);
        this.mode = mode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
       /* view.requestFocus(View.FOCUS_UP);
		view.requestFocus(View.FOCUS_DOWN);
		view.requestFocus(View.FOCUS_LEFT);
		view.requestFocus(View.FOCUS_RIGHT);*/
        
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView movieImage;
        private ImageView moviePlayer;
        private TextView movieTitle;
        private TextView movieFormat;
        private Movie movie;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setBackgroundResource(R.drawable.ic_design_movies_selector);
            movieImage = itemView.findViewById(R.id.movieImage);
            moviePlayer = itemView.findViewById(R.id.moviePlayer);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieFormat = itemView.findViewById(R.id.movieFormat);
            
            CardView cardView = itemView.findViewById(R.id.movieCardView);
            //cardView.setLayoutParams(new LayoutParams(dp(130),dp(165)));
            cardView.setElevation(dp(10));
            cardView.setRadius(dp(7));
            cardView.setCardBackgroundColor(Color.WHITE);
            cardView.setMaxCardElevation(dp(10));
            cardView.setUseCompatPadding(true);

            itemView.setOnClickListener(this);

            itemView.setOnFocusChangeListener(onFocus);
        }

        public void bind(final Movie movie) {
            this.movie = movie;
            movieTitle.setText(movie.getNome());

            if (mode.equalsIgnoreCase("recents")) {
                moviePlayer.setVisibility(View.VISIBLE);
            } else {
                moviePlayer.setVisibility(View.GONE);
            }

            if(movie.getNome().contains("4K")){
                movieFormat.setText("4K");
                movieFormat.setBackgroundResource(R.drawable.ic_design_item_movie_format_4k);
                
            } else if(movie.getNome().contains("[L]") || movie.getNome().contains("[LEG]")){
                movieFormat.setText("L");
                movieFormat.setBackgroundResource(R.drawable.ic_design_item_movie_format_leg);
            }else movieFormat.setVisibility(View.GONE);
            
            String imageUrl = movie.getLogo(); // URL da imagem
            Activity activity = activityReference.get();
            if (activity != null) {
                RequestOptions options = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // Armazena cache em disco
                        .placeholder(R.drawable.iconapp) // Imagem de placeholder enquanto carrega
                        .error(R.drawable.ic_checkbox_off); // Imagem de erro, caso ocorra algum problema

                Glide.with(activity)
                        .load(imageUrl)
                        .apply(options)
                        .into(movieImage);
            }
        }

        @Override
        public void onClick(View v) {
            if (movie != null) {
                // Exibir o Toast com a categoria e o nome do filme
                //BDFilms bdFilms = HomeActivity.Bd
                
                Intent intent = null;

                String nome, categoria, logo, type;
                int id;
                         id = movie.getId();
                         nome = movie.getNome();
                         categoria = movie.getCategoria();
                         logo = movie.getLogo();
                         type = movie.getType();
                    
                         Movie movie2 = new Movie(id, nome, categoria, logo, type);
                    
                         intent = new Intent(activityReference.get(), MovieActivity.class);
                         intent.putExtra("db_movie", movie2);
                         activityReference.get().startActivity(intent);

                Toast.makeText(itemView.getContext(), "Filme: " + movie.getNome(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Foco TV
    View.OnFocusChangeListener onFocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                view.setSelected(true); // Altere a cor do componente dentro do item
            } else {
                view.setSelected(false); // Restaure a cor original do componente dentro do item
            }
        }
    };

    public final int dp(int value) {
        return (int) TypedValue.applyDimension(1, (float) value, activityReference.get().getResources().getDisplayMetrics());
    }

    public static String limparTitulo(String titulo) {
        // Remove o ano entre parênteses
        titulo = titulo.replaceAll("\\(\\d{4}\\)", "");

        // Remove o formato de vídeo (ex: 4K)
        titulo = titulo.replaceAll("\\b\\d{1,}K\\b", "");

        // Remove os marcadores entre colchetes (ex: [Hybrid], [L])
        titulo = titulo.replaceAll("\\[\\w+\\]", "");

        // Remove espaços em excesso
        titulo = titulo.trim();

        return titulo;
    }
}
