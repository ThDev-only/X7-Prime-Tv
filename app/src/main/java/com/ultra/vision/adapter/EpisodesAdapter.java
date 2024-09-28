package com.ultra.vision.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ultra.vision.connection.GetLinkFromName;
import com.ultra.vision.connection.URLConnection;
import com.ultra.vision.databinding.EpisodeItemBinding;
import com.ultra.vision.movies.database.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.ultra.vision.movies.object.Movie;
import com.ultra.vision.player.BDMediaPlayer;
import com.ultra.vision.series.object.EpisodeObject;
import java.lang.ref.WeakReference;
import java.util.List;
import com.ultra.vision.R;


public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.ViewHolder> {

    private WeakReference<Activity> activityReference;
    private Movie movie;
    private List<EpisodeObject> listEpisodes;

    public EpisodesAdapter(Activity activity, Movie movie, List<EpisodeObject> listEpisodes) {
        this.activityReference = new WeakReference<>(activity);
        this.movie = movie;
        this.listEpisodes = listEpisodes;
        setHasStableIds(true); // Adicione esta linha para manter os IDs estáveis
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EpisodeItemBinding binding = EpisodeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EpisodeObject episodeObject = listEpisodes.get(position);
        
        //if(position == 0){
                //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2,)
                //ViewGroup.LayoutParams lp = binding.getRoot().getLayoutParams();
                //holder.binding.getRoot().setX(dp(60));
          //  }
        
        holder.bind(episodeObject);
    }

    @Override
    public int getItemCount() {
        return listEpisodes.size();
    }

    @Override
    public long getItemId(int position) {
        return position; // Certifique-se de que os IDs sejam únicos e estáveis para cada item
    }
      public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public EpisodeItemBinding binding;
        private EpisodeObject episodeObject;

        public ViewHolder(EpisodeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            
            binding.getRoot().setBackgroundResource(R.drawable.ic_design_movies_selector);
            binding.getRoot().setOnClickListener(this);
            binding.getRoot().setOnFocusChangeListener(onFocus);
            
            binding.cardView.setElevation(dp(10));
            binding.cardView.setRadius(dp(4));
            //binding.cardView.setCardBackgroundColor(Color.WHITE);
            binding.cardView.setMaxCardElevation(dp(4));
            //binding.cardView.setUseCompatPadding(true);
            
        }

        public void bind(EpisodeObject episodeObject) {
            this.episodeObject = episodeObject;
            EpisodeObject.InfoEpisode ep = episodeObject.getEpisodeInfo();

            binding.title.setText(ep.getTitle());
            binding.sinopse.setText(ep.getSinopse());
            binding.duration.setText(formatHours(ep.getDuration()));
            binding.progress.setProgress(ep.getProgress());
            
            String baseUrl = "https://image.tmdb.org/t/p/";
            String posterSize = "original"; // Tamanho desejado do pôster

            String imageUrl = baseUrl + posterSize + ep.getBanner(); // URL da imagem
            Activity activity = activityReference.get();
            if (activity != null) {
                RequestOptions options = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // Armazena cache em disco
                        //.placeholder(R.drawable.) // Imagem de placeholder enquanto carrega
                        .error(R.drawable.ic_checkbox_off); // Imagem de erro, caso ocorra algum problema

                Glide.with(activity)
                        .load(imageUrl)
                        .apply(options)
                        .into(binding.image);
            }
                
                //}
           // binding.progress.setpr
        }

        @Override
        public void onClick(View v) {
            // Handle the click event here
            GetLinkFromName get = new GetLinkFromName(activityReference.get());
                                get.getLinkFromId(
                                        episodeObject.getId(),
                                        new URLConnection.Auth() {

                                            @Override
                                            public void onSucess(String response) {
                                                // BDFilms.Film.FILM.setLink(response);
                                                //movie.setLink(response);

                                                Intent intent =
                                                        new Intent(
                                                                activityReference.get(),
                                                                BDMediaPlayer.class);

                                                String nome, categoria, logo, type, link;
                                                int id = movie.getId();
                                                nome = movie.getNome();
                                                categoria = movie.getCategoria();
                                                logo = episodeObject.getEpisodeInfo().getBanner();
                                                type = movie.getType();
                                                link = response;

                                                Movie movie2 =
                                                        new Movie(id, nome, categoria, logo, type, link);

                                                intent.putExtra("db_player", movie2);
                                                activityReference.get().startActivity(intent);
                                                // BDFilms.Film.startMovie(BDFilms.Film.FILM,
                                                // MovieActivity.this);
                                                //Toast.makeText(MovieActivity.this,"teste" + movie.getId(), Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onFailed(String error) {
                                                // TODO: Implement this method
                                            }
                                        },
                                        "/series/get/get_link.php");
            Toast.makeText(activityReference.get(), episodeObject.getEpisodeInfo().getTitle(), Toast.LENGTH_LONG).show();
            
        }

        public String formatHours(int minutes) {
            int hours = minutes / 60;
            int remainingMinutes = minutes % 60;

            String formattedText = String.format("%02d:%02d:%02d", hours, remainingMinutes, 0);

            return formattedText;
        }
    }

    //Other methods and classes remain the same
    // ...


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
