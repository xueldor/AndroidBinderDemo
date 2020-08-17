package com.hsae.aftersaleclient;

import android.os.IBinder;
import android.os.RemoteException;

public class AfterSalesServiceProxy implements IAfterSalesService{
    private android.os.IBinder mRemote;

    public AfterSalesServiceProxy(IBinder remote) {
        this.mRemote = remote;
    }

    @Override
    public int runFileCommand(String str) throws RemoteException {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int result;
        try {
            _data.writeInterfaceToken(DESCRIPTOR);
            _data.writeByteArray(str.getBytes());
            mRemote.transact(TRANSACTION_runFileCommand, _data, _reply, 0);
            _reply.readException();
            result = _reply.readInt();
        } finally {
            _reply.recycle();
            _data.recycle();
        }
        return result;
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}
