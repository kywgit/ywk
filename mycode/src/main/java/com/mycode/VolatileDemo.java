package com.mycode;

import java.util.concurrent.TimeUnit;

/**
 * @Author kyw
 * @Date 2020/9/12
 **/
public class VolatileDemo {
    /**
     * volatile 关键字 三大特性：1、可见性；2不保证原子性；3、禁止指令重排；
     * JMM : java内存模型；
     */
    private static  int i = 1;

    public static void main(String[] args) {

        new Thread(()->{
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             i++;
             System.out.println(Thread.currentThread().getName()+" \t i="+ i);
        },"A").start();

        while (i == 1){

        }
        System.out.println(Thread.currentThread().getName()+" \t i="+ i);
    }
}
