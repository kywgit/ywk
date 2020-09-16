package com.mycode.juc;

/**
 * @Author kyw
 * @Date 2020/9/13
 **/
//模拟cas算法
class CASTest{
  private int  value;

    //获取值
    public synchronized int getValue() {
        return value;
    }

    public synchronized boolean compareAndSwap(int epxctedValue ,int value) {
        //比较
        if(this.getValue() == epxctedValue){
            //赋值
            this.value = value;
            return true;
        }
       return false;
    }
}

public class CASDemo {
    public static void main(String[] args) {
       final  CASTest cast = new CASTest();
        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
               int expexted  = cast.getValue();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean bl =cast.compareAndSwap(expexted,(int)(Math.random()*100));
                    System.out.println(Thread.currentThread().getName()+"\t"+bl);
            }).start();
        }

    }
}
