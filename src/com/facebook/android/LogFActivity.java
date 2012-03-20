package com.facebook.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LogFActivity extends Activity {
    /** Called when the activity is first created. */
	private static final String[] PERMISSIONS =
	        new String[] {"friends_photos", "read_friendlists"};
	public static final String APP_ID = "156100147833882";
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Utility.mFacebook = new Facebook(APP_ID);
        Utility.mAsyncRunner = new AsyncFacebookRunner(Utility.mFacebook);
        LoginButton loginButton = (LoginButton) findViewById(R.id.login);
        loginButton.init(this, 0, Utility.mFacebook, PERMISSIONS);
        Button friends = (Button) findViewById(R.id.Friend);
        friends.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!Utility.mFacebook.isSessionValid()) {
                    Toast.makeText(getApplicationContext(), "Warning"+ " You must first log in.", Toast.LENGTH_SHORT).show();
				}
				else{
					Bundle params = new Bundle();
					params.putString("fields", "name, picture, location");
					Utility.mAsyncRunner.request("me/friends", params,
	                    new FriendsRequestListener()); 
				}
			}
		});
        
    }
    
    public class FriendsRequestListener extends BaseRequestListener {

        @Override
        public void onComplete(final String response, final Object state) {
            
            Intent myIntent = new Intent(getApplicationContext(), FriendsList.class);
            myIntent.putExtra("API_RESPONSE", response);
            myIntent.putExtra("METHOD", "graph");
            Log.e("ok", "ok");
            startActivity(myIntent);
            Log.e("ok", "ok");
        }

        public void onFacebookError(FacebookError error) {
            
            Toast.makeText(getApplicationContext(), "Facebook Error: " + error.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
    
}