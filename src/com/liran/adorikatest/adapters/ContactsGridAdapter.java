package com.liran.adorikatest.adapters;

import java.util.LinkedHashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adorikatest.R;
import com.liran.adorikatest.others.User;

public class ContactsGridAdapter extends BaseAdapter{

	private LayoutInflater mInflater; 
	private User[] mUsersArray;  
	
	private class ViewHolder {
		public ImageView avatar;
		public TextView name;
	}
	 
	public ContactsGridAdapter(Context context, LinkedHashMap<String, User> usersMap){
  		mInflater 	   = LayoutInflater.from(context);
  		
  		// Create array from LinkedHashMap
  		mUsersArray = new User[usersMap.size()];
  		usersMap.values().toArray(mUsersArray); 
  		
  	}
	
	@Override
	public int getCount() { 
		return mUsersArray.length;
	}

	@Override
	public Object getItem(int position) { 
		return mUsersArray[position];
	}

	@Override
	public long getItemId(int position) {	
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
 
		View view;
		ViewHolder holder;
		 
		if(convertView == null) {
		
			view   = mInflater.inflate(R.layout.cell_grid_contact, parent, false);
			holder = new ViewHolder();
			
			holder.avatar = (ImageView)view.findViewById(R.id.contact_image);
	     	holder.name = (TextView)view.findViewById(R.id.name); 
			view.setTag(holder);
		
		} else {
		
			view   = convertView;
			holder = (ViewHolder)view.getTag();
		
		}     
		
		// Check if we got user and set its data on UI elements
		if (mUsersArray[position] != null ) {

		   holder.avatar.setImageBitmap(mUsersArray[position].getAvatar());
	       holder.name.setText(mUsersArray[position].getName());
		
		}
		 return view;
	}
	
	/*
	 * Reefresh data
	 * Calling notifyDataSetChanged itself didn't work
	 * so I added this code additionally
	 */
	public void refresh(LinkedHashMap<String, User> users) {
		
		//Update array
		this.mUsersArray = new User[users.size()];
		users.values().toArray(mUsersArray);
        
		notifyDataSetChanged();

	}

}
