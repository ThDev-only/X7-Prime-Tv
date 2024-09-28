package com.ultra.vision.connection;

import java.util.*;
import com.ultra.vision.account.*;
import com.ultra.vision.connection.URLConnection.*;
import android.content.*;

public class GetLinkFromName extends URLConnection
{
	
	Context CONTEXT;
	
	public GetLinkFromName(Context context){
		this.CONTEXT = context;
	}
	
	public void getLinkFromName(String name, URLConnection.Auth auth, String dir){
		HashMap<String, String> hash = new HashMap<String, String>();
		hash.put("user", UserObject.USER.getName());
		hash.put("pass", UserObject.USER.getPass());
		hash.put("search", name);
		
		URLConnection(CONTEXT, hash, auth, dir);
	}

    public void getLinkFromId(int id, URLConnection.Auth auth, String dir){
		HashMap<String, String> hash = new HashMap<String, String>();
		hash.put("user", UserObject.USER.getName());
		hash.put("pass", UserObject.USER.getPass());
		hash.put("id", String.valueOf(id));
		
		URLConnection(CONTEXT, hash, auth, dir);
	}
    
	@Override
	public void URLConnection(Context context, Map<String, String> hash_map, URLConnection.Auth auth, String dir)
	{
		super.URLConnection(context, hash_map, auth, dir);
	}
	
	
}
