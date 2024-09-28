package com.ultra.vision.tv;

import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.source.*;
import com.google.android.exoplayer2.source.hls.*;
import com.google.android.exoplayer2.trackselection.*;
import com.google.android.exoplayer2.ui.*;
import com.google.android.exoplayer2.upstream.*;
import com.google.android.exoplayer2.util.*;
import com.ultra.vision.database.*;
import com.ultra.vision.debug.*;
import com.ultra.vision.implementations.*;
import com.ultra.vision.tv.database.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.*;
import com.ultra.vision.connection.URLConnection;
import com.ultra.vision.connection.*;
import com.ultra.vision.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class StreamingActivity extends AppCompatActivity
{

	private int FLAG_FULLSCREEN = WindowManager.LayoutParams.FLAG_FULLSCREEN;
	//List<String> getChannels, getCategory = new ArrayList<>();
	//private Context CONTEXT = StreamingActivity.this;
	private int FLAG_SCREEN_ON = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
	DataSource.Factory dataSourceFactory;
	SimpleExoPlayerView playerView;
    //ProgressBar loading;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
	MediaSource mediaSource;
    ExoPlayer player;
	
	ListView listViewChannels;
	//DatabaseReference dRef;
	//Query queryChannels;
	
	
	//CANAIS
	BDChannels stream;
	
	//Outros
	BDChannels outrosCANAIS;
	
	//CATEGORIAS
	List<String> categorias;
	
	//ListView categoria
	ListView listViewCategory;
	
	//Boolean View Clique
	boolean visibilityPlayer = true;
	
	
	//Layout loading
	LinearLayout linearModelGraphic;
	
	//Chave do SharedPreferences para pegar o ultimo canal selecionado
	String key = "out_channel";
	
    //String do ultino canal selecionado para reconectar
    String channel_reconnect;
    
	@Override
	public void onStop()
	{
		if (player != null)
		{
			player.release();
		}


		super.onStop();



	}

	@Override
	protected void onResume()
	{
		player.prepare(mediaSource);


		player.prepare(mediaSource, true, false);
		super.onResume();
	}
	@Override
    public void onStart()
	{
        super.onStart();
      //  LibVLC
        //--------------------------------------
        //Creating default track selector
        //and init the player
        TrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        player = ExoPlayerFactory.newSimpleInstance(
            new DefaultRenderersFactory(this),
            new DefaultTrackSelector(adaptiveTrackSelection),
            new DefaultLoadControl());

        playerView.setPlayer(player);
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        dataSourceFactory = new DefaultDataSourceFactory(this,
														 Util.getUserAgent(this, "Exo2"), defaultBandwidthMeter);

		// String hls_url = "http://acsc.cc:80/2mqYW/QfK3q5/71761.m3u8";


        player.setPlayWhenReady(playWhenReady);
        player.addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason)
				{
					//Toast("onTimelineChanged");
                }
                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections)
				{
					//Toast("onTracksChanged");
                }
                @Override
                public void onLoadingChanged(boolean isLoading)
				{
					//Toast("onLoadingChanged\n\nisLoading: " + isLoading);
					if (isLoading == false)
					{
						linearModelGraphic.setVisibility(View.VISIBLE);
						//Toast("Loading...");
                        
                        if(channel_reconnect != null){
                              connectChannel(channel_reconnect);
                           }

						/*if(alertError == false){
						 alertError = true;
						 new Handler().postDelayed(new Runnable(){

						 @Override
						 public void run()
						 {
						 /*if(isLoading == false){
						 Alert("Error","Não foi possivel reproduzir este canal\n\n1- Canal Pode estar em Manutenção\n2- Canal pode estar travando\n3- Conexão com a Internet\n\nVerifique e tente novamente");
						 }}


						 },10000);

						 }*/
					}
					else
					{
						linearModelGraphic.setVisibility(View.INVISIBLE);
						//Toast("Sucess");
						/*if(IsRunning){
						 if(ativado == true){
						 IsRunning = false;
						 relativeLayout01.addView(relativeTuto);
						 }
						 }*/

					}



					/*if(isLoading){
					 linearModelGraphic.setVisibility(View.VISIBLE);
					 }*/
                }
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState)
				{
                    switch (playbackState)
					{
                        case ExoPlayer.STATE_READY:
							//Toast("STATE_READY");
                            linearModelGraphic.setVisibility(View.INVISIBLE);
							//Toast("Sucess");

                            break;
                        case ExoPlayer.STATE_BUFFERING:
							//Toast("STATE_BUFFERING");
                           linearModelGraphic.setVisibility(View.VISIBLE);
                        
                           
						 //  Toast("...");
                            break;
                    }
                }
                @Override
                public void onRepeatModeChanged(int repeatMode)
				{
					//Toast("onRepeatModeChanged");
                }
                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled)
				{
					//Toast("onShuffleModeEnabledChanged");
                }
                @Override
                public void onPlayerError(ExoPlaybackException error)
				{
					//Toast("onPlayerError");
                }
                @Override
                public void onPositionDiscontinuity(int reason)
				{
					//Toast("onPositionDiscontinuity");
                }
                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters)
				{
					//Toast("onPlaybackParametersChanged");
                }
                @Override
                public void onSeekProcessed()
				{
					//Toast("onSeekProcessed");
                }
           
			});
			
        player.seekTo(currentWindow, playbackPosition);

    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().setFlags(FLAG_FULLSCREEN,FLAG_FULLSCREEN);
		getWindow().setFlags(FLAG_SCREEN_ON, FLAG_SCREEN_ON);
		setTheme(R.style.AppTheme);
		setContentView(R.layout.activity_stream);
		
		Typeface fontBunkenTechSans = Typeface.createFromAsset(getAssets(), "fonts/BunkenTechSans.ttf");    
		
		listViewCategory = findViewById(R.id.activity_streamListViewCategory);
		listViewChannels = findViewById(R.id.activity_streamListViewChannels);
		
		verifyTesting();
		//startHandler();
		
		//PlayerView
		playerView = findViewById(R.id.activity_stream_exoPlayerView);
		playerView.setUseController(false);
		playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
		playerView.setPlayer(player);
		
		//recyclerViewCategory = findViewById(R.id.activity_streamRecyclerViewCategory);
		//recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
		//recyclerViewCategory.setVisibility(View.VISIBLE);

		TextView textByCredits = findViewById(R.id.activity_streamTextView);
		textByCredits.setVisibility(View.VISIBLE);
		textByCredits.setTypeface(fontBunkenTechSans);
	
		
		//View Clique
		View view = findViewById(R.id.activity_streamView);
		
		view.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1) {
					if(visibilityPlayer){
						listViewChannels.setVisibility(View.GONE);
						listViewCategory.setVisibility(View.GONE);
						//playerView.setControllerShowTimeoutMs(3000); // Definir tempo limite de exibição dos controles (3 segundos)
						playerView.setUseController(true);
						visibilityPlayer = false;
					}else{
						listViewChannels.setVisibility(View.VISIBLE);
						listViewCategory.setVisibility(View.VISIBLE);
						//playerView.setShowTimeoutMs(0); // Oculta os botões
						//playerView.setControllerShowTimeoutMs(0); // Ocultar os controles
						playerView.setUseController(false);
						visibilityPlayer = true;
					}
				}

			
		});
		
		android.graphics.drawable.GradientDrawable BCFDDIH = new android.graphics.drawable.GradientDrawable();
		BCFDDIH.setColor(Color.parseColor("#50000000"));
		BCFDDIH.setCornerRadii(new float[] { 5, 5, 5, 5, 5, 5, 5, 5 });
		
		linearModelGraphic = findViewById(R.id.activity_streamLinearLayout);
		linearModelGraphic.setBackground(BCFDDIH);
		if (Build.VERSION.SDK_INT >= 21)
		{ linearModelGraphic.setElevation(1f); }

		//layoutParams = new LayoutParams(-2, -2);
		//loading = new ProgressBar(this);

		ProgressBar loading = findViewById(R.id.activity_streamProgressBar);

		loading = new android.widget.ProgressBar(
			this,
			null,
			android.R.attr.progressBarStyle);

		loading.getIndeterminateDrawable().setColorFilter(0xFF1A0EDD, android.graphics.PorterDuff.Mode.MULTIPLY);
		
		
		listViewCategory.setOnItemClickListener(new ListView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int itemPosition, long p4)
				{
					BDChannels newChannelsByCategory = new BDChannels(); 
					for(int i = 0; i < stream.getContext().size(); i++){
						if(categorias.get(itemPosition).equalsIgnoreCase(stream.getContext().get(i))){
							newChannelsByCategory.addName(stream.getName().get(i));
							newChannelsByCategory.addContext(stream.getContext().get(i));
							newChannelsByCategory.addLogo(stream.getLogo().get(i));
							//newChannelsByCategory.addLink(stream.getLink().get(i));
						}
					}

					outrosCANAIS = newChannelsByCategory;
					listViewChannels.setAdapter(new BDListView(StreamingActivity.this, newChannelsByCategory));
				}

			
		});
		
		
		listViewChannels.setOnItemClickListener(new ListView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
					//Toast(outrosCANAIS.getLink().get(p3));
					//Log.e("akaaaka", outrosCANAIS.getLink().get(p3));
                    
					GetLinkFromName get = new GetLinkFromName(StreamingActivity.this);
					get.getLinkFromName(outrosCANAIS.getName().get(p3), new URLConnection.Auth(){

							@Override
							public void onSucess(String response)
							{
                                channel_reconnect = response;
                                
								connectChannel(channel_reconnect);
							}

							@Override
							public void onFailed(String error)
							{
								// TODO: Implement this method
							}

						
						}, "/stream/get/get_link.php");
					
					
				}

			
		});
		
		
		
	}
	
	private void startHandler(){
		Handler handler = new Handler();
		handler.postDelayed(new Runnable(){

				@Override
				public void run()
				{
					// TODO: Implement this method
					loadingChannels();
				}

			
		}, 3000);
	}
	
    private void connectChannel(String link){
       /* Uri uri = Uri.parse(link);
        Handler mainHandler = new Handler();
		mediaSource = new HlsMediaSource(uri, dataSourceFactory,mainHandler, null);
        player.prepare(mediaSource);
		player.prepare(mediaSource, true, false);*/
        
      //  private void connectChannel(String link) {
        
       // link = "http://bdtb.io:80/aQTgkuMB/Wpz75C/90647.ts";
        if(link.contains(".ts")){
            link = link.replaceAll(".ts",".m3u8");
        }
    Uri uri = Uri.parse(link);

    // Criar uma instância de HlsMediaSource
    mediaSource = new HlsMediaSource.Factory(dataSourceFactory)
        .createMediaSource(uri);

    // Preparar o ExoPlayer com a nova fonte de mídia
    player.prepare(mediaSource);
    player.setPlayWhenReady(true);

        
		DatabaseMemory db = new DatabaseMemory(StreamingActivity.this);
        db.putString(key,link);
        
        //Toast(link);
    }
    
	private void loadingChannels(){
		//GetChannels get = new GetChannels();
		BDChannels myChannels = new BDChannels();
		myChannels.getChannelsList(new BDChannels.getAllChannels(){

				@Override
				public void getStreaming(BDChannels channel) {
					stream = channel;
					outrosCANAIS = channel;
					reproduzirPrimeiroCanal();
                    
                    Toast(channel.getName().size()+"\n" + channel.getLogo().size() +
                        "\n" + channel.getContext().size());
					
					//List<String> categoria = stream.getContext();
					//GFG2.removeDuplicates(categ
					categorias = new ArrayList<>();
					for(int j = 0; j < stream.getContext().size(); j++){
						//categorias = stream.getContext();
						categorias.add(stream.getContext().get(j));
					}
					
					categorias = GFG2.removeDuplicates(categorias);
					//categorias = GFG2.removeDuplicates(categorias);
                    listViewChannels.setAdapter(new BDListView(StreamingActivity.this, stream));
					listViewCategory.setAdapter(new listViewCategoryAdapter(StreamingActivity.this,categorias));
					
				}


		}, this);
	
	}
	
	private void reproduzirPrimeiroCanal(){
		//("https://stmv1.srvif.com/gospelf/gospelf/playlist.m3u8");
		
	//	String outChannel = outrosCANAIS.getLink().get(6);
		
		DatabaseMemory db = new DatabaseMemory(StreamingActivity.this);
	
		if(!(db.getString(key).isEmpty())){
			
		//	outChannel = db.getString(key);
			
		}
        
        //channel_reconnect = outChannel;
		
	//	connectChannel(outChannel);
	}
	
	private void verifyTesting()
	{
		if(Applications.i == 41){
				startHandler();
			}else{
            finish();
        }
    }
	
	public final int dp(int value)
	{
        return (int) TypedValue.applyDimension(1, (float) value, getResources().getDisplayMetrics());
    }
	
	private void Toast(String msg)
	{

        Toast.makeText(this,
                       msg, Toast.LENGTH_SHORT).show();

    }

	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		//ArrayList<HashMap<String, Object>> data;
		
		List<String> LIST;
		
		public Recyclerview1Adapter(List<String> list) {
			this.LIST = list;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflater.inflate(R.layout.custom_recyclerview, null);
			//v.setOnClickListener(new MyOnClickListener());

			RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			v.setLayoutParams(lp);
			return new ViewHolder(v);
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, final int position) {
			View view = holder.itemView;

			final Button button = view.findViewById(R.id.testinpo);
			button.setText(LIST.get(position));
			//button.setOnClickListener(new MyOnClickListener());
			//final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);

			RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			lp.setMarginStart(dp(3));
			view.setLayoutParams(lp);
			
			view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View view, boolean hasFocus) {
						if (hasFocus) {
							button.setSelected(true); // Altere a cor do componente dentro do item
						} else {
							button.setSelected(false); // Restaure a cor original do componente dentro do item
						}
					}
				});
				
		}

		@Override
		public int getItemCount() {
			return LIST.size();
		}

		public class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
			}
		}

	}
	
	public class listViewCategoryAdapter extends BaseAdapter {
		private Context context;
		//private LruCache<String, Bitmap> imageCache;
		private List<String> categorias;

		public listViewCategoryAdapter(Context context, List<String> categorias) {
			this.context = context;
			this.categorias = categorias;
		}

		@Override
		public int getCount() {
			return categorias.size();
		}

		@Override
		public Object getItem(int position) {
			return categorias.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View rowView = inflater.inflate(R.layout.list_view_adapter, parent, false);
			rowView.setBackgroundResource(R.drawable.ic_listview_selector);

			TextView text = rowView.findViewById(android.R.id.text1);
			text.setText(categorias.get(position));

			rowView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View view, boolean hasFocus) {
						if (hasFocus) {
							rowView.setSelected(true); // Altere a cor do componente dentro do item
						} else {
							rowView.setSelected(false); // Restaure a cor original do componente dentro do item
						}
					}
				});

			return rowView;
		}

		
	}
	

	public class BDListView extends BaseAdapter {
		private Context context;
		//private LruCache<String, Bitmap> imageCache;
		private BDChannels BDCHANNELS;
		
		public BDListView(Context context, BDChannels bdChannels) {
			this.context = context;
			this.BDCHANNELS = bdChannels;

			//int maxCacheSize = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;
			//imageCache = new LruCache<String, Bitmap>(maxCacheSize);
		}

		@Override
		public int getCount() {
			return BDCHANNELS.getName().size();
		}

		@Override
		public Object getItem(int position) {
			return BDCHANNELS.getName().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View rowView = inflater.inflate(R.layout.custom_listview2, parent, false);
			rowView.setBackgroundResource(R.drawable.ic_listview_selector);
			
			final ImageView img = rowView.findViewById(R.id.iconn);
			TextView name = rowView.findViewById(R.id.text01);
			TextView unname = rowView.findViewById(R.id.text02);

			name.setText(BDCHANNELS.getName().get(position));
			unname.setText(BDCHANNELS.getContext().get(position));

			String imageUrl = BDCHANNELS.getLogo().get(position);

			
            RequestOptions options = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL) // Armazena cache em disco
            .placeholder(R.drawable.iconapp) // Imagem de placeholder enquanto carrega
            .error(R.drawable.ic_checkbox_off); // Imagem de erro, caso ocorra algum problema

    Glide.with(img.getContext())
            .load(imageUrl)
            .apply(options)
            .into(img);
            
			
			rowView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View view, boolean hasFocus) {
						if (hasFocus) {
							rowView.setSelected(true); // Altere a cor do componente dentro do item
						} else {
							rowView.setSelected(false); // Restaure a cor original do componente dentro do item
						}
					}
				});

			return rowView;
		}

		private Bitmap loadImageFromUrl(String imageUrl) {
			try {
				URL url = new URL(imageUrl);

				if (imageUrl.startsWith("https://")) {
					HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
					https.connect();

					int responseCode = https.getResponseCode();

					if (responseCode == HttpsURLConnection.HTTP_OK) {
						InputStream input = https.getInputStream();
						return BitmapFactory.decodeStream(input);
					}
				} else {
					HttpURLConnection http = (HttpURLConnection) url.openConnection();
					http.connect();

					int responseCode = http.getResponseCode();

					if (responseCode == HttpURLConnection.HTTP_OK) {
						InputStream input = http.getInputStream();
						return BitmapFactory.decodeStream(input);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}
	
	
	}













/*
    package com.ultra.vision.tv;

import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.source.*;
import com.google.android.exoplayer2.source.hls.*;
import com.google.android.exoplayer2.trackselection.*;
import com.google.android.exoplayer2.ui.*;
import com.google.android.exoplayer2.upstream.*;
import com.google.android.exoplayer2.util.*;
import com.ultra.vision.database.*;
import com.ultra.vision.debug.*;
import com.ultra.vision.implementations.*;
import com.ultra.vision.tv.database.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.*;
import com.ultra.vision.connection.URLConnection;
import com.ultra.vision.connection.*;
import com.ultra.vision.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import org.videolan.libvlc.LibVLC;

public class StreamingActivity extends AppCompatActivity
{

	private int FLAG_FULLSCREEN = WindowManager.LayoutParams.FLAG_FULLSCREEN;
	//List<String> getChannels, getCategory = new ArrayList<>();
	//private Context CONTEXT = StreamingActivity.this;
	private int FLAG_SCREEN_ON = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
	DataSource.Factory dataSourceFactory;
	SimpleExoPlayerView playerView;
    //ProgressBar loading;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
	MediaSource mediaSource;
    ExoPlayer player;
	
	ListView listViewChannels;
	//DatabaseReference dRef;
	//Query queryChannels;
	
	
	//CANAIS
	BDChannels stream;
	
	//Outros
	BDChannels outrosCANAIS;
	
	//CATEGORIAS
	List<String> categorias;
	
	//ListView categoria
	ListView listViewCategory;
	
	//Boolean View Clique
	boolean visibilityPlayer = true;
	
	
	//Layout loading
	LinearLayout linearModelGraphic;
	
	//Chave do SharedPreferences para pegar o ultimo canal selecionado
	String key = "out_channel";
	
    //String do ultino canal selecionado para reconectar
    String channel_reconnect;
    
	@Override
	public void onStop()
	{
		if (player != null)
		{
			player.release();
		}


		super.onStop();



	}

	@Override
	protected void onResume()
	{
		player.prepare(mediaSource);


		player.prepare(mediaSource, true, false);
		super.onResume();
	}
	@Override
    public void onStart()
	{
        super.onStart();
      //  LibVLC
        //--------------------------------------
        //Creating default track selector
        //and init the player
        TrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        player = ExoPlayerFactory.newSimpleInstance(
            new DefaultRenderersFactory(this),
            new DefaultTrackSelector(adaptiveTrackSelection),
            new DefaultLoadControl());

        playerView.setPlayer(player);
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        dataSourceFactory = new DefaultDataSourceFactory(this,
														 Util.getUserAgent(this, "Exo2"), defaultBandwidthMeter);

		// String hls_url = "http://acsc.cc:80/2mqYW/QfK3q5/71761.m3u8";


        player.setPlayWhenReady(playWhenReady);
        player.addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason)
				{
					//Toast("onTimelineChanged");
                }
                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections)
				{
					//Toast("onTracksChanged");
                }
                @Override
                public void onLoadingChanged(boolean isLoading)
				{
					//Toast("onLoadingChanged\n\nisLoading: " + isLoading);
					if (isLoading == false)
					{
						linearModelGraphic.setVisibility(View.VISIBLE);
						//Toast("Loading...");
                        
                        if(channel_reconnect != null){
                              connectChannel(channel_reconnect);
                           }

						
					}
					else
					{
						linearModelGraphic.setVisibility(View.INVISIBLE);
						//Toast("Sucess");
						

					}



					
                }
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState)
				{
                    switch (playbackState)
					{
                        case ExoPlayer.STATE_READY:
							//Toast("STATE_READY");
                            linearModelGraphic.setVisibility(View.INVISIBLE);
							//Toast("Sucess");

                            break;
                        case ExoPlayer.STATE_BUFFERING:
							//Toast("STATE_BUFFERING");
                           linearModelGraphic.setVisibility(View.VISIBLE);
                        
                           
						 //  Toast("...");
                            break;
                    }
                }
                @Override
                public void onRepeatModeChanged(int repeatMode)
				{
					//Toast("onRepeatModeChanged");
                }
                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled)
				{
					//Toast("onShuffleModeEnabledChanged");
                }
                @Override
                public void onPlayerError(ExoPlaybackException error)
				{
					//Toast("onPlayerError");
                }
                @Override
                public void onPositionDiscontinuity(int reason)
				{
					//Toast("onPositionDiscontinuity");
                }
                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters)
				{
					//Toast("onPlaybackParametersChanged");
                }
                @Override
                public void onSeekProcessed()
				{
					//Toast("onSeekProcessed");
                }
           
			});
			
        player.seekTo(currentWindow, playbackPosition);

    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().setFlags(FLAG_FULLSCREEN,FLAG_FULLSCREEN);
		getWindow().setFlags(FLAG_SCREEN_ON, FLAG_SCREEN_ON);
		setTheme(R.style.AppTheme);
		setContentView(R.layout.activity_stream);
		
		Typeface fontBunkenTechSans = Typeface.createFromAsset(getAssets(), "fonts/BunkenTechSans.ttf");    
		
		listViewCategory = findViewById(R.id.activity_streamListViewCategory);
		listViewChannels = findViewById(R.id.activity_streamListViewChannels);
		
		verifyTesting();
		//startHandler();
		
		//PlayerView
		playerView = findViewById(R.id.activity_stream_exoPlayerView);
		playerView.setUseController(false);
		playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
		playerView.setPlayer(player);
		
		//recyclerViewCategory = findViewById(R.id.activity_streamRecyclerViewCategory);
		//recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
		//recyclerViewCategory.setVisibility(View.VISIBLE);

		TextView textByCredits = findViewById(R.id.activity_streamTextView);
		textByCredits.setVisibility(View.VISIBLE);
		textByCredits.setTypeface(fontBunkenTechSans);
	
		
		//View Clique
		View view = findViewById(R.id.activity_streamView);
		
		view.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1) {
					if(visibilityPlayer){
						listViewChannels.setVisibility(View.GONE);
						listViewCategory.setVisibility(View.GONE);
						//playerView.setControllerShowTimeoutMs(3000); // Definir tempo limite de exibição dos controles (3 segundos)
						playerView.setUseController(true);
						visibilityPlayer = false;
					}else{
						listViewChannels.setVisibility(View.VISIBLE);
						listViewCategory.setVisibility(View.VISIBLE);
						//playerView.setShowTimeoutMs(0); // Oculta os botões
						//playerView.setControllerShowTimeoutMs(0); // Ocultar os controles
						playerView.setUseController(false);
						visibilityPlayer = true;
					}
				}

			
		});
		
		android.graphics.drawable.GradientDrawable BCFDDIH = new android.graphics.drawable.GradientDrawable();
		BCFDDIH.setColor(Color.parseColor("#50000000"));
		BCFDDIH.setCornerRadii(new float[] { 5, 5, 5, 5, 5, 5, 5, 5 });
		
		linearModelGraphic = findViewById(R.id.activity_streamLinearLayout);
		linearModelGraphic.setBackground(BCFDDIH);
		if (Build.VERSION.SDK_INT >= 21)
		{ linearModelGraphic.setElevation(1f); }

		//layoutParams = new LayoutParams(-2, -2);
		//loading = new ProgressBar(this);

		ProgressBar loading = findViewById(R.id.activity_streamProgressBar);

		loading = new android.widget.ProgressBar(
			this,
			null,
			android.R.attr.progressBarStyle);

		loading.getIndeterminateDrawable().setColorFilter(0xFF1A0EDD, android.graphics.PorterDuff.Mode.MULTIPLY);
		
		
		listViewCategory.setOnItemClickListener(new ListView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int itemPosition, long p4)
				{
					BDChannels newChannelsByCategory = new BDChannels(); 
					for(int i = 0; i < stream.getContext().size(); i++){
						if(categorias.get(itemPosition).equalsIgnoreCase(stream.getContext().get(i))){
							newChannelsByCategory.addName(stream.getName().get(i));
							newChannelsByCategory.addContext(stream.getContext().get(i));
							newChannelsByCategory.addLogo(stream.getLogo().get(i));
							//newChannelsByCategory.addLink(stream.getLink().get(i));
						}
					}

					outrosCANAIS = newChannelsByCategory;
					listViewChannels.setAdapter(new BDListView(StreamingActivity.this, newChannelsByCategory));
				}

			
		});
		
		
		listViewChannels.setOnItemClickListener(new ListView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
					//Toast(outrosCANAIS.getLink().get(p3));
					//Log.e("akaaaka", outrosCANAIS.getLink().get(p3));
                    
					GetLinkFromName get = new GetLinkFromName(StreamingActivity.this);
					get.getLinkFromName(outrosCANAIS.getName().get(p3), new URLConnection.Auth(){

							@Override
							public void onSucess(String response)
							{
                                channel_reconnect = response;
                                
								connectChannel(channel_reconnect);
							}

							@Override
							public void onFailed(String error)
							{
								// TODO: Implement this method
							}

						
						}, "/stream/get/get_link.php");
					
					
				}

			
		});
		
		
		
	}
	
	private void startHandler(){
		Handler handler = new Handler();
		handler.postDelayed(new Runnable(){

				@Override
				public void run()
				{
					// TODO: Implement this method
					loadingChannels();
				}

			
		}, 3000);
	}
	
    private void connectChannel(String link){
       // Uri uri = Uri.parse(link);
      //  Handler mainHandler = new Handler();
	//	mediaSource = new HlsMediaSource(uri, dataSourceFactory,mainHandler, null);
    //    player.prepare(mediaSource);
	//	player.prepare(mediaSource, true, false);
        
      //  private void connectChannel(String link) {
        
        link = "http://bdtb.io:80/aQTgkuMB/Wpz75C/90647.ts";
    Uri uri = Uri.parse(link);

    // Criar uma instância de HlsMediaSource
    mediaSource = new HlsMediaSource.Factory(dataSourceFactory)
        .createMediaSource(uri);

    // Preparar o ExoPlayer com a nova fonte de mídia
    player.prepare(mediaSource);
    player.setPlayWhenReady(true);

        
		DatabaseMemory db = new DatabaseMemory(StreamingActivity.this);
        db.putString(key,link);
        
        Toast(link);
    }
    
	private void loadingChannels(){
		//GetChannels get = new GetChannels();
		BDChannels myChannels = new BDChannels();
		myChannels.getChannelsList(new BDChannels.getAllChannels(){

				@Override
				public void getStreaming(BDChannels channel) {
					stream = channel;
					outrosCANAIS = channel;
					reproduzirPrimeiroCanal();
                    
                    Toast(channel.getName().size()+"\n" + channel.getLogo().size() +
                        "\n" + channel.getContext().size());
					
					//List<String> categoria = stream.getContext();
					//GFG2.removeDuplicates(categ
					categorias = new ArrayList<>();
					for(int j = 0; j < stream.getContext().size(); j++){
						//categorias = stream.getContext();
						categorias.add(stream.getContext().get(j));
					}
					
					categorias = GFG2.removeDuplicates(categorias);
					//categorias = GFG2.removeDuplicates(categorias);
                    listViewChannels.setAdapter(new BDListView(StreamingActivity.this, stream));
					listViewCategory.setAdapter(new listViewCategoryAdapter(StreamingActivity.this,categorias));
					
				}


		}, this);
	
	}
	
	private void reproduzirPrimeiroCanal(){
		//("https://stmv1.srvif.com/gospelf/gospelf/playlist.m3u8");
		
	//	String outChannel = outrosCANAIS.getLink().get(6);
		
		DatabaseMemory db = new DatabaseMemory(StreamingActivity.this);
	
		if(!(db.getString(key).isEmpty())){
			
		//	outChannel = db.getString(key);
			
		}
        
        //channel_reconnect = outChannel;
		
	//	connectChannel(outChannel);
	}
	
	private void verifyTesting()
	{
		if(Applications.i == 41){
				startHandler();
			}else{
            finish();
        }
    }
	
	public final int dp(int value)
	{
        return (int) TypedValue.applyDimension(1, (float) value, getResources().getDisplayMetrics());
    }
	
	private void Toast(String msg)
	{

        Toast.makeText(this,
                       msg, Toast.LENGTH_SHORT).show();

    }

	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		//ArrayList<HashMap<String, Object>> data;
		
		List<String> LIST;
		
		public Recyclerview1Adapter(List<String> list) {
			this.LIST = list;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflater.inflate(R.layout.custom_recyclerview, null);
			//v.setOnClickListener(new MyOnClickListener());

			RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			v.setLayoutParams(lp);
			return new ViewHolder(v);
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, final int position) {
			View view = holder.itemView;

			final Button button = view.findViewById(R.id.testinpo);
			button.setText(LIST.get(position));
			//button.setOnClickListener(new MyOnClickListener());
			//final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);

			RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			lp.setMarginStart(dp(3));
			view.setLayoutParams(lp);
			
			view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View view, boolean hasFocus) {
						if (hasFocus) {
							button.setSelected(true); // Altere a cor do componente dentro do item
						} else {
							button.setSelected(false); // Restaure a cor original do componente dentro do item
						}
					}
				});
				
		}

		@Override
		public int getItemCount() {
			return LIST.size();
		}

		public class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
			}
		}

	}
	
	public class listViewCategoryAdapter extends BaseAdapter {
		private Context context;
		//private LruCache<String, Bitmap> imageCache;
		private List<String> categorias;

		public listViewCategoryAdapter(Context context, List<String> categorias) {
			this.context = context;
			this.categorias = categorias;
		}

		@Override
		public int getCount() {
			return categorias.size();
		}

		@Override
		public Object getItem(int position) {
			return categorias.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View rowView = inflater.inflate(R.layout.list_view_adapter, parent, false);
			rowView.setBackgroundResource(R.drawable.ic_listview_selector);

			TextView text = rowView.findViewById(android.R.id.text1);
			text.setText(categorias.get(position));

			rowView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View view, boolean hasFocus) {
						if (hasFocus) {
							rowView.setSelected(true); // Altere a cor do componente dentro do item
						} else {
							rowView.setSelected(false); // Restaure a cor original do componente dentro do item
						}
					}
				});

			return rowView;
		}

		
	}

	public class BDListView extends BaseAdapter {
		private Context context;
		//private LruCache<String, Bitmap> imageCache;
		private BDChannels BDCHANNELS;
		
		public BDListView(Context context, BDChannels bdChannels) {
			this.context = context;
			this.BDCHANNELS = bdChannels;

			//int maxCacheSize = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;
			//imageCache = new LruCache<String, Bitmap>(maxCacheSize);
		}

		@Override
		public int getCount() {
			return BDCHANNELS.getName().size();
		}

		@Override
		public Object getItem(int position) {
			return BDCHANNELS.getName().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View rowView = inflater.inflate(R.layout.custom_listview2, parent, false);
			rowView.setBackgroundResource(R.drawable.ic_listview_selector);
			
			final ImageView img = rowView.findViewById(R.id.iconn);
			TextView name = rowView.findViewById(R.id.text01);
			TextView unname = rowView.findViewById(R.id.text02);

			name.setText(BDCHANNELS.getName().get(position));
			unname.setText(BDCHANNELS.getContext().get(position));

			String imageUrl = BDCHANNELS.getLogo().get(position);

			
            RequestOptions options = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL) // Armazena cache em disco
            .placeholder(R.drawable.iconapp) // Imagem de placeholder enquanto carrega
            .error(R.drawable.ic_checkbox_off); // Imagem de erro, caso ocorra algum problema

    Glide.with(img.getContext())
            .load(imageUrl)
            .apply(options)
            .into(img);
            
			
			rowView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View view, boolean hasFocus) {
						if (hasFocus) {
							rowView.setSelected(true); // Altere a cor do componente dentro do item
						} else {
							rowView.setSelected(false); // Restaure a cor original do componente dentro do item
						}
					}
				});

			return rowView;
		}

		private Bitmap loadImageFromUrl(String imageUrl) {
			try {
				URL url = new URL(imageUrl);

				if (imageUrl.startsWith("https://")) {
					HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
					https.connect();

					int responseCode = https.getResponseCode();

					if (responseCode == HttpsURLConnection.HTTP_OK) {
						InputStream input = https.getInputStream();
						return BitmapFactory.decodeStream(input);
					}
				} else {
					HttpURLConnection http = (HttpURLConnection) url.openConnection();
					http.connect();

					int responseCode = http.getResponseCode();

					if (responseCode == HttpURLConnection.HTTP_OK) {
						InputStream input = http.getInputStream();
						return BitmapFactory.decodeStream(input);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}
	
	
	}

*/