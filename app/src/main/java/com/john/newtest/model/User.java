package com.john.newtest.model;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.john.newtest.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

/**
 * Modified by john on 2020/4/29
 * <p>
 * Description:
 */
public class User extends BaseObservable {

    public String name;
    private int age;
    private String header;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Bindable
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
        this.notifyPropertyChanged(BR.header);
    }

    public void setName(String name) {
        this.name = name;
        //this.notifyPropertyChanged(BR.name);
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
        this.notifyPropertyChanged(BR.age);
    }

    @Bindable
    public int getAge() {
        return age;
    }

    @BindingAdapter("url")
    public static void setImage(ImageView imageView, String url) {
        Log.d("Temp", "setImage: " + url); // todo remove later
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
