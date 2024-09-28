package com.ultra.vision.account;
import java.util.HashMap;
import android.widget.*;
import android.util.*;

public class UserObject implements Usuarios {

	public static UserObject USER;
	
	@Override
	public String getName() {
		return get("user");
	}

	@Override
	public String getSeller() {
		return get("pass");
	}
	
    @Override
	public String getMethod() {
		return get("method");
	}
	
	@Override
	public String getPass() {
		return get("pass");
	}

	@Override
	public int getVersion()
	{
		// TODO: Implement this method
		try{
			int version = Integer.parseInt(get("version"));
			return version;
		}catch(Exception e){
			Log.e("uwue", e.toString());
		}
		return Integer.parseInt(get("1"));
	}
	
	public static put P;
	private HashMap<String, String> HASH_MAP;
	
    public UserObject(){
		//P.mensagem("oii ^-^");
	}
    
	public UserObject(String s){
		start();
	}
	
	public void setNewUser(put p){
		this.P = p;
	}
	
	public void start(){
		setNewUser(new put(){

				@Override
				public void mensagem(String s) {
					mensagem("Não tem nada para você olhar aqui ^-^");
				}

				@Override
				public void definir(HashMap<String, String> p1) {
					HASH_MAP = p1;
				}
				
			
		});
	}
	
	public abstract interface put{
		public void mensagem(String s);
		public void definir(HashMap<String,String> p1);
	}
	
	private String get(String k){
		
		return HASH_MAP.get(k);
	}
	
}
