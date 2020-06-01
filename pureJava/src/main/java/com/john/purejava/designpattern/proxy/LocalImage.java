package com.john.purejava.designpattern.proxy;

/**
 * Created by JohnZh on 2020/5/27
 *
 * <p>Local image for proxy</p>
 */
public class LocalImage implements Image {

    private String fileName;

    public LocalImage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void display() {
        System.out.println("Local image display");
    }

    public void load() {
        System.out.println("Load " + fileName);
    }
}
