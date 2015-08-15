package com.liran.adorikatest.sync;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.liran.adorikatest.others.User;

public class ImageLoader extends AsyncTask <String, String, Bitmap> {
	 
	Bitmap  mBitmap;
	String  mId;
	String  mName; 
	Context mContext;
	public  AsyncResponse delegate=null;
 
	 
	public interface AsyncResponse {
		void processFinish(User user);
		 
	}

	
    protected void onPostExecute(Bitmap bitmap){
    	
    	Log.i("USER", mId + " " + mName);
        User mUser = new User(mId, mName, bitmap, mContext);
    	 
    	// Call the interface method
    	delegate.processFinish(mUser);
     }
    
    public ImageLoader(String id, String name, Context context) {
    	mId = id;
    	mName = name;
    	mContext = context;
    }
    
	@Override
	protected Bitmap doInBackground(String... params) {
		 			
		 try {
			 
			 // mBitmap = BitmapFactory.decodeStream((InputStream)new URL(params[0]).getContent());
			 return downloadBitmap(params[0]);
			 
			
		 } catch (Exception e) {
		          e.printStackTrace();
		 }
		
		 return mBitmap;

	}
	  
	  private Bitmap downloadBitmap(String url) {
			// initilize the default HTTP client object
			final DefaultHttpClient client = new DefaultHttpClient();

			//forming a HttoGet request 
			final HttpGet getRequest = new HttpGet(url);
			try {

				HttpResponse response = client.execute(getRequest);

				//check 200 OK for success
				final int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode != HttpStatus.SC_OK) {
					Log.w("ImageDownloader", "Error " + statusCode + 
							" while retrieving bitmap from " + url);
					return null;

				}

				final HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream inputStream = null;
					try {
						// getting contents from the stream 
						inputStream = entity.getContent();

						// decoding stream data back into image Bitmap that android understands
						final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

						return bitmap;
					} finally {
						if (inputStream != null) {
							inputStream.close();
						}
						entity.consumeContent();
					}
				}
				
			} catch (Exception e) {
				// You Could provide a more explicit error message for IOException
				getRequest.abort();
				Log.e("ImageDownloader", "Something went wrong while" +
						" retrieving bitmap from " + url + e.toString());
			} 

			return null;
	  	 
	 }
}
