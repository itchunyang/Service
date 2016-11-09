package com.itchunyang.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * android:process = package:remote，将运行在package:remote进程中，属于全局进程，其他具有相同shareUID与签名的APP可以跑在这个进程中。
 * android:process = :remote ，将运行在默认包名:remote进程中，而且是APP的私有进程，不允许其他APP的组件来访问。
 * 注意 android:process:remote是可以的,但是android:process=remote直接这样写却不可以!要按照包名的格式才会能安装到andorid设备里面
 */

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private IService myBind;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: ComponentName="+name);
            myBind = (IService) service;
            Toast.makeText(MainActivity.this,"连接成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this,"意外断开连接",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //通过bindService开启的服务,服务开启之后,调用者和服务之间 还存在着联系,
    //一旦调用者挂掉了.service也会跟着挂掉,但是如果是通过startService之后再绑定的,则无影响
    public void bindLocal(View view) {
        Intent intent = new Intent(this,LocalService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    public void call(View view) {
        System.out.println(myBind.open());
    }

    public void unBind(View view) {
        unbindService(connection);
    }

    public void startLocal(View view) {
        Intent intent = new Intent(this,LocalService.class);
        startService(intent);
    }

    @Override
    protected void onStop() {

        if(myBind != null) {
            unbindService(connection);
            connection = null;
        }
        super.onStop();
    }

    public void longService(View view) {
    }
}
