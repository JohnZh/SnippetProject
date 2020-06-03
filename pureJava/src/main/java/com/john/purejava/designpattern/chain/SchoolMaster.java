package com.john.purejava.designpattern.chain;

/**
 * Created by JohnZh on 2020/6/1
 *
 * <p></p>
 */
public class SchoolMaster extends Leader {

    @Override
    public ProcessResult processHolidays(int holidays) {
        if (holidays < 12) {
            return new ProcessResult(true, "SchoolMaster");
        }

        if (getNext() != null) {
            return getNext().processHolidays(holidays);
        }

        return new ProcessResult(false);
    }
}
