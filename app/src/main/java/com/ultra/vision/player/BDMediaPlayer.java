package com.ultra.vision.player;

import android.app.Activity;
import android.view.WindowManager;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.ultra.vision.database.DatabaseMemory;
import com.ultra.vision.database.SQLiteHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import com.ultra.vision.databinding.BdMediaPlayerBinding;
import com.ultra.vision.movies.object.Movie;
import android.os.Bundle;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.ultra.vision.database.MyDbHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ExoPlayerFactory;
import android.net.Uri;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import android.util.Log;
import com.google.android.exoplayer2.Player;
import java.util.ArrayList;
import java.util.TimerTask;



public class BDMediaPlayer extends Activity {

    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
	DatabaseMemory db1;
    SQLiteHashMap hashMap;
    HashMap<String, List<String>> retrievedHashMap;
    Timer timer;
	//ExoPlayer exoPlayer;
	boolean stateFilm = false;
	
//	BDControlViewTv bdControlViewTv;
    
    BdMediaPlayerBinding binding;
    
    Movie movie;

    @Override
    public void onBackPressed() {
        exoPlayer.stop();
        exoPlayer.release();
        stopTimer();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BdMediaPlayerBinding.inflate(getLayoutInflater());
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
	/*	RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new LayoutParams(-1, -1));
        relativeLayout.setBackgroundColor(Color.BLACK);*/

        exoPlayerView = binding.idExoPlayerVIew;
        //new SimpleExoPlayerView(this);
       // exoPlayerView.setLayoutParams(new RelativeLayout.LayoutParams(
										//  RelativeLayout.LayoutParams.MATCH_PARENT,
										//  RelativeLayout.LayoutParams.MATCH_PARENT));
										  
        getMovieFromIntent();
										  
		db1 = new DatabaseMemory(this);  
		MyDbHelper database = new MyDbHelper(this);     
		SQLiteDatabase db = database.getWritableDatabase();    
		String tableName = "dados";     
		hashMap = new SQLiteHashMap(db, tableName);   
		retrievedHashMap = hashMap.get("historico");
		
		exoPlayerConfig();
		// Criando a instância do controle personalizado
		/*bdControlViewTv = new BDControlViewTv(BDMediaPlayer.this);
		bdControlViewTv.setFileName(BDFilms.Film.FILM.getNome());
		bdControlViewTv.setPlayer(exoPlayer);
		//exoPlayerView.setControllerVisibilityListener(BDControlView);
		exoPlayerView.addView(bdControlViewTv);*/
		//exoPlayerView.setUseController(true);
		//exoPlayerView.setControllerAutoShow(false);
		//exoPlayerView.(customControlView);
		//startTimer();
		//start(exoPlayer);
		
       // relativeLayout.addView(exoPlayerView);
		
        setContentView(binding.getRoot());
		
    }
	
    private void getMovieFromIntent() {
        Intent intent = getIntent();

        movie = (Movie) intent.getSerializableExtra("db_player");
    }
    
	public void exoPlayerConfig(){
		try {

            // bandwisthmeter is used for
            // getting default bandwidth
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            // track selector is used to navigate between
            // video using a default seekbar.
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

            // we are adding our track selector to exoplayer.
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            // we are parsing a video url
            // and parsing its video uri.
			//Toast.makeText(BDMediaPlayer.this, BDFilms.Film.FILM.getNome()+"\n"+BDFilms.Film.FILM.getLink(), Toast.LENGTH_LONG).show();
            Uri videouri = Uri.parse(movie.getLink());

            // we are creating a variable for datasource factory
            // and setting its user agent as 'exoplayer_view'
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");

            // we are creating a variable for extractor factory
            // and setting it to default extractor factory.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            // we are creating a media source with above variables
            // and/ passing our event handler as null,
            MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);

            // inside our exoplayer view
            // we are setting our player
            exoPlayerView.setPlayer(exoPlayer);

            // we are preparing our exoplayer
            // with media source.
            exoPlayer.prepare(mediaSource);

            // we are setting our exoplayer
            // when it is ready.
            exoPlayer.setPlayWhenReady(true);

			// Em seu código, registre o ouvinte de eventos no exoPlayer
			MyPlayerEventListener playerEventListener = new MyPlayerEventListener();
			exoPlayer.addListener(playerEventListener);
            
            

        } catch (Exception e) {
            // below line is used for
            // handling our errors.
            Log.e("TAG", "Error : " + e.toString());
        }
	}

    public class MyPlayerEventListener implements Player.EventListener
	{

		@Override
		public void onTimelineChanged(Timeline p1, Object p2, int p3)
		{
			// TODO: Implement this method
		}

		@Override
		public void onTracksChanged(TrackGroupArray p1, TrackSelectionArray p2)
		{
			// TODO: Implement this method
		}

		@Override
		public void onLoadingChanged(boolean p1)
		{
			// TODO: Implement this method
		}

		@Override
		public void onRepeatModeChanged(int p1)
		{
			// TODO: Implement this method
		}

		@Override
		public void onShuffleModeEnabledChanged(boolean p1)
		{
			// TODO: Implement this method
		}

		@Override
		public void onPositionDiscontinuity(int p1)
		{
			// TODO: Implement this method
		}

		@Override
		public void onPlaybackParametersChanged(PlaybackParameters p1)
		{
			// TODO: Implement this method
		}

		@Override
		public void onSeekProcessed()
		{
			// TODO: Implement this method
		}
		
        // Implemente os métodos da interface Player.EventListener conforme necessário
        // ...

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
           if(movie.getType().equalsIgnoreCase("filme")){
                if (playbackState == Player.STATE_READY && playWhenReady) {
                // O vídeo está reproduzindo
				if(stateFilm == false){
					stateFilm = true;
					startTimer();
					verifySetTime(movie.getNome());
				}
            } else if (playbackState == Player.STATE_READY) {
                // O vídeo está pausado
				stateFilm = false;
				stopTimer();
            } else if (playbackState == Player.STATE_ENDED) {
                // O vídeo terminou de ser reproduzido
				stopTimer();
				stateFilm = false;
            }
           }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            // Trate o erro de reprodução do vídeo aqui
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.setPlayWhenReady(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.stop();
        exoPlayer.release();
        stopTimer();
    }
	
	public String getCurrentTime()
	{
		long currentPosition = exoPlayer.getCurrentPosition();

		String formattedTime = formatTime(currentPosition);

		return formattedTime;
	}

	//@Override
	public String formatTime(long timeMs) {
		int seconds = (int) (timeMs / 1000) % 60;
		int minutes = (int) ((timeMs / (1000 * 60)) % 60);
		int hours = (int) ((timeMs / (1000 * 60 * 60)) % 24);

		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

	//@Override
	public void setTime(String formattedTime) {
		// Extrair horas, minutos e segundos da string formatada
		String[] timeParts = formattedTime.split(":");
		int hours = Integer.parseInt(timeParts[0]);
		int minutes = Integer.parseInt(timeParts[1]);
		int seconds = Integer.parseInt(timeParts[2]);

		// Converter horas, minutos e segundos para milissegundos
		long timeMs = hours * 60 * 60 * 1000 + minutes * 60 * 1000 + seconds * 1000;

		// Definir o tempo no ExoPlayer
		exoPlayer.seekTo(timeMs);
	}

	//@Override
	public List<String> getFilmList() {
        List<String> filmList = retrievedHashMap.get("list1");
        if (filmList == null) {
            filmList = new ArrayList<>();
        }
        return filmList;
    }

	//@Override
	public List<String> getTimeList() {
        List<String> timeList = retrievedHashMap.get("list2");
        if (timeList == null) {
            timeList = new ArrayList<>();
        }
        return timeList;
    }

	//@Override
	public List<String> getLogoList() {
        List<String> logoList = retrievedHashMap.get("list3");
        if (logoList == null) {
            logoList = new ArrayList<>();
        }
        return logoList;
    }

	//@Override
	public void putFilm(String film, String currentTime, String logo) {
		List<String> filmList = getFilmList();
		List<String> timeList = getTimeList();
		List<String> logoList = getLogoList();

		//aqui verifica se o filmList passou de 12 elementos, se passou apaga o item 0 tanto d9 filmList quando do timeList

		if (filmList.size() > 6) {
			filmList.remove(0); // Remove o primeiro elemento da lista de filmes
			timeList.remove(0); // Remove o primeiro elemento da lista de tempos
		}

		int index = filmList.indexOf(film);
        
		if (index != -1) {
			timeList.set(index, currentTime);
            
            filmList.remove(index);
            timeList.remove(index);
            logoList.remove(index);
            
            timeList.add(currentTime);
            filmList.add(film);
            logoList.add(logo);
            
		} else {
			filmList.add(film);
			timeList.add(currentTime);
			logoList.add(logo);
		}

		retrievedHashMap.put("list1", filmList);
		retrievedHashMap.put("list2", timeList);
		retrievedHashMap.put("list3", logoList);

		hashMap.put("historico", retrievedHashMap);

		Log.i("BDMediaPlayer$putFilm", "Historico dos filmes: " +retrievedHashMap.get("list1").toString());	
		Log.i("BDMediaPlayer$putFilm", "Historico dos tempo: " +retrievedHashMap.get("list2").toString());

	}

	//@Override
	public void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					String currentTime = getCurrentTime();
					putFilm(movie.getNome(), currentTime, movie.getLogo());
				}
			}, 0, 1000); // Atualiza o tempo a cada 1 segundo (1000 milissegundos)
    }

	//@Override
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


	//definir tempo


	//
	//@Override
	public void verifySetTime(String film) {
		List<String> filmList = getFilmList();
		List<String> timeList = getTimeList();
		List<String> logoList = getLogoList();


		int index = filmList.indexOf(film);
		if (index != -1) {
			setTime(timeList.get(index));
		} else {

		}

	}
}




/*import android.app.*;
import android.database.sqlite.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.LinearLayout.*;
import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.extractor.*;
import com.google.android.exoplayer2.source.*;
import com.google.android.exoplayer2.trackselection.*;
import com.google.android.exoplayer2.ui.*;
import com.google.android.exoplayer2.upstream.*;
import com.ultra.vision.database.*;
import com.ultra.vision.movies.database.*;
import java.util.*;


public class BDMediaPlayer extends Activity {

    // creating a variable for exoplayerview.
    SimpleExoPlayerView exoPlayerView;

    // creating a variable for exoplayer
    SimpleExoPlayer exoPlayer;
	
	DatabaseMemory db;
	SQLiteHashMap hashMap;
	HashMap<String, List<String>> retrievedHashMap;
	
	@Override
	public void onBackPressed()
	{
		exoPlayer.stop(); // Para a reprodução do vídeo e libera os recursos do ExoPlayer
		exoPlayer.release(); // Libera todos os recursos do ExoPlayer
		finish();
	}

    // url of video which we are loading.
    //String videoURL = "http://acsc.cc:80/movie/2mqYW/QfK3q5/162354.mp4";
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);

		db = new DatabaseMemory(this);
		MyDbHelper database = new MyDbHelper(this);
		SQLiteDatabase db = database.getWritableDatabase();
		String tableName = "dados"; // Nome da tabela no banco de dados
		hashMap = new SQLiteHashMap(db, tableName);
		retrievedHashMap = hashMap.get("historico");
		
        exoPlayerView = new SimpleExoPlayerView(this);
        exoPlayerView.setLayoutParams(new RelativeLayout.LayoutParams(
									   RelativeLayout.LayoutParams.MATCH_PARENT,
									   RelativeLayout.LayoutParams.MATCH_PARENT));

        RelativeLayout relativeLayout = new RelativeLayout(this);
		relativeLayout.setLayoutParams(new LayoutParams(-1,-1));
		relativeLayout.setBackgroundColor(Color.BLACK);
		
        relativeLayout.addView(exoPlayerView);
        setContentView(relativeLayout);
        try {

            // bandwisthmeter is used for
            // getting default bandwidth
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            // track selector is used to navigate between
            // video using a default seekbar.
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

            // we are adding our track selector to exoplayer.
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            // we are parsing a video url
            // and parsing its video uri.
            Uri videouri = Uri.parse(BDFilms.Film.FILM.getLink());

            // we are creating a variable for datasource factory
            // and setting its user agent as 'exoplayer_view'
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");

            // we are creating a variable for extractor factory
            // and setting it to default extractor factory.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            // we are creating a media source with above variables
            // and/ passing our event handler as null,
            MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);

            // inside our exoplayer view
            // we are setting our player
            exoPlayerView.setPlayer(exoPlayer);

            // we are preparing our exoplayer
            // with media source.
            exoPlayer.prepare(mediaSource);

            // we are setting our exoplayer
            // when it is ready.
            exoPlayer.setPlayWhenReady(true);

			// Em seu código, registre o ouvinte de eventos no exoPlayer
			MyPlayerEventListener playerEventListener = new MyPlayerEventListener();
			exoPlayer.addListener(playerEventListener);
			
        } catch (Exception e) {
            // below line is used for
            // handling our errors.
            Log.e("TAG", "Error : " + e.toString());
        }
    }
	
	// Implemente a interface Player.EventListener
	public class MyPlayerEventListener implements Player.EventListener
	{

		@Override
		public void onTimelineChanged(Timeline p1, Object p2, int p3)
		{
			// TODO: Implement this method
		}

		@Override
		public void onTracksChanged(TrackGroupArray p1, TrackSelectionArray p2)
		{
			// TODO: Implement this method
		}

		@Override
		public void onRepeatModeChanged(int p1)
		{
			// TODO: Implement this method
		}

		@Override
		public void onShuffleModeEnabledChanged(boolean p1)
		{
			// TODO: Implement this method
		}

		@Override
		public void onPositionDiscontinuity(int p1)
		{
			// TODO: Implement this method
		}

		@Override
		public void onPlaybackParametersChanged(PlaybackParameters p1)
		{
			// TODO: Implement this method
		}

		@Override
		public void onSeekProcessed()
		{
			// TODO: Implement this method
		}
		

		@Override
		public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
			// Callback acionado quando o estado de reprodução do player muda
			if (playbackState == Player.STATE_READY && playWhenReady) {
				// O vídeo está reproduzindo
			} else if (playbackState == Player.STATE_READY) {
				// O vídeo está pausado
			} else if (playbackState == Player.STATE_ENDED) {
				// O vídeo terminou de ser reproduzido
			}
		}

		@Override
		public void onPlayerError(ExoPlaybackException error) {
			// Callback acionado em caso de erro na reprodução do vídeo
			// Aqui você pode tratar o erro ou exibir uma mensagem de erro para o usuário
		}

		@Override
		public void onLoadingChanged(boolean isLoading) {
			// Callback acionado quando o estado de carregamento do player muda
			if (isLoading) {
				// O player está carregando
			} else {
				// O player terminou de carregar
			}
		}

		// Implemente outros métodos da interface Player.EventListener conforme necessário

	}
	
	public List<String> getFilmList(){
		
		List<String> retrievedLista1 = retrievedHashMap.get("list1");
		
		return retrievedLista1;
	}
	
	public List<String> getTimerList(){
		List<String> retrievedLista2 = retrievedHashMap.get("list2");

		return retrievedLista2;
	}
	
	public void putFilm(){
		// Cria uma instância do banco de dados SQLite
		//SQLiteDatabase database = ...; // Obtenha a instância do banco de dados conforme necessário

// Cria uma instância do SQLiteHashMap
		

// Cria as duas listas
		List<String> lista1 = new ArrayList<>();
		lista1.add("Item 1");
		lista1.add("Item 2");

		List<String> lista2 = new ArrayList<>();
		lista2.add("Item A");
		lista2.add("Item B");

// Salva as duas listas usando o método put
		String key = "historico"; // Chave para identificar os dados salvos
		hashMap.put(key, lista1, lista2);

// Recupera as duas listas usando o método get
		HashMap<String, List<String>> retrievedHashMap = hashMap.get(key);
		List<String> retrievedLista1 = retrievedHashMap.get("list1");
		List<String> retrievedLista2 = retrievedHashMap.get("list2");

// Imprime as listas recuperadas
		System.out.println("Lista 1: " + retrievedLista1);
		System.out.println("Lista 2: " + retrievedLista2);
		
	}
	

}*/

