package com.ultra.vision.adapter;

/*
	PARENT{HomeActivity$Search}
	BY{recyclerViewSearch}
	
	adapter functions:
	
 		this class can create a keyboard in the recyclerView
		mentioned above,we use it to make the movie search 
 		keyboard
 
*/

import android.app.Activity;
import androidx.annotation.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import com.ultra.vision.databinding.AdapterKeyboardBinding;
import com.ultra.vision.movies.database.BDFilms;
import com.ultra.vision.movies.object.Movie;
import java.lang.ref.WeakReference;
import java.util.*;
import androidx.recyclerview.widget.*;
import com.ultra.vision.R;

public class KeyboardAdapter extends RecyclerView.Adapter<KeyboardAdapter.ViewHolder> {

    String[] keys = new String[]
	{
		"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
		"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
         "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
	};
	
	private TextView txtSearch;
	
    private Activity activity;
    
    private RecyclerView result;
    
    private ProgressBar loading;
    
    private WeakReference<Activity> weak;
    
    private boolean canSearch = true;
    
    private static AdapterKeyboardBinding binding;

    public KeyboardAdapter(Activity activity, RecyclerView result, ProgressBar loading, TextView txtSearch) {
       // this.context = context;
		this.txtSearch = txtSearch;
        this.result = result;
        this.loading = loading;
        this.weak = new WeakReference<>(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = AdapterKeyboardBinding.inflate(weak.get().getLayoutInflater());
        //View view = LayoutInflater.from(weak.get()).inflate(R.layout.custom_keyboard, parent, false);
        
        binding.getRoot().setFocusable(true);
        binding.getRoot().setFocusableInTouchMode(true);
        //binding.getRoot().requestFocus();
        
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String key = keys[position];
        holder.keyTextView.setText(key);

        // Add any other customization or click listeners here if needed
		holder.keyTextView.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					String keySearch = txtSearch.getText().toString();
					if(keySearch.contains("Mais Pesquisados")){
						keySearch = "";
						//txtSearch.setText(keySearch);
						}
                    
                    String realSearch = keySearch + keys[position];
                    
					txtSearch.setText(realSearch);
                    
                    if(realSearch.length() >= 3){
                        searchMovie(realSearch);
                    }
                    
					
				}

			
		});
    }

    @Override
    public int getItemCount() {
        return keys.length;
    }

    private void searchMovie(String nome){
        if(canSearch){
           canSearch = false;
            
            loading.setVisibility(View.VISIBLE);
            result.setVisibility(View.GONE);
            BDFilms bdFilms = new BDFilms();
        bdFilms.searchFilmsList(nome, new BDFilms.getAllFilms(){
            @Override
            public void getFilms(BDFilms film) {
                // TODO: Implement this method
                    String movieName, movieCategory, movieLogo, movieType;
                    int movieId;     
                    List<Movie> listResult = new ArrayList<>();
                    
                    for(int i = 0; i < film.getContext().size(); i++){
                        movieId = film.getId().get(i);
                        movieName = film.getName().get(i);
                        movieCategory = film.getContext().get(i);
                        movieLogo = film.getLogo().get(i);
                        movieType = film.getType().get(i);
                        
                        listResult.add(new Movie(movieId, movieName, movieCategory, movieLogo, movieType));
                    }
                   
                  // if(listResult.size()){
                      // if(!listResult.isEmpty()){
                        
                           MovieAdapter m = new MovieAdapter(weak.get(), listResult, "default");
                        result.setAdapter(m);
                        loading.setVisibility(View.GONE);
                        result.setVisibility(View.VISIBLE);
                     //  }
                           //Toast.makeText(weak.get(), listResult.size(), Toast.LENGTH_SHORT).show();
                        canSearch = true;
        //           }
            }
            
        });
        }
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView keyTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            keyTextView = binding.key;

            itemView.setBackgroundResource(R.drawable.ic_keyboard_selector);
            itemView.setOnFocusChangeListener(onFocus);
            //itemView.requestFocus();
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

    }
}
