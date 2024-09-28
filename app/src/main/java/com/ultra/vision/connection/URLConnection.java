package com.ultra.vision.connection;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.widget.*;
import com.ultra.vision.account.*;
import com.ultra.vision.database.*;
import com.ultra.vision.movies.database.*;
import com.ultra.vision.settings.*;
import com.ultra.vision.tv.database.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.*;
import org.json.*;


public class URLConnection extends AsyncTask<Void, Void, String> {

	public void URLConnection(Context context, Map<String, String> hash_map, URLConnection.Auth auth, String dir) {
		this.hash_map = hash_map;
		this.CONTEXT = context;
		this.AUTH = auth;
		this.DIR = dir;
		execute();
	}

	private String DIR;
	private Auth AUTH;
	private Context CONTEXT;
	private final String USER_AGENT = "Mozilla/5.0";
	private Map<String, String> hash_map;
	private String retorno;
	private HttpsURLConnection con;
	private HashMap<String, String> hash_map_result;
	private String LINK = "aHR0cHM6Ly9ibGFja3NoZWVwLm9ubGluZS9wYWdpbmEvbG9naW4vbG9naW4ucGhw";
	//private boolean resultado;

	ProgressDialog pDialog;

	@Override
	protected void onPreExecute() {
		pDialog = new ProgressDialog(CONTEXT);
		pDialog.setTitle("Aguarde");
		pDialog.setMessage("Verificando conexão...");
		pDialog.setCancelable(false);
		pDialog.create();
		pDialog.show();
	}



	@Override
	protected String doInBackground(Void... voids) {
		try {
			//B base64 = new B();
			//Toast.makeText(CONTEXT,base64.decode("k"),Toast.LENGTH_SHORT).show();
			URL url = new URL(HttpsUrlConnectionService.get() + DIR); //base64.decode(LINK));
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
		
		pDialog.dismiss();
		//Toast.makeText(CONTEXT, result, Toast.LENGTH_SHORT).show();
		if(result != null){
			AUTH.onSucess(result);
		}else AUTH.onFailed(result);
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
		public void onSucess(String response);
		public void onFailed(String error);
	}

}
