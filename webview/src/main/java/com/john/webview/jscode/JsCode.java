package com.john.webview.jscode;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Created by JohnZh on 2020/7/15
 *
 * <p></p>
 */
public class JsCode {

    public static final String SCHEME = "app:";
    public static final String OBJECT = "object";
    public static final String METHOD = "method";
    public static final String ARGS = "args";

    /**
     * function callback(cmd, response) { }
     */
    public static String getJsCallbackMethod(String cmd, String response) {
        return new StringBuilder().append("javascript:").append("callback(\'")
                .append(cmd).append("\', ").append(response).append(")")
                .toString();
    }

    /**
     * 单个 JS 接口对象和单个方法的代码
     * javascript:(function autofun() {
     * if (typeof (window.webview) == 'undefined') {
     *  window.webview = {
     *      post:function(cmd, params) {
     *          return prompt('app:' + JSON.stringify({object: 'webview', method: 'post', args:[cmd, params]}));
     *      }
     *   }
     * } else {
     * console.log("window.webview is exist.");
     * }
     * })()
     */
    public static String getInjectJsCode(Map<String, Object> jsInterfaceMap) {
        StringBuilder code = new StringBuilder();
        if (jsInterfaceMap.size() > 0) {
            code.append("javascript:(function autofun() {");

            Set<String> keys = jsInterfaceMap.keySet();
            for (String key : keys) {
                addObjectAndMethods(key, jsInterfaceMap.get(key), code);
            }

            code.append("})()");
        }
        return code.toString();
    }

    /**
     * if (typeof (window.webview) == 'undefined') {
     *      window.webview = {
     *          post:function(cmd, params) {
     *              return prompt('app:' + JSON.stringify({object: 'webview', method: 'post', args:[cmd, params]}));
     *          }
     *      }
     * } else {
     *      console.log("window.webview is exist.");
     * }
     */
    private static void addObjectAndMethods(String objectName, Object object, StringBuilder code) {
        code.append("if (typeof (window.").append(objectName).append(")").append("==").append("'undefined'").append(") {")
                .append("window.").append(objectName).append("=").append("{");

        Method[] declaredMethods = object.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            JsInterface annotation = method.getAnnotation(JsInterface.class);
            if (annotation == null) continue;

            addJsMethod(objectName, method, code);
        }
        code.append("}")
                .append("} else {")
                .append("console.log(").append("\"window.").append(objectName).append(" is exist.\");")
                .append("}");
    }

    /**
     * post:function(cmd, params) {
     *      return prompt('app:' + JSON.stringify({object: 'webview', method: 'post', args:[cmd, params]}));
     * }
     */
    private static void addJsMethod(String objectName, Method method, StringBuilder code) {
        code.append(method.getName()).append(":").append("function(");
        int parameterCount = method.getParameterTypes().length;
        if (parameterCount > 0) {
            for (int i = 0; i < parameterCount; i++) {
                code.append("arg").append(i).append(',');
            }
            code.deleteCharAt(code.length() - 1);
        }
        code.append(") {")
                .append("return prompt(").append('\'').append(SCHEME).append('\'').append('+')
                .append("JSON.stringify({")
                .append("object:").append('\'').append(objectName).append('\'').append(',')
                .append("method:").append('\'').append(method.getName()).append('\'').append(',')
                .append("args:[");
        if (parameterCount > 0) {
            for (int i = 0; i < parameterCount; i++) {
                code.append("arg").append(i).append(',');
            }
            code.deleteCharAt(code.length() - 1);
        }
        code.append("]}));");
        code.append("}\n");
    }
}
