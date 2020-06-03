package com.john.purejava.designpattern.chain;

/**
 * Created by JohnZh on 2020/6/1
 *
 * <p></p>
 */
public class Teacher extends Leader {

    @Override
    public ProcessResult processHolidays(int holidays) {
        if (holidays < 3) {
            return new ProcessResult(true, "Teacher");
        }

        if (getNext() != null) {
            return getNext().processHolidays(holidays);
        }

        return new ProcessResult(false);
    }
}
