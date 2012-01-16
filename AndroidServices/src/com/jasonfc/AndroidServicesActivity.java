package com.jasonfc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AndroidServicesActivity extends Activity {
	 private static final String TAG = "MainActivity";
	    private int counter = 1;

	    @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	    }

	    public void doClick(View view) {
	        switch(view.getId()) {
	        case R.id.startBtn:
	            Log.i(TAG, "Starting service... counter = " + counter);
	            Intent intent = new Intent(AndroidServicesActivity.this,
	                    BackgroundService.class);
	            intent.putExtra("counter", counter++);
	            startService(intent);
	            break;
	        case R.id.stopBtn:
	            stopService();
	        }
	    }

	    private void stopService() {
	    	Log.i(TAG, "Stopping service...");
	        if(stopService(new Intent(AndroidServicesActivity.this,
	                    BackgroundService.class)))
	        	Log.i(TAG, "stopService was successful");
	        else
	        	Log.i(TAG, "stopService was unsuccessful");
	    }

	    @Override
	    public void onDestroy()
	    {
	    	stopService();
	        super.onDestroy();
	    }
}