package com.ultra.vision.movies;

import android.app.*;
import android.database.sqlite.*;
import android.graphics.*;
import android.os.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
//import com.nostra13.universalimageloader.core.*;

import com.google.android.material.chip.Chip;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.ultra.vision.*;
import com.ultra.vision.account.UserObject;
import com.ultra.vision.adapter.*;
import com.ultra.vision.adapter.CategoryAdapter;
import com.ultra.vision.movies.database.BDFilms;
import com.ultra.vision.movies.database.BDSeries;
import com.ultra.vision.movies.object.Category;
import com.ultra.vision.database.SQLiteHashMap;
import com.ultra.vision.movies.fragments.FragmentSearch;
import com.ultra.vision.movies.fragments.FragmentRecommended;
import com.ultra.vision.movies.fragments.FragmentHome;
import com.ultra.vision.movies.fragments.FragmentMyList;
import com.ultra.vision.movies.fragments.FragmentAccount;
import com.ultra.vision.databinding.ActivityyHomeBinding;
import com.ultra.vision.movies.object.Movie;
import com.ultra.vision.database.*;
import com.ultra.vision.databinding.ActivityyHomeBinding;
import com.ultra.vision.implementations.*;
import com.ultra.vision.movies.config.*;
import com.ultra.vision.movies.connection.*;
import com.ultra.vision.movies.database.*;
import com.ultra.vision.movies.object.*;
import java.io.*;
import java.net.*;
import java.util.*;
import com.ultra.vision.R;
import com.ultra.vision.movies.object.Movie;
import androidx.recyclerview.widget.*;
import com.ultra.vision.movies.fragments.*;


public class HomeActivity extends AppCompatActivity {
    
    private CategoryAdapter categoryAdapter;
    
	public static BDFilms bdFilms;
	public static BDSeries bdSeries;
	List<String> categorias;
	List<Category> categoryList; 
	
	SQLiteHashMap hashMap;
	HashMap<String, List<String>> retrievedHashMap;
	
	ImageButton imbSearch, imbRecomends, imbHome, imbMyList, imbAccount;
    
    RelativeLayout rltMenu;
    LinearLayout lnrLoading;
    
    FragmentSearch fragmentSearch;
    FragmentRecommended fragmentRecommended;
    FragmentHome fragmentHome;
    FragmentMyList fragmentMyList;
    FragmentAccount fragmentAccount;
	ActivityyHomeBinding binding;
    
    //Lista dos itens do chipNavigationBar
    List<View> chipNavBarItens;
    View ultimoItem = null;
    int keyCode = 0123; //ultimo botao selecionado no controle tv, 0123 por padrão
  //  private Drawer result;
   // private MiniDrawer miniDrawer;

   /* @Override
    public void onSaveInstanceState(Bundle arg0, PersistableBundle arg1) {
        //super.onSaveInstanceState(arg0, arg1);
        // TODO: Implement this method
    }*/
    
   
    
    @Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
        this.keyCode = keyCode;
 //   if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
        // O código aqui será executado quando o botão da esquerda for pressionado
        // Adicione sua lógica aqui
       // return true;  // Retorna true para indicar que o evento foi tratado
        
    return super.onKeyDown(keyCode, event);
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);

        binding = ActivityyHomeBinding.inflate(getLayoutInflater());

        int FLAG_FULLSCREEN = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        // List<String> getChannels, getCategory = new ArrayList<>();
        // private Context CONTEXT = StreamingActivity.this;
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        setContentView(binding.getRoot());

        // ChipNavigationBar bottomNavigation = findViewById(R.id.bottom_navigation);

        chipNavBarItens = new ArrayList<View>();

        for (int i = 0; i < binding.bottomMenu.getChildCount(); i++) {
            final int index = i;
            View view = (View) binding.bottomMenu.getChildAt(i);
            view.setFocusableInTouchMode(true);

            if(i == 2) view.requestFocus();
            view.setOnFocusChangeListener(itemChipNavigationBarFocus);
            
            chipNavBarItens.add(view);
        }
        
        
        

        binding.bottomMenu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
    @Override
    public void onItemSelected(int id) {
        // O código aqui será executado quando um item for selecionado
        // 'id' representa o ID do item selecionado
        //Log.d("ChipNavigationBar", "Item selecionado: " + id);
         Toast.makeText(HomeActivity.this, "selecionado", Toast.LENGTH_LONG).show();
    }
});
       /* binding.drawerBarLayout.openDrawer(GravityCompat.START);
        // binding.drawerLayout.openDrawer(GravityCompat.START);

        binding.drawerLayout.addDrawerListener(
                new DrawerLayout.SimpleDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // O código aqui será executado quando o DrawerLayout for aberto
                        if (drawerView.getId() == binding.navigationViewTv.getId()) {
                            // Se o drawer aberto for o NavigationView
                            // Coloque aqui o código que você deseja executar quando o
                            // NavigationView for aberto
                            binding.drawerBarLayout.closeDrawer(GravityCompat.START);
                        }
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // O código aqui será executado quando o DrawerLayout for fechado
                        if (drawerView.getId() == binding.navigationViewTv.getId()) {
                            // Se o drawer fechado for o NavigationView
                            // Coloque aqui o código que você deseja executar quando o
                            // NavigationView for fechado
                            binding.drawerBarLayout.openDrawer(GravityCompat.START);
                        }
                    }
                });
*/
        /*MyDbHelper database = new MyDbHelper(getApplicationContext());
        SQLiteDatabase db = database.getWritableDatabase();
        String tableName = "dados";
        hashMap = new SQLiteHashMap(db, tableName);
        retrievedHashMap = hashMap.get("historico");*/
        
     /*   binding.navigationBarTv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                    
                    
              //  int itemId = item.getItemId();

                // Exemplo: ação com base no item selecionado
                    
                    
              
                // Fechar o Drawer após o item ser selecionado
                binding.drawerBarLayout.closeDrawer(GravityCompat.START);
                binding.drawerLayout.openDrawer(GravityCompat.START);
                    
                return true;
            }
        });*/
		
		/*imbSearch = findViewById(R.id.activitynetflixImageViewSearch);
		imbRecomends = findViewById(R.id.activitynetflixImageViewMoviesAlt);
		imbHome = findViewById(R.id.activitynetflixImageViewHome);
		imbMyList = findViewById(R.id.activitynetflixImageViewMyList);
		imbAccount = findViewById(R.id.activitynetflixImageViewAccount);
        
        rltMenu = findViewById(R.id.activity_filmesRelativeLayoutMenu);
        
        lnrLoading = findViewById(R.id.activity_filmesLinearLayout);
        
        // Configurando os listeners dos botões
        imbSearch.setOnClickListener(v -> {
            if(fragmentSearch == null){
                fragmentSearch = FragmentSearch.newInstance();
            }
                
                replaceFragment(fragmentSearch);
        });
        
        imbRecomends.setOnClickListener(v -> {
            if(fragmentRecommended == null){
                fragmentRecommended = FragmentRecommended.newInstance();
            }
                replaceFragment(fragmentRecommended);
        });
        
        imbHome.setOnClickListener(v -> {
           if(fragmentHome == null){
               fragmentHome = FragmentHome.newInstance();
           }   
            replaceFragment(fragmentHome);
        });
        
        imbMyList.setOnClickListener(v -> {
            if(fragmentMyList == null){
               fragmentMyList = FragmentMyList.newInstance();
           }   
            replaceFragment(fragmentMyList);
        });
      
         imbAccount.setOnClickListener(v -> {
            if(fragmentAccount == null){
               fragmentAccount = FragmentAccount.newInstance();
           }   
            replaceFragment(fragmentAccount);
        });
        */
       // createCategoryList();
        
     //   loadMovies();
        
        if(fragmentHome == null){
               fragmentHome = FragmentHome.newInstance();
           }   
            View v = chipNavBarItens.get(2);
            v.setSelected(true);
            replaceFragment(fragmentHome);
        }
    
    
   OnFocusChangeListener itemChipNavigationBarFocus = new View.OnFocusChangeListener() {
		@Override
		public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                clearSelectorFromChipNavBar();
                
                if(view != ultimoItem && ultimoItem != null && keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
                    ultimoItem.requestFocus();
                }else{
				view.setSelected(true); // Altere a cor do componente dentro do item
                ultimoItem = view;
                    int position = getSelectedPositionFromChipNavBar();
                    
                    switch(position){
                        case 0:
                            if(fragmentSearch == null){
                fragmentSearch = FragmentSearch.newInstance();
            }
                
                replaceFragment(fragmentSearch);
                        break;
                        case 1: 
                                       if(fragmentRecommended == null){
                fragmentRecommended = FragmentRecommended.newInstance();
            }
                replaceFragment(fragmentRecommended);
                        break;
                        case 2: 
                            if(fragmentHome == null){
               fragmentHome = FragmentHome.newInstance();
           }   
            replaceFragment(fragmentHome);
                        break;
                        case 3:
                            if(fragmentMyList == null){
               fragmentMyList = FragmentMyList.newInstance();
           }   
            replaceFragment(fragmentMyList);
                        break;
                        case 4:
                            if(fragmentAccount == null){
               fragmentAccount = FragmentAccount.newInstance();
           }   
            replaceFragment(fragmentAccount);
                        break;
                    }
                }
                binding.bottomMenu.expand();
			} else {
				//view.setSelected(false); // Restaure a cor original do componente dentro do item
		        binding.bottomMenu.collapse();
                
        	}
            
           
            }
		

	};
    
   private int getSelectedPositionFromChipNavBar(){
        int position = 0;
       for(int i = 0; i < chipNavBarItens.size(); i++){
           View v = chipNavBarItens.get(i);
            if(ultimoItem == v){
                position = i;
                break;
            }
            
       }
        
        return position;
   } 
    
    private void clearSelectorFromChipNavBar(){
        for(View v : chipNavBarItens){
                    v.setSelected(false);
                }
    }
    
    private void replaceFragment(androidx.fragment.app.Fragment fragment) {
        // Substituir o conteúdo do FrameLayout pelo fragmento
       // if(= null){
            getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(binding.contentFrame.getId(), fragment)
            .commitAllowingStateLoss();
       // }
    }
    
    
    
/*	private void createCategoryList() {
		categoryList = new ArrayList<>();
		categoryList.add(new Category("", "", null)); // Adicionando item nulo por causa que o banner ocupa a primeira categoria
		
		final BDFilms myFilms = new BDFilms();
        bdFilms = new BDFilms();
		myFilms.getFilmsList(new BDFilms.getAllFilms() {
				@Override
				public void getFilms(BDFilms films) {
                    
                    for(int w =  0; w < films.getContext().size(); w++){
                        bdFilms.addName(films.getName().get(w));
                        bdFilms.addLogo(films.getLogo().get(w));
                        bdFilms.addType(films.getType().get(w));
                        bdFilms.addId(films.getId().get(w));
                        
                        String getCategory = films.getContext().get(w);
                        
                        if (getCategory != null) {
                            getCategory = getCategory.replaceAll("(?i)\\s*Filmes\\s*\\|\\s*", "");
                        }

                        
                        bdFilms.addContext(getCategory);
                    }
                    
                   //Toast.makeText(getApplicationContext(), "test "+ "Aqui tambem", Toast.LENGTH_SHORT).show();
					//bdFilms = films;

					List<String> categorias = new ArrayList<>();
					List<Movie> lancamentosMovies = new ArrayList<>();
					
					//Verificando se o historico esta diferente de nulo
					if(retrievedHashMap.get("list1") != null){
						List<String> recentsName = retrievedHashMap.get("list1");
						List<String> recentsLogo = retrievedHashMap.get("list3");
						String category = Category.recent;
						
                        //Adicionando historico a lista
						for (int p = recentsName.size() - 1; p >= 0; p--) {
							
                            int id = 1;//Integer.parseInt(recentsName.get(p));
							String name = recentsName.get(p);
							String logo = recentsLogo.get(p);
                            String type = "serie";//recentsLogo.get(p);
							
							lancamentosMovies.add(new Movie(id,name, category, logo, type));
						}
						categoryList.add(new Category(category, "category_recents", lancamentosMovies));
						
					}
                    
                    //Adicionando filmes
					for (int i = 0; i < bdFilms.getContext().size(); i++) {
                        
                        String getCategory = bdFilms.getContext().get(i);
                        
						categorias.add(getCategory);
					}

					categorias = GFG2.removeDuplicates(categorias);

					for (int j = 0; j < categorias.size();j++) {
						String categoriaTitulo = categorias.get(j);
						lancamentosMovies = new ArrayList<>();

						for (int x = 0; x < bdFilms.getContext().size(); x++) {
							String category = bdFilms.getContext().get(x);
                            
							if (category.equalsIgnoreCase(categoriaTitulo)) {
                                
                                int id = bdFilms.getId().get(x);
								String name = bdFilms.getName().get(x);
								String logo = bdFilms.getLogo().get(x);
								String type = bdFilms.getType().get(x);

								lancamentosMovies.add(new Movie(id, name, category, logo, type));
								
							}
						}

						categoryList.add(new Category(categoriaTitulo, "category_default", lancamentosMovies));
					}

					//progress.dismiss();
					
					setDestaque(categoryList);
					
					//Toast.makeText(NetflixActivity.this, "Sucesso " + categoryList.size() + "\n\n", Toast.LENGTH_SHORT).show();
					//Log.e("akajwjjajajfilmes", categoryList.toString());
				}
			}, getApplicationContext());

		
	}*/

	
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
	
    public void Toast(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
	

	
    /*private List<Category> createCategoryList2() {
		List<Category> categoryList = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			String categoryTitle = "Lançamentos " + i;

			// Adicione as categorias de exemplo com seus filmes
			List<Movie> lancamentosMovies = new ArrayList<>();
			//lancamentosMovies.add(new Movie("Filme 1", R.drawable.iconapp, categoryTitle));
			//lancamentosMovies.add(new Movie("Filme 2", R.drawable.iconapp, categoryTitle));
			//lancamentosMovies.add(new Movie("Filme 3", R.drawable.iconapp, categoryTitle));
			// ...

			categoryList.add(new Category(categoryTitle, "category_default", lancamentosMovies));
		}

		// Adicione mais categorias e filmes de exemplo aqui
		// ...

		return categoryList;
	}
    
    
    //screens
	private View screenSearch(){
		final ImageButton imgSpace, imgDelete;
		final RecyclerView recyclerViewKeyboard, recyclerViewResultMoviesSearch;
        final ProgressBar progressBarLoading;
		final TextView txtTitleMovie;
		final ListView listViewCategory;
		
		View v = getLayoutInflater().inflate(R.layout.activity_search, null);
		v.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
		
		imgSpace = v.findViewById(R.id.activity_searchImageButtonSpace);
		imgDelete = v.findViewById(R.id.activity_searchImageButtonDelete);
		recyclerViewKeyboard = v.findViewById(R.id.activity_searchRecyclerViewKeyboard);
		listViewCategory = v.findViewById(R.id.activity_searchListViewCategory);
		txtTitleMovie = v.findViewById(R.id.activity_searchTextViewMovie);
		recyclerViewResultMoviesSearch = v.findViewById(R.id.activity_searchRecyclerViewResultMovies);
        progressBarLoading = v.findViewById(R.id.activity_searchProgressBar);
        
		imgSpace.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					String space = txtTitleMovie.getText().toString();
					txtTitleMovie.setText(space + " ");
				}


			});


		imgDelete.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					String keyTitle = txtTitleMovie.getText().toString();
					if(!(keyTitle.isEmpty())){
						String newTitle = keyTitle.substring(0, keyTitle.length() - 1);
						txtTitleMovie.setText(newTitle);
					}

				}


			});

		int numberOfColumns = 6; // Define o número de colunas desejado

		GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), numberOfColumns, RecyclerView.VERTICAL, false);

		recyclerViewKeyboard.setLayoutManager(layoutManager);


		recyclerViewKeyboard.setAdapter(new KeyboardAdapter(HomeActivity.this, recyclerViewResultMoviesSearch, progressBarLoading, txtTitleMovie));
        
        Category category1 = categoryList.get(2);
        List<Movie> movieList0 = category1.getMovieList();
        
        MovieAdapter m = new MovieAdapter(HomeActivity.this, movieList0, "default");
        
        // Use um novo LayoutManager para recyclerViewResultMoviesSearch com um número diferente de colunas
        int numberOfColumnsMovies = 4; // Defina o número de colunas desejado para recyclerViewResultMoviesSearch
        GridLayoutManager layoutManagerMovies = new GridLayoutManager(getApplicationContext(), numberOfColumnsMovies, RecyclerView.VERTICAL, false);

        //recyclerViewResultMoviesSearch.setLayoutManager(layoutManagerMovies);

        
        recyclerViewResultMoviesSearch.setLayoutManager(layoutManagerMovies); 
		recyclerViewResultMoviesSearch.setAdapter(m);
		
		return v;
	}
    
    private View screenHome(){
        View v = getLayoutInflater().inflate(R.layout.activity_home, null);
        
        recyclerView = v.findViewById(R.id.activityhomeRecyclerView);
        
        createCategoryList();
        return v;
    }*/
    
    

	
	/*private void createCategoryList3() {
		//categoryList = new ArrayList<>();

		final BDSeries mySeries = new BDSeries();
		mySeries.getSeriesList(new BDSeries.getAllSeries() {
				@Override
				public void getSeries(BDSeries series) {
					bdSeries = series;

					List<String> categorias = new ArrayList<>();

					for (int i = 0; i < bdSeries.getContext().size(); i++) {
						categorias.add(bdFilms.getContext().get(i));
					}

					categorias = GFG2.removeDuplicates(categorias);

					for (int j = 0; j < categorias.size();j++) {
						String categoriaTitulo = categorias.get(j);
						List<Movie> lancamentosMovies = new ArrayList<>();

						for (int x = 0; x < bdSeries.getContext().size(); x++) {
							String category = bdFilms.getContext().get(x);

							if (category.equalsIgnoreCase(categoriaTitulo)) {
								String name = bdSeries.getName().get(x);
								String logo = bdSeries.getLogo().get(x);
								//String link = bdFilms.getLink().get(x);

								lancamentosMovies.add(new Movie(name, category, logo));

							}
						}

						categoryList.add(new Category(categoriaTitulo,"category_default", lancamentosMovies));
					}



					//progress.dismiss();

					LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
					recyclerView.setLayoutManager(layoutManager);
					categoryAdapter = new CategoryAdapter(HomeActivity.this, categoryList);
					recyclerView.setAdapter(categoryAdapter);

					//Toast.makeText(NetflixActivity.this, "Sucesso " + categoryList.size() + "\n\n", Toast.LENGTH_SHORT).show();
					//Log.e("akajwjjajajfilmes", categoryList.toString());
				}
			}, this);


	}*/

}
