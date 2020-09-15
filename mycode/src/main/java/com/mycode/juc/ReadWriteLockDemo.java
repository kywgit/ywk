package com.mycode.juc;

/**
 * @Author kyw
 * @Date 2020/9/15
 **/

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *  ReadWriteLock 读写锁
 *  读写、写写 需要互斥
 *  读读 不需要互斥
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
      ReadWriteLockTest rwlt = new ReadWriteLockTest();
        for (int i = 1; i <=2 ; i++) {
            final int tem = i;
            new Thread(()->{
               rwlt.put(String.valueOf(tem).toString(),(int)(Math.random()*100));
               System.out.println(Thread.currentThread().getName()+"\t write");
            }).start();
        }
        for (int i = 1; i <=20 ; i++) {
            new Thread(()->{
               System.out.println(Thread.currentThread().getName()+" "+rwlt.getValue("1"));
            }).start();
        }
    }
}

class ReadWriteLockTest{
    private Map hashMap = new HashMap<String,Object>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public Object put(String key,Object obj){
        readWriteLock.writeLock().lock();
        try{
          return hashMap.put(key,obj);

        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public Object getValue(String key){
        readWriteLock.readLock().lock();
        try{
            return hashMap.get(key);
        }finally {
            readWriteLock.readLock().unlock();
        }

    }
}
