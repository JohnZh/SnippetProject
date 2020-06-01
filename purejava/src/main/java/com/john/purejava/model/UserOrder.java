package com.john.purejava.model;

import com.john.purejava.annotation.Column;
import com.john.purejava.annotation.ColumnType;
import com.john.purejava.annotation.Constraint;
import com.john.purejava.annotation.Table;

@Table(name = "Order")
public class UserOrder {
    @Column(value = ColumnType.INTEGER, name = "order_id")
    public long orderId;

    @Column(value = ColumnType.STRING,
            length = 10,
            constraint = @Constraint(unique = true, primaryKey = true))
    public String serialNo;

    @Column(value = ColumnType.STRING, length = 50)
    public String name;
}
