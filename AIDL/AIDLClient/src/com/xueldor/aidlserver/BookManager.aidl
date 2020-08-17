package com.xueldor.aidlserver;
import com.xueldor.aidlserver.Book;
import com.xueldor.aidlserver.ICallback;

interface BookManager {
	List<Book> getBooks();
	Book getBook(String name);
	int getBookCount();
	
	void setBookPrice(in Book book,int price);
	void setBookName(in Book book , String name);
    void addBookIn(in Book book);
    void addBookOut(out Book book);
    void addBookInout(inout Book book);
    
    void registerBookAddListener(ICallback callback);
    void unregisterBookAddListener(ICallback callback);
}
