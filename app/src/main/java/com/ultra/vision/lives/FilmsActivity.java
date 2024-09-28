package com.ultra.vision.lives;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import androidx.recyclerview.widget.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import androidx.cardview.widget.*;
import com.ultra.vision.*;
import com.ultra.vision.implementations.*;
import com.ultra.vision.lives.*;
import com.ultra.vision.movies.database.*;
import java.io.*;
import java.net.*;
import java.util.*;



public class FilmsActivity extends Activity
{
	
	BDFilms bdFilms;
	ListView listViewCategoryFilms;
	RecyclerView recyclerViewFilms;
	List<String> categorias;
	
	LinearLayout lnrLoading;
	
	private int FLAG_FULLSCREEN = WindowManager.LayoutParams.FLAG_FULLSCREEN;
	//List<String> getChannels, getCategory = new ArrayList<>();
	//private Context CONTEXT = StreamingActivity.this;
	private int FLAG_SCREEN_ON = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lives);
		getWindow().setFlags(FLAG_FULLSCREEN,FLAG_FULLSCREEN);
		getWindow().setFlags(FLAG_SCREEN_ON, FLAG_SCREEN_ON);
		
		listViewCategoryFilms = findViewById(R.id.activity_filmsListViewCategorias);
		recyclerViewFilms = findViewById(R.id.activity_filmsRecyclerViewFilms);
		lnrLoading = findViewById(R.id.activity_filmsLinearLayout);
		
		int numberOfColumns = 3; // Define o número de colunas desejado

		//RecyclerView recyclerView = findViewById(R.id.recyclerView);

		GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns, RecyclerView.VERTICAL, false);
		//recyclerView.setLayoutManager(layoutManager);

// Defina o adapter do RecyclerView e outros detalhes de configuração aqui
// ...
		
		recyclerViewFilms.setLayoutManager(layoutManager);
		
		//int itemSpacing = getResources().getDimensionPixelSize(dp(10));
		//recyclerViewFilms.addItemDecoration(new CustomItemDecoration(itemSpacing));
		
		
		new Handler().postDelayed(new Runnable(){

				@Override
				public void run()
				{
					loadFilms();
				}

			
		}, 3000);
		
		
		ItemClickSupport.addTo(recyclerViewFilms)
			.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
				@Override
				public void onItemClicked(RecyclerView recyclerView, int itemPosition, View v)
				{
					/*BDFilms.Film filme = new BDFilms.Film();
					filme.setNome(bdFilms.getName().get(itemPosition));
					filme.setCategoria(bdFilms.getContext().get(itemPosition));
					filme.setLogo(bdFilms.getLogo().get(itemPosition));
					//filme.setLink(bdFilms.getLink().get(itemPosition));
					BDFilms.Film.startMovie(filme,FilmsActivity.this);*/
					
				}

			});
			
		listViewCategoryFilms.setOnItemClickListener(new ListView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					BDFilms newFilmByCategory = new BDFilms(); 
					lnrLoading.setVisibility(View.VISIBLE);
					for(int i = 0; i < bdFilms.getContext().size(); i++){
						if(categorias.get(p3).equalsIgnoreCase(bdFilms.getContext().get(i))){
							newFilmByCategory.addName(bdFilms.getName().get(i));
							newFilmByCategory.addContext(bdFilms.getContext().get(i));
							newFilmByCategory.addLogo(bdFilms.getLogo().get(i));
							//newFilmByCategory.addLink(bdFilms.getLink().get(i));
						}
					}

					//outrosCANAIS = newChannelsByCategory;
					recyclerViewFilms.setAdapter(new Recyclerview1Adapter(newFilmByCategory));
					lnrLoading.setVisibility(View.GONE);
				}

				
			});
		
	}
	
	private void loadFilms(){
		//GetChannels get = new GetChannels();
	/*	BDFilms myFilms = new BDFilms();
		myFilms.getFilmsList(new BDFilms.getAllFilms(){

				@Override
				public void getFilms(BDFilms films) {
					bdFilms = films;
					//outrosCANAIS = channel;
					//reproduzirPrimeiroCanal();
					
					categorias = new ArrayList<>();
					for(int j = 0; j < bdFilms.getContext().size(); j++){
						//categorias = stream.getContext();
						categorias.add(bdFilms.getContext().get(j));
					}

					categorias = GFG2.removeDuplicates(categorias);
					//Toast.makeText(FilmsActivity.this, categorias.get(1),Toast.LENGTH_SHORT).show();
					//categorias = GFG2.removeDuplicates(categorias);
					listViewCategoryFilms.setAdapter(new BDListView(categorias));
					recyclerViewFilms.setAdapter(new Recyclerview1Adapter(bdFilms));
					lnrLoading.setVisibility(View.GONE);
					//listViewFilms.setAdapter(new BDListView(FilmsActivity.this, bdFilms));
					//List<String> categoria = stream.getContext();
					//GFG2.removeDuplicates(categ
					
				}


			}, this);*/

	}
	
	
	public class BDListView extends BaseAdapter {
		//private Context context;
		//private LruCache<String, Bitmap> imageCache;
		private List<String> CATEGORY_FILMS;

		public BDListView(List<String> categoryFilms) {
			//this.context = context;
			this.CATEGORY_FILMS = categoryFilms;

		}

		@Override
		public int getCount() {
			return CATEGORY_FILMS.size();
		}

		@Override
		public Object getItem(int position) {
			return CATEGORY_FILMS.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) FilmsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View rowView = inflater.inflate(R.layout.list_view_adapter, parent, false);
			rowView.setBackgroundResource(R.drawable.ic_listview_selector);

			//final ImageView img = rowView.findViewById(R.id.iconn);
			//TextView name = rowView.findViewById(R.id.text01);
			TextView unname = rowView.findViewById(android.R.id.text1);

			//Toast.makeText(FilmsActivity.this, CATEGORY_FILMS.get(1),Toast.LENGTH_SHORT).show();
			//unname.setText(BDCHANNELS.getName().get(position));
			unname.setText(CATEGORY_FILMS.get(position));

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
	
	LinearLayoutManager layoutManagerr = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
		@Override
		public boolean canScrollHorizontally() {
			return false; // Impede a rolagem horizontal do RecyclerView
		}

		@Override
		public boolean canScrollVertically() {
			return true; // Impede a rolagem vertical do RecyclerView
		}

		@Override
		public void onLayoutCompleted(RecyclerView.State state) {
			super.onLayoutCompleted(state);
			int itemCount = state.getItemCount();
			if (itemCount > 0) {
				View lastChild = getChildAt(itemCount - 1);
				int lastChildBottom = getDecoratedBottom(lastChild);
				int parentBottom = getHeight() - getPaddingBottom();
				if (lastChildBottom < parentBottom) {
					offsetChildrenVertical(parentBottom - lastChildBottom);
				}
			}
		}
	};
	
	
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		//ArrayList<HashMap<String, Object>> data;
		
		BDFilms BDFILMS;
		//RecyclerView recyclerView;
		//boolean isFuntion;
		
		public Recyclerview1Adapter(BDFilms bdfilms) {
			this.BDFILMS = bdfilms;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflater.inflate(R.layout.custom_recyclerview_films, null);
			//v.setOnClickListener(new MyOnClickListener());
			
			RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			//lp.setMarginStart(dp(10));
			v.setLayoutParams(lp);
			
			return new ViewHolder(v);
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, final int position) {
			View view = holder.itemView;

			CardView cardView = view.findViewById(R.id.cardView11);
			//cardView.setLayoutParams(new LayoutParams(dp(130),dp(165)));
			cardView.setElevation(dp(10));
			cardView.setRadius(dp(7));
			cardView.setCardBackgroundColor(Color.WHITE);
			cardView.setMaxCardElevation(dp(10));
			cardView.setUseCompatPadding(true);
			
			
			final ImageView imageView5 = view.findViewById(R.id.imgImagee);
			TextView textView = view.findViewById(R.id.txtNamee);

			textView.setText(BDFILMS.getName().get(position));
			textView.setShadowLayer(2,2,2,Color.BLACK);
		

			RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			lp.setMarginStart(dp(3));
			view.setLayoutParams(lp);
			
			new Thread(new Runnable(){

					URL url = null;

					HttpURLConnection http = null;

					Bitmap bit = null;

					@Override
					public void run()
					{//"https://www.thiengo.com.br/img/system/logo/thiengo-80-80.png"

						try
						{
							url = new URL(BDFILMS.getLogo().get(position));
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
											imageView5.setImageBitmap(bit);
											//img.setImageResource(R.mipmap.app3);

											/*Toast.makeText(context, 
											 "Finalizou...".toString(),
											 Toast.LENGTH_LONG).show();*/
											// TODO: Implement this method
										}else{
											//Toast(this,"KAKKKAK");
										}
									}


								});



						}
						catch (Exception e)
						{
							/*Toast.makeText(PrimeActivity.this,
										   e.toString(),
										   Toast.LENGTH_LONG).show();*/
						}
					}

				}).start();
			
			
		}
		
		@Override
		public int getItemCount() {
			return BDFILMS.getContext().size();
		}

		public class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
			}
		}

	}
	
	public final int dp(int value)
	{
		return (int) TypedValue.applyDimension(1, (float) value, getResources().getDisplayMetrics());
	}
	
	
}
