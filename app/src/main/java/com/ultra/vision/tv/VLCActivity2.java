package com.ultra.vision.tv;


public class VLCActivity2 {
    
}
/*import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ultra.vision.connection.GetLinkFromName;
import com.ultra.vision.connection.URLConnection;
import com.ultra.vision.databinding.ActivityVlcBinding;
import com.ultra.vision.implementations.GFG2;
import com.ultra.vision.tv.database.BDCategories;
import com.ultra.vision.tv.database.BDChannels;
import com.ultra.vision.tv.database.BDChannels2;
import com.ultra.vision.tv.object.TvCategory;
import com.ultra.vision.tv.object.TvChannel;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.util.VLCVideoLayout;
import com.ultra.vision.R;

public class VLCActivity2 extends Activity {

    private LibVLC libVLC;
    private MediaPlayer mediaPlayer;
    private Media media;

    private ActivityVlcBinding binding;
    
    //Objeto canais (nome,logo,categoria)
    private BDChannels stream, stream2;
    
    //Lista de Categorias (Canais)
    private List<String> categorias;
    
    //String com ultimo link do canal para caso de reconectar
    private String linkReconnect;
    
    //boolean responsavel pela visibilidade das listas
    private boolean isVisibleList = true;
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        if (libVLC != null) {
            libVLC.release();
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVlcBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int FLAG_FULLSCREEN = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        int FLAG_SCREEN_ON = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        getWindow().setFlags(FLAG_FULLSCREEN,FLAG_FULLSCREEN);
		getWindow().setFlags(FLAG_SCREEN_ON, FLAG_SCREEN_ON);

        // Initialize LibVLC
        libVLC = new LibVLC(this);
        mediaPlayer = new MediaPlayer(libVLC);

        // Set media options
        String mediaUrl = "http://bdtb.io:80/aQTgkuMB/Wpz75C/90647.ts"; // Replace with your .ts streaming URL
        media = new Media(libVLC, Uri.parse(mediaUrl));

        // Set media options and add event listener
        mediaPlayer.setEventListener(eventListener);

      //  mediaPlayer.setAspectRatio(null);
        //mediaPlayer.setScale(0);
        
        mediaPlayer.setMedia(media);
       // mediaPlayer.getVLCVout().setVideoView(binding.player);
        
        // Obtém as dimensões da tela
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Configura os parâmetros de layout para ajustar à tela
        mediaPlayer.getVLCVout().setWindowSize(screenWidth, screenHeight);
        
      //  mediaPlayer.getVLCVout().attachViews();
        mediaPlayer.attachViews(binding.player, null, true, false);
        mediaPlayer.play();
        
        binding.viewVisibility.setOnClickListener(v ->{
            if(isVisibleList){
                binding.listViewCategory.setVisibility(View.GONE);
                binding.listViewChannels.setVisibility(View.GONE); 
                isVisibleList = false;    
            }else{
                binding.listViewCategory.setVisibility(View.VISIBLE);
                binding.listViewChannels.setVisibility(View.VISIBLE); 
                isVisibleList = true;   
            }
        });
        
        //loadChannels();
        loadCategories();
            
            
        binding.listViewCategory.setOnItemClickListener(new ListView.OnItemClickListener(){

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

					stream2 = newChannelsByCategory;
					binding.listViewChannels.setAdapter(new BDListView(getApplicationContext(), newChannelsByCategory));
				}

			
		});
        
        
        binding.listViewChannels.setOnItemClickListener(new ListView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
					//Toast(outrosCANAIS.getLink().get(p3));
					//Log.e("akaaaka", outrosCANAIS.getLink().get(p3));
                    
					GetLinkFromName get = new GetLinkFromName(VLCActivity2.this);
					get.getLinkFromName(stream2.getName().get(p3), new URLConnection.Auth(){

							@Override
							public void onSucess(String response)
							{
                                linkReconnect = response;
                                
								connectChannel(linkReconnect);
							}

							@Override
							public void onFailed(String error)
							{
								// TODO: Implement this method
                                Toast(error);
							}

						
						}, "/stream/get/get_link.php");
					
					
				}

			
		});
        
    }
    
    private MediaPlayer.EventListener eventListener = new MediaPlayer.EventListener() {
        @Override
        public void onEvent(MediaPlayer.Event event) {
                    
            switch (event.type) {
            case MediaPlayer.Event.Opening:
                  //binding.lnrLoading.setVisibility(View.GONE);    
                  Toast("Opening");
                // O vídeo está carregando
                // Exemplo: exibir uma mensagem de buffering
                break;
            case MediaPlayer.Event.EncounteredError:
                // Ocorreu um erro durante a reprodução
                // Exemplo: exibir uma mensagem de erro
                Toast("Error");
                break;
            case MediaPlayer.Event.Playing:
                 binding.lnrLoading.setVisibility(View.GONE); 
                Toast("Playing");
                // O vídeo está sendo reproduzido
                // Exemplo: atualizar a interface do usuário para indicar que está reproduzindo
                break;
            // Adicione mais casos para outros tipos de eventos, se necessário
             case MediaPlayer.Event.EndReached:
                 mediaPlayer.release();
                 Toast("End");
        }
                    
        }
        };

    private void Toast(String msg){
        //Toast.makeText(VLCActivity2.this,msg,Toast.LENGTH_LONG).show();
    }
    
    //Conectar Canal
    private void connectChannel(String link) {
    if (mediaPlayer.isPlaying()) {
        mediaPlayer.stop();
        mediaPlayer.release();
        libVLC.release();
    }
        binding.lnrLoading.setVisibility(View.VISIBLE);
        
        mediaPlayer.detachViews();
        

    libVLC = new LibVLC(this);
    mediaPlayer = new MediaPlayer(libVLC);
    mediaPlayer.setEventListener(eventListener);

    media = new Media(libVLC, Uri.parse(link));
    mediaPlayer.setMedia(media);
   // mediaPlayer.getVLCVout().setVideoView(binding.player);
   // mediaPlayer.getVLCVout().attachViews();
    mediaPlayer.attachViews(binding.player, null, true, false);
    mediaPlayer.play();
}
    
    //Carregar Canais
    private void loadChannels(){
		//GetChannels get = new GetChannels();
		BDChannels myChannels = new BDChannels();
		myChannels.getChannelsList(new BDChannels.getAllChannels(){

				@Override
				public void getStreaming(BDChannels channel) {
					stream = channel;
					stream2 = channel;
                    
                    //Toast(channel.getName().size()+"\n" + channel.getLogo().size() +
                       // "\n" + channel.getContext().size());
                    
					categorias = new ArrayList<String>();
					for(int j = 0; j < stream.getContext().size(); j++){
						categorias.add(stream.getContext().get(j));
					}
					
					categorias = GFG2.removeDuplicates(categorias);
					
                    binding.listViewChannels.setAdapter(new BDListView(getApplicationContext(), stream));
                    
					binding.listViewCategory.setAdapter(new listViewCategoryAdapter(getApplicationContext(),categorias));
					
				}


		}, getApplicationContext());
        
	
	}
    
    private void loadChannels2(){
       
        
        }
    
    private void loadCategories(){
        BDCategories bdCategories = new BDCategories(VLCActivity2.this);
        bdCategories.getCategoryList(new BDCategories.getAllCategories(){
            @Override
            public void onSucess(List<TvCategory> listCategory){
                
               List<String> listCategoryTitle = new ArrayList<>();
                    
                for(TvCategory category : listCategory){
                    String title = category.getCategoryName();
                    listCategoryTitle.add(title);
                }
                    
                    runOnUiThread(new Runnable() {
    @Override
    public void run() {
        binding.listViewCategory.setAdapter(new listViewCategoryAdapter(getApplicationContext(), listCategoryTitle));
    }
});
					
            }
                
           @Override
           public void onFailed(String error){
               Toast(error);
                    //Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
           }  
        });
    }
    
    //Adapters para listView canais & listView Categoria
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
	}
	
}*/
