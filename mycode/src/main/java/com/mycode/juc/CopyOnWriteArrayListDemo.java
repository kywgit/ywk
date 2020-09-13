package com.mycode.juc;

/**
 * @Author kyw
 * @Date 2020/9/13
 **/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 集合类不安全问题
 * new ArrayList() 默认长度是10；扩容是原长度的一半; 高并发情况下会报错 java.util.ConcurrentModificationException
 *解决方案：
 * 1、使用 Vector
 * 2、使用   Collections.synchronizedList(new ArrayList<>());
 * 3、使用 CopyOnWriteArrayList（写时复制）
 * CopyOnWriteArrayList 写时复制原理 （读写分离）
 * CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器Object[]添加。而是先将当前容器Object[]进行copy
 * 复制出一个新的容器Object[] newElements,然后新的容器Object[] newElements里面添加元素，添加完元素之后，
 * 再将元容器的引用指向新的容器setArray(newElements);这样做的好处是可以对CopyOnWrite容器进行并发的读，
 * 而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器
 * 源码如下：
 *  ReentrantLock默认创建一个非公平锁，构造方法中可以传入一个boolear值 ，true创建一个公平锁，false 表示创建一个非公平锁 synchronized 是非公平锁
 *  公平锁：公平锁是指多个线程按照申请锁的顺序来获取锁，类似排队打饭，先来后到
 *  非公平锁：非公平锁是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁在高并发的情况下，有可能会造成优先级反转或者饥饿现象
 *  public boolean add(E e) {
 *         final ReentrantLock lock = this.lock;
 *         lock.lock();
 *         try {
 *             Object[] elements = getArray();
 *             int len = elements.length;
 *             Object[] newElements = Arrays.copyOf(elements, len + 1);
 *             newElements[len] = e;
 *             setArray(newElements);
 *             return true;
 *         } finally {
 *             lock.unlock();
 *         }
 *     }
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {
        //List list = new ArrayList();
        //Vector list = new Vector();
        //List list = Collections.synchronizedList(new ArrayList<>());
        CopyOnWriteArrayList list= new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30 ; i++) {
            new Thread(()->{
                list.add((int)(Math.random()*100));
                System.out.println(list.toString());
            },String.valueOf(i)).start();
        }

    }
}
