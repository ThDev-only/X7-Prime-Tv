package com.ultra.vision.tv.database;
import android.app.Activity;
import com.ultra.vision.account.UserObject;
import com.ultra.vision.tv.connection.TvChannelsConnection;
import com.ultra.vision.tv.object.TvChannel;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

public class BDChannels2 {
    
    public static getAllChannels CHANNEL;
    WeakReference weak;

    public interface getAllChannels {
        public void onSucess(List<TvChannel> listChannel);
        public void onFailed(String error);
    }
    
    public BDChannels2(Activity activity) {
        weak = new WeakReference<Activity>(activity);
    }
    
    public void getChannelList(getAllChannels channels){
		CHANNEL = channels;
		//UserObject user = new UserObject();
		/*GetChannels get = new GetChannels();
		get.execute("https://bdservert.online/stream/config/canais/get_transmissoes.php");
		*/
		
		//CONEXAO
		
		HashMap<String, String> hash = new HashMap<>();
		hash.put("user", UserObject.USER.getName());
		hash.put("pass", UserObject.USER.getPass());
        TvChannelsConnection conn = new TvChannelsConnection(hash);
	//	hash.put("response", String.valueOf(1));
		conn.executeRequest();
	}
	
    public static void putChannels(List<TvChannel> listCategories){
        CHANNEL.onSucess(listCategories);
    }

    public static void putError(String error){
        CHANNEL.onFailed(error);
    }
    
    }

