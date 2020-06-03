package com.john.purejava.designpattern.chain.variety;

/**
 * Created by JohnZh on 2020/6/3
 *
 * <p></p>
 */
public class MidInterceptor implements Interceptor {

    @Override
    public Response intercept(ChainNode nextChainNode, Request request) {
        System.out.println("MidInterceptor # intercept " + request);
        Response response = nextChainNode.process(request);
        System.out.println("MidInterceptor # intercept " + response);
        return response;
    }
}
