package com.xueldor.aidlclient;

import java.util.List;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xueldor.aidl.Book;
import com.xueldor.aidl.BookManager;
import com.xueldor.aidl.ICallback;

public class MainActivity extends Activity {
	
	 private BookManager mBookManager = null;
	 private boolean mBound = false;
	 private List<Book> mBooks;
	 
	 Button buttonIn = null;
	 Button buttonOut = null;
	 Button buttonInOut = null;
	 Button buttonRegis = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonIn = findViewById(R.id.buttonIn);
		buttonIn.setOnClickListener(v -> addBookIn());

		buttonOut = findViewById(R.id.buttonOut);
		buttonOut.setOnClickListener(v -> addBookOut());

		buttonInOut = findViewById(R.id.buttonInOut);
		buttonInOut.setOnClickListener(v -> addBookInOut());
		
		
		buttonRegis = findViewById(R.id.register);
		final ICallback callback = new ICallback.Stub() {
			
			@Override
			public void callback(Book book) throws RemoteException {
				System.out.println("client " + this + "book " + book + " Thread " + Thread.currentThread().getId()
						+ " ,tid " + Process.myTid());
			}
		};
		buttonRegis.setOnClickListener(v -> {
			 if (!mBound) {
					attemptToBindService();
					Toast.makeText(MainActivity.this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
					return;
				}
				if (mBookManager == null) return;
			try {
				if (buttonRegis.getText().equals("register")) {

					Executors.newFixedThreadPool(1).execute(new Runnable() {
						@Override
						public void run() {
							try {
								System.out.println("client tid " + Process.myTid());
								//服务端callback是两个对象，但asBinder是同一个,RemoteCallbackList类这是利用这一点
								mBookManager.registerBookAddListener(callback);
								mBookManager.registerBookAddListener(callback);
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						}
					});
					buttonRegis.setText("unregister");
				}else {
					mBookManager.unregisterBookAddListener(callback);
					buttonRegis.setText("register");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}
	
	Book book = new Book();
	{
		book.setName("APP研发录In");
		book.setPrice(30);
	}
	
	public void addBookIn() {
        //如果与服务端的连接处于未连接状态，则尝试连接
        if (!mBound) {
            attemptToBindService();
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mBookManager == null) return;


        try {
        	System.out.println("Thread " + Thread.currentThread().getId());
        	book.setPrice(30);
            mBookManager.addBookIn(book);//方法是同步的
            System.out.println("Client " + book.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
	public void addBookOut() {
		//如果与服务端的连接处于未连接状态，则尝试连接
		if (!mBound) {
			attemptToBindService();
			Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
			return;
		}
		if (mBookManager == null) return;
		
		
		try {
			book.setPrice(20);
			System.out.println("Client " + book.toString());
			//因为是Out，server端其实重新调用构造函数构造了一个对象输出到client的参数
			mBookManager.addBookOut(book);//方法是同步的
			 System.out.println("Client out" + book.toString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	public void addBookInOut() {
		//如果与服务端的连接处于未连接状态，则尝试连接
		if (!mBound) {
			attemptToBindService();
			Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
			return;
		}
		if (mBookManager == null) return;
		
		
		try {
			mBookManager.addBookInout(book);//方法是同步的
			 System.out.println("Client " + book.toString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
     * 尝试与服务端建立连接
     */
    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.xueldor.aidlserver.AIDLService");
        intent.setPackage("com.xueldor.aidlserver");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        
        System.out.println("attemptToBindService");
    }

    
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println(getLocalClassName() + " service connected");
            mBookManager = BookManager.Stub.asInterface(service);
            mBound = true;
            System.out.println(mBookManager.asBinder() == service);//true
            
            try {
				service.linkToDeath(mDeathRecipient, 0);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}

            if (mBookManager != null) {
                try {
                    mBooks = mBookManager.getBooks();
                    Log.e(getLocalClassName(), mBooks.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println(getLocalClassName() + " service disconnected");
            mBound = false;
        }
    };
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {  
    	  
        @Override  
        public void binderDied() {
        	System.out.println("binderDied");
        	mBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
        	//收到死亡代理后重新连接。
        	attemptToBindService();
        }  
    };
    
    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            attemptToBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }
}
