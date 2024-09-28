package com.ultra.vision.movies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.widget.RadioGroup.*;
import com.google.android.exoplayer2.source.hls.*;
//import com.google.android.youtube.player.*;
import com.ultra.vision.*;
import com.ultra.vision.account.UserObject;
import com.ultra.vision.adapter.*;
import com.ultra.vision.connection.*;
import com.ultra.vision.database.*;
import com.ultra.vision.movies.config.*;
import com.ultra.vision.movies.connection.*;
import com.ultra.vision.movies.database.*;
import com.ultra.vision.movies.object.*;
import com.ultra.vision.player.BDMediaPlayer;
import com.ultra.vision.player.BDProPlayer;
import com.ultra.vision.series.connection.EpisodesConnection;
import com.ultra.vision.series.connection.EpisodesDetailsConnection;
import com.ultra.vision.series.connection.SeasonsConnection;
import com.ultra.vision.series.object.EpisodeObject;
import com.ultra.vision.series.object.SeasonObject;
import com.ultra.vision.tv.*;
import java.io.*;
import java.net.*;
import java.text.*;
import androidx.recyclerview.widget.*;
import android.view.View.OnFocusChangeListener;
import com.ultra.vision.connection.URLConnection;
import com.ultra.vision.R;
import android.app.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MovieActivity extends Activity
{
	
//	private LinearLayout pLinearLayout;
	//private YouTubePlayerView pYoutubePlayerView;
    private RelativeLayout pRelativeLayoutMenuInfo;
	private ImageView pImageViewBanner;
	private TextView pMovieTitle;
    private ImageView pMovieImgTitle;
	private TextView pMovieSubTitle;
	private ListView pMovieListViewOptions;
	private LinearLayout pLinearLayoutLoading;
	private LinearLayout pLinearLayoutIntroducao;
	private TextView pTextViewMoreIntro;
	private Button pCloseLinearIntroducao;
    private ImageView pMovieImageViewLogo;
    private RecyclerView pRecyclerViewEpisodes;
	
	private String trailer = "";
	private String movieIntro;
	
	List<String> movieOptionsName;

	List<Integer> movieOptionsIcon;
	
	String[] movieMoreOptionsName = new String[]{
		"Qualidade",
		"Idioma",
		"Reprodução automática"
	};

	int[] movieMoreOptionsIcon = new int[]{
		R.drawable.group_24px,
		R.drawable.group_24px,
		R.drawable.play_arrow_24px,
		};
	
    private final String sINTRO = "Introdução",
    sELENCO = "Elenco",
    sPLAY = "Reproduzir",
    sSEASONS = "Temporadas",
    sADDPLAYLIST = "Adicionar a fila",
    sMORE = "Mais Opções";
    
    private final String MODE_HOME = "home",
    MODE_SEASONS = "seasons",
    MODE_MORE = "more_options";
    
    String modeListViewOptions = "home";
    Movie movie;
    
    //id da serie
    private int serieId;
	
	private int FLAG_FULLSCREEN = WindowManager.LayoutParams.FLAG_FULLSCREEN;
    
    //Lista que armazena as temporadas
    private List<SeasonObject> listSeasons;
    
    //Animations
    float alpha = 1.0f;
    Thread th = null;
	
    @Override
    @Deprecated
    public void onBackPressed() {
        //super.onBackPressed();
        // TODO: Implement this method
        switch(modeListViewOptions){
            case MODE_HOME: finish(); break;
            case MODE_SEASONS:
                modeListViewOptions = MODE_HOME;
                pMovieSubTitle.setVisibility(View.VISIBLE);
                startAnimationsSeasons(false);
               // startAnimationsHome(true);
            break;
        }
    }
    
    
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);
		getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        
		findViewById();
        
        Intent intent = getIntent();
        
        movie = (Movie) intent.getSerializableExtra("db_movie");
        
        changeArray();
		start();
		
	}
    
    private void changeArray(){
        
        movieOptionsName = new ArrayList<String>();
        movieOptionsIcon = new ArrayList<Integer>();
        
        switch(movie.getType()){
            case "filme":
                movieOptionsName.add(sINTRO);
                movieOptionsIcon.add(R.drawable.group_24px);
                movieOptionsName.add(sELENCO);
                movieOptionsIcon.add(R.drawable.group_24px);
                movieOptionsName.add(sPLAY);
                movieOptionsIcon.add(R.drawable.play_arrow_24px);
                movieOptionsName.add(sADDPLAYLIST);
                movieOptionsIcon.add(R.drawable.playlist_add_24px);
                movieOptionsName.add(sMORE);
                movieOptionsIcon.add(R.drawable.more_vert_24px);
                
            break;
            
            case "serie":
                movieOptionsName.add(sINTRO);
                movieOptionsIcon.add(R.drawable.group_24px);
                movieOptionsName.add(sELENCO);
                movieOptionsIcon.add(R.drawable.group_24px);
                movieOptionsName.add(sPLAY);
                movieOptionsIcon.add(R.drawable.play_arrow_24px);
                movieOptionsName.add(sSEASONS);
                movieOptionsIcon.add(R.drawable.playlist_add_24px);
                movieOptionsName.add(sADDPLAYLIST);
                movieOptionsIcon.add(R.drawable.playlist_add_24px);
                movieOptionsName.add(sMORE);
                movieOptionsIcon.add(R.drawable.more_vert_24px);
            
            break;
        }
    }
	
	private void findViewById(){
		//pSimpleExoPlayerViewTrailer = findViewById(R.id.activity_movieSimpleExoPlayerViewTrailer);
		pImageViewBanner = findViewById(R.id.activitymovieImageViewBanner);
		pMovieTitle = findViewById(R.id.activitymovieTextViewTitle);
        pMovieImgTitle = findViewById(R.id.activitymovieImageViewTitle);
		pMovieSubTitle = findViewById(R.id.activitymovieTextViewSubTitle);
		pMovieListViewOptions = findViewById(R.id.activitymovieListViewOptions);
		pLinearLayoutLoading = findViewById(R.id.activitymovieLinearLayoutLoading);
        pRecyclerViewEpisodes = findViewById(R.id.activitymovieRecyclerViewEpisodes);
		pRelativeLayoutMenuInfo = findViewById(R.id.relativeLayoutMenuInfo);
		//pYoutubePlayerView = new YouTubePlayerView(this);
		//pYoutubePlayerView.setLayoutParams(new LayoutParams(-1,-1));
		pLinearLayoutIntroducao = findViewById(R.id.activitymovieLinearIntroducao);
		pTextViewMoreIntro = findViewById(R.id.activitymovieTextViewMoreIntro);
		pCloseLinearIntroducao = findViewById(R.id.activity_movieButtonCloseIntro);
        pMovieImageViewLogo = findViewById(R.id.activity_movieImageViewLogoCompany);
	}
	private void start(){
		
		pMovieListViewOptions.setFocusable(true);
		pMovieListViewOptions.requestFocus(View.FOCUS_UP);
		pMovieListViewOptions.requestFocus(View.FOCUS_DOWN);
		
		pMovieSubTitle.setOnFocusChangeListener(onFocus);
		pCloseLinearIntroducao.setOnFocusChangeListener(onFocus);
		//pSimpleExoPlayerViewTrailer.setUseController(false);
		//youtubePlayerView = findViewById(R.id.youtube_player_view);
		
		new Handler().postDelayed(new Runnable(){

				@Override
				public void run()
				{
					pLinearLayoutLoading.setVisibility(View.VISIBLE);
					MoviesService movieS = new MoviesService(new TheMovieDatabase.getDetails(){

							@Override
							public void onSucess(MovieObject movieObject)
							{
                                serieId = movieObject.id;
								pLinearLayoutLoading.setVisibility(View.GONE);
								setImageBanner(movieObject.getBackdropPath());
								pMovieTitle.setText(movieObject.getName());
								pMovieSubTitle.setText(movieObject.getOverview());
								pMovieListViewOptions.setAdapter(new MovieOptionsAdapter(MovieActivity.this, movieOptionsName, movieOptionsIcon));
								pMovieListViewOptions.requestFocus();
								movieIntro = movieObject.getOverview();
								
                                if(movieObject.getFilePath() != null){
                                    loadMovieTitle(movieObject.getFilePath());
                                }else pMovieTitle.setVisibility(View.VISIBLE);
                                
								if(movieObject.isVideo()){
									//pLinearLayout.addView(pYoutubePlayerView);
									startTrailer(movieObject.getId());
								}
                                
                                
                                loadLogoCompany(movieObject.id);
							}

							@Override
							public void onFailed(String error)
							{
								Toast.makeText(MovieActivity.this, "Falha", Toast.LENGTH_LONG).show();
							}

						
					});
					String nome = movie.getNome();//BDFilms.Film.FILM.getNome();
					movieS.execute(limparTitulo(nome), movie.getType());
                    
				}
			}, 3000);

        pMovieListViewOptions.setOnItemClickListener(
                new ListView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                        switch(modeListViewOptions){
                            case MODE_HOME:
                                verifyListHome(p3);
                            break;
                        
                            case MODE_SEASONS:
                                verifyListSeasons(p3);
                            break;
                        
                            case MODE_MORE:
                                verifyListMoreOptions(p3);
                            break;
                        }
                    }
                });

		pCloseLinearIntroducao.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					pLinearLayoutIntroducao.setVisibility(View.GONE);
					pMovieListViewOptions.requestFocus(View.FOCUS_UP);
					pMovieListViewOptions.requestFocus(View.FOCUS_DOWN);
				}

			
		});
		
	}
    
    //mode "home" in listViewOptions
    private void verifyListHome(int p3){
        switch (movieOptionsName.get(p3)) {
                            case sINTRO:
                                pLinearLayoutIntroducao.setVisibility(View.VISIBLE);
                                pCloseLinearIntroducao.requestFocus();
                                pTextViewMoreIntro.setText(movieIntro);
                                break;
                            case sELENCO:
                                // Elenco
                                break;
                            case sPLAY:
                                // Reproduzir filme/serie
                                GetLinkFromName get = new GetLinkFromName(MovieActivity.this);
                                get.getLinkFromName(
                                        movie.getNome() /*BDFilms.Film.FILM.getNome()*/,
                                        new URLConnection.Auth() {

                                            @Override
                                            public void onSucess(String response) {
                                                // BDFilms.Film.FILM.setLink(response);
                                                //movie.setLink(response);

                                                Intent intent =
                                                        new Intent(
                                                                MovieActivity.this,
                                                                BDProPlayer.class);

                                                String nome, categoria, logo, type, link;
                                                int id = movie.getId();
                                                nome = movie.getNome();
                                                categoria = movie.getCategoria();
                                                logo = movie.getLogo();
                                                type = movie.getType();
                                                link = response;

                                                Movie movie2 =
                                                        new Movie(id, nome, categoria, logo, type, link);

                                                intent.putExtra("db_player", movie2);
                                                startActivity(intent);
                                                // BDFilms.Film.startMovie(BDFilms.Film.FILM,
                                                // MovieActivity.this);
                                                Toast.makeText(MovieActivity.this,"teste" + movie.getId(), Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onFailed(String error) {
                                                // TODO: Implement this method
                                            }
                                        },
                                        "/movies/get/get_link.php");

                                break;
                            case sSEASONS: 
                                    modeListViewOptions = MODE_SEASONS;
                                    pMovieSubTitle.setVisibility(View.GONE);
                                    startAnimationsSeasons(true);
                                    getSeasonsFromSerie(movie.getId());
                            break;
                            case sADDPLAYLIST:
                                // Adicionar na minha lista?
                                break;
                            case sMORE:
                                // Mais opcoes?
                                // pMovieListViewOptions.setAdapter(new
                                // MovieOptionsAdapter(MovieActivity.this, movieMoreOptionsName,
                                // movieMoreOptionsIcon));
                                break;
                        }
    }

    // mode "seasons" in ListViewOptions
    private void verifyListSeasons(int p3) {
        
        SeasonObject seasonObject = listSeasons.get(p3);
        
        int id = seasonObject.getId();
        int numSeason = seasonObject.getNumSeason();
        
        getEpisodesFromSeasons(id, numSeason);
        
    }

    //mode "more_options" in listViewOptions
    private void verifyListMoreOptions(int p3){
        
    }
    
    private void startAnimationHome(boolean open){
        
    }
    
    private void startAnimationsSeasons(boolean open){
        
        pRelativeLayoutMenuInfo.setBackgroundResource(R.drawable.ic_d_gradient_details_movie_animator);
        
        AnimationDrawable animationDrawable = (AnimationDrawable) pRelativeLayoutMenuInfo.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.setOneShot(true);
        animationDrawable.start();
        
        
        
        
        int visibility = (open) ? View.INVISIBLE : View.VISIBLE;
                    
                        pMovieTitle.setVisibility(visibility);

        
        
               th = new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                // TODO: Implement this method
                                
                                Timer timer = new Timer();
                                Handler handler = new Handler(Looper.getMainLooper());

                                timer.schedule(
                                        new TimerTask() {
                                            @Override
                                            public void run() {
                                                // TODO: Implement this method
                                                
                                handler.post(new Runnable(){
                                                     @Override
                                            public void run() {
                                                // TODO: Implement this method
                                            
                                            if(open){
                                                    alpha += 0.1f;
                                                }else alpha -= 0.1f;
                                            
                                                  
                                                            pMovieImgTitle.setAlpha(alpha);
                                                if(alpha < 0){
                                                    pMovieImgTitle.setVisibility(View.INVISIBLE);
                                                    th.interrupt();  
                                                            }               
                                            
                                            }
                                                });

                                            }
                                        }, 0, 1000);
                    
                
                            }
                        });

        th.start();

       // pMovieTitle.startAnimation(fadeOut);
    }
    
    private void loadMovieTitle(String url){
        RequestOptions options = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // Armazena cache em disco
                        //.placeholder(R.drawable.logo_app_blue) // Imagem de placeholder enquanto carrega
                        .error(R.drawable.ic_checkbox_off); // Imagem de erro, caso ocorra algum problema

                Glide.with(MovieActivity.this)
                        .load(url)
                        .apply(options)
                        .into(pMovieImgTitle);
    }
    
    private void loadLogoCompany(int id){
        MoviesLogoConnection mlConn = new MoviesLogoConnection("get_logotipo_company.php", new TheMovieDatabase.getUrlLogo(){
                        @Override
                        public void onSucess(String response) {
                            // TODO: Implement this method
                            RequestOptions options = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // Armazena cache em disco
                        .placeholder(R.drawable.logo_app_blue) // Imagem de placeholder enquanto carrega
                        .error(R.drawable.ic_checkbox_off); // Imagem de erro, caso ocorra algum problema

                Glide.with(MovieActivity.this)
                        .load(response)
                        .apply(options)
                        .into(pMovieImageViewLogo);
                    
                   // Toast.makeText(MovieActivity.this, "link é " + response, Toast.LENGTH_LONG).show();
                                
                        }
                        @Override
                        public void onFailed(String error) {
                            // TODO: Implement this method
                        }
                        
                    });
                    
                    mlConn.execute(String.valueOf(id));
    }
	
	private void setImageBanner(final String posterPath){
        
        String baseUrl = "https://image.tmdb.org/t/p/";
          			 	String posterSize = "original"; // Tamanho desejado do pôster
           				String posterUrl = baseUrl + posterSize + posterPath;
		
        RequestOptions options = new RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL) // Armazena cache em disco
        //.placeholder(R.drawable.logo_app_blue) // Imagem de placeholder enquanto carrega
        .error(R.drawable.ic_checkbox_off); // Imagem de erro, caso ocorra algum problema

Drawable blackBackground = new ColorDrawable(Color.BLACK);

Glide.with(MovieActivity.this)
        .load(posterUrl)
        .apply(options)
        .thumbnail(Glide.with(MovieActivity.this).load(blackBackground))
        .into(pImageViewBanner);
        
	/*	new Thread(new Runnable(){

				URL url = null;

				HttpURLConnection http = null;

				Bitmap bit = null;

				@Override
				public void run()
				{//"https://www.thiengo.com.br/img/system/logo/thiengo-80-80.png"

					try
					{
						String baseUrl = "https://image.tmdb.org/t/p/";
          			 	String posterSize = "original"; // Tamanho desejado do pôster
           				String posterUrl = baseUrl + posterSize + posterPath;
						//Log.e("MovieActivity","Link do banner: " + posterUrl);
						url = new URL(posterUrl);
						try{
							http = 
								(HttpURLConnection)
								url.openConnection();
							http.connect();


							int responseCode = 
								http.getResponseCode();

							if(responseCode==
							   HttpURLConnection.HTTP_OK){

								InputStream input = 
									http.getInputStream();


								bit=BitmapFactory.decodeStream(input);

							}else{
								//Toast(this,"erro");
							}

						}catch (Exception e){
							//Toast(this,"catch");
						}

						runOnUiThread(new Runnable(){

								@Override
								public void run()
								{
									if(bit!=null){
										pImageViewBanner.setImageBitmap(bit);
										//img.setImageResource(R.mipmap.app3);

										
										// TODO: Implement this method
									}else{
										//Toast(this,"KAKKKAK");
									}
								}


							});



					}
					catch (Exception e)
					{
						Toast.makeText(MovieActivity.this,
						 e.toString(),
						 Toast.LENGTH_LONG).show();
					}
				}

			}).start();*/
	}
	
	private void startTrailer(int id){
		Toast.makeText(MovieActivity.this, "Tem trailer", Toast.LENGTH_LONG).show();

		MoviesTrailerConnection movieTrailer = new MoviesTrailerConnection(new TheMovieDatabase.getUrlTrailer(){

				@Override
				public void onSucess(String response)
				{
					//YouTubePlayerView youtubePlayerView = new YouTubePlayerView(MovieActivity.this);
					pImageViewBanner.setVisibility(View.GONE);
					trailer = response;
					//Toast.makeText(MovieActivity.this, response, Toast.LENGTH_LONG).show();
					
					//pYoutubePlayerView.initialize("AIzaSyDbUaQYDywsHKZ4rPjHt62bxIbotAVzOFU", listener);
					
				}

				@Override
				public void onFailed(String error)
				{
					Toast.makeText(MovieActivity.this, error, Toast.LENGTH_LONG).show();
				}

			
		});
		
		movieTrailer.execute(id);
	}
	
	/*YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener() {
		@Override
		public void onInitializationSuccess(YouTubePlayer.Provider p1, YouTubePlayer p2, boolean p3) {
			p2.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
			p2.loadVideo(trailer);
			p2.play();
		}

		@Override
		public void onInitializationFailure(YouTubePlayer.Provider p1, YouTubeInitializationResult p2) {
			Toast.makeText(MovieActivity.this, "Error", Toast.LENGTH_SHORT).show();
		}
	};*/
	
	//Foco TV
	OnFocusChangeListener onFocus = new View.OnFocusChangeListener() {
		@Override
		public void onFocusChange(View view, boolean hasFocus) {
			if (hasFocus) {
				view.setSelected(true); // Altere a cor do componente dentro do item
			} else {
				view.setSelected(false); // Restaure a cor original do componente dentro do item
			}
		}

	};
	
	public static String limparTitulo(String titulo) {
        // Remove o ano entre parênteses
        titulo = titulo.replaceAll("\\(\\d{4}\\)", "");

        // Remove o formato de vídeo (ex: 4K)
        titulo = titulo.replaceAll("\\b\\d{1,}K\\b", "");

        // Remove os marcadores entre colchetes (ex: [Hybrid], [L])
        titulo = titulo.replaceAll("\\[\\w+\\]", "");

        // Remove espaços em excesso
        titulo = titulo.trim();

        return titulo;
    }
	
	public String removeAllDistractions(String getName){

		if(getName.contains("4K [HDR]")){
			getName = getName.replaceAll("4K [HDR]","");
		}

		if(getName.contains(" [L]")){
			getName = getName.replace(" [L]", "");
		}

		if(getName.contains("[Cinema]")){
			getName = getName.replace("[Cinema]", "");
		}

		if(getName.contains("Nos+Cinemas")){
			getName = getName.replace("Nos+Cinemas", "");
		}

		if(getName.contains(" - ")){
			getName = getName.replaceAll(" - ", " ");
		}

		if(getName.contains("-")){
			getName = getName.replaceAll("-", " ");
		}

		if(getName.contains(".")){
			getName = getName.replaceAll(".", "");
		}

		if(getName.contains(":")){
			getName = getName.replaceAll(":", "");
		}

		if(getName.contains("'")){
			getName = getName.replaceAll("'", "");
		}

		if(getName.contains("(")){
			getName = getName.replaceAll("(", "");
		}

		if(getName.contains(")")){
			getName = getName.replaceAll(")", "");
		}

		if(getName.contains(" ")){
			getName = getName.replaceAll(" ", "-");
		}


		getName = Normalizer.normalize(getName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

		return getName;
	}
	
    
    //SERIE
    private void getSeasonsFromSerie(int id){
        
        
        //aqui carrega as temporadas, mas vamos testas os episodios
        
        HashMap<String,String> hashMap = new HashMap<String,String>();
        
        hashMap.put("user", UserObject.USER.getName());
        hashMap.put("pass", UserObject.USER.getPass());
        hashMap.put("id", String.valueOf(id));

        SeasonsConnection seasonsConnection =
                new SeasonsConnection(
                        hashMap,
                        new SeasonsConnection.SeasonsConnectionEvent() {
                            @Override
                            public void onSucess(List<SeasonObject> listSeasons2) {
                                // TODO: Implement this method

                                runOnUiThread(
                                        new Runnable() {
                                            public void run() {
                                                listSeasons = listSeasons2;
                                                SeasonObject seasonObject = listSeasons2.get(0);

                                                int id = seasonObject.getId();
                                                int numSeason = seasonObject.getNumSeason();

                                                List<String> listSeasonsNumber = new ArrayList<>();

                                                for (SeasonObject season : listSeasons2) {
                                                    listSeasonsNumber.add("Temporada " + 
                                                            String.valueOf(season.getNumSeason()));
                                                }
                                                pMovieListViewOptions.setAdapter(
                                                        new MovieOptionsAdapter(
                                                                MovieActivity.this,
                                                                listSeasonsNumber,
                                                                null));
                                                modeListViewOptions = MODE_SEASONS;

                                                getEpisodesFromSeasons(id, numSeason);
                                            }
                                        });
                            }

                            @Override
                            public void onFailed(String error) {
                                runOnUiThread(new Runnable() {
                                            public void run() {
                                Toast.makeText(MovieActivity.this, error, Toast.LENGTH_LONG).show();
                                               // getInfoEpisodes(listEpisodes, season);
                                            }
                                        });
                            }
                
                
                        });

        //getEpisodesFromSeasons(23, 1);
    }

    private void getEpisodesFromSeasons(int id, int season) {
        // aqui vai carregar os episodios tlgd;-;
        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put("user", UserObject.USER.getName());
        hashMap.put("pass", UserObject.USER.getPass());
        hashMap.put("id", String.valueOf(id));

        EpisodesConnection seasonsConnection =
                new EpisodesConnection(
                        hashMap,
                        new EpisodesConnection.EpisodesConnectionEvent() {
                            @Override
                            public void onSucess(final List<EpisodeObject> listEpisodes) {
                                // TODO: Implement this method

                                runOnUiThread(
                                        new Runnable() {
                                            public void run() {
                                              getInfoEpisodes(listEpisodes, season);
                                            }
                                        });
                            }

                            @Override
                            public void onFailed(final String error) {
                                 runOnUiThread(
                                        new Runnable() {
                                            public void run() {
                                Toast.makeText(MovieActivity.this, error, Toast.LENGTH_LONG).show();
                                               // getInfoEpisodes(listEpisodes, season);
                                            }
                                        });
                    
                                // TODO: Implement this method
                            }
                        });

        /* List<EpisodesObject> listEpisodes = new ArrayList<EpisodesObject>();

        for(int i = 0; i < 10; i++){

            EpisodesObject episodeObject = new EpisodesObject("Episode " + i, "Exciting episode with lots of action", "banner_url", "00:45:37", 10*i);

            listEpisodes.add(episodeObject);
        }

        pRecyclerViewEpisodes.setLayoutManager(new LinearLayoutManager(this));
        pRecyclerViewEpisodes.setAdapter(new EpisodesAdapter(MovieActivity.this, listEpisodes));
        */
    }
    
    private void getInfoEpisodes(List<EpisodeObject> listEpisodes, int season){
        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put("user", UserObject.USER.getName());
        hashMap.put("pass", UserObject.USER.getPass());
        hashMap.put("id", String.valueOf(serieId));
        hashMap.put("season", String.valueOf(season));

        EpisodesDetailsConnection seasonsConnection =
                new EpisodesDetailsConnection(listEpisodes,
                        hashMap,
                        new EpisodesDetailsConnection.EpisodesDetailsConnectionEvent() {
                            @Override
                            public void onSucess(List<EpisodeObject> listEpisodes2) {
                                // TODO: Implement this method

                                runOnUiThread(
                                        new Runnable() {
                                            public void run() {
                                                pRecyclerViewEpisodes.setLayoutManager(new LinearLayoutManager(MovieActivity.this));
                                                pRecyclerViewEpisodes.setAdapter(new EpisodesAdapter(MovieActivity.this, movie, listEpisodes2));
                                            }
                                        });
                            }

                            @Override
                            public void onFailed(String error) {
                                runOnUiThread(
                                        new Runnable() {
                                            public void run() {
                                Toast.makeText(MovieActivity.this, error, Toast.LENGTH_LONG).show();
                                // TODO: Implement this method
                    
                    }
                });
                            }
                        });

        
        
    }
}
