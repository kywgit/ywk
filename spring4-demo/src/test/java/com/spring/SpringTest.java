package com.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.applet.AppletContext;

/**
 * @Author kyw
 * @Date 2020/9/16
 **/
public class SpringTest {

/*    @Test*/
    public void helloTest(){
        // HelloWorld helloWorld = new HelloWorld();
        // helloWorld.setName("kk");
        //helloWorld.hello();

        //创建Spring IOC容器对象
        // ApplicationContext 代表IOC容器  ClassPathXmlApplicationContext 是ApplicationContext的一个实现类，该实现类从类路径下加载配置文件；
        ApplicationContext ctx  = new ClassPathXmlApplicationContext("applicationContext.xml");
        //从IOC容器中获取Bean实例
        /**
         *    Object getBean(String var1) throws BeansException;
         *
         *     <T> T getBean(String var1, Class<T> var2) throws BeansException;
         *
         *     <T> T getBean(Class<T> var1) throws BeansException;
         *
         *     Object getBean(String var1, Object... var2) throws BeansException;
         *
         *     <T> T getBean(Class<T> var1, Object... var2) throws BeansException;
         */
        HelloWorld helloWorld = (HelloWorld) ctx.getBean("helloWorld");
        //HelloWorld helloWorld = (HelloWorld) ctx.getBean(HelloWorld.class);
        //调用方法
        helloWorld.hello();
    }
    @Test
    public void carTest(){

        ApplicationContext ctx  = new ClassPathXmlApplicationContext("applicationContext.xml");

        Car car = (Car) ctx.getBean("car");
        System.out.println(car.toString());

        Car car2 = (Car) ctx.getBean("car2");
        System.out.println(car2.toString());

        Person person = (Person) ctx.getBean("person");
        System.out.println(person.toString());

        Person person2 = (Person) ctx.getBean("person2");
        System.out.println(person2.toString());

        Person person3 = (Person) ctx.getBean("person3");
        System.out.println(person3.toString());
    }
}
