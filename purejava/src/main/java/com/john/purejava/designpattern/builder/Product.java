package com.john.purejava.designpattern.builder;

/**
 * Created by JohnZh on 2020/5/26
 *
 * <p>Product for builer pattern</p>
 */
public class Product {

    private String part1;
    private String part2;

    public void setPart1(String part) {
        this.part1 = part;
    }

    public void setPart2(String part) {
        this.part2 = part;
    }

    public static Builder2 builder() {
        return new Builder();
    }

    public static class Builder implements Builder2 {

        private String part1;
        private String part2;

        @Override
        public Builder2 buildPart1(String part1) {
            this.part1 = part1;
            return this;
        }

        @Override
        public Builder2 buildPart2(String part2) {
            this.part2 = part2;
            return this;
        }

        @Override
        public Product bind() {
            Product product = new Product();
            product.part1 = this.part1;
            product.part2 = this.part2;
            return product;
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "part1='" + part1 + '\'' +
                ", part2='" + part2 + '\'' +
                '}';
    }
}
