package com.john.purejava.model;

import com.john.purejava.annotation.Column;
import com.john.purejava.annotation.ColumnType;
import com.john.purejava.annotation.Constraint;
import com.john.purejava.annotation.Table;

@Table
public class User {
    public User(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Column(value = ColumnType.LONG,
            constraint = @Constraint(primaryKey = true))
    public long id;

    @Column(value = ColumnType.STRING, length = 30,
            constraint = @Constraint(allowNull = false))
    public String name;

    @Column(ColumnType.INTEGER)
    public int age;
}
