package com.ultra.vision.tv.database;

import android.content.*;
import com.ultra.vision.account.*;
import com.ultra.vision.connection.*;
import java.util.*;
import com.ultra.vision.account.connection.*;

public class BDChannels {

	private List<String> NAME, CONTEXT, LOGO, LINK;
	private static getAllChannels PUT;
    public static final String iptv = "CANAIS|IPTV";
    public static final String moviesAndSeries = "FILMES|SERIES";
    public static final String lives = "LIVES|24 HRS";
    public static final String settings = "SETTINGS|ON";
    public static final String credits = "CREDITS|APP";
    public static final String exit = "EXIT|APP";
	
	public BDChannels(){
		this.NAME = new ArrayList<>();
		this.CONTEXT = new ArrayList<>();
		this.LOGO = new ArrayList<>();
		this.LINK = new ArrayList<>();
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
	
	public void addLink(String link){
		this.LINK.add(link);
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

	public List<String> getLink() {
		return this.LINK;
	}
	
	public void getChannelsList(getAllChannels channels, Context context){
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
		hash.put("response", String.valueOf(1));
		conn.SingInWithUserAndPass(hash);
	}
	
	public static void putChannels(BDChannels bd){
		PUT.getStreaming(bd);
	}

    public interface getAllChannels {
		public void getStreaming(BDChannels channel);
	}
    
    
}
