package com.ultra.vision.movies.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ultra.vision.account.UserObject;
import com.ultra.vision.adapter.CategoryAdapter;
import com.ultra.vision.databinding.FragmentHomeBinding;
import com.ultra.vision.movies.config.TheMovieDatabase;
import com.ultra.vision.movies.connection.MoviesGetConnection;
import com.ultra.vision.movies.connection.MoviesService;
import com.ultra.vision.movies.database.BDFilms;
import com.ultra.vision.movies.object.Category;
import com.ultra.vision.movies.object.Movie;
import com.ultra.vision.movies.object.MovieObject;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FragmentHome extends Fragment {

    private CategoryAdapter categoryAdapter;

    FragmentHomeBinding binding;

    //	List<String> categorias;
    List<Category> categoryList;

    public FragmentHome() {
        // Required empty public constructor
    }

    public static FragmentHome newInstance() {
        return new FragmentHome();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        if (getActivity() != null) {
            binding.progress.setVisibility(View.VISIBLE);
            loadMovies();
            
        }

        return binding.getRoot();
    }

    private void loadMovies() {
        HashMap<String, String> hash = new HashMap<>();
        hash.put("user", UserObject.USER.getName());
        hash.put("pass", UserObject.USER.getPass());

        MoviesGetConnection moviesConn =
                new MoviesGetConnection(
                        hash,
                        new MoviesGetConnection.MoviesGetConnectionEvent() {
                            @Override
                            public void onSucess(List<Category> categories) {
                                // TODO: Implement this method
                                getActivity().runOnUiThread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                categoryList = categories;
                                                setDestaque(categoryList);
                                            }
                                        });
                            }

                            @Override
                            public void onFailed(String error) {
                                getActivity().runOnUiThread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast(error);
                                            }
                                        });
                            }
                        });
    }

    public void Toast(String s) {
        Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private void setDestaque(List<Category> list) {

        Category category = null;

        // int index = list.indexOf("Filmes | Lancamentos");

        // if (index != -1) {
        // category = list.get(index);
        //  }else category = list.get(3);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTitle().equalsIgnoreCase(Category.lancamentos)) {
                category = list.get(i);
                break;
            } else category = list.get(1);
        }

        Movie randomMovie = getRandomMovie(category.getMovieList());

        MoviesService movieS =
                new MoviesService(
                        new TheMovieDatabase.getDetails() {

                            @Override
                            public void onSucess(MovieObject movieObject) {
                                CategoryAdapter.movieObject = movieObject;
                                setImageBanner(movieObject.backdropPath);
                            }

                            @Override
                            public void onFailed(String error) {
                                Toast.makeText(getActivity().getApplicationContext(), "Falha", Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
        movieS.execute(limparTitulo(randomMovie.getNome()), randomMovie.getType());
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

    private static Movie getRandomMovie(List<Movie> movieList) {
        Random random = new Random();
        int randomIndex = random.nextInt(movieList.size()); // Gera um índice aleatório

        return movieList.get(randomIndex); // Retorna o objeto Movie na posição aleatória
    }

    private void setImageBanner(final String posterPath) {

        // CategoryAdapter.imageViewBanner = bit;

        CategoryAdapter.urlBannerPosterPath = posterPath;

        // rltMenu.setVisibility(View.VISIBLE);
        //  lnrLoading.setVisibility(View.GONE);
        BDFilms.setCategoryList(categoryList);
        
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            binding.recyclerView.setLayoutManager(layoutManager);
            categoryAdapter = new CategoryAdapter(getActivity(), BDFilms.getCategoryList());
            binding.recyclerView.setAdapter(categoryAdapter);
        
        binding.progress.setVisibility(View.GONE);
    }

}
