package com.ultra.vision.movies.connection;

import android.os.*;
import android.util.*;
import com.ultra.vision.settings.*;
import java.io.*;
import java.net.*;
import com.ultra.vision.movies.config.*;

public class MoviesBannerConnection extends AsyncTask<String, Void, String> {

    private static final String TAG = MoviesBannerConnection.class.getSimpleName();
    private TheMovieDatabase.getUrlBanner listener;

    public MoviesBannerConnection(TheMovieDatabase.getUrlBanner listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String movieTitle = params[0];
        String response = "";
        HttpURLConnection connection = null;

        try {
            URL url = new URL(HttpsUrlConnectionService.get() + "/movies/getmovie/get_movie_banner.php");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String postData = "movie_search=" + movieTitle; // Dados a serem enviados
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
            //connection.disconnect();
        } catch (IOException e) {
            Log.e(TAG, "Error fetching data from API: " + e.getMessage());
        }finally{
            if (connection != null) {
			connection.disconnect();
		}
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        if (listener != null) {
            listener.onSucess(response);
        }else listener.onFailed(response);
    }

}

