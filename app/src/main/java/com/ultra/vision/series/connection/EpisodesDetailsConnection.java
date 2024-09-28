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

public class EpisodesDetailsConnection implements EpisodesDetailsConnectionCallback {

    private static final String TAG = EpisodesDetailsConnection.class.getSimpleName();
    private final String PAGE = "get_details_episodes.php";
    private HashMap<String, String> HASHMAP;
    private EpisodesDetailsConnectionEvent EVENT;
    private List<EpisodeObject> listEpisodes;
    
    @Override
    public void onPreExecute() {
       // EVENT.onFailed("Caiu no preExecute");
    }

    @Override
    public void onPostExecute(String result) {
        if(result != null){
            try{
                JSONArray jsonArray = new JSONArray(result);
                List<EpisodeObject> listEpisodes2 = new ArrayList<>();
                
             /*   int listSize =
                 (listEpisodes.size() <= jsonArray.length())
                 ? listEpisodes.size() : jsonArray.length();*/
            
                for(int i = 0; i < listEpisodes.size(); i++){
                    
                    EpisodeObject ep = listEpisodes.get(i);
                    
                    for(int x = 0; x < jsonArray.length(); x++){
                        
                        JSONObject jsonObject = jsonArray.getJSONObject(x);
                    
                    final int number = jsonObject.getInt("episode_number");
                    
                    
                    if(ep.getNumber() == number){
                        String title = number + "." + jsonObject.getString("episode_name");
                        String overview = jsonObject.getString("episode_description");
                        Double vote = jsonObject.getDouble("episode_vote");
                        String banner = jsonObject.getString("episode_banner");
                        int duration = jsonObject.getInt("episode_duration");
                    
                        EpisodeObject.InfoEpisode EpisodeInfoObject = new EpisodeObject.InfoEpisode(title, overview, banner, duration, 0, number, vote);
                    
                        EpisodeObject episodeObject = listEpisodes.get(i);
                    
                        episodeObject.setEpisodeInfo(EpisodeInfoObject);
                    
                        listEpisodes2.add(episodeObject);
                    }
                        
                    }
                    
                    
                }
                
               /* if(listEpisodes.size() > 0){
                    
                }*/
                
                EVENT.onSucess(listEpisodes2);
                
            }catch(JSONException e){
                e.printStackTrace();
                EVENT.onFailed(e.toString());
            }
        }else EVENT.onFailed(""+result);
    }

    public EpisodesDetailsConnection(List<EpisodeObject> listEpisodes, HashMap<String, String> hashmap, EpisodesDetailsConnectionEvent event) {
        this.listEpisodes = listEpisodes;
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
    
    public static interface EpisodesDetailsConnectionEvent{
        void onSucess(List<EpisodeObject> EpisodeObject);
        void onFailed(String error);
    }
}

    // Interface para definir callbacks
    interface EpisodesDetailsConnectionCallback {
        void onPreExecute();

        void onPostExecute(String result);
    }
