package com.itchunyang.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by luchunyang on 2016/11/9.
 */

public class MessengerService extends Service {

    public static final String TAG = MessengerService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            Bundle b = msg.getData();

            Log.i(TAG, "handleMessage: what="+what+" data="+b.getString("name"));
            //获取客户端message中的Messenger，用于回调
            final Messenger client = msg.replyTo;
            try {
                client.send(Message.obtain(null,-1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private Messenger messenger = new Messenger(handler);
}
