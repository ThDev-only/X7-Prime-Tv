package com.ultra.vision.tv.connection;

import android.util.Log;
import com.ultra.vision.settings.HttpsUrlConnectionService;
import com.ultra.vision.tv.database.BDCategories;
import com.ultra.vision.tv.object.TvCategory;
import com.ultra.vision.tv.object.TvChannel;
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

public class TvChannelsConnection implements TvChannelsCallback {

    private static final String TAG = TvChannelsConnection.class.getSimpleName();
    private final String PAGE = "get_channels.php";
    private HashMap<String, String> HASHMAP;
    
    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(String result) {
        try {
            JSONArray jsonArray = new JSONArray(/*Seu JSON aqui*/ );

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                int num = obj.getInt("num");
                String name = obj.getString("name");
                String streamType = obj.getString("stream_type");
                int streamId = obj.getInt("stream_id");
                String streamIcon = obj.getString("stream_icon");
                String epgChannelId = obj.optString("epg_channel_id");
                String added = obj.getString("added");
                String isAdult = obj.getString("is_adult");
                String categoryId = obj.getString("category_id");
                String customSid = obj.getString("custom_sid");
                int tvArchive = obj.getInt("tv_archive");
                String directSource = obj.getString("direct_source");
                int tvArchiveDuration = obj.getInt("tv_archive_duration");

                TvChannel tvChannel =
                        new TvChannel(
                                num,
                                name,
                                streamType,
                                streamId,
                                streamIcon,
                                epgChannelId,
                                added,
                                isAdult,
                                categoryId,
                                customSid,
                                tvArchive,
                                directSource,
                                tvArchiveDuration);

                // Faça o que quiser com o objeto TvChannel aqui.
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error total", e.toString());
            // BDCategories.putError(e.toString());
        }
    }

    public TvChannelsConnection(HashMap<String, String> hashMap) {
        this.HASHMAP = hashMap;
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
                    URL url = new URL(HttpsUrlConnectionService.get() + "/stream/get/" + PAGE);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);

                    OutputStream outputStream = connection.getOutputStream();
                        
                    BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(outputStream, "UTF-8"));
                        
                    writer.write(getPostDataString(HASHMAP));
                    writer.flush();
                    writer.close();
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
}

    // Interface para definir callbacks
    interface TvChannelsCallback {
        void onPreExecute();

        void onPostExecute(String result);
    }