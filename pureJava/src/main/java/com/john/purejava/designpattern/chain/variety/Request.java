package com.john.purejava.designpattern.chain.variety;

/**
 * Created by JohnZh on 2020/6/3
 *
 * <p></p>
 */
public class Request {

    private String name;

    public Request(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                '}';
    }
}
