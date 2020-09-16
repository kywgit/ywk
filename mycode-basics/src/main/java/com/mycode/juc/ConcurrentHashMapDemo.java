package com.mycode.juc;

/**
 * @Author kyw
 * @Date 2020/9/13
 **/

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ConcurrentHashMap采用的是“锁分段”机制，默认有16个段
 *对与多线程的操作，介于 HashMap 与 Hashtable 之间。内部采用“锁分段”
 * 机制替代 Hashtable 的独占锁。进而提高性能.
 *运行结果： 不是每次运行结果都都会出错
 * ConcurrentHashMap: 10	{1=Thread-0, 2=Thread-1, 3=Thread-2, 4=Thread-3, 5=Thread-4, 6=Thread-5, 7=Thread-6, 8=Thread-7, 9=Thread-8, 10=Thread-9}
 * Map: 9	{1=Thread-0, 3=Thread-2, 4=Thread-3, 5=Thread-4, 6=Thread-5, 7=Thread-6, 8=Thread-7, 9=Thread-8, 10=Thread-9}
 */
public class ConcurrentHashMapDemo {

    public static void main(String[] args) {
        Map map= new HashMap<>();

        ConcurrentHashMap chm = new ConcurrentHashMap();
        for (int i=1;i<=10;i++){
            final int temp = i;
            new Thread(()->{
                map.put(String.valueOf(temp),Thread.currentThread().getName());
                chm.put(String.valueOf(temp),Thread.currentThread().getName());
            }).start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ConcurrentHashMap: "+ chm.size()+"\t"+chm.toString());
        System.out.println("Map: "+ map.size()+"\t"+map.toString());
    }
}
