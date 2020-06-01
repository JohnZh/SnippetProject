package com.john.purejava.designpattern.builder;

/**
 * Created by JohnZh on 2020/5/26
 *
 * <p>abstract builder for builder pattern</p>
 */
public interface Builder {

    void buildPart1(String part1);

    void buildPart2(String part2);

    Product bind();
}
