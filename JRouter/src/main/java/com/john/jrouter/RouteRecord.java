package com.john.jrouter;

/**
 * Created by JohnZh on 2020/7/8
 *
 * <p></p>
 */
public class RouteRecord {
    protected RouteType type;
    protected Class clazz;
    protected String path;

    public RouteRecord(String path) {
        this.path = path;
    }

    public RouteRecord(RouteType type, Class clazz, String path) {
        this.type = type;
        this.clazz = clazz;
        this.path = path;
    }

    public void setType(RouteType type) {
        this.type = type;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RouteType getType() {
        return type;
    }

    public Class getClazz() {
        return clazz;
    }

    public String getPath() {
        return path;
    }
}
