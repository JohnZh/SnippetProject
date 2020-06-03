package com.john.purejava.designpattern.chain.variety;

import java.util.List;

/**
 * Created by JohnZh on 2020/6/3
 *
 * <p></p>
 */
public class RealChainNode implements ChainNode {

    private final int interceptorIndex;
    private final List<Interceptor> interceptorList;

    public RealChainNode(int interceptorIndex, List<Interceptor> interceptorList) {
        this.interceptorIndex = interceptorIndex;
        this.interceptorList = interceptorList;
    }

    @Override
    public Response process(Request request) {
        Interceptor interceptor = interceptorList.get(interceptorIndex);
        RealChainNode nextChainNode = new RealChainNode(interceptorIndex + 1, interceptorList);
        return interceptor.intercept(nextChainNode, request);
    }
}
