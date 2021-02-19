package com.john.purejava;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Modified by john on 2020/3/20
 * <p>
 * Description:
 */
public class MyCode {

    public static void main(String[] args) throws Exception {
//        String utcTime = "2018-05-23T16:05:52.123+09:00";
//        String utcTimePatten = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
//        String localTimePatten = "yyyy-MM-dd HH:mm:ss.SSS";
//
//        System.out.println(utcTime);
//        System.out.println(utc2Local(utcTime, utcTimePatten, localTimePatten));
//        System.out.println(str2DateLong(utc2Local(utcTime, utcTimePatten, localTimePatten)));

        String domain = "http://www.baidu.com//http://ss";
        System.out.println(domain.replaceFirst("http://", "https://"));
    }

    public static Date str2DateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 函数功能描述:UTC时间转本地时间格式
     *
     * @param utcTime         UTC时间
     * @param utcTimePatten   UTC时间格式
     * @param localTimePatten 本地时间格式
     * @return 本地时间格式的时间
     * eg:utc2Local("2017-06-14T09:37:50.788+08:00", "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", "yyyy-MM-dd HH:mm:ss.SSS")
     */
    public static String utc2Local(String utcTime, String utcTimePatten, String localTimePatten) {
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));//时区定义并进行时间获取
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return utcTime;
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }


    private static class A {

    }

    private static class B extends A {
    }

    private static class C extends B {
    }


    static class MyThreadFactory implements ThreadFactory {

        private AtomicInteger atomicInteger = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "Worker-" + atomicInteger.getAndIncrement());
        }
    }

//    private static void testAnnotation() {
//        TableUtils.ConnectionSource source = new TableUtils.ConnectionSource();
//        TableUtils.createTable(source, User.class);
//        TableUtils.createTable(source, UserOrder.class);
//    }
}
