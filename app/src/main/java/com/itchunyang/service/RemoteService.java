package com.itchunyang.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by luchunyang on 2016/11/8.
 *
 * build -> make project
 */

public class RemoteService extends Service {

    public static final String TAG = RemoteService.class.getSimpleName();
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return binder;
    }

    //aidl调用会导致被调用进程启动一个子线程来执行实际代码
    IAIDLChannel.Stub binder = new IAIDLChannel.Stub() {
        @Override
        public String queryVersion() throws RemoteException {
            System.out.println("thread ---> "+Thread.currentThread().getName());//Binder线程
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "version 1.0.0";
        }

        //具体运行在哪个线程 有调用者决定的!!!!!
        @Override
        public Bitmap downImage(String url) throws RemoteException {

            Bitmap bitmap = null;
            try {
                URL src = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) src.openConnection();
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
            }catch (Exception e){
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        public void method1(String[] list) throws RemoteException {
            list[0] = "method1";
            list[1] = "hello";
        }

        @Override
        public void method2(List<String> inList, List<String> outList) throws RemoteException {
            for (int i = 0; i < inList.size(); i++) {
                outList.add(inList.get(i));
            }
        }

        @Override
        public void getPerson(Person p) throws RemoteException {
            p.setName("newName");
            p.setAge(100);
        }
    };
}
