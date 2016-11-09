package com.itchunyang.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luchunyang on 2016/11/9.
 */

public class Person implements Parcelable {

    String name;
    int age;

    public Person() {
    }

    protected Person(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {

        //从Parcel中读取我们的对象。
        @Override
        public Person createFromParcel(Parcel in) {
            System.out.println("---->createFromParcel");
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    //内容描述接口，基本不用管
    @Override
    public int describeContents() {
        return 0;
    }

    //将我们的对象序列化一个Parcel对象，也就是将我们的对象存入Parcel中
    //注意写入变量和读取变量的顺序应该一致 不然得不到正确的结果
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    //注意读取变量和写入变量的顺序应该一致 不然得不到正确的结果
    public void readFromParcel(Parcel src){
        name = src.readString();
        age = src.readInt();
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
