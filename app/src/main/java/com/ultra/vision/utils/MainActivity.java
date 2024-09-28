package com.ultra.vision.utils;


import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.os.*;
import android.provider.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.RelativeLayout.*;
import com.ultra.vision.*;
import com.ultra.vision.account.*;
import com.ultra.vision.account.connection.*;
import com.ultra.vision.database.*;
import com.ultra.vision.debug.*;
import com.ultra.vision.launcher.*;
import com.ultra.vision.tv.*;
import com.ultra.vision.tv.connection.*;
import com.ultra.vision.utils.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import com.ultra.vision.R;

import android.view.View.OnFocusChangeListener;
import com.ultra.vision.launcher.LauncherActivity;

public class MainActivity extends Activity
{

    RelativeLayout linearLayout;
   // SharedPreferences preferences;
    //String op = Kingk.seila2();
    //String op2 = Kingk.seila10();
    //final String op3 = Kingk.seila7();
	//private boolean checked=false;
	//private boolean ativado;
	//private boolean PermissionAtived = false;
	//static Uri urii;
	//String link = King.seila();
	//public static String oi;
    //private boolean boFlutuante = true;
    //boolean boTesteVersion = true;
    ProgressDialog pd;
    //private boolean enviado;
	//MediaPlayer mediaPlayer;
	String getVersion = "";
	String toUtl; 
	private boolean show = true;
	//static List<String> ctryButtons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		//loadAccount();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN/*|WindowManager.LayoutParams.FLAG_SECURE*/,
                                  WindowManager.LayoutParams.FLAG_FULLSCREEN);

								  
		Typeface fontBunkenTechSans = Typeface.createFromAsset(getAssets(), "fonts/BunkenTechSans.ttf");
		/*preferences = getSharedPreferences(
            "db", MODE_PRIVATE);*/

		//ativado = preferences.getBoolean("ativado", false);

        //oi = uri(this);
		
		/*toUtl = uri(this, MyBase64.decodeString(FileHelper.s));
		
		
		oi = uri(this, MyBase64.decodeString(toUtl));*/	
		
		//oi = uri(this, "");
		

		//Toast.makeText(MainActivity.this,oi,Toast.LENGTH_LONG).show();
		
        linearLayout = new RelativeLayout(this);
        linearLayout.setLayoutParams(new LayoutParams(-1, -1));
       // linearLayout.setGravity(Gravity.CENTER);
        //linearLayout.setOrientation(linearLayout.VERTICAL);

        //urii = urii.parse("http://45.162.230.234:1935/agrobrasiltv/agrobrasiltv/playlist.m3u8");

		LayoutParams layoutParams = new LayoutParams(dp(240), -2);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		
        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setLayoutParams(layoutParams);
        linearLayout2.setOrientation(linearLayout2.VERTICAL);
		
        android.graphics.drawable.GradientDrawable CJCFBDB = new android.graphics.drawable.GradientDrawable();
        int CJCFBDBADD[] = new int[]{ Color.parseColor("#50000000"), Color.parseColor("#70000000") };
        CJCFBDB.setColors(CJCFBDBADD);
        CJCFBDB.setOrientation(android.graphics.drawable.GradientDrawable.Orientation.BOTTOM_TOP);
        CJCFBDB.setCornerRadii(new float[] { 5, 5, 5, 5, 5, 5, 5, 5 });
        CJCFBDB.setStroke(2, Color.parseColor("#FFFFFF"));
        linearLayout.setBackgroundResource(R.drawable.tela);
        linearLayout2.setBackground(CJCFBDB);
		linearLayout2.setId(R.id.linearLayout2);

        layoutParams = new LayoutParams(-1, -2);
        layoutParams.topMargin = dp(10);

        TextView textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setText("X7 Prime");
        textView.setTextSize(16);
		textView.setTypeface(fontBunkenTechSans);
        //textView.setTextColor(Color.BLACK);
		

        layoutParams = new LayoutParams(-1, -2);
        layoutParams.topMargin = dp(15);
        layoutParams.bottomMargin = dp(10);

        TextView textView2 = new TextView(this);
        textView2.setLayoutParams(layoutParams);
        textView2.setTextColor(Color.WHITE);
        textView2.setGravity(Gravity.CENTER);
        textView2.setText("Faça login para prosseguir");
        textView2.setTextSize(12);
        textView2.setTextColor(Color.WHITE);

        layoutParams = new LayoutParams(-1, -2);
        layoutParams.bottomMargin = dp(3);

        final EditText editText = new EditText(this);
        editText.setLayoutParams(layoutParams);
        editText.setHint("Usuario:");
        editText.setHintTextColor(Color.WHITE);
        editText.setTextColor(Color.WHITE);
        editText.setTextSize(14);
		editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        final EditText editText2 = new EditText(this);
        editText.setLayoutParams(layoutParams);
        editText2.setHint("Senha:");
        editText2.setHintTextColor(Color.WHITE);
        editText2.setTextColor(Color.WHITE);
        editText2.setTextSize(14);
		editText2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		
        final DatabaseMemory db2 = new DatabaseMemory(MainActivity.this);
        if(!db2.getString("user").isEmpty()){
            String user = db2.getString("user");
			String pass = db2.getString("pass");
        
            editText.setText(user);
            editText2.setText(pass);
        }
        
        RelativeLayout relativeLayout = new RelativeLayout(this);

        CheckBox checkBox = new CheckBox(this);
        checkBox.setLayoutParams(new LayoutParams(-2, -2));
        checkBox.setText("Save");
        checkBox.setTextSize(14);
        checkBox.setTextColor(Color.WHITE);
		checkBox.setButtonDrawable(R.drawable.ic_design_selector_checkbox);

		//  final String fixo = "com sucesso";

     /*   if (ativado == true)
		{

            final String usuario = preferences.getString(
                "user", "");

            final String senha = preferences.getString(
                "senha", "");

            editText.setText(usuario);

            editText2.setText(senha);

            checked = true;

            checkBox.setChecked(true);
			

		}
		*/
		//final StringBuffer sb = new StringBuffer();
		//StringBuffer sb3 = new StringBuffer();
		//  System.out.println("Carregando");
		// for(int i = 0;i<5;i++){


		//String file = MainActivity.oi;
		/*sb.append(file);
		ctryButtons = SeachConnection.getCategoryBtn(sb.toString(), ctryButtons);
		GFG2.removeDuplicates(ctryButtons);*/
		checkBox.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{

					//createAlertDialogSucess("", "");

					String usuario = editText.getText().toString().trim().toString();

					String senha = editText2.getText().toString().trim().toString();


					/*if (usuario.isEmpty() || senha.isEmpty())
					{

						i("Campos vazios...");
					}
					else
					{
						if (!(checked))
						{


							checked = true;
							SharedPreferences.
								Editor getEditor = 
								preferences.edit();





							getEditor.putString("user", usuario);
							getEditor.putString("senha", senha);

							getEditor.putBoolean("ativado", true);

							getEditor.apply();

							//i("Salvado "+fixo);


						}
						else
						{
							checked = false;

							SharedPreferences.
								Editor getEditor = 
								preferences.edit();
							getEditor.remove("user");
							getEditor.remove("senha");

							getEditor.remove("ativado");

							getEditor.apply();

							//i("Removido "+fixo);

						}
					}*/
				}
			});

        layoutParams = new LayoutParams(-2, -2);
        layoutParams.addRule(relativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.setMarginEnd(dp(10));
        //layoutParams.bottomMargin = dp(30);
        CheckBox checkBox2 = new CheckBox(this);
        checkBox2.setLayoutParams(layoutParams);
        checkBox2.setText("Show");
        checkBox2.setId(R.id.checkbox2);
        checkBox2.setTextSize(14);
        checkBox2.setTextColor(Color.WHITE);
		checkBox2.setButtonDrawable(R.drawable.ic_design_selector_checkbox);

		checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					if (show)
					{
						//createAlertDialogSucess("","");
						editText2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
						show = false;
					}
					else
					{
						editText2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
						show = true;
					}
				}

			
		});

        layoutParams = new LayoutParams(-1, -2);
        layoutParams.addRule(relativeLayout.BELOW, R.id.checkbox2);
        layoutParams.topMargin = dp(10);
        layoutParams.bottomMargin = dp(20);

		/*if(textView.getText().toString().equals(R.string.app_name)){
			
		}else{
			//finish();
		}*/
        TextView textView3 = new TextView(this);
        textView3.setLayoutParams(layoutParams);
        textView3.setGravity(Gravity.CENTER);
        textView3.setText(Html.fromHtml("Esqueci a senha, <b>Clique Aqui</b>", Html.FROM_HTML_MODE_LEGACY));
        textView3.setTextSize(13);
        textView3.setTextColor(Color.WHITE);
        textView3.setId(R.id.textview3);

		textView3.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1) {
					createAlertDialog("Recuperação de senha", "Se você esqueceu sua senha, você presisará conversar com o vendedor responsavel por sua conta, com isso, você pode recuperar sua senha.");
				}

			
		});
		
        layoutParams = new LayoutParams(-1, -2);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.textview3);

        RelativeLayout relativeLayout2 = new RelativeLayout(this);
        relativeLayout2.setLayoutParams(layoutParams);

        layoutParams = new LayoutParams(-2, dp(35));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.setMarginEnd(dp(7));
        layoutParams.bottomMargin = dp(5);

      /*  CJCFBDB = new GradientDrawable();
        //CJCFBDB.setColors(CJCFBDBADD);
        CJCFBDB.setOrientation(android.graphics.drawable.GradientDrawable.Orientation.BOTTOM_TOP);
        CJCFBDB.setCornerRadii(new float[] { 5, 5, 5, 5, 5, 5, 5, 5 });
        CJCFBDB.setStroke(2, Color.parseColor("#FFFFFF"));*/

        Button button = new Button(this);
        button.setLayoutParams(layoutParams);
        button.setText("Login");
        button.setTextSize(14);
        button.setTextColor(Color.WHITE);
        button.setBackgroundResource(R.drawable.ic_login_selector);
		button.setOnFocusChangeListener(onFocus);
		button.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					String usuario = editText.getText().toString();
					String senha = editText2.getText().toString();

					if (usuario.isEmpty() || senha.isEmpty())
					{
						Toast("Preencha os campos");
					}
					else
					{
						conectarUsuario(usuario, senha);
					}


				}


			});

		
		layoutParams = new LayoutParams(-2,-2);
		layoutParams.addRule(RelativeLayout.BELOW, R.id.linearLayout2);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		//layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		//layoutParams.setMarginEnd(dp(10));
		layoutParams.topMargin = dp(10);

		TextView textInfoPrivacidade = new TextView(this);
		textInfoPrivacidade.setLayoutParams(layoutParams);
		//textInfoPrivacidade.setTypeface(Typeface.MONOSPACE);
		textInfoPrivacidade.setTextColor(Color.WHITE);
		textInfoPrivacidade.setText(Html.fromHtml("ao logar, você concorda com nossa <FONT color='blue'>Política de Privacidade.<FONT/>"));
		textInfoPrivacidade.setTextSize(dp(7));
		textInfoPrivacidade.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					GetTallbackName pri = new GetTallbackName(MainActivity.this);
					//get.execute("https://bdservert.online/stream/config/canais/get_transmissoes.php");
					pri.getMessagePri();
				}

			
		});
		
		layoutParams = new LayoutParams(-2,-2);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layoutParams.setMarginEnd(dp(10));
		layoutParams.bottomMargin = dp(10);
		
		TextView textByCredits = new TextView(this);
		textByCredits.setLayoutParams(layoutParams);
		textByCredits.setTypeface(Typeface.MONOSPACE);
		textByCredits.setText("BY BDDEV");
		textByCredits.setTextColor(Color.WHITE);
		
        
		linearLayout2.addView(textView);
        linearLayout2.addView(textView2);
        linearLayout2.addView(editText);
        linearLayout2.addView(editText2);
        relativeLayout.addView(checkBox);
        relativeLayout.addView(checkBox2);
        relativeLayout.addView(textView3);
        relativeLayout2.addView(button);
        linearLayout2.addView(relativeLayout);
        linearLayout2.addView(relativeLayout2);
        linearLayout.addView(linearLayout2);
		linearLayout.addView(textInfoPrivacidade);
		linearLayout.addView(textByCredits);
		
		//setTheme(R.style.AppTheme);
        setContentView(linearLayout);
        
        
        final DatabaseMemory db = new DatabaseMemory(this);
		GetTallbackName dir = new GetTallbackName(MainActivity.this);
		//get.execute("https://bdservert.online/stream/config/canais/get_transmissoes.php");
		
					if(db.getString("dir_aut").equalsIgnoreCase("")){
						dir.getMessageDir();
						db.putString("dir_aut", "BD DEV");
					}

    }

	
	//Foco TV
	OnFocusChangeListener onFocus = new View.OnFocusChangeListener() {
	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		if (hasFocus) {
			view.setSelected(true); // Altere a cor do componente dentro do item
		} else {
			view.setSelected(false); // Restaure a cor original do componente dentro do item
		}
	}
	
	};
	
	private void loadAccount(){
		DatabaseMemory db = new DatabaseMemory(MainActivity.this);
		
		if(!(db.getString("user").isEmpty())){
			
			String user = db.getString("user");
			String pass = db.getString("pass");
			
			conectarUsuario(user,pass);
		}
	}
	
	private void conectarUsuario(String user, String pass){
		//CONEXAO
		Connection conn = new Connection(MainActivity.this);
		HashMap<String, String> hash = new HashMap<>();
		hash.put("user", user);
		hash.put("pass", pass);
		hash.put("response", String.valueOf(0));
		conn.SingInWithUserAndPass(hash, new Connection.Auth(){

				@Override
				public void onSucess(UserObject userObject) {
					//Toast(userObject.getMethod());
					UserObject.USER = userObject;
					
					DatabaseMemory db = new DatabaseMemory(MainActivity.this);
					
					//if(db.getString("user").isEmpty()){
						db.putString("user", userObject.getName());
						db.putString("pass", userObject.getPass());
					//}

					Applications.i = 41;
					start(userObject.getMethod());
					finish();

					//UserObject u = new UserObject(";-;");
					//Toast(u.getName() + "\n" + u.getPass());
				}


				@Override
				public void onFailed(String s) {
					createAlertDialog("Ops",s);
				}


			});
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

    public final int dp(int value)
	{
        return (int) TypedValue.applyDimension(1, (float) value, getResources().getDisplayMetrics());
    }

    private String getVersion5()
	{
        String name="";
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);

            name = info.versionName;


        }
        catch (PackageManager.NameNotFoundException e)
        {}
        name = name.replace(".", "_");

        return name;

    }
	
    private void Toast(String msg)
	{

        Toast.makeText(this,
                       msg, Toast.LENGTH_SHORT).show();

    }
    public void createAlertDialogSucess(String msg, String title)
	{
        Applications.i = 41;
        Intent intent = new Intent(MainActivity.this, StreamingActivity.class);
        startActivity(intent);
        //i(King.seilaissei(msg));

    }
    public void createAlertDialog(String title, String msg)
	{
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage(msg)

            .setTitle(title)
            .setPositiveButton("Ok", null)
            .setCancelable(false)
            .create().show();
    }
	
    public void createProgress(String title, String msg)
	{
        if (pd == null)
		{
            pd = new ProgressDialog(this);
            pd.setMessage(msg);
            pd.setTitle(title);
            pd.setCancelable(false);
            pd.setIndeterminate(true);
        }
        pd.create();
        pd.show();
    }
    public void closeProgress()
	{
        if (pd != null)
            pd.dismiss();
    }

    public String getUniqueId(Context ctx)
	{
        String key = (getDeviceName() + Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID) + Build.HARDWARE).replace(" ", "");
        UUID uniqueKey = UUID.nameUUIDFromBytes(key.getBytes());
        return uniqueKey.toString().replace("-", "");
    }


    private String getDeviceName()
	{
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer))
		{
            return model;
        }
		else
		{
            return manufacturer + " " + model;
        }
    }

	public String uri(final Context ctx, String url)
	{
        try
		{
            URL request = new URL(url);
            BufferedReader z = new BufferedReader(
                new InputStreamReader(request.openStream()));

            String version = z.readLine();
            z.close();
            return version;
        }
		catch (IOException s)
		{

        }
        return "k";
    }
	
	public void sParametreDefault(){
		startActivity(new Intent(MainActivity.this, LauncherActivity.class));
	}
	
	

}
