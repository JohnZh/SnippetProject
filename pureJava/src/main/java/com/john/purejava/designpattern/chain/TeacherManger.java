package com.john.purejava.designpattern.chain;

/**
 * Created by JohnZh on 2020/6/1
 *
 * <p></p>
 */
public class TeacherManger extends Leader {

    @Override
    public ProcessResult processHolidays(int holidays) {
        if (holidays < 8) {
            return new ProcessResult(true, "TeacherManger");
        }

        if (getNext() != null) {
            return getNext().processHolidays(holidays);
        }

        return new ProcessResult(false);
    }
}
