package com.ultra.vision.adapter;

import android.annotation.*;
import android.app.*;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import androidx.cardview.widget.*;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.ultra.vision.*;
import com.ultra.vision.movies.object.*;
import java.lang.ref.WeakReference;
import java.util.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import com.ultra.vision.adapter.config.CenterlZoomLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
//import com.facebook.shimmer.ShimmerFrameLayout;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>
 {
    private List<Category> categoryList;
//	private Activity activity;
	Category category;
	MovieAdapter movieAdapter;
    
    //Variaveis recebidas pelo HomeActivity
	public static MovieObject movieObject;
	public static String urlBannerPosterPath;
    private CenterlZoomLayoutManager layoutManager;
	//private RecyclerView recyclerView;
    private WeakReference<Activity> weak;

    private int lastFocusedItem = 0;
    
    public CategoryAdapter(Activity activity, List<Category> categoryList) {
	//	this.activity = activity;
        this.weak = new WeakReference<>(activity);
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

		// Configurar o modo de foco dos elementos filhos do RecyclerView da categoria
		//viewHolder.movieRecyclerView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        //recyclerView = viewHolder.movieRecyclerView;
		/*viewHolder.movieRecyclerView.setFocusable(true);
		viewHolder.movieRecyclerView.requestFocus(View.FOCUS_UP);
		viewHolder.movieRecyclerView.requestFocus(View.FOCUS_DOWN);
		viewHolder.movieRecyclerView.requestFocus(View.FOCUS_LEFT);
		viewHolder.movieRecyclerView.requestFocus(View.FOCUS_RIGHT);*/
        
      //  viewHolder.shimmerLayoutBtnPlay.startShimmer();
       // viewHolder.shimmerLayoutBtnMyList.startShimmer();
        //CenterlZoomLayoutManager layoutManager = new CenterlZoomLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        //viewHolder.movieRecyclerView.setLayoutManager(layoutManager);
       // viewHolder.movieRecyclerView.addOnScrollListener(onScrollCenter);
		
		viewHolder.movieAssistir.setOnFocusChangeListener(onFocus);
		
		viewHolder.movieAddLista.setOnFocusChangeListener(onFocus);
		return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		
		category = categoryList.get(position);
		
		
		
		switch(position){
			case 0:
				
				category = categoryList.get(position);
				
				holder.relativeLayoutBannerDestaque.setVisibility(View.VISIBLE);
                //	holder.imageBanner.setImageBitmap(bitMapBanner);
                String baseUrl = "https://image.tmdb.org/t/p/";
                String posterSize = "original"; // Tamanho desejado do pôster
                String posterUrl = baseUrl + posterSize + urlBannerPosterPath;

            RequestOptions options = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // Armazena cache em disco
                      //  .placeholder(R.drawable.iconapp) // Imagem de placeholder enquanto carrega
                        .error(R.drawable.ic_checkbox_off); // Imagem de erro, caso ocorra algum problema

                Glide.with(weak.get())
                        .load(posterUrl)
            .listener(new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            // Tratar falha no carregamento da imagem aqui
            return false; // Retorna false para permitir que o Glide continue com o tratamento padrão de erro
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            // A imagem foi carregada com sucesso
          //  holder.shimmerLayoutBtnPlay.stopShimmer();
            //holder.shimmerLayoutBtnPlay.setVisibility(View.GONE);
                        
           // holder.shimmerLayoutBtnMyList.stopShimmer();
            return false; // Retorna false para permitir que o Glide continue com o tratamento padrão
        }
    })
                        .apply(options)
                        .into(holder.imageBanner);
            
				holder.movieTitle.setText(movieObject.getTitle());
				holder.movieSubTitle.setText(movieObject.getOverview());
				
				//holder.movieAssistir.requestFocus();
				holder.relativeCategoryDefault.setVisibility(View.GONE);
				break;
			
			default:
			
				/*switch(category.getType()){
					case "category_?":break;
				}*/
				
				holder.relativeLayoutBannerDestaque.setVisibility(View.GONE);
				
				category = categoryList.get(position);
				holder.categoryTitle.setText(category.getTitle());
				
				String mode;

				if(category.getTitle().equalsIgnoreCase(Category.recent)){
					mode = "recents";
				}else mode = "default";

				movieAdapter = new MovieAdapter(weak.get(), category.getMovieList(), mode);
				holder.movieRecyclerView.setAdapter(movieAdapter);
			break;
		}
        
    }

    
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
    
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
	 
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
		//Destaque
		public CardView relativeLayoutBannerDestaque;
		public ImageView imageBanner;
		public TextView movieTitle;
		public TextView movieSubTitle;
		public Button movieAssistir;
		public Button movieAddLista;
		//Categorias
		public RelativeLayout relativeCategoryDefault;
        public TextView categoryTitle;
        public RecyclerView movieRecyclerView;
        //private ShimmerFrameLayout shimmerLayoutBtnPlay,shimmerLayoutBtnMyList;
        public ViewHolder(View itemView) {
            super(itemView);
			
			relativeLayoutBannerDestaque = itemView.findViewById(R.id.category_itemRelativeLayoutDestaque);
			imageBanner = itemView.findViewById(R.id.category_itemImageViewBanner);
			movieTitle = itemView.findViewById(R.id.categoryitemTextViewMovieTitle);
			movieSubTitle = itemView.findViewById(R.id.categoryitemTextViewMovieSubTitle);
			movieAssistir = itemView.findViewById(R.id.categoryitemButtonAssistir);
			movieAddLista = itemView.findViewById(R.id.categoryitemButtonAddLista);
			
			relativeCategoryDefault = itemView.findViewById(R.id.categoryitemRelativeLayoutCategoryDefault);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            movieRecyclerView = itemView.findViewById(R.id.movieRecyclerView);
			
           // shimmerLayoutBtnPlay = itemView.findViewById(R.id.shimmer_layout_btnAssistir);
           // shimmerLayoutBtnMyList = itemView.findViewById(R.id.shimmer_layout_btnMyList);
            
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            movieRecyclerView.setLayoutManager(layoutManager);
        }
    }
}

