/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\MyCode\\myAndroidSpace\\AIDLServer\\src\\com\\xueldor\\aidlserver\\ICallback.aidl
 */
package com.xueldor.aidlserver;
public interface ICallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.xueldor.aidlserver.ICallback
{
private static final java.lang.String DESCRIPTOR = "com.xueldor.aidlserver.ICallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.xueldor.aidlserver.ICallback interface,
 * generating a proxy if needed.
 */
public static com.xueldor.aidlserver.ICallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.xueldor.aidlserver.ICallback))) {
return ((com.xueldor.aidlserver.ICallback)iin);
}
return new com.xueldor.aidlserver.ICallback.Stub.Proxy(obj);
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
case TRANSACTION_callback:
{
data.enforceInterface(DESCRIPTOR);
com.xueldor.aidlserver.Book _arg0;
if ((0!=data.readInt())) {
_arg0 = com.xueldor.aidlserver.Book.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.callback(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.xueldor.aidlserver.ICallback
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
@Override public void callback(com.xueldor.aidlserver.Book book) throws android.os.RemoteException
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
mRemote.transact(Stub.TRANSACTION_callback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_callback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void callback(com.xueldor.aidlserver.Book book) throws android.os.RemoteException;
}
