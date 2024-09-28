package com.ultra.vision.debug.connection;

import android.util.Log;
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

public class DebugConnection implements DebugConnectionCallback {

    private static final String TAG = DebugConnection.class.getSimpleName();
    private final String PAGE = "debug.php";
    private HashMap<String, String> HASHMAP;
    private DebugConnectionEvent EVENT;
    private String BUG;
    
    @Override
    public void onPreExecute() {
        
    }

    @Override
    public void onPostExecute(String result) {
        EVENT.response(result);
    }

    public DebugConnection(String bug, DebugConnectionEvent event) {
        //this.HASHMAP = hashMap;
        this.BUG = bug;
        this.EVENT = event;
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
                    URL url = new URL(HttpsUrlConnectionService.get() + "/debug/" + PAGE);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                        
                    
                    String postData = "bug=" + BUG; // Dados a serem enviados
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
    
    public static interface DebugConnectionEvent{
        void response(String s);
    }
}

    // Interface para definir callbacks
    interface DebugConnectionCallback {
        void onPreExecute();

        void onPostExecute(String result);
    }
