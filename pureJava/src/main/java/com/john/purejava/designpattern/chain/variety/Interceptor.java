package com.john.purejava.designpattern.chain.variety;

/**
 * Created by JohnZh on 2020/6/3
 *
 * <p></p>
 */
public interface Interceptor {

    Response intercept(ChainNode nextChainNode, Request request);
}
