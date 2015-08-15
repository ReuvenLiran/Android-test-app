package com.liran.adorikatest.others;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.adorikatest.R;

public class User {
	
	private String mId;
	private Bitmap mAvatar;
	private String mName;
	
	public User(String id, String name, Bitmap bitmap, Context context) {
		mId 	= id;
			
		 // If name  is null, User doesn't have an name...
		 // then set "no name" string
		if(name == null) {
			name = "No Name";
		}
		
		mName 	= name;
		
		 // If bitmap is null, User doesn't have an avatar;
		 // then set a blank image
		 if(bitmap == null) {
			 
			bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.img_blank_contact);
 
		} 
		
		mAvatar = bitmap;
		
	}
	
	public String getId(){
		return mId;
	}
	
	public Bitmap getAvatar(){
		return mAvatar;
	}
	
	public String getName(){
		return mName;
	}

}
