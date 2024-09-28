package com.ultra.vision.movies.config;
import com.ultra.vision.series.object.EpisodeObject;
import java.util.*;
import com.ultra.vision.movies.object.*;

public interface TheMovieDatabase {
	
	public static interface getDetails{
		void onSucess(MovieObject movieObject);
		void onFailed(String error);
	}
	
	public static interface getUrlBanner{
		void onSucess(String response);
		void onFailed(String error);
	}
	
	public static interface getUrlTrailer{
		void onSucess(String response);
		void onFailed(String error);
	}
    
    public static interface getUrlLogo{
		void onSucess(String response);
		void onFailed(String error);
	}
    
    //Series
    public static interface getDetailsEpisodes{
        void onSucess(List<EpisodeObject> listEpisodes);
        void onFailed(String error);
    }
    
}
