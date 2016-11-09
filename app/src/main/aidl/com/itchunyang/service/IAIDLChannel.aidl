// IAIDLChannel.aidl
package com.itchunyang.service;
import android.graphics.Bitmap;
import com.itchunyang.service.Person;


// Declare any non-default types here with import statements

interface IAIDLChannel {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    String queryVersion();
    Bitmap downImage(String url);
    void method1(out String[] list);
    void method2(in List<String> inList,out List<String> outList);
    void getPerson(out Person p);
}
