package com.liran.adorikatest.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.liran.adorikatest.others.Constants;

import android.util.Log;

/*
 * Handle fill all JSONs'
 */
public class MyJson {
      
	/*
	 * Fill a single message data into JSON
	 */
	private static JSONArray fillMessage(String message, String from, JSONArray jsonArrayMsgs) {
		
		if(message != null) {
				
			try {
				JSONObject jsonObjMsg = new JSONObject();
				jsonObjMsg.put(Constants.MESSAGE, message); 
				jsonObjMsg.put(Constants.FROM, from);  
			
				jsonArrayMsgs.put(jsonObjMsg);
			 
			} catch (JSONException e) { 
				e.printStackTrace();
			}
		}
		
		return jsonArrayMsgs;
		 
	}
	
	/*
	 * Fill All data into JSON
	 */
	public static JSONObject fillAllJSONData() {
	    
		JSONObject jsonAllData  = new JSONObject();
		
		JSONArray  jsonArrayMsgs = new JSONArray();

		//Fill messages data into JSON
	     fillMessage("Hi, what's up?", "UserID1", jsonArrayMsgs);
		 fillMessage("ok, how are you?", "UserID2", jsonArrayMsgs);
	  
		try {
			jsonAllData.put(Constants.MESSAGES, jsonArrayMsgs);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		JSONArray  jsonArrayUsers = new JSONArray();
		 
		//Fill Users in Json
		fillUser("http://37.48.90.31/android/01.jpg", "Joe"  , "UserID1", 
				jsonArrayUsers);
	 
		fillUser("http://37.48.90.31/android/02.jpg", "Lyla" , "UserID2", 
				jsonArrayUsers);
		
		fillUser("http://37.48.90.31/android/03.jpg", "Ronit", "UserID3", 
				jsonArrayUsers); 
		
  		fillUser("http://37.48.90.31/android/04.jpg", "Nazer", "UserID4", 
  				jsonArrayUsers);
  		
      	fillUser("http://37.48.90.31/android/05.jpg", "Katia", "UserID5", 
      			jsonArrayUsers); 
      	
	 	fillUser("http://37.48.90.31/android/06.jpg", "Sam"  , "UserID6", 
	 			jsonArrayUsers); 
	
		try { 
			jsonAllData.put(Constants.USERS, jsonArrayUsers);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonAllData;
	}
	
	/*
	 * Fill a single user data into JSON
	 */
	 private static JSONArray fillUser(String img, String name, String id, JSONArray jsonArrayUsers) {
		 
		try {
			JSONObject jsonObjUser = new JSONObject();
		
			jsonObjUser.put(Constants.IMG, img); 
			jsonObjUser.put(Constants.NAME, name);
			jsonObjUser.put(Constants.ID, id); 
			jsonArrayUsers.put(jsonObjUser);
			 
		} catch (JSONException e) {
			 e.printStackTrace();
		}
		
		return jsonArrayUsers;
		
	 } 
	 
 }
