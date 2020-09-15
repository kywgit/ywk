package com.mycode.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author kyw
 * @Date 2020/9/15
 **/
public class ConditionDemo {
    /**
     * A、B、C三个线程交替执行 10次 如：A、B、C、A、B、C
     *
     */
    public static void main(String[] args) {
        Alternate alternate = new Alternate();
        for (int i = 1; i <=3 ; i++) {
            final int index =i;
            new Thread(()->{
                for (int j = 1; j <=10 ; j++) {
                 /*   if (index==1){
                        alternate.alternateTestA(j);
                    }else if(index==2){
                        alternate.alternateTestB(j);
                    }else if(index==3){
                        alternate.alternateTestC(j);
                    }*/
                    alternate.alternateTest(index,j);
                }

            },Enumeration.get(i).toString()).start();
        }

    }
}

class Alternate{

    private int num = 1;//线程运行标志
    Lock lock = new ReentrantLock();
    Condition conditionA =lock.newCondition();
    Condition conditionB =lock.newCondition();
    Condition conditionC =lock.newCondition();

    public void alternateTest(int num,int total){
        lock.lock();
        try{
            if (this.num != num){
                try {
                    if (num ==1){
                        conditionA.await();
                    }else if(num ==2){
                        conditionB.await();
                    }else{
                        conditionC.await();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 1; i <=1 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t "+total);
            }


            if (num ==1){
                this.num = 2;
                conditionB.signal();
            }else if(num==2){
                this.num = 3;
                conditionC.signal();
            }else{
                this.num = 1;
                conditionA.signal();
            }

        }finally {
            lock.unlock();
        }

    }




}
//枚举类
enum  Enumeration{
    A,B,C;
    public static Enumeration get(int i){
        switch (i){
            case 1:
                return Enumeration.A;
            case 2:
                return Enumeration.B;
            case 3:
                return Enumeration.C;
        }
       return null;
    }
}