package com.itchunyang.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by luchunyang on 2016/11/8.
 *
 * 双守护服务，stick，startforeground，起一个一像素页面?
 */

public class SimpleService extends Service {

    public static final String TAG = SimpleService.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return null;
    }

    /**
     * startId：代表启动服务的次数，由系统生成。
     * stopSelf(int startId)：
     * 在其参数startId跟最后启动该service时生成的ID相等时才会执行停止服务。
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: "+Thread.currentThread().getName());//main
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    System.out.println("Service 工作");
                    stopSelf();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
