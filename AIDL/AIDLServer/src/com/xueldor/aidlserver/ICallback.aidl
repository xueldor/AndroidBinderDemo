package com.xueldor.aidlserver;
import com.xueldor.aidlserver.Book;

interface ICallback {
	void callback(in Book book);
}

