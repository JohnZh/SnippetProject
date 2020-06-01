package com.john.purejava.annoprocesser;

import com.john.purejava.annotation.Column;
import com.john.purejava.annotation.ColumnType;
import com.john.purejava.annotation.Constraint;
import com.john.purejava.annotation.Table;

import java.lang.reflect.Field;

/**
 * Modified by john on 2020/3/21
 * <p>
 * Description:
 */
public class TableUtils {

    public static class ConnectionSource {
        public void executeSQL(String sql) {
            System.out.println(sql);
        }
    }

    public static void createTable(ConnectionSource source, Class clazz) {
        StringBuilder sql = new StringBuilder("CREATE TABLE");
        Table table = (Table) clazz.getAnnotation(Table.class);

        if (table == null) {
            return;
        }

        String tableName = table.name();
        if (tableName.equals("")) {
            tableName = clazz.getSimpleName();
        }
        sql.append(" ").append(tableName.toUpperCase()).append("(");

        for (Field field : clazz.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column == null) continue;

            String columnName = column.name();
            if (columnName.equals("")) {
                columnName = field.getName();
            }
            sql.append(columnName.toUpperCase());

            String columnType = getColumnType(column);
            String columnConstraint = getConstraints(column.constraint());
            sql.append(columnType).append(columnConstraint).append(", ");
        }
        String finalSql = sql.toString();
        finalSql = finalSql.substring(0, finalSql.length() - 2) + ");";

        source.executeSQL(finalSql);
    }

    private static String getConstraints(Constraint constraint) {
        StringBuilder sb = new StringBuilder("");
        if (!constraint.allowNull()) {
            sb.append(" NOT NULL");
        } else if (constraint.primaryKey()) {
            sb.append(" PRIMARY KEY");
        } else if (constraint.unique()) {
            sb.append(" UNIQUE");
        }
        return sb.toString();
    }

    private static String getColumnType(Column column) {
        StringBuilder sb = new StringBuilder("");
        if (column.value() == ColumnType.STRING) {
            sb.append(" VARCHAR(").append(column.length()).append(")");
        } else if (column.value() == ColumnType.LONG) {
            sb.append(" LONG");
        } else if (column.value() == ColumnType.DOUBLE) {
            sb.append(" DOUBLE");
        } else if (column.value() == ColumnType.FLOAT) {
            sb.append(" FLOAT");
        } else if (column.value() == ColumnType.INTEGER) {
            sb.append(" INT");
        }
        return sb.toString();
    }
}
