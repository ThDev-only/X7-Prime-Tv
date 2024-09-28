package com.ultra.vision.adapter;

import android.content.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import com.ultra.vision.*;
import java.io.*;
import java.net.*;
import java.util.List;
import javax.net.ssl.*;
import androidx.recyclerview.widget.*;


public class MovieOptionsAdapter extends BaseAdapter
 {
	private Context context;
	ImageView icon;
	TextView text;
	
	List<String> movieOptionsName;
	
	List<Integer> movieOptionsIcon;
	
	public MovieOptionsAdapter(Context context, List<String> listName, List<Integer> listIcon) {
		this.context = context;
		this.movieOptionsName = listName;
		this.movieOptionsIcon = listIcon;
		
	}

	@Override
	public int getCount() {
		return movieOptionsName.size();
	}

	@Override
	public Object getItem(int position) {
		return movieOptionsName.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.custom_listview_movie_options, parent, false);
		
		rowView.setBackgroundResource(R.drawable.ic_design_details_movie_listview_options_selector);
		
		icon = rowView.findViewById(R.id.customListViewMovieOptionsImageViewIcon);
		text = rowView.findViewById(R.id.customListViewMovieOptionsTextViewText);
		
		if(movieOptionsIcon != null) icon.setImageResource(movieOptionsIcon.get(position));
		text.setText(movieOptionsName.get(position));
		
		/*rowView.setOnTouchListener(new View.OnTouchListener(){

				@Override
				public boolean onTouch(View p1, MotionEvent p2)
				{
					if(p2.getAction() == MotionEvent.ACTION_DOWN){
						text.setShadowLayer(2,2,2,Color.WHITE);
						//clearIcons();
						icon.setImageResource(movieOptionsIconSelected[position]);
						//quero exibir um toast aqui com a posicao que fou presionada da lista
					}else{
						text.setShadowLayer(0,0,0,Color.TRANSPARENT);
						icon.setImageResource(movieOptionsIcon[position]);
					}
					return true;
				}

			
		});*/
		
		rowView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean hasFocus) {
					if (hasFocus) {
						rowView.setSelected(true); // Altere a cor do componente dentro do item
						//clearIcons();
						//icon.setImageResource(movieOptionsIconSelected[position]);
						TextView t = view.findViewById(R.id.customListViewMovieOptionsTextViewText);
						t.setShadowLayer(5,5,5,Color.WHITE);
					} else {
						//icon.setImageResource(movieOptionsIcon[position]);
						TextView t = view.findViewById(R.id.customListViewMovieOptionsTextViewText);
						t.setShadowLayer(0,0,0,Color.WHITE);
						rowView.setSelected(false); // Restaure a cor original do componente dentro do item
					}
				}
			});

		return rowView;
	}

}
