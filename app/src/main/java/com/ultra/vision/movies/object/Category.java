package com.ultra.vision.movies.object;

import android.graphics.*;
import java.util.*;

public class Category 
 {
    private String title;
	private String type;
	public static String recent = "Continue Assistindo";
    public static String lancamentos = "Lancamentos";
    private List<Movie> movieList;

    public Category(String title, String type, List<Movie> movieList) {
        this.title = title;
		this.type = type;
        this.movieList = movieList;
    }

    public String getTitle() {
        return title;
    }
	
	public String getType() {
        return type;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
    
}

