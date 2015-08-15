package com.liran.adorikatest.fragments;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adorikatest.R;
import com.liran.adorikatest.adapters.ContactsGridAdapter;
import com.liran.adorikatest.adapters.MessagesListAdapter;
import com.liran.adorikatest.data.MyJson;
import com.liran.adorikatest.others.Constants;
import com.liran.adorikatest.others.MyGridView;
import com.liran.adorikatest.others.User;
import com.liran.adorikatest.sync.ImageLoader;
import com.liran.adorikatest.sync.ImageLoader.AsyncResponse;





//import com.example.adorikatest.MainActivity.PlaceholderFragment;
import org.json.JSONException;

 
public class MessagesFragment extends android.support.v4.app.Fragment
							  implements AsyncResponse{
	
  
	MyGridView gridView;
	ListView listView;
	String mMessagesArray [][];
	ContactsGridAdapter contactsGridAdapter;
	MessagesListAdapter messagesListAdapter;
	LinkedHashMap<String, User> mUsersMap = new LinkedHashMap<String, User>();
	
	View footerView;
	JSONArray jsonArrayUsers;
	JSONArray jsonArrayMsgs;
	
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static android.support.v4.app.Fragment newInstance(int sectionNumber, Boolean isBlank) {
		
		android.support.v4.app.Fragment fragment = new MessagesFragment();
		Bundle args = new Bundle();
		
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		args.putBoolean(Constants.SHOW_CHAT, isBlank);
		
		fragment.setArguments(args);
		return fragment;
	}

	public MessagesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		  
		View rootView;
		
		Bundle args = getArguments();
		
		Boolean isBlank = args.getBoolean(Constants.SHOW_CHAT);
		
		//Check which fragment should show the chat
		if(isBlank != true) {
			
			rootView = inflater.inflate(R.layout.fragment_messages, container,
					false);
			
			// initialize data
			initialize();
			
			// Setting up all UI elements
			arrangeUIElements(rootView);
			  
		} else {
			
			// Set up blank page
			rootView = inflater.inflate(R.layout.fragment_blank, container,
					false);
			
		}
		   
		return rootView;
	}
	
	/*
	 * Alert dialog with title and message
	 */
	public void alertDialog( View v ) {

		String title, message;
		message = null;
		title = null;
		 
	    TextView txt = (TextView) v.findViewById(R.id.name);
	    title = txt.getText().toString();
	    
	    if(v.getId() == R.id.message_row) {
	       txt = (TextView) v.findViewById(R.id.message);
	       message = txt.getText().toString();
	    }

	    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());
 
			// set title
			alertDialogBuilder.setTitle(title);
			alertDialogBuilder.setMessage(message);
			alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                //do things
		           }
		       });
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
 
	}

	/*
	 * Get the user object, in moment it had created in asynctask
	 * The asynctask get bitmap and download it, then it creates an user object
	 * @see com.example.adorikatest.ImageLoader.AsyncResponse#processFinish(com.example.adorikatest.User)
	 */
	@Override
	public void processFinish(User user) {
		 
		//Check if its not null for any case...
		if( user != null) {

		 mUsersMap.put(user.getId(), user);
			
		 // Take care for refreshing adapters of grid and list
		 // and refresh the UI elements - Grid and List themselves
		 listView.invalidateViews(); 
		 gridView.invalidateViews(); 
		 
		 messagesListAdapter.notifyDataSetChanged();
 
	     contactsGridAdapter.notifyDataSetChanged();  
		 contactsGridAdapter.refresh(mUsersMap);
		
		 //Check whether Max users is lower than 4,
		 // if it is, wait until we got all users objects
		 // and then set the footer view - > grid + button visible
		 if (jsonArrayUsers.length() < 4) {
	 
			 if (mUsersMap.size() == jsonArrayUsers.length()) {
				 footerView.setVisibility(View.VISIBLE);
			 }
			 
		  // If there are more, than check current num of users
		  // if more than 4, then set the footer view - > grid + button visible
		  } else {
			 if (mUsersMap.size() > 4 ) {
			  footerView.setVisibility(View.VISIBLE);
			 }
		  }
	  }  
		
  }
	
	/*
	 * Initialize data for screen
	 */
	private void initialize() {
		int numMessages = 0;
		String[][] tmpMessagesMap = null;
		JSONObject jsonAllData = new JSONObject();
		
		// Fill messages and users
		jsonAllData = MyJson.fillAllJSONData(); 
		 		
		// I know I could get users' array directly from the global json array,
		// but I didn't know, if I should show you that I know how to get array
		// from Json object
		try {
			
			jsonArrayUsers = jsonAllData.getJSONArray(Constants.USERS);
			jsonArrayMsgs  = jsonAllData.getJSONArray(Constants.MESSAGES);
			tmpMessagesMap = new String[2][jsonArrayMsgs.length()];
			//mMessagesArray = new String[2][jsonArrayMsgs.length()];
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		  	 	 
		for(int i = 0; i < jsonArrayUsers.length(); i ++) {
			
			String id = null, name = null, img = null;
						
			try { 
				id   = jsonArrayUsers.getJSONObject(i).getString(Constants.ID);								 
			} catch (JSONException e) {
				e.printStackTrace();
			} 
			
			try { 
				name = jsonArrayUsers.getJSONObject(i).getString(Constants.NAME);
			} catch (JSONException e) {
				e.printStackTrace();
			} 
			
			try { 
				img  = jsonArrayUsers.getJSONObject(i).getString(Constants.IMG);
			} catch (JSONException e) {
				e.printStackTrace();
			} 
			 
			//Download image and create user in case
			//we got user is
			if(id != null && !id.isEmpty()) {
			
				ImageLoader imageLoader = new ImageLoader(id, name, getActivity());
				
				imageLoader.delegate = this;
				imageLoader.execute(img);
			
			}
			
			//Iterate throuth Messages JSON and create array
			// If there is a message that its user doesn't match
			// one of our users, then dont show it
			for(int k = 0; k < jsonArrayMsgs.length(); k ++) {
				 
				   String message = null, msgUserId = null;
				    
				   try {
					   msgUserId = jsonArrayMsgs.getJSONObject(k).getString(Constants.FROM);
				   } catch (JSONException e1) {
					   e1.printStackTrace();
					}
				 
					try {
						message   = jsonArrayMsgs.getJSONObject(k).getString(Constants.MESSAGE);
					} catch (JSONException e) {
						e.printStackTrace();
					}
		

					if(id == msgUserId) {

						tmpMessagesMap[0][numMessages] = msgUserId;
						tmpMessagesMap[1][numMessages] = message;
						numMessages ++;
				 	}
	
			    }   
			
	     } 
			
			//Create a new array for all messages
			// that 
		   	mMessagesArray = new String[2][numMessages];
			  
			if (jsonArrayMsgs.length() != numMessages) {
				 
				if(numMessages > 0) {
				
					for (int i = 0; i < tmpMessagesMap.length; i++) {
	
						System.arraycopy(tmpMessagesMap[i], 0, mMessagesArray[i],
				         		0, numMessages);
				    }
						
				}
				
			} else {
			
				mMessagesArray = tmpMessagesMap;
			
			} 
			
	}
	
	/*
	 * Arrange all UI elements on fragments
	 * Add "Compose" button as a footer to grid
	 * Add grid as a footer to list
	 */
	private void arrangeUIElements (View rootView){
	  
		listView = (ListView) rootView.findViewById(R.id.list_messages);

		footerView = ((LayoutInflater) 
					getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
					inflate(R.layout.grid_contacts, listView, false);
	 
		gridView = (MyGridView) footerView.findViewById(R.id.grid_view);
 
		contactsGridAdapter = new ContactsGridAdapter(getActivity(),
				   mUsersMap); 
		 
		/*View btnFooterView = ((LayoutInflater) 
				getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
				inflate(R.layout.button_compose_footer, gridView, false);
	   */
		// gridView.addFooterView(btnFooterView);
		 
		 // Show dialog with contact name 
		 gridView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View row_view,
						int arg2, long arg3) {
				 	alertDialog(row_view);
					
				}

			 }); 
		 
		 // set footer invisible, it would set to visible 
		 // in case there are more than 3 users,
		 // because it became laggy
		 footerView.setVisibility(View.GONE);
		 listView.addFooterView(footerView);
		   
		 // Show dialog with contact name and the message
		 listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View row_view,
					int arg2, long arg3) {
				alertDialog(row_view);
				
			}

		 });  
		 
		messagesListAdapter = new MessagesListAdapter(getActivity(), mMessagesArray,
		   mUsersMap); 
		listView.setAdapter(messagesListAdapter);
		gridView.setAdapter(contactsGridAdapter);
		 
	}
	 
}
