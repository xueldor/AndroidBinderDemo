package com.xueldor.aidlserver;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.xueldor.aidl.Book;
import com.xueldor.aidl.BookManager;
import com.xueldor.aidl.ICallback;

/**
 * 演示aidl server端的实现
 *
 */
public class AIDLService extends Service {

	private List<Book> mBooks = new ArrayList<>();

	private final RemoteCallbackList<ICallback> mListenerList = new RemoteCallbackList<>();

	private final BookManager.Stub mBookManager = new BookManager.Stub() {

		@Override
		public void setBookPrice(Book book, int price) {
			book.setPrice(price);
		}

		@Override
		public void setBookName(Book book, String name) {
			book.setName(name);

		}

		@Override
		public List<Book> getBooks() {
			if (mBooks == null) {
				mBooks = new ArrayList<>();
			}
			return mBooks;
		}

		@Override
		public int getBookCount() {
			return mBooks.size();
		}

		@Override
		public Book getBook(String name) {
			for (Book book : mBooks) {
				if (book.getName().equals(name)) {
					return book;
				}
			}
			return null;
		}

		@Override
		public void addBookOut(Book book) {
			mBooks.add(book);
			System.out.println("测试定向tag out");
			book.setPrice(book.getPrice()+10);
			System.out.println("Server " + book.toString());

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void addBookInout(Book book) {
			mBooks.add(book);
			System.out.println("测试定向tag inout");
			book.setPrice(book.getPrice()+30);
			System.out.println("Server " + book.toString());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void addBookIn(Book book) throws RemoteException {
			mBooks.add(book);
			System.out.println("测试定向tag in");
			book.setPrice(book.getPrice()+5);
			System.out.println("Server " + book.toString());
			onNewBookArrived(book);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void registerBookAddListener(ICallback callback) {
			System.out.println("server callback" + callback);
			System.out.println("server binder" + callback.asBinder());
			mListenerList.register(callback);
			System.out.println("getRegisteredCallbackCount " + mListenerList.getRegisteredCallbackCount());
		}

		@Override
		public void unregisterBookAddListener(ICallback callback) {
			System.out.println("server " + callback);
			mListenerList.unregister(callback);
			System.out.println("getRegisteredCallbackCount " + mListenerList.getRegisteredCallbackCount());
		}



	};

	private void onNewBookArrived(Book book) throws RemoteException {
		int N = mListenerList.beginBroadcast();
		for (int i = 0; i < N; i++) {
			ICallback listener = mListenerList.getBroadcastItem(i);
			if (listener != null) {
				listener.callback(book);
			}
		}
		mListenerList.finishBroadcast();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Book book = new Book();
		book.setName("Android开发艺术探索");
		book.setPrice(28);
		mBooks.add(book);
	}

	@Override
	public IBinder onBind(Intent intent) {
		System.out.println("onBind");
		return mBookManager;
	}

}
