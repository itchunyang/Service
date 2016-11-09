// Person.aidl
package com.itchunyang.service;

// Declare any non-default types here with import statements

//这个Person.aidl文件很简单，就是定义了一个Parcelable类，告诉系统我们需要序列化和反序列化的类型。每一个实现了Parcelable的类型都需要对应的.aidl文件
parcelable Person;