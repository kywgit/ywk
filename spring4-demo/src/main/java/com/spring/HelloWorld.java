package com.spring;

/**
 * @Author kyw
 * @Date 2020/9/16
 **/
public class HelloWorld {
    private String  name;

    public HelloWorld(){
        System.out.println("这事构造器");
    }

    public void setName(String  name){
        System.out.println("这是setName 赋值方法");
        this.name =name;
    }

    public void hello(){
        System.out.println("hello :"+this.name);
    }
}
