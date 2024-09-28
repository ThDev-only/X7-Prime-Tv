package com.ultra.vision.tv.database;
import android.app.Activity;
import com.ultra.vision.account.UserObject;
import com.ultra.vision.tv.connection.TvCategoriesConnection;
import com.ultra.vision.tv.object.TvCategory;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

public class BDCategories {
    
    public static getAllCategories CATEGORY;
    WeakReference weak;

    public interface getAllCategories {
        public void onSucess(List<TvCategory> listCategory);
        public void onFailed(String error);
    }
    
    public BDCategories(Activity activity) {
        weak = new WeakReference<Activity>(activity);
    }
    
    public void getCategoryList(getAllCategories categories){
		CATEGORY = categories;
		//UserObject user = new UserObject();
		/*GetChannels get = new GetChannels();
		get.execute("https://bdservert.online/stream/config/canais/get_transmissoes.php");
		*/
		
		//CONEXAO
		
		HashMap<String, String> hash = new HashMap<>();
		hash.put("user", UserObject.USER.getName());
		hash.put("pass", UserObject.USER.getPass());
        TvCategoriesConnection conn = new TvCategoriesConnection(hash);
	//	hash.put("response", String.valueOf(1));
		conn.executeRequest();
	}
	
    public static void putCategories(List<TvCategory> listCategories){
        CATEGORY.onSucess(listCategories);
    }

    public static void putError(String error){
        CATEGORY.onFailed(error);
    }
    
    }

