package com.mycode.juc;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author kyw
 * @Date 2020/9/12
 **/

/**
 * 1、i++的原子问题，i++的 操作实际上是3个步骤“读-改-写”
 * 2、原子变量：jdk1.5后java.util.comcurrent.atomic 包下提供了常用的原子变量
 *     1.volatile 保证内存可见性
 *     2.CAS 算法保证了数据的原子性
 *      CAS算法是硬件对于并发操作共享数据的支持
 *      CAS包含了三个操作数： 内存值v 预估值a 更新值b
 *      当且仅当v==a时，将b替换v，否则将不做任何操作。
 *     3.unsafe类 native修饰的本地方法
 */
class AtomicDemo implements Runnable{
        private volatile int j = 0;
        //Integer包装类的原子类 能保证i++的原子性
        AtomicInteger integer = new AtomicInteger();
    @Override
    public void run() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //i++;
       // System.out.println("i = "+ getInt());
        System.out.println("integer = "+ integer.incrementAndGet());
    }
    public int getInt (){
        return j++;
    }
}
public class VolatileDemo {
    /**
     * volatile 关键字 是java提供的一种同步方式， 是一种轻量级的同步机制
     *三大特性：1、可见性；2不保证原子性；3、禁止指令重排；
     * JMM : java内存模型，是一种抽象的概念并不真实存在，是一组规则或规范，通过这组规范定义了程序中变量的访问方式。
     * JMM同步规定
     * 1、线程解锁钱，必须把共享变量的值刷新回主内存‘
     * 2、线程加锁前，必须读取主内存的最新值到自己的工作内存
     * 3、加锁解锁是同一把锁
     * 在多线程操作共享变量时；线程会先从主内存中将变量拷贝到自己的工作内存中，进行操作，操作完成之后在刷回主内存，线程之间是不通信的；
     */
    private static  volatile int i = 1;

    public static void main(String[] args) {
       // System.out.println(Integer.MAX_VALUE);
       // volatieTest();
        AtomicDemo ad =new AtomicDemo();
        for (int j = 0; j < 10; j++) {
            new Thread(ad).start();
        }
    }
    public static void volatieTest(){
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
