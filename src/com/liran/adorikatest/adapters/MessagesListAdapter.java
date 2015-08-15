package com.liran.adorikatest.adapters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adorikatest.R;
import com.liran.adorikatest.others.Constants;
import com.liran.adorikatest.others.User;

@SuppressLint("SimpleDateFormat")
public class MessagesListAdapter extends BaseAdapter  {
	
	private LayoutInflater mInflater;
	private String[][] mMessagesMap;
	private LinkedHashMap<String, User> mUsersMap;
	private User mUser;
 
	private class ViewHolder {
		public ImageView avatar;
		public TextView name, time, message;
	}
	
	public MessagesListAdapter(Context context, 
			    String[][] messagesMap,
			  	LinkedHashMap<String, User> users){
		
		mInflater 	  = LayoutInflater.from(context);
		mMessagesMap  = messagesMap;
		mUsersMap     = users;
 
	}
	
	@Override
	public int getCount() {
		 return mMessagesMap[0].length; 
	}

	@Override
	public Object getItem(int position) { 
		  
		return mMessagesMap[position]; 

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
		
			view = mInflater.inflate(R.layout.row_msg_layout, parent, false);
			
			holder = new ViewHolder();
			
			holder.avatar  = (ImageView)view.findViewById(R.id.contact_image);
			holder.name    = (TextView)view.findViewById(R.id.name);
			holder.time    = (TextView)view.findViewById(R.id.time);
			holder.message = (TextView)view.findViewById(R.id.message);
		
			view.setTag(holder);
	
		} else {
			
			view = convertView;
			holder = (ViewHolder)view.getTag();
		}    
		
		// Get user from json messages
		mUser = mUsersMap.get(mMessagesMap[0][position]);
			   
		// If we got user then, set its data on UI elements
		if (mUser != null ) {  
			
			holder.avatar.setImageBitmap(mUser.getAvatar());
			holder.name.setText(mUser.getName());
	     
			//Get message
			holder.message.setText(mMessagesMap[1][position]);
	
			// Set current time
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		    String formattedDate = df.format(c.getTime());
		        
			holder.time.setText(formattedDate);
		}
	 
	   return view;
	}
 
}
