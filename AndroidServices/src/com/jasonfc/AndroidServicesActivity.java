package com.jasonfc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class AndroidServicesActivity extends Activity {
	private static final String TAG = "AndroidServicesActivity";
	private int counter = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void doClick(View view) {
		switch (view.getId()) {
		case R.id.startBtn:
			Log.i(TAG, "Starting service... counter = " + counter);
			Toast.makeText(this, "Starting service... counter = " + counter, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(AndroidServicesActivity.this, BackgroundService.class);
			intent.putExtra("counter", counter++);
			startService(intent);
			break;
		case R.id.stopBtn:
			stopService();
		}
	}

	private void stopService() {
		Log.i(TAG, "Stopping service...");
		Toast.makeText(this, "Stopping service...", Toast.LENGTH_SHORT).show();
		if (stopService(new Intent(AndroidServicesActivity.this, BackgroundService.class))) {
			Log.i(TAG, "stopService was successful");
			Toast.makeText(this, "stopService was successful", Toast.LENGTH_SHORT).show();
		} else {
			Log.i(TAG, "stopService was unsuccessful");
			Toast.makeText(this, "stopService was successful", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onDestroy() {
		stopService();
		super.onDestroy();
	}
}