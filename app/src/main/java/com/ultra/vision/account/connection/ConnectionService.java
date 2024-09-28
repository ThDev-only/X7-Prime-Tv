package com.ultra.vision.account.connection;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.widget.*;
import com.ultra.vision.account.*;
import com.ultra.vision.connection.*;
import com.ultra.vision.movies.database.*;
import com.ultra.vision.settings.*;
import com.ultra.vision.tv.database.*;
import com.ultra.vision.tv.object.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.*;
import org.json.*;


public class ConnectionService extends AsyncTask<Void, Void, String> implements iConnection, Account.get {

	@Override
	public HashMap<String, String> hash() {
		return hash_map_result;
	}
	

	@Override
	public void onStart(Context context, Map<String, String> hash_map) {
		this.hash_map = hash_map;
		this.CONTEXT = context;
		execute();
	}
	@Override
	public boolean onResult(boolean b) {
		
		if(b){
			UserObject userObject = new UserObject("<T>");
			userObject.P.definir(hash());
			Connection.AUTH.onSucess(userObject);
		}
		else Connection.AUTH.onFailed(hash().get("msg"));
		
		return b;
	}

  private Context CONTEXT;
  private final String USER_AGENT = "Mozilla/5.0";
  private Map<String, String> hash_map;
  private String retorno;
  private HttpsURLConnection con;
  private HashMap<String, String> hash_map_result;
  private String LINK = "aHR0cHM6Ly9ibGFja3NoZWVwLm9ubGluZS9wYWdpbmEvbG9naW4vbG9naW4ucGhw";
	//private boolean resultado;
	
	ProgressDialog pDialog;
    
    private static final int RESPONSE_TYPE_AUTH = 0;
    private static final int RESPONSE_TYPE_CANAL = 1;
    private static final int RESPONSE_TYPE_FILM = 2;
    private static final int RESPONSE_TYPE_SERIES = 3;
    
	@Override
	protected void onPreExecute() {
		pDialog = new ProgressDialog(CONTEXT);
		pDialog.setTitle("Aguarde");
		pDialog.setMessage("Verificando conexão...");
		pDialog.setCancelable(false);
		//pDialog.create();
		//pDialog.show();
	}

  
  
  @Override
  protected String doInBackground(Void... voids) {
    try {
		//B base64 = new B();
		//Toast.makeText(CONTEXT,base64.decode("k"),Toast.LENGTH_SHORT).show();
		URL url = new URL(HttpsUrlConnectionService.get() + "/clients/verify.php"); //base64.decode(LINK));
      con = (HttpsURLConnection) url.openConnection();

      // Adicionando os cabeçalhos
      con.setRequestMethod("POST");
      con.setRequestProperty("User-Agent", USER_AGENT);
      con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
      con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

      // Enviando os dados
      OutputStream os = con.getOutputStream();
      BufferedWriter writer = new BufferedWriter(
          new OutputStreamWriter(os, "UTF-8"));

      writer.write(getPostDataString(this.hash_map));

      writer.flush();
      writer.close();
      os.close();

      int responseCode = con.getResponseCode();

      if (responseCode == HttpsURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();
        con.disconnect();
        return response.toString();
      }
    } catch (Exception e) {
      e.printStackTrace();
	  
    }finally{
		if (con != null) {
			con.disconnect();
		}
	}
	
    return null;
  }

  @Override
protected void onPostExecute(String result) {
    if(result != null){
            
            //Toast.makeText(CONTEXT, result, Toast.LENGTH_LONG).show();
        try {
        int responseType = Integer.parseInt(hash_map.get("response"));
        switch (responseType) {
            case RESPONSE_TYPE_AUTH:
                handleAuthResponse(result);
                break;
            case RESPONSE_TYPE_CANAL:
                handleCanalResponse(result, new BDChannels());
                break;
            case RESPONSE_TYPE_FILM:
                handleFilmResponse(result, new BDFilms());
                break;
            case RESPONSE_TYPE_SERIES:
                handleSerieResponse(result, new BDSeries());
                break;
        }
    } catch (JSONException e) {
        logErrorAndShowToast("JSON parsing error", e);
    } catch (NumberFormatException e) {
        logErrorAndShowToast("Invalid response type", e);
    }
            
    }else{
        Log.e("ConnectionService","result is null, try again");
            
    }
}

private void handleAuthResponse(String result) throws JSONException {
    JSONObject jsonObject = new JSONObject(result);
    hash_map_result = new HashMap<>();
    Iterator<String> keys = jsonObject.keys();
    while (keys.hasNext()) {
        String key = keys.next();
        if (!jsonObject.isNull(key)) {
            hash_map_result.put(key, jsonObject.getString(key));
        }
    }
    onResult(Boolean.parseBoolean(hash_map_result.get("auth")));
    Log.e("kk", "deu");
}

private void handleCanalResponse(String result, BDChannels bdChannels) throws JSONException {
    JSONArray jsonArray = new JSONArray(result);
    List<Canal> canais = new ArrayList<>();

    for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        String nome = jsonObject.getString("nome");
        String logo = jsonObject.getString("logo");
        String categoria = jsonObject.getString("categoria");
        Canal canal = new Canal(nome, logo, categoria);
        canais.add(canal);
    }

    if (!canais.isEmpty()) {
        for (Canal canal : canais) {
            bdChannels.addName(canal.getNome());
            bdChannels.addContext(canal.getCategoria());
            bdChannels.addLogo(canal.getLogo());
        }
        bdChannels.putChannels(bdChannels);
    } else {
        Log.e("Error", "empty response");
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
        bdFilms.putFilms(bdFilms);
    } else {
            Log.e("Error", "empty response");
        //Toast.makeText(CONTEXT, "Empty response", Toast.LENGTH_SHORT).show();
    }
}
    
    private void handleSerieResponse(String result, BDSeries bdSeries) throws JSONException {
    JSONArray jsonArray = new JSONArray(result);
    List<Canal> canais = new ArrayList<>();

    for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        String nome = jsonObject.getString("nome");
        String logo = jsonObject.getString("logo");
        String categoria = jsonObject.getString("categoria");
        Canal canal = new Canal(nome, logo, categoria);
        canais.add(canal);
    }

    if (!canais.isEmpty()) {
        for (Canal canal : canais) {
            bdSeries.addName(canal.getNome());
            bdSeries.addContext(canal.getCategoria());
            bdSeries.addLogo(canal.getLogo());
        }
        bdSeries.putSeries(bdSeries);
    } else {
         Log.e("Error", "empty response");
       // Toast.makeText(CONTEXT, "Empty response", Toast.LENGTH_SHORT).show();
    }
}



private void logErrorAndShowToast(String message, Exception e) {
    Log.e("Error", message, e);
    //Toast.makeText(CONTEXT, message, Toast.LENGTH_SHORT).show();
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
  
	public static abstract interface Auth{
		public void onSucess();
		public void onFailed();
	}
	
}

/*
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Conexao 


*/
