/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\MyCode\\myAndroidSpace\\AIDLServer\\src\\com\\xueldor\\aidlserver\\BookManager.aidl
 */
package com.xueldor.aidlserver;
public interface BookManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.xueldor.aidlserver.BookManager
{
private static final java.lang.String DESCRIPTOR = "com.xueldor.aidlserver.BookManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.xueldor.aidlserver.BookManager interface,
 * generating a proxy if needed.
 */
public static com.xueldor.aidlserver.BookManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.xueldor.aidlserver.BookManager))) {
return ((com.xueldor.aidlserver.BookManager)iin);
}
return new com.xueldor.aidlserver.BookManager.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getBooks:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.xueldor.aidlserver.Book> _result = this.getBooks();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_getBook:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.xueldor.aidlserver.Book _result = this.getBook(_arg0);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_getBookCount:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getBookCount();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setBookPrice:
{
data.enforceInterface(DESCRIPTOR);
com.xueldor.aidlserver.Book _arg0;
if ((0!=data.readInt())) {
_arg0 = com.xueldor.aidlserver.Book.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
this.setBookPrice(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setBookName:
{
data.enforceInterface(DESCRIPTOR);
com.xueldor.aidlserver.Book _arg0;
if ((0!=data.readInt())) {
_arg0 = com.xueldor.aidlserver.Book.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
java.lang.String _arg1;
_arg1 = data.readString();
this.setBookName(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_addBookIn:
{
data.enforceInterface(DESCRIPTOR);
com.xueldor.aidlserver.Book _arg0;
if ((0!=data.readInt())) {
_arg0 = com.xueldor.aidlserver.Book.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.addBookIn(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_addBookOut:
{
data.enforceInterface(DESCRIPTOR);
com.xueldor.aidlserver.Book _arg0;
_arg0 = new com.xueldor.aidlserver.Book();
this.addBookOut(_arg0);
reply.writeNoException();
if ((_arg0!=null)) {
reply.writeInt(1);
_arg0.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_addBookInout:
{
data.enforceInterface(DESCRIPTOR);
com.xueldor.aidlserver.Book _arg0;
if ((0!=data.readInt())) {
_arg0 = com.xueldor.aidlserver.Book.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.addBookInout(_arg0);
reply.writeNoException();
if ((_arg0!=null)) {
reply.writeInt(1);
_arg0.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_registerBookAddListener:
{
data.enforceInterface(DESCRIPTOR);
com.xueldor.aidlserver.ICallback _arg0;
_arg0 = com.xueldor.aidlserver.ICallback.Stub.asInterface(data.readStrongBinder());
this.registerBookAddListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterBookAddListener:
{
data.enforceInterface(DESCRIPTOR);
com.xueldor.aidlserver.ICallback _arg0;
_arg0 = com.xueldor.aidlserver.ICallback.Stub.asInterface(data.readStrongBinder());
this.unregisterBookAddListener(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.xueldor.aidlserver.BookManager
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public java.util.List<com.xueldor.aidlserver.Book> getBooks() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.xueldor.aidlserver.Book> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBooks, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.xueldor.aidlserver.Book.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.xueldor.aidlserver.Book getBook(java.lang.String name) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.xueldor.aidlserver.Book _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
mRemote.transact(Stub.TRANSACTION_getBook, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.xueldor.aidlserver.Book.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getBookCount() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBookCount, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setBookPrice(com.xueldor.aidlserver.Book book, int price) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((book!=null)) {
_data.writeInt(1);
book.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(price);
mRemote.transact(Stub.TRANSACTION_setBookPrice, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setBookName(com.xueldor.aidlserver.Book book, java.lang.String name) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((book!=null)) {
_data.writeInt(1);
book.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeString(name);
mRemote.transact(Stub.TRANSACTION_setBookName, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addBookIn(com.xueldor.aidlserver.Book book) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((book!=null)) {
_data.writeInt(1);
book.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_addBookIn, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addBookOut(com.xueldor.aidlserver.Book book) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_addBookOut, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
book.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addBookInout(com.xueldor.aidlserver.Book book) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((book!=null)) {
_data.writeInt(1);
book.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_addBookInout, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
book.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void registerBookAddListener(com.xueldor.aidlserver.ICallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerBookAddListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterBookAddListener(com.xueldor.aidlserver.ICallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterBookAddListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getBooks = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getBookCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setBookPrice = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setBookName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_addBookIn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_addBookOut = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_addBookInout = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_registerBookAddListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_unregisterBookAddListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
}
public java.util.List<com.xueldor.aidlserver.Book> getBooks() throws android.os.RemoteException;
public com.xueldor.aidlserver.Book getBook(java.lang.String name) throws android.os.RemoteException;
public int getBookCount() throws android.os.RemoteException;
public void setBookPrice(com.xueldor.aidlserver.Book book, int price) throws android.os.RemoteException;
public void setBookName(com.xueldor.aidlserver.Book book, java.lang.String name) throws android.os.RemoteException;
public void addBookIn(com.xueldor.aidlserver.Book book) throws android.os.RemoteException;
public void addBookOut(com.xueldor.aidlserver.Book book) throws android.os.RemoteException;
public void addBookInout(com.xueldor.aidlserver.Book book) throws android.os.RemoteException;
public void registerBookAddListener(com.xueldor.aidlserver.ICallback callback) throws android.os.RemoteException;
public void unregisterBookAddListener(com.xueldor.aidlserver.ICallback callback) throws android.os.RemoteException;
}
