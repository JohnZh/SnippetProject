package com.john.purejava.designpattern.builder;

/**
 * Created by JohnZh on 2020/5/26
 *
 * <p>Real product builder for builder pattern</p>
 */
public class RealProductBuilder implements Builder {

    private Product product;

    public RealProductBuilder() {
        product = new Product();
    }

    @Override
    public void buildPart1(String part1) {
        product.setPart1(part1);
    }

    @Override
    public void buildPart2(String part2) {
        product.setPart2(part2);
    }

    @Override
    public Product bind() {
        return product;
    }
}
