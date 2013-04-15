package com.example.jukeboxghadapters;

import com.example.jukeboxgh.R;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jukeboxghTask.Artists_Items;

public class SubscribeArtistAdapter extends ArrayAdapter<Artists_Items>{

	private final ArrayList<Artists_Items> artistsList;
	private final Activity context;
	
	public SubscribeArtistAdapter(Activity context,	ArrayList<Artists_Items> artistsList) {
		super(context, R.layout.subartist_list_item, artistsList);
		
		this.context = context;
		this.artistsList = new ArrayList<Artists_Items>();
		this.artistsList.addAll(artistsList);
	}
	
	static class ViewHolder {
		TextView artistNameTextView;
		TextView genreTextView;
		CheckBox checkBox;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if (convertView == null ) {
			LayoutInflater inflator = context.getLayoutInflater();			
			view = inflator.inflate(R.layout.subartist_list_item, null);
			
			final ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.artistNameTextView = (TextView) view.findViewById(R.id.artistNameTextView);
			viewHolder.checkBox = (CheckBox) view.findViewById(R.id.checkBox1);
			viewHolder.genreTextView = (TextView) view.findViewById(R.id.genreTextView);
			
			viewHolder.checkBox.setOnClickListener( new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					CheckBox checkBox = (CheckBox) view;
					Artists_Items artistItems = (Artists_Items) checkBox.getTag();
					artistItems.setChecked(checkBox.isChecked());
					
				}
			});
			
			view.setTag(viewHolder);
			viewHolder.checkBox.setTag(artistsList.get(position));
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).checkBox.setTag(artistsList.get(position));
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();

		Artists_Items artistsItems = artistsList.get(position);
		
		holder.artistNameTextView.setText(artistsItems.getName());
		holder.genreTextView.setText(artistsItems.getGenre());
		holder.checkBox.setChecked(artistsItems.isChecked());
		
		return view;
	}

}
