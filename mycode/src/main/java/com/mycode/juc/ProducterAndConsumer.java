package com.mycode.juc;

/**
 * @Author kyw
 * @Date 2020/9/15
 **/

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者、消费者模式
 */
public class ProducterAndConsumer {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Producter producter = new Producter(clerk);
        Consumer consumer = new Consumer(clerk);
        new Thread(producter,"生产者").start();
        new Thread(consumer,"消费者").start();
    }
}
class Producter implements Runnable{
    private Clerk clerk;

    public  Producter (Clerk clerk){
        this.clerk = clerk;
    }
    @Override
    public void run() {
        for (int i = 0; i <20 ; i++) {
            clerk.getProductA();
        }

    }
}

class Consumer implements Runnable{

    private Clerk clerk;
    public Consumer(Clerk clerk){
        this.clerk = clerk;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i <20 ; i++) {
            clerk.saleA();
        }
    }
}

/**
 * Lock 和 synchronized的区别：
 * 1、原始构成 synchronized是jvm层面的关键字，Lock是一个具体的类，是api层面的锁。
 * 2、使用方法 synchronized不需要用户自己去释放锁，Lock需要用户自己释放锁。
 * 3、等待是否可中断 synchronized不可中断除非抛出异常，或者正常运行完成
 *     ReentrantLock 可以中断，1.设置超时方法tryLock(long timeout,TimeUnit unit)
 *                         2。lockInterruptibly()放代码块中，调用interrupy()方法可中断
 * 4、加锁是否公平 synchronized 是非公平锁
 *      ReentrantLock 两者皆可，构造方法可以传入一个boolear值，true 表示公平锁，false 表示非公平锁。默认是非公平锁；
 *
 */
class Clerk{

    private int product = 0;
    private int productMax = 1;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    //Condition conditionC = lock.newCondition();

    public synchronized void getProduct(){
        while(product >=productMax){ //使用while循环判断 防止虚假唤醒
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("满货！");
        }
            this.notify();
        System.out.println(Thread.currentThread().getName()+" : "+ ++product);
    }
    public  void getProductA(){
        lock.lock();
        try{
            while(product >=productMax){ //使用while循环判断 防止虚假唤醒
                try {
                    System.out.println("满货！");
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(Thread.currentThread().getName()+" : "+ ++product);
           condition.signal();

        }finally {
            lock.unlock();
        }

    }


    public synchronized void sale(){
        while (product <= 0) { //使用while循环判断 防止虚假唤醒
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("缺货！");
        }
            this.notify();
        System.out.println(Thread.currentThread().getName()+" : "+ --product);
    }

    public  void saleA(){
        lock.lock();
        try{
            while (product <= 0) { //使用while循环判断 防止虚假唤醒
                try {
                    System.out.println("缺货！");
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(Thread.currentThread().getName()+" : "+ --product);
            condition.signal();

        }finally {
            lock.unlock();
        }

    }
}