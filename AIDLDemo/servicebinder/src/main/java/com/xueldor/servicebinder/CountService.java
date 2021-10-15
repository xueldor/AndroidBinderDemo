package com.xueldor.servicebinder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class CountService extends Service implements ICountService{
	private boolean threadDisable;
	private int count;
	
	private ServiceBinder serviceBinder = new ServiceBinder();
	
	public class ServiceBinder extends Binder implements ICountService{

		@Override
		public int getCount() {
			return count;
		}
		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return serviceBinder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(!threadDisable){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					count++;
					Log.v("CountService", "The count is "+ count);
				}
			}
		}).start();
	}



	@Override
	public void onDestroy() {
		super.onDestroy();
		this.threadDisable = true;
		Log.v("CountService", "on destroy");
	}



	@Override
	public int getCount() {
		return count;
	}

}
