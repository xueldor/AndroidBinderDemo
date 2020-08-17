package com.hsae.aftersaleclient;

import android.os.IInterface;
import android.os.RemoteException;

public interface IAfterSalesService extends IInterface {
    static final java.lang.String DESCRIPTOR = "android.hsae.aftersale";
    public int runFileCommand(String str)throws RemoteException;
    static final int TRANSACTION_runFileCommand = android.os.IBinder.FIRST_CALL_TRANSACTION;
}

