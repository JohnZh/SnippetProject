package com.john.purejava.designpattern.chain;

/**
 * Created by JohnZh on 2020/6/1
 *
 * <p></p>
 */
public interface ILeader {

    void setNext(ILeader next);

    ILeader getNext();

    ProcessResult processHolidays(int holidays);
}
