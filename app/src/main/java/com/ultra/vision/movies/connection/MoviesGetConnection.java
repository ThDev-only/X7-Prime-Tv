package com.ultra.vision.movies.connection;

import android.util.Log;

import com.ultra.vision.movies.object.Category;
import com.ultra.vision.movies.object.Movie;
import com.ultra.vision.series.object.EpisodeObject;
import com.ultra.vision.settings.HttpsUrlConnectionService;
import com.ultra.vision.tv.database.BDCategories;
import com.ultra.vision.tv.object.TvCategory;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviesGetConnection implements MoviesGetConnectionCallback {

    private static final String TAG = MoviesGetConnection.class.getSimpleName();
    private final String PAGE = "get_movies.php";
    private HashMap<String, String> HASHMAP;
    private MoviesGetConnectionEvent EVENT;
    
    @Override
    public void onPreExecute() {
       // EVENT.onFailed("Caiu no preExecute");
    }

    @Override
    public void onPostExecute(String result) {
        
        if (result != null) {
            try {
                // Substitua "seu_json_aqui" pelo JSON fornecido

                // Criar um JSONObject a partir da string JSON
                JSONObject jsonObject = new JSONObject(result);

                // Iterar sobre as chaves (categorias) no objeto JSON
                Iterator<String> keys = jsonObject.keys();

                List<Category> categories = new ArrayList<Category>();
                List<Movie> movies = null;

                while (keys.hasNext()) {
                    String category = keys.next();
                    // categories = new ArrayList<Category>();
                    movies = new ArrayList<Movie>();

                    // Obter o array de filmes para a categoria atual
                    JSONArray filmesArray = jsonObject.getJSONArray(category);

                    // Iterar sobre os filmes na categoria
                    for (int i = 0; i < filmesArray.length(); i++) {
                        // Obter o objeto JSON do filme
                        JSONObject filmeObj = filmesArray.getJSONObject(i);

                        int id = filmeObj.getInt("id");
                        String name = filmeObj.getString("nome");
                        String logo = filmeObj.getString("logo");
                        String type = filmeObj.getString("type");
                        String categoryy = filmeObj.getString("category");
                        movies.add(new Movie(id, name, category, logo, type));
                    }

                    categories.add(new Category(category, "category_default", movies));
                }

                if (categories.size() > 0) {
                    EVENT.onSucess(categories);
                  } else EVENT.onFailed("Lista vazia");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else EVENT.onFailed("" + result);
    }

    public MoviesGetConnection(HashMap<String, String> hashmap, MoviesGetConnectionEvent event) {
        //this.HASHMAP = hashMap;
        this.HASHMAP = hashmap;
        this.EVENT = event;
        executeRequest();
    }

    public void executeRequest() {
        // Antes de iniciar a tarefa em segundo plano
        onPreExecute();

        // Crie um Executor (normalmente em um objeto de aplicativo ou classe de utilitário)
        Executor executor = Executors.newSingleThreadExecutor();

        // Execute a tarefa em segundo plano usando o Executor
        executor.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                String response = "";

                try {
                    URL url = new URL(HttpsUrlConnectionService.get() + "/movies/get/" + PAGE);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                        
                    
                     // Enviando os dados
                    OutputStream os = connection.getOutputStream();
                     BufferedWriter writer = new BufferedWriter(
                      new OutputStreamWriter(os, "UTF-8"));

                      writer.write(getPostDataString(HASHMAP));

                      writer.flush();
                      writer.close();
                      os.close();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    response = stringBuilder.toString();

                    reader.close();
                } catch (Exception e) {
                    Log.e(TAG, "Error fetching data from API: " + e.getMessage());
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }

                    // Após a conclusão da tarefa em segundo plano
                    onPostExecute(response);
                }
            }
        });
    }
    
    private String getPostDataString(Map<String, String> params) throws Exception {
    StringBuilder result = new StringBuilder();
    boolean first = true;
    for (Map.Entry<String, String> entry : params.entrySet()) {
      if (first)
        first = false;
      else
        result.append("&");

      result.append(entry.getKey());
      result.append("=");
      result.append(entry.getValue());
    }

    return result.toString();
  }
    
    public static interface MoviesGetConnectionEvent{
        void onSucess(List<Category> categories);
        void onFailed(String error);
    }
}

    // Interface para definir callbacks
    interface MoviesGetConnectionCallback {
        void onPreExecute();

        void onPostExecute(String result);
    }
