package com.mycode.juc;

/**
 * @Author kyw
 * @Date 2020/9/14
 **/

import sun.awt.windows.ThemeReader;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程同步方式：
 * 1、synchronized 同步代码块
 * 2、synchronized 同步方法
 * 3、 lock 同步锁机制
 */

class Ticket {
    private volatile AtomicInteger atomicInteger = new AtomicInteger(10);
    Lock lock = new ReentrantLock();

    public void buyTickets() {
        while (atomicInteger.get()>0){
            lock.lock();
            try{
            if (atomicInteger.get()>0){
                System.out.println(Thread.currentThread().getName()+"号窗口正在买票，\t余票为"+atomicInteger.decrementAndGet()+"张");
            }
            }finally {
                lock.unlock();
            }
        }
    }
}
public class LookDemo {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        for (int i = 1; i<=10;i++){
            new Thread(()->{
                ticket.buyTickets();
            },String.valueOf(i)).start();
        }
    }
}
