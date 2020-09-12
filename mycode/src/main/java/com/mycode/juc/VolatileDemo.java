package com.mycode.juc;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author kyw
 * @Date 2020/9/12
 **/
public class VolatileDemo {
    /**
     * volatile 关键字 是java提供的一种同步方式， 是一种轻量级的同步机制
     *三大特性：1、可见性；2不保证原子性；3、禁止指令重排；
     * JMM : java内存模型；三大特性：1、可见性；2、原子性； 3、按顺序执行
     * 主内存：
     * 工作内存：
     * 在多线程操作共享变量时；线程会先从主内存中将变量拷贝到自己的工作内存中，进行操作，操作完成之后在刷回主内存，线程之间是不通信的；
     */
    private static  int i = 1;

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);


    }
    public void volatieTest(){
        new Thread(()->{
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            System.out.println(Thread.currentThread().getName()+" \t i="+ i);
        },"A").start();

     /*   while (i == 1){

        }*/
        System.out.println(Thread.currentThread().getName()+" \t i="+ i);
    }
}
