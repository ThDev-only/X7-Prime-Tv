package com.ultra.vision.tv.connection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.*;
import android.text.Html;
import android.util.*;
import com.ultra.vision.settings.*;
import com.ultra.vision.tv.object.*;
import java.io.*;
import java.lang.ref.WeakReference;
import java.net.*;
import java.util.*;

public class GetTallbackName extends AsyncTask<String, Void, String> {

    private static final String TAG = "GetCanaisAsyncTask";
	private Direito DIREITO;
	private Privacidade PRIVACIDADE;
	private int SELECIONAR;
    private WeakReference<Activity> weak;
	
    public GetTallbackName(Activity activity){
        weak = new WeakReference<>(activity);
    }
    
    @Override
    protected String doInBackground(String... params) {
        String endpointURL = params[0];
        List<Canal> canais = null;
        HttpURLConnection conn = null;

        try {
            // Cria a conexão HTTP
            URL url = new URL(endpointURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Verifica o código de resposta da requisição
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lê a resposta da requisição
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Converte a resposta JSON em uma lista de canais no Java
				
				conn.disconnect();
                return response.toString();
            } else {
                Log.e(TAG, "Erro na requisição: " + responseCode);
            }

            // Fecha a conexão
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }finally{
           if (conn != null) {
			conn.disconnect();
                }
        }

        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        // Faça o que for necessário com os dados dos canais
	//	if(weak.get() != null){
            switch(SELECIONAR){
			case 0:
				DIREITO.message(result);
				break;
			case 1:
				PRIVACIDADE.message(result);
				break;
	//	}
        }
        
    }
	
	
	public interface Direito{
		public void message(String s);
	}
	
	public interface Privacidade{
		public void message(String s);
	}
	
	public void getMessageDir(){
		//this.CONTEXT = context;
		this.DIREITO = new Direito(){
            public void message(String s){
                createAlertDialog("Direitos Autorais",s);
            }
        };
		this.SELECIONAR = 0;
		execute(HttpsUrlConnectionService.get() + "/clients/direitos_autorais.php");
	}
	
	public void getMessagePri(){
		//this.CONTEXT = context;
		this.PRIVACIDADE = new Privacidade(){
            @Override
            public void message(String s) {
                createAlertDialog("Politica de Privacidade",s);
            }
            
        };
		this.SELECIONAR = 1;
		execute(HttpsUrlConnectionService.get() + "/clients/privacidade.php");
	}
    
    public void createAlertDialog(String title, String msg)
	{
        AlertDialog.Builder builder = new AlertDialog.Builder(weak.get());
        builder.setMessage(Html.fromHtml(msg))

            .setTitle(title)
            .setPositiveButton("Ok", null)
            .setCancelable(false)
            .create().show();
    }
}

