package com.itchunyang.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by luchunyang on 2016/11/9.
 * 长时间运行的服务
 *
 * 1.onStartCommand返回START_STICKY。系统因内存不足回收之后,如果内存足够了,会尝试恢复
 * 2.提升service进程优先级 android:priority = "1000"
 * 3.service +broadcast  方式
 *      当service走ondestory的时候，发送一个自定义的广播，当收到广播的时候，重新启动service；
 * 4.系统各种广播监听
 *      通过系统的广播，监听并捕获到，然后判断是否需要重新启动service。
 * 5.设置成为前台进程
 *      NotificationManager + startForeground
 *
 */

public class LongService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
