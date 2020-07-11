# SnippetProject

# 代码说明

## app 

### activities.TestAidlActivity & aidl & aidl.People

进程间通信，AIDL sample：定义了一个自定义通信数据 People

### 

## bindCompiler & bindLib 

学习 APT 模仿 BufferKnife 使用注解在编译期生成 findViewById 的代码

- activities.BindLibTestActivity 测试 bindLib 的代码
- bindCompiler 定义注解解析器，注册注解解析器
- bindLib 调用库类，定义注解，定义静态调用工具

## pureJava
纯 Java 模块，用于测试学习 Java

注意：运行需要另外配置

### com.john.purejava.annoprocesser & annotation & model

模仿 ORMLite 自定义数据 ORM 注解，输出 SQL 语句

- .annoprocesser 注解解析器
- .annotation 自定义注解
- .model ORM 映射对象

### com.john.purejava.designpattern 设计模式

- .builer 构建者模式例子
- .proxy 代理模式例子（静态代理，动态代理）
- .chain 责任链模式例子（含有基于 List 的写法）