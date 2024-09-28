package com.ultra.vision.account.connection;

import android.content.*;
import com.ultra.vision.account.*;
import com.ultra.vision.settings.*;
import java.util.*;
import com.ultra.vision.connection.*;

public class Connection extends ConnectionService {
	
	Context CONTEXT;
	Map<String, String> hash = new HashMap<>();
	static Auth AUTH;
	
	public Connection(Context context){
		this.CONTEXT = context;
	}
	
	public void SingInWithUserAndPass(HashMap<String, String> hash, Auth auth){
		
		//Device device = new Device();
		//hash.put("type", device.get(CONTEXT));
		this.AUTH = auth;
		onStart(CONTEXT, hash);
		//Toast.makeText(CONTEXT, "teste" + hash.toString(), Toast.LENGTH_SHORT).show();
	}
	
	
	public void SingInWithUserAndPass(HashMap<String, String> hash){

		//Device device = new Device();
		//hash.put("type", device.get(CONTEXT));
		onStart(CONTEXT, hash);
		//Toast.makeText(CONTEXT, "teste" + hash.toString(), Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onStart(Context p1, Map<String, String> p2) {
		super.onStart(p1, p2);
		
	}

	
	
	public static abstract interface Auth{
		public void onSucess(UserObject userObject);
		public void onFailed(String s);
	}
  
}
