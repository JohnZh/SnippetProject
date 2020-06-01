package com.john.purejava.designpattern.builder;

/**
 * Created by JohnZh on 2020/5/26
 *
 * <p>Director for builder pattern</p>
 */
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public Product createProduct() {
        builder.buildPart1("part1");
        builder.buildPart2("part2");
        return builder.bind();
    }
}
