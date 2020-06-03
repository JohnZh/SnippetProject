package com.john.purejava.designpattern.chain.variety;

/**
 * Created by JohnZh on 2020/6/3
 *
 * <p></p>
 */
public class Response {
    private String name;

    public Response(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Response{" +
                "name='" + name + '\'' +
                '}';
    }
}