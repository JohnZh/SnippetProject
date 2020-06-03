package com.john.purejava.designpattern.chain.variety;

/**
 * Created by JohnZh on 2020/6/3
 *
 * <p></p>
 */
public class LowInterceptor implements Interceptor {
    @Override
    public Response intercept(ChainNode nextChainNode, Request request) {
        System.out.println("LowInterceptor # intercept " + request);
        Response response = new Response("Response 1");
        System.out.println("LowInterceptor # obtain " + response);
        System.out.println("LowInterceptor # intercept " + response);
        return response;
    }
}
