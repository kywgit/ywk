package com.mycode.juc;

/**
 * @Author kyw
 * @Date 2020/9/14
 **/

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLath :闭锁，在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才会继续执行
 */
class LathDemo implements Runnable{

    private CountDownLatch latch;
    public LathDemo(CountDownLatch latch){
        this.latch= latch;
    }
    @Override
    public void run() {
        for (int i = 1; i <= 2; i++) {
            if(i%2 == 0)
                System.out.println(Thread.currentThread().getName()+" \t"+i);
        }
        this.latch.countDown();
    }
}
public class CountDownLathDemo {

    public static void main(String[] args) {
        CountDownLatch countDownLath= new CountDownLatch(10);
        LathDemo lathDemo= new LathDemo(countDownLath);
        try {
            long start = System.currentTimeMillis();
            for (int i = 1; i <=10 ; i++) {
                new Thread(lathDemo).start();
            }

            countDownLath.await();
            long end = System.currentTimeMillis();
            System.out.println("所用的时间"+ (end - start));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
