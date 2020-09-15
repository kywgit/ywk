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