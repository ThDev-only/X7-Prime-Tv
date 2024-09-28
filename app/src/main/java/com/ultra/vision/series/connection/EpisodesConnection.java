package com.ultra.vision.series.connection;

import android.util.Log;

import com.ultra.vision.series.object.EpisodeObject;
import com.ultra.vision.settings.HttpsUrlConnectionService;
import com.ultra.vision.tv.database.BDCategories;
import com.ultra.vision.tv.object.TvCategory;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EpisodesConnection implements EpisodesConnectionCallback {

    private static final String TAG = EpisodesConnection.class.getSimpleName();
    private final String PAGE = "get_episodes.php";
    private HashMap<String, String> HASHMAP;
    private EpisodesConnectionEvent EVENT;
    
    @Override
    public void onPreExecute() {
       // EVENT.onFailed("Caiu no preExecute");
    }

    @Override
    public void onPostExecute(String result) {
        if(result != null){
            try{
                JSONArray jsonArray = new JSONArray(result);
                List<EpisodeObject> listEpisodes = new ArrayList<EpisodeObject>();
            
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    
                    int id = jsonObject.getInt("id");
                    int numEpisode = jsonObject.getInt("episode");
                    //parou aqui as 6:13
                    EpisodeObject EpisodeObject = new EpisodeObject(id, numEpisode);
                    
                    listEpisodes.add(EpisodeObject);
                }
                
               /* if(listEpisodes.size() > 0){
                    
                }*/
                
                EVENT.onSucess(listEpisodes);
                
            }catch(JSONException e){
                e.printStackTrace();
                EVENT.onFailed(e.toString());
            }
        }else EVENT.onFailed(""+result);
    }

    public EpisodesConnection(HashMap<String, String> hashmap, EpisodesConnectionEvent event) {
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
                    URL url = new URL(HttpsUrlConnectionService.get() + "/series/get/" + PAGE);
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
    
    public static interface EpisodesConnectionEvent{
        void onSucess(List<EpisodeObject> EpisodeObject);
        void onFailed(String error);
    }
}

    // Interface para definir callbacks
    interface EpisodesConnectionCallback {
        void onPreExecute();

        void onPostExecute(String result);
    }
