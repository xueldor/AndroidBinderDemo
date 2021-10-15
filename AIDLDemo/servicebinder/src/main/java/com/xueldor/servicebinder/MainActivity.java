package com.xueldor.servicebinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 演示在同一个应用内service与activity的通信
 *
 * @author xuexiangyu
 *
 */
public class MainActivity extends Activity {
	private boolean threadDisable = false;
	private final ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			countService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			countService = (ICountService) service;
			Log.v("CountService", "on service connected,count is " + countService.getCount());
		}
	};

	private ICountService countService;

	private Button button1 = null;
	private TextView textView1 = null;

	Handler handler = new MyHandler(Looper.getMainLooper());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = new Intent("com.xueldor.servicebinder.CountService");
		intent.setClassName(this, CountService.class.getName());
		this.bindService(intent, this.serviceConnection, BIND_AUTO_CREATE);
		button1 = (Button) findViewById(R.id.button1);
		textView1 = (TextView) findViewById(R.id.textView1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						if(countService == null) {
							Log.i("onClick", "click again later");
							return;
						}
						while (!threadDisable) {
							String count = String.valueOf(countService.getCount());
							Bundle bundle = new Bundle();
							bundle.putString("count", count);
							Message message = Message.obtain();//使用obtain方法，Message内部维护了一个Message池用于Message的复用，避免使用new 重新分配内存
							message.setData(bundle);
							handler.sendMessage(message);
							Log.d("MainActivity", "count");
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
							}
						}
					}
				}).start();
			}
		});
	}

	@Override
	protected void onDestroy() {
		threadDisable = true;
		this.unbindService(serviceConnection);
		super.onDestroy();
		Log.d("MainActivity", "onDestroy");
	}


	class MyHandler extends Handler{

		public MyHandler(Looper looper) {
			super(looper);
		}
		@Override
		public void handleMessage(Message msg) {
			String count = msg.getData().getString("count");
			textView1.setText(count);
		}
	}

}
