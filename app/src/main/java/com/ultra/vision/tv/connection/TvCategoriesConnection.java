package com.ultra.vision.tv.connection;

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

public class TvCategoriesConnection implements TvCategoriesCallback {

    private static final String TAG = TvCategoriesConnection.class.getSimpleName();
    private final String PAGE = "get_categories.php";
    private HashMap<String, String> HASHMAP;
    
    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(String result) {
        try {
        JSONArray jsonArray = new JSONArray(result);

        // Crie uma lista ou estrutura de dados para armazenar as categorias
        List<TvCategory> categoriesList = new ArrayList<>();

        // Loop através dos elementos do JSONArray
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String categoryId = jsonObject.getString("category_id");
            String categoryName = jsonObject.getString("category_name");
            int parentId = jsonObject.getInt("parent_id");

            // Crie uma instância de TvCategory com os dados
            TvCategory category = new TvCategory(categoryId, categoryName, parentId);

            // Adicione a categoria à lista
            categoriesList.add(category);
            
        }

        BDCategories.putCategories(categoriesList);  
            
        // Agora você tem a lista de categorias preenchida com os dados do JSONArray
        // categoriesList contém todas as categorias do JSONArray

        // Exemplo de como acessar as categorias:
        /*for (TvCategory category : categoriesList) {
            String categoryId = category.getCategoryId();
            String categoryName = category.getCategoryName();
            int parentId = category.getParentId();

            // Faça algo com os dados da categoria, como exibir em uma lista
        }*/
    } catch (JSONException e) {
        e.printStackTrace();
            Log.e("Error total", e.toString());
       // BDCategories.putError(e.toString());
    }
    }

    public TvCategoriesConnection(HashMap<String, String> hashMap) {
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
    interface TvCategoriesCallback {
        void onPreExecute();

        void onPostExecute(String result);
    }