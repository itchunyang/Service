package com.itchunyang.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by luchunyang on 2016/11/8.
 *
 * 不需要主动调用stopSelft()来结束服务。因为，在所有的intent被处理完后，系统会自动关闭服务。
 */

public class MyIntentService extends IntentService {

    public static final String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService(){
        super("");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    //每次只会执行一个工作线程
    @Override
    protected void onHandleIntent(Intent intent) {
//        Log.i(TAG, "onHandleIntent: "+Thread.currentThread().getName());//线程
        Log.i(TAG, "onHandleIntent: 开始执行任务");
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "onHandleIntent: 执行任务完毕");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.i(TAG, "onStart: ");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: " + Thread.currentThread().getName());
        return super.onStartCommand(intent, flags, startId);
    }

}
