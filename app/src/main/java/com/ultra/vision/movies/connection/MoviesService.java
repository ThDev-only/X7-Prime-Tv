package com.ultra.vision.movies.connection;

import android.os.*;
import android.util.*;
import com.ultra.vision.movies.config.*;
import com.ultra.vision.settings.*;
import java.io.*;
import java.net.*;
import org.json.*;
import com.ultra.vision.movies.*;
import com.ultra.vision.movies.object.*;

public class MoviesService extends AsyncTask<String, Void, String> {

    private static final String TAG = MoviesService.class.getSimpleName();
    private TheMovieDatabase.getDetails listener;
    private String movieType;

    public MoviesService(TheMovieDatabase.getDetails listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String movieTitle = params[0];
        this.movieType = params[1];
        String response = "";
        HttpURLConnection connection = null;

        try {
            URL url = new URL(HttpsUrlConnectionService.get() + getUrl(movieType));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String postData = "movie_search=" + movieTitle;// Dados a serem enviados
            byte[] postDataBytes = postData.getBytes("UTF-8");

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(postDataBytes);
            outputStream.flush();
            outputStream.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            response = stringBuilder.toString();

            reader.close();
            //connection.disconnect();
        } catch (IOException e) {
            Log.e(TAG, "Error fetching data from API: " + e.getMessage());
        }finally{
            if (connection != null) {
			connection.disconnect();
                }
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        if (listener != null) {
            
            if(movieType.equalsIgnoreCase("filme")){
                getJsonMovie(response);
            }else{
                getJsonSerie(response);
            }
            
            
        /*    
            try {
    JSONObject jsonObject = new JSONObject(response);
    int page = jsonObject.getInt("page");
    int totalResults = jsonObject.getInt("total_results");

    System.out.println("Page: " + page);
    System.out.println("Total Results: " + totalResults);

    JSONArray results = jsonObject.getJSONArray("results");

    if (results.length() > 0) {
        JSONObject result = results.getJSONObject(0);
        String title = result.has("title") ? result.getString("title") : "";
        String overview = result.has("overview") ? result.getString("overview") : "";
        String posterPath = result.has("poster_path") ? result.getString("poster_path") : "";
        String backdropPath = result.has("backdrop_path") ? result.getString("backdrop_path") : "";
        String voteAverage = result.has("vote_average") ? result.getString("vote_average") : "";
        String popularity = result.has("popularity") ? result.getString("popularity") : "";
        String voteCount = result.has("vote_count") ? result.getString("vote_count") : "";
        boolean video = result.has("video") && result.getBoolean("video");
        int id = result.has("id") ? result.getInt("id") : 0;

        MovieObject movieObject = new MovieObject(id, title, overview, posterPath, backdropPath, voteAverage, popularity, voteCount, video);

        listener.onSucess(movieObject);
    } else {
        // Handle the case when no results are present
    }

} catch (JSONException e) {
    e.printStackTrace();
    Log.e("JsonExceptionError", e.toString());
}
            
        }*/}
    }
        private void getJsonMovie(String response){
            try
			{
				JSONObject jsonObject = new JSONObject(response);
				int page = jsonObject.getInt("page");
				int totalResults = jsonObject.getInt("total_results");

				System.out.println("Page: " + page);
				System.out.println("Total Results: " + totalResults);

				JSONArray results = jsonObject.getJSONArray("results");

				
				JSONObject result = results.getJSONObject(0);
				String title = result.getString("title");
				String overview = result.getString("overview");
				String posterPath = result.getString("poster_path");
				String backdropPath = result.getString("backdrop_path");
				String voteAverage = result.getString("vote_average");
				String popularity = result.getString("popularity");
				String voteCount = result.getString("vote_count");
				boolean video = result.getBoolean("video");
				int id = result.getInt("id");
				
				MovieObject movieObject = new MovieObject(id,title, overview, posterPath, backdropPath, voteAverage, popularity, voteCount, video);
				
				listener.onSucess(movieObject);
				
			}
			catch (JSONException e)
			{
				e.printStackTrace();
                Log.e("JsonExceptionError", e.toString());
			}
        }
    
        private void getJsonSerie(String response){
            try {
            JSONObject jsonObject = new JSONObject(response);
            //boolean adult = jsonObject.getBoolean("adult");
            String backdropPath = jsonObject.getString("backdrop_path");
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            String originalName = jsonObject.getString("original_name");
            String overview = jsonObject.getString("overview");
            String posterPath = jsonObject.getString("poster_path");
            String voteAverage = jsonObject.getString("vote_average");
			String popularity = jsonObject.getString("popularity");
            String voteCount = jsonObject.getString("vote_count");
            String filePath = jsonObject.getString("file_path");
            // and so on for other fields

           // System.out.println("Adult: " + adult);
            System.out.println("Backdrop Path: " + backdropPath);
            System.out.println("ID: " + id);
            System.out.println("Original Name: " + originalName);
            // print other fields accordingly
            
            MovieObject movieObject = new MovieObject(id, name, originalName, overview, posterPath, backdropPath, voteAverage, popularity, voteCount, false, filePath);

        listener.onSucess(movieObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        }
    
    private String getUrl(String type){
        
        String i = null;
        
        if(type.equalsIgnoreCase("filme")){
            i = "/movies/get/get_info_movie.php";
        }else if(type.equalsIgnoreCase("serie")){
            i = "/series/get/get_serie.php";
        }
        
        return i;
    }

}

