package com.hsae.aftersaleclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    IBinder mRemote;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);

        SystemProperties.set("service.hsae.aftersale", "1");

        button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(mRemote == null || !mRemote.isBinderAlive()){
            mRemote = getRemoteBinder();
            if(mRemote == null){
                System.out.println("Remote service is not start!!");
            }
        }

        IAfterSalesService myService = new AfterSalesServiceProxy(mRemote);
        try {
            int status = myService.runFileCommand("/sdcard/file.sh");
            System.out.println("result: " + status);
        } catch (RemoteException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SystemProperties.set("service.hsae.aftersale", "0");
    }

    private IBinder getRemoteBinder(){
        return ServiceManager.getService("service.aftersale");
    }
}
