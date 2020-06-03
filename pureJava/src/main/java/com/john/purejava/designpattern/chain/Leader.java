package com.john.purejava.designpattern.chain;

/**
 * Created by JohnZh on 2020/6/1
 *
 * <p></p>
 */
public abstract class Leader implements ILeader {

    ILeader next;

    @Override
    public void setNext(ILeader next) {
        this.next = next;
    }

    @Override
    public ILeader getNext() {
        return next;
    }
}
