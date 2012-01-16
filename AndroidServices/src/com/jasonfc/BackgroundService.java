package com.jasonfc;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BackgroundService extends Service
{
    private static final String TAG = "BackgroundService";
	private NotificationManager notificationMgr;
    private ThreadGroup myThreads = new ThreadGroup("ServiceWorker");
    
    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "in onCreate()");
        notificationMgr =(NotificationManager)getSystemService(
               NOTIFICATION_SERVICE);
        displayNotificationMessage("Background Service is running");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        int counter = intent.getExtras().getInt("counter");
        Log.i(TAG, "in onStartCommand(), counter = " + counter +
        		", startId = " + startId);

        new Thread(myThreads, new ServiceWorker(counter), "BackgroundService")
        	.start();
        
        return START_NOT_STICKY;
    }

    class ServiceWorker implements Runnable
    {
    	private int counter = -1;
		public ServiceWorker(int counter) {
			this.counter = counter;
		}

		public void run() {
	        final String TAG2 = "ServiceWorker:" + Thread.currentThread().getId();
            // do background processing here...
            try {
				Log.i(TAG2, "sleeping for 10 seconds. counter = " + counter);
				Thread.sleep(10000);
				Log.i(TAG2, "... waking up");
			} catch (InterruptedException e) {
				Log.i(TAG2, "... sleep interrupted with the counter of " + counter);
			}
        }
    }

    @Override
    public void onDestroy()
    {
        Log.i(TAG, "in onDestroy(). Interrupting threads and cancelling notifications");
        myThreads.interrupt();
        notificationMgr.cancelAll();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "in onBind()");
        return null;
    }

    private void displayNotificationMessage(String message)
    {
        Notification notification = new Notification(R.drawable.emo_im_winking, 
                message, System.currentTimeMillis());
        
        notification.flags = Notification.FLAG_NO_CLEAR;

        PendingIntent contentIntent = 
                PendingIntent.getActivity(this, 0, new Intent(this, AndroidServicesActivity.class), 0);

        notification.setLatestEventInfo(this, TAG, message, contentIntent);

        notificationMgr.notify(0, notification);
    }
}
