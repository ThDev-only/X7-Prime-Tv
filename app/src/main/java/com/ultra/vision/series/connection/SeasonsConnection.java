package com.ultra.vision.series.connection;

import android.util.Log;

import com.ultra.vision.series.object.SeasonObject;
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

public class SeasonsConnection implements SeasonsConnectionCallback {

    private static final String TAG = SeasonsConnection.class.getSimpleName();
    private final String PAGE = "get_seasons.php";
    private HashMap<String, String> HASHMAP;
    private SeasonsConnectionEvent EVENT;
    
    @Override
    public void onPreExecute() {
       // EVENT.onFailed("Caiu no preExecute");
    }

    @Override
    public void onPostExecute(String result) {
        if(result != null){
            try{
                JSONArray jsonArray = new JSONArray(result);
                List<SeasonObject> listSeasons = new ArrayList<SeasonObject>();
            
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    
                    int id = jsonObject.getInt("id");
                    int numSeason = jsonObject.getInt("season");
                    
                    SeasonObject seasonObject = new SeasonObject(id, numSeason);
                    
                    listSeasons.add(seasonObject);
                }
                
               /* if(listSeasons.size() > 0){
                    
                }*/
                
                EVENT.onSucess(listSeasons);
                
            }catch(JSONException e){
                e.printStackTrace();
                EVENT.onFailed(e.toString() + "teste " + result);
            }
        }else EVENT.onFailed(""+result);
    }

    public SeasonsConnection(HashMap<String, String> hashmap, SeasonsConnectionEvent event) {
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
    
    public static interface SeasonsConnectionEvent{
        void onSucess(List<SeasonObject> seasonObject);
        void onFailed(String error);
    }
}

    // Interface para definir callbacks
    interface SeasonsConnectionCallback {
        void onPreExecute();

        void onPostExecute(String result);
    }
