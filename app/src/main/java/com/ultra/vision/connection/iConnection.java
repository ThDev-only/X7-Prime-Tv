package com.ultra.vision.connection;

import android.content.Context;
import java.util.Map;

public interface iConnection {
    
	public void onStart(Context p1, Map<String, String> p2);
	public boolean onResult(boolean b);
	
}
