package com.john.purejava.designpattern;

import com.john.purejava.designpattern.builder.Product;
import com.john.purejava.designpattern.chain.ProcessResult;
import com.john.purejava.designpattern.chain.SchoolMaster;
import com.john.purejava.designpattern.chain.Teacher;
import com.john.purejava.designpattern.chain.TeacherManger;
import com.john.purejava.designpattern.chain.variety.ChainNode;
import com.john.purejava.designpattern.chain.variety.HighInterceptor;
import com.john.purejava.designpattern.chain.variety.Interceptor;
import com.john.purejava.designpattern.chain.variety.LowInterceptor;
import com.john.purejava.designpattern.chain.variety.MidInterceptor;
import com.john.purejava.designpattern.chain.variety.RealChainNode;
import com.john.purejava.designpattern.chain.variety.Request;
import com.john.purejava.designpattern.chain.variety.Response;
import com.john.purejava.designpattern.proxy.Image;
import com.john.purejava.designpattern.proxy.ImageInvocationHandler;
import com.john.purejava.designpattern.proxy.ImageProxy;
import com.john.purejava.designpattern.proxy.LocalImage;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JohnZh on 2020/6/1
 *
 * <p></p>
 */
public class PatternTester {

    public static void testChainOfResponsibilityVariety() {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new HighInterceptor());
        interceptors.add(new MidInterceptor());
        interceptors.add(new LowInterceptor());

        ChainNode chainNode = new RealChainNode(0, interceptors);
        Request request = new Request("Request 1");

        System.out.println("SEND: " + request);
        Response response = chainNode.process(request);
        System.out.println("GET: " + response);
    }

    public static void testChainOfResponsibility() {
        Teacher teacher = new Teacher();
        TeacherManger teacherManger = new TeacherManger();
        teacher.setNext(teacherManger);
        teacherManger.setNext(new SchoolMaster());

        int holidays = 6;
        ProcessResult processResult = teacher.processHolidays(holidays);
        System.out.println(processResult.isOk() ?
                holidays + " holidays is approved. Approver is " + processResult.getApprover()
                : holidays + " holidays is not approved.");
    }

    public static void testDynamicProxy() {
        LocalImage localImage = new LocalImage("im1.png");
        Image imageProxy = (Image) Proxy.newProxyInstance(localImage.getClass().getClassLoader(),
                localImage.getClass().getInterfaces(),
                new ImageInvocationHandler(localImage));
        imageProxy.display();
        imageProxy.hashCode();
    }

    private static void testProxyPattern() {
        ImageProxy proxy = new ImageProxy("im.png");
        proxy.display();
    }

    private static void testBuilderPattern() {
        Product product = Product.builder()
                .buildPart1("part1")
                .buildPart2("part2")
                .bind();
        System.out.println(product);
    }
}
