package com.john.purejava.designpattern.builder;

/**
 * Created by JohnZh on 2020/5/26
 *
 * <p>Chain builder for builder pattern</p>
 */
public interface Builder2 {

    Builder2 buildPart1(String part1);

    Builder2 buildPart2(String part2);

    Product bind();
}
