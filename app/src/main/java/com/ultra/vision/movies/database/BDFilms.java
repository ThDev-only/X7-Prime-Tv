package com.ultra.vision.movies.database;

import android.content.*;
import com.ultra.vision.account.*;
import com.ultra.vision.connection.*;
import com.ultra.vision.movies.connection.MoviesSearchConnection;
import com.ultra.vision.movies.object.Category;
import com.ultra.vision.player.*;
import java.util.*;
import com.ultra.vision.movies.*;
import com.ultra.vision.account.connection.*;

public class BDFilms {

	private List<String> NAME, CONTEXT, LOGO, TYPE;
    private List<Integer> ID;
	private static getAllFilms PUT;
    private static getAllFilms SEARCH;
    public static List<Category> categoryList;
	
	public BDFilms(){
		this.NAME = new ArrayList<>();
		this.CONTEXT = new ArrayList<>();
		this.LOGO = new ArrayList<>();
		this.TYPE = new ArrayList<>();
        this.ID = new ArrayList<>();
	}
	
	public void addName(String name){
		this.NAME.add(name);
	}
    
    public void addId(int id){
        this.ID.add(id);
    }
	
	public void addContext(String context){
		this.CONTEXT.add(context);
	}
	
	public void addLogo(String logo){
		this.LOGO.add(logo);
	}
	
	public void addType(String type){
		this.TYPE.add(type);
	}
	
	public List<String> getName() {
		return this.NAME;
	}

	public List<String> getContext() {
		return this.CONTEXT;
	}

	public List<String> getLogo() {
		return this.LOGO;
	}
    
    public List<Integer> getId(){
        return this.ID;
    }
    
    //CategoryList
    public static void setCategoryList(List<Category> list){
        BDFilms.categoryList = list;
    }
    
    public static List<Category> getCategoryList(){
        return BDFilms.categoryList;
    }

	public List<String> getType() {
		return this.TYPE;
	}
    
    public void searchFilmsList(String nome, getAllFilms movies){
        this.SEARCH = movies;
        MoviesSearchConnection moviesSearch = new MoviesSearchConnection();
        moviesSearch.searchMovie(UserObject.USER.getName(), UserObject.USER.getPass(), nome);
    }
    
    public static void putSearch(BDFilms BDFilmsss){
        SEARCH.getFilms(BDFilmsss);
    }
	
	public void getFilmsList(getAllFilms channels, Context context){
		this.PUT = channels;
		//UserObject user = new UserObject();
		/*GetChannels get = new GetChannels();
		get.execute("https://bdservert.online/stream/config/canais/get_transmissoes.php");
		*/
		
		//CONEXAO
		Connection conn = new Connection(context);
		HashMap<String, String> hash = new HashMap<>();
		hash.put("user", UserObject.USER.getName());
		hash.put("pass", UserObject.USER.getPass());
		hash.put("response", String.valueOf(2));
		//hash.put("version", String.valueOf(UserObject.USER.getVersion()));
		conn.SingInWithUserAndPass(hash);
	}
	
	public static void putFilms(BDFilms bd){
		PUT.getFilms(bd);
	}

    public interface getAllFilms {
		public void getFilms(BDFilms film);
	}
	
/*	public static class Film {
		private String nome;
		private String logo;
		private String categoria;
		private String link;
		public static BDFilmsss.Film FILM;
		
		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getLogo() {
			return logo;
		}

		public void setLogo(String logo) {
			this.logo = logo;
		}

		public String getCategoria() {
			return categoria;
		}

		public void setCategoria(String categoria) {
			this.categoria = categoria;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}
		
		public BDFilmsss.Film getFilm(){
			return this.FILM;
		}
		
		public static void startInformationMovie(BDFilmsss.Film filme, Context context){
			BDFilmsss.Film.FILM = filme;
			context.startActivity(new Intent(context, MovieActivity.class));
		}
		
		public static void startMovie(BDFilmsss.Film filme, Context context){
			BDFilmsss.Film.FILM = filme;
			context.startActivity(new Intent(context, BDMediaPlayer.class));
		}
	}
	*/
}
