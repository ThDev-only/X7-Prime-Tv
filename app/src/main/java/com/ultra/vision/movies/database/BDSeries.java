package com.ultra.vision.movies.database;

import android.content.*;
import com.ultra.vision.account.*;
import com.ultra.vision.connection.*;
import com.ultra.vision.movies.object.Movie;
import com.ultra.vision.player.*;
import java.util.*;
import com.ultra.vision.movies.*;
import com.ultra.vision.account.connection.*;

public class BDSeries {

	private List<String> NAME, CONTEXT, LOGO; //LINK;
	private static getAllSeries PUT;
	
	public BDSeries(){
		this.NAME = new ArrayList<>();
		this.CONTEXT = new ArrayList<>();
		this.LOGO = new ArrayList<>();
		//this.LINK = new ArrayList<>();
	}
	
	public void addName(String name){
		this.NAME.add(name);
	}
	
	public void addContext(String context){
		this.CONTEXT.add(context);
	}
	
	public void addLogo(String logo){
		this.LOGO.add(logo);
	}
	
	/*public void addLink(String link){
		this.LINK.add(link);
	}*/
	
	public List<String> getName() {
		return this.NAME;
	}

	public List<String> getContext() {
		return this.CONTEXT;
	}

	public List<String> getLogo() {
		return this.LOGO;
	}

	/*public List<String> getLink() {
		return this.LINK;
	}*/
	
	public void getSeriesList(getAllSeries series, Context context){
		this.PUT = series;
		//UserObject user = new UserObject();
		/*GetChannels get = new GetChannels();
		get.execute("https://bdservert.online/stream/config/canais/get_transmissoes.php");
		*/
		
		//CONEXAO
		Connection conn = new Connection(context);
		HashMap<String, String> hash = new HashMap<>();
		hash.put("user", UserObject.USER.getName());
		hash.put("pass", UserObject.USER.getPass());
		hash.put("response", String.valueOf(3));
		//hash.put("version", String.valueOf(UserObject.USER.getVersion()));
		conn.SingInWithUserAndPass(hash);
	}
	
	public static void putSeries(BDSeries bd){
		PUT.getSeries(bd);
	}

    public interface getAllSeries {
		public void getSeries(BDSeries serie);
	}
    
    static class Serie{
        public static Movie SERIE;
    }
	
}
