package com.ultra.vision.movies.connection;

import android.os.*;
import android.util.*;
import com.ultra.vision.movies.database.BDFilms;
import com.ultra.vision.settings.*;
import com.ultra.vision.tv.object.Canal;
import java.io.*;
import java.net.*;
import com.ultra.vision.movies.config.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviesSearchConnection extends AsyncTask<String, Void, String> {

    private static final String TAG = MoviesBannerConnection.class.getSimpleName();
    //private TheMovieDatabase.getUrlBanner listener;
    private String user, pass;
    public MoviesSearchConnection() {
       // this.listener = listener;
        
    }
    
    public void searchMovie(String user, String pass, String name){
        this.user = user;
        this.pass = pass;
        execute(name);
    }

    @Override
    protected String doInBackground(String... params) {
        String movieTitle = params[0];
        String response = "";
        HttpURLConnection connection = null;

        try {
            URL url = new URL(HttpsUrlConnectionService.get() + "/movies/get/get_movie_search.php");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String postData = "movie_search=" + movieTitle + "&user="  + user + "&pass=" + pass;// Dados a serem enviados
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
            Log.e(TAG, "Error fetching data from API: " + e.toString());
        }finally{
            if (connection != null) {
			connection.disconnect();
                }
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        if(response != null){
            try{
               handleFilmResponse(response, new BDFilms());
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

    private void handleFilmResponse(String result, BDFilms bdFilms) throws JSONException {
    JSONArray jsonArray = new JSONArray(result);
    List<Canal> canais = new ArrayList<>();

    for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        int id = jsonObject.getInt("id");
        String nome = jsonObject.getString("nome");
        String logo = jsonObject.getString("logo");
        String categoria = jsonObject.getString("categoria");
        String type = jsonObject.getString("type");
        Canal canal = new Canal(id, nome, logo, categoria, type);
        canais.add(canal);
    }

    if (!canais.isEmpty()) {
        for (Canal canal : canais) {
            bdFilms.addId(canal.getId());
            bdFilms.addName(canal.getNome());
            bdFilms.addContext(canal.getCategoria());
            bdFilms.addLogo(canal.getLogo());
            bdFilms.addType(canal.getType());
        }
        BDFilms.putSearch(bdFilms);
    } else {
            Log.e("Error", "empty response");
        //Toast.makeText(CONTEXT, "Empty response", Toast.LENGTH_SHORT).show();
    }
}
}

