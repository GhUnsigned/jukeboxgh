package com.example.jukeboxghadapters;

import java.util.ArrayList;

import javax.security.auth.PrivateCredentialPermission;

import com.example.jukeboxghTask.Artists_Items;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SubscribedArtistAdapter extends ArrayAdapter<Artists_Items>{

	private final ArrayList<Artists_Items> artistsList;
	private final Activity context;
	private ListView listV;
	
	public SubscribedArtistAdapter(Activity context, ArrayList<Artists_Items> artistList) {
		super(context, R.layout.activity_list_item, artistList);
		
		this.context = context;
		this.artistsList = artistList;
			
	}
		
	static class ViewHolder {
		TextView artistNameTextView;
		TextView genreTextView;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			
			view = inflator.inflate(R.layout.activity_list_item, null);
			final ViewHolder viewHolder = new ViewHolder();
			//viewHolder.artistNameTextView = (TextView)view.findViewById(R.id.);
		}
		return super.getView(position, convertView, parent);
	}	
}
