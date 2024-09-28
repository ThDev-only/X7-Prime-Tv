package com.ultra.vision.movies.fragments;

import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ultra.vision.adapter.KeyboardAdapter;
import com.ultra.vision.adapter.MovieAdapter;
import com.ultra.vision.databinding.FragmentSearchBinding;
import com.ultra.vision.movies.database.BDFilms;
import com.ultra.vision.movies.object.Category;
import com.ultra.vision.movies.object.Movie;
import java.util.List;

public class FragmentSearch extends Fragment {

    FragmentSearchBinding binding;
    
    public FragmentSearch() {
        // Required empty public constructor
    }

    public static FragmentSearch newInstance() {
        return new FragmentSearch();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar o layout da tela inicial (activity_home.xml)
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        binding.progress.setVisibility(View.VISIBLE);
        binding.layoutSearch.setVisibility(View.GONE);
        
		binding.btnKeySpace.setOnClickListener(v ->{
            String space = binding.textViewSearch.getText().toString();
			binding.textViewSearch.setText(space + " ");
        });


		binding.btnKeyDelete.setOnClickListener(v ->{
            String keyTitle = binding.textViewSearch.getText().toString();
					if(!(keyTitle.isEmpty())){
						String newTitle = keyTitle.substring(0, keyTitle.length() - 1);
						binding.textViewSearch.setText(newTitle);
					}
        });

        new Handler()
                .postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                if(getActivity() != null){
                                    int numberOfColumns = 6; // Define o número de colunas desejado

                                GridLayoutManager layoutManager =
                                        new GridLayoutManager(
                                                getActivity().getApplicationContext(),
                                                numberOfColumns,
                                                RecyclerView.VERTICAL,
                                                false);

                                binding.recyclerViewKeyboard.setLayoutManager(layoutManager);

                                binding.recyclerViewKeyboard.setAdapter(
                                        new KeyboardAdapter(
                                                getActivity(),
                                                binding.recyclerViewResult,
                                                binding.progressBarLoading,
                                                binding.textViewSearch));

                                Category category1 = BDFilms.getCategoryList().get(2);
                                List<Movie> movieList0 = category1.getMovieList();

                                MovieAdapter m =
                                        new MovieAdapter(getActivity(), movieList0, "default");

                                // Use um novo LayoutManager para recyclerViewResultMoviesSearch com
                                // um número diferente de colunas
                                int numberOfColumnsMovies =
                                        4; // Defina o número de colunas desejado para
                                           // recyclerViewResultMoviesSearch
                                GridLayoutManager layoutManagerMovies =
                                        new GridLayoutManager(
                                                getActivity().getApplicationContext(),
                                                numberOfColumnsMovies,
                                                RecyclerView.VERTICAL,
                                                false);

                                // recyclerViewResultMoviesSearch.setLayoutManager(layoutManagerMovies);

                                binding.recyclerViewResult.setLayoutManager(layoutManagerMovies);
                                binding.recyclerViewResult.setAdapter(m);
                        
                                binding.progress.setVisibility(View.GONE);
                                binding.layoutSearch.setVisibility(View.VISIBLE);
                                }
                            }
                        },
                        4000);

        return binding.getRoot();
    }

     
}