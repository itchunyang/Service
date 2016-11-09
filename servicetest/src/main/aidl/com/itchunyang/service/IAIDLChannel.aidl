// IAIDLChannel.aidl
package com.itchunyang.service;

// Declare any non-default types here with import statements

import android.graphics.Bitmap;
import com.itchunyang.service.Person;


interface IAIDLChannel {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String queryVersion();
    Bitmap downImage(String url);
    void method1(out String[] list);
    void method2(in List<String> inList,out List<String> outList);
    void getPerson(out Person p);

}
