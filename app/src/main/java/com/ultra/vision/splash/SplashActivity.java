package com.ultra.vision.splash;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.Toast;
import com.ultra.vision.*;
import com.ultra.vision.account.*;
import com.ultra.vision.account.connection.*;
import com.ultra.vision.database.*;
import com.ultra.vision.debug.*;
import com.ultra.vision.utils.*;
import java.lang.reflect.*;
import java.util.*;
import com.ultra.vision.R;
import com.ultra.vision.utils.*;

public class SplashActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		new Handler().postDelayed(new Runnable(){

				@Override
				public void run()
				{
                    setTheme(R.style.AppTheme);
	            	setContentView(R.layout.activity_splash);
					loadAccount();
				}

			
		},2000);
	}
	
	private void loadAccount(){
		DatabaseMemory db = new DatabaseMemory(SplashActivity.this);

		if(!(db.getString("user").isEmpty())){

			String user = db.getString("user");
			String pass = db.getString("pass");

			conectarUsuario(user,pass);
		}else startActivity(new Intent(getApplicationContext(), com.ultra.vision.utils.MainActivity.class));
	}

	private void conectarUsuario(String user, String pass){
		//CONEXAO
		Connection conn = new Connection(getApplicationContext());
		HashMap<String, String> hash = new HashMap<>();
		hash.put("user", user);
		hash.put("pass", pass);
		hash.put("response", String.valueOf(0));
		conn.SingInWithUserAndPass(hash, new Connection.Auth(){

				@Override
				public void onSucess(UserObject userObject) {
					//Toast(userObject.getMethod());
					UserObject.USER = userObject;

					DatabaseMemory db = new DatabaseMemory(getApplicationContext());

				//	if(db.getString("user").isEmpty()){
						db.putString("user", userObject.getName());
						db.putString("pass", userObject.getPass());
				//	}

					Applications.i = 41;
					start(userObject.getMethod());
                  //  Toast(userObject.getMethod());
                    
				//	finish();

					//UserObject u = new UserObject(";-;");
					//Toast(u.getName() + "\n" + u.getPass());
				}


				@Override
				public void onFailed(String s) {
					createAlertDialog("Ops",s);
				}


			});
	}
    
    private void Toast(String msg)
	{

        Toast.makeText(getApplicationContext(),
                       msg, Toast.LENGTH_SHORT).show();

    }
	
	public void createAlertDialog(String title, String msg)
	{
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage(msg)

            .setTitle(title)
            .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO: Implement this method
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                
            })
            .setCancelable(false)
            .create().show();
    }
	
	private void start(String s){
		try {
            Class<?> clazz = getClass();
            Method method = clazz.getDeclaredMethod(s);
            method.invoke(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
    
    private void sParametreDefault(){
        //Toast("Caiu aquii");
	//	startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
        Intent intent = new Intent(getApplicationContext(), com.ultra.vision.launcher.LauncherActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
        
	}
	
}
