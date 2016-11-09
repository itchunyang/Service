package com.itchunyang.servicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.itchunyang.service.IAIDLChannel;
import com.itchunyang.service.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.iv);
    }

    //Service的时需要采用隐私启动的方式，但是Android 5.0一出来后，其中有个特性就是Service Intent  must be explitict，
    //也就是说从Lollipop开始，service服务必须采用显示方式启动
    public void startSimpleService(View view) {
        Intent intent = new Intent("com.itchunyang.service.simple");
        intent.setPackage("com.itchunyang.service");
        startService(intent);
    }

    public void stopSimpleService(View view) {
        Intent intent = new Intent("com.itchunyang.service.simple");
        intent.setPackage("com.itchunyang.service");//不加抛异常
        stopService(intent);
    }

    public void startIntentService(View view) {
        Intent intent = new Intent("com.itchunyang.service.intent");
        intent.setPackage("com.itchunyang.service");
        startService(intent);
    }


    private IAIDLChannel channel ;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            channel = IAIDLChannel.Stub.asInterface(service);
            Toast.makeText(MainActivity.this,"绑定成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void aidlBind(View view) {
        Intent intent = new Intent("com.itchunyang.service.aidl");
        intent.setPackage("com.itchunyang.service");
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    /**
     * AIDL 其实是直接调用,远程的service运行在哪个线程取决于调用者.同样,进程内bindService也是这样的
     * @param view
     */
    public void call(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //因为调用是耗时函数。
                    System.out.println("---->"+channel.queryVersion());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        },"ABC").start();
    }

    public void downImage(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                   final Bitmap bitmap = channel.downImage("http://www.people.com.cn/mediafile/pic/20161104/61/9178927391272937673.jpg");
                    if(bitmap != null)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv.setImageBitmap(bitmap);
                            }
                        });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public void method1(View view) {
        String[] list = new String[3];
        try {
            //out
            channel.method1(list);
            System.out.println(Arrays.toString(list));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void method2(View view) {
        List<String> inList = new ArrayList<>();
        inList.add("hello");
        inList.add("java");

        List<String> outList = new ArrayList<>(2);
        try {
            channel.method2(inList,outList);
            System.out.println("out:"+outList.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void method3(View view) {
        Person person = new Person("it",1);
        try {
            channel.getPerson(person);
            if(person == null)
                System.out.println("person is null !!!!");
            else
                System.out.println(person);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private Messenger messenger;
    private Messenger replay;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "handleMessage: "+msg.obj);
        }
    };

    public void bindMessenger(View view) {

        Intent intent = new Intent("com.itchunyang.service.messenger");
        intent.setPackage("com.itchunyang.service");
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                messenger = new Messenger(service);
                Log.i(TAG, "onServiceConnected: ");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        },Context.BIND_AUTO_CREATE);

        replay = new Messenger(handler);
    }

    public void sendMessenger(View view) {

        Message msg = Message.obtain();
        //注意!Message的obj不能设置为设置为non-Parcelable的对象，比如在跨进程的情形下，Message的obj设置为了一个String对象，那么在Messenger执行send(Message)方法时就会报如下错误:
//        msg.obj = new String("hello");

        Bundle bundle = new Bundle();
        bundle.putString("name","itchunyang");
        msg.setData(bundle);
        // 设置回调用的Messenger
        msg.replyTo = replay;

        try {
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
