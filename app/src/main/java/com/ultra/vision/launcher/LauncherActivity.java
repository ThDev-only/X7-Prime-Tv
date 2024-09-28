package com.ultra.vision.launcher;

import android.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import androidx.recyclerview.widget.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.RelativeLayout.*;
import com.ultra.vision.tv.VLCActivity2;
import com.ultra.vision.account.*;
import com.ultra.vision.debug.*;
import com.ultra.vision.implementations.*;
import com.ultra.vision.launcher.*;
import com.ultra.vision.lives.*;
import com.ultra.vision.R;
import com.ultra.vision.tv.database.BDChannels;
import java.util.*;

import com.ultra.vision.debug.Applications;
import com.ultra.vision.launcher.LauncherActivity;
import com.ultra.vision.tv.*;
import com.ultra.vision.movies.*;

public class LauncherActivity extends Activity
{

	List<String> list = new ArrayList<>();
	List<Integer> listIcon = new ArrayList<>();
	TextView textViewTM;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		/*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

		LayoutParams layoutParams = new LayoutParams(-1,-1);
	//	verifyTesting();
		//Typeface typerface = Typeface.createFromAsset(InicioActivity.this.getAssets(), "fonts/facon.ttf");
        //Toast.makeText(LauncherActivity.this, "teste", Toast.LENGTH_LONG).show();
		RelativeLayout relativeParent = new RelativeLayout(this);
		relativeParent.setLayoutParams(layoutParams);
		//relativeParent.setBackgroundColor(Color.BLACK);
		relativeParent.setBackgroundResource(R.drawable.tela);
		//relativeParent.addView(relativeParent);

		layoutParams = new LayoutParams(-2,-2);
		layoutParams.topMargin = dp(15);
		layoutParams.setMarginStart(dp(15));

		LinearLayout lnrAlignTitle = new LinearLayout(this);
		lnrAlignTitle.setLayoutParams(layoutParams);
		lnrAlignTitle.setGravity(Gravity.CENTER_VERTICAL);
		
		layoutParams = new LayoutParams(dp(80),dp(80));
		
		ImageView imageLogo = new ImageView(this);
		imageLogo.setLayoutParams(layoutParams);
		imageLogo.setImageResource(R.drawable.logo_app_blue);
		//imageLogo.setId(R.id.imageViewLogo1);

		layoutParams = new LayoutParams(-2,dp(100));
		layoutParams.setMarginStart(5);
		//layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.imageViewLogo1);

		TextView textViewTitle = new TextView(this);
		textViewTitle.setLayoutParams(layoutParams);
		textViewTitle.setGravity(Gravity.CENTER);
		textViewTitle.setTextColor(Color.WHITE);
		textViewTitle.setText(R.string.app_name);
		textViewTitle.setTypeface(Typeface.DEFAULT_BOLD);
		textViewTitle.setTextSize(dp(15));

		layoutParams = new LayoutParams(dp(200),dp(130));
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		//layoutParams.topMargin = dp(15);
		//layoutParams.setMarginStart(dp(15));

		textViewTM = new TextView(this);
		textViewTM.setLayoutParams(layoutParams);
		textViewTM.setGravity(Gravity.CENTER);
		textViewTM.setTextColor(Color.WHITE);
		textViewTM.setText("18:00 AM");
		textViewTM.setTextSize(dp(15));

		layoutParams = new LayoutParams(-1,-2);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		RecyclerView recyclerview1 = new RecyclerView(this);
		recyclerview1.setLayoutParams(layoutParams);

		//Metodo para adicionar opcoes na Launcher
		addMenu();
		
		recyclerview1.setBackgroundColor(Color.parseColor("#50000000"));
		recyclerview1.setAdapter(new Recyclerview1Adapter());
		recyclerview1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

		//recyclerview1.setOnClickListener(new MyOnClickListener());
		//.set
		/*

		 categoria: filmes (7)

		 int peguei_o_valor = position(filmes) [7]

		 int pegar_o_valor_da_lista = 0;

		 contar quantos tem:
		 for(int j = 0; j < peguei_o_valor j++){
		 pegar_o_valor_da_lista = j;
		 }

		 supondo que a lista tem 10 itens
		 entao: pegar_o_valor_da_lista = 10;






		 */
		ItemClickSupport.addTo(recyclerview1)
			.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
				@Override
				public void onItemClicked(RecyclerView recyclerView, int itemPosition, View v)
				{
					//LiveActivity.Toast(LauncherActivity.this, "" + itemPosition);
                    
                    switch(list.get(itemPosition)){
                        case BDChannels.iptv:
							startActivity(new Intent(LauncherActivity.this, StreamingActivity.class));
                           // classes homeactivit
                           // com.ultra.vision.movies.

                           /* Context context = this;
                            Class<?> destinationActivity = KotlinActivity.class;
                            Intent intent = new Intent(context, destinationActivity);*/

                            // Iniciar a nova Activity
                           // startActivity(intent);
							break;
						case BDChannels.moviesAndSeries:
						    startActivity(new Intent(LauncherActivity.this, HomeActivity.class));
							break;
						case BDChannels.lives:
							startActivity(new Intent(LauncherActivity.this, FilmsActivity.class));
							break;
						case BDChannels.settings:
							//startActivity(new Intent(InicioActivity.this, LiveActivity.class));
							break;
						case BDChannels.credits:
							//startActivity(new Intent(InicioActivity.this, LiveActivity.class));
							break;
						case BDChannels.exit:
							//startActivity(new Intent(InicioActivity.this, LiveActivity.class));
							finish();
							break;
                    }
					/*switch(itemPosition){
						case 0:
							startActivity(new Intent(LauncherActivity.this, VLCActivity2.class));
							break;
						case 1:
						    startActivity(new Intent(LauncherActivity.this, HomeActivity.class));
							break;
						case 2:
							startActivity(new Intent(LauncherActivity.this, FilmsActivity.class));
							break;
						case 3:
							//startActivity(new Intent(InicioActivity.this, LiveActivity.class));
							break;
						case 4:
							//startActivity(new Intent(InicioActivity.this, LiveActivity.class));
							break;
						case 5:
							//startActivity(new Intent(InicioActivity.this, LiveActivity.class));
							finish();
							break;
					}*/
				}


			});


		lnrAlignTitle.addView(imageLogo);
		lnrAlignTitle.addView(textViewTitle);
		relativeParent.addView(lnrAlignTitle);
		relativeParent.addView(textViewTM);
		relativeParent.addView(recyclerview1);

		setTheme(R.style.AppTheme);
		setContentView(relativeParent);

		Thread myThread = null;
		Runnable runnable = new CountDownRunner();
		myThread= new Thread(runnable);
		myThread.start();
	}

	private void verifyTesting()
	{
        if (Applications.i == 41)
		{

        }
		else
		{
            //finish();
        }
    }
	
	public void addMenu(){	
		if (UserObject.USER != null) {
			if(UserObject.USER.getVersion() == 1){
				list.add(BDChannels.iptv);
				listIcon.add(R.drawable.tv_40px);
			}else if(UserObject.USER.getVersion() == 2){
				list.add(BDChannels.moviesAndSeries);
				listIcon.add(R.drawable.movie_tv_40px);
			}else if(UserObject.USER.getVersion() == 3){
				list.add(BDChannels.iptv);
				listIcon.add(R.drawable.tv_40px);
				list.add(BDChannels.moviesAndSeries);
				listIcon.add(R.drawable.movie_tv_40px);
				
			}//else finish();
			// Do something with the version
		} else {
			Toast.makeText(LauncherActivity.this,"Errorrr",Toast.LENGTH_SHORT).show();
		//	finish();
			// Handle the case when userObject is null
		}
		
		list.add(BDChannels.lives);
		listIcon.add(R.drawable.live_tv_40px);
		
		list.add(BDChannels.settings);
		listIcon.add(R.drawable.settings_40px);
		
		list.add(BDChannels.credits);
		listIcon.add(R.drawable.info_40px);
		
		list.add(BDChannels.exit);
		listIcon.add(R.drawable.close_40px);
	}

	public void doWork() {
		runOnUiThread(new Runnable() {
				public void run() {
					try{
						Date dt = new Date();
						int hora = dt.getHours();
						int minutos = dt.getMinutes();
						int segundos = dt.getSeconds();
						String curTime = hora + ":" + minutos + ":" + segundos;
						textViewTM.setText(curTime);
					}catch (Exception e) {}
				}
			});
	}

	class CountDownRunner implements Runnable{
		public void run() {
			while(!Thread.currentThread().isInterrupted()){
				try {
					doWork();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}catch(Exception e){
				}
			}
		}
	}

	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		//ArrayList<HashMap<String, Object>> data;
		public Recyclerview1Adapter() {
			//data = arr;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflater.inflate(R.layout.custom_recyclerciew2, null);
			//v.setOnClickListener(new MyOnClickListener());


			RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(dp(150), ViewGroup.LayoutParams.WRAP_CONTENT);
			//lp.setMarginStart(dp(10));
			v.setLayoutParams(lp);
            
			return new ViewHolder(v);
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, final int position) {
			View view = holder.itemView;
			
			view.setBackgroundResource(R.drawable.ic_launcher_graphic_ctr);

			ImageView imageView = view.findViewById(R.id.customrecyclerview2ImageView1);
			TextView textView = view.findViewById(R.id.customRecyclerView2TxtInfo);

			textView.setText(list.get(position));
			imageView.setImageResource(listIcon.get(position));

			RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(dp(150), ViewGroup.LayoutParams.WRAP_CONTENT);
			lp.setMarginStart(dp(20));
			view.setLayoutParams(lp);
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            
            if(position == 0) view.requestFocus();
			
			view.setOnFocusChangeListener(new View.OnFocusChangeListener(){

					@Override
					public void onFocusChange(View p1, boolean p2)
					{
						if(p2){
							p1.setSelected(true);
						}else{
							p1.setSelected(false);
						}
					}

				
			});
		}

		@Override
		public int getItemCount() {
			return list.size();
		}

		public class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
                v.requestFocus();
			}
		}

	}

	public final int dp(int value)
	{
        return (int) TypedValue.applyDimension(1, (float) value, getResources().getDisplayMetrics());
    }
}
