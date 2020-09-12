package com.mycode.juc;


import java.util.concurrent.*;

/**
 * @Author kyw
 * @Date 2020/9/12
 **/

 class MyThread extends  Thread{

    @Override
    public void run() {
        System.out.println("我是继承Thread创建线程");
    }
}
  class MyThread2 implements Runnable{

    @Override
    public void run() {
        System.out.println("我是实现Runnable创建线程");
    }
}
  class MyThread3 implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("我是实现Callable创建线程");
        return 12;
    }
}


public class ThreadDemo {


    /**
     * 创建线程的方式（4种）
     * 1、继承thread类
     * 2、实现Runable接口
     * 3、实现callable接口
     * 4、线程池
     *
     */

    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        //1、继承Threadle类
        MyThread f = new MyThread();
        Thread t1 = new Thread(f);
        t1.start();
        //2、实现Runable 接口
        MyThread2 f2 = new MyThread2();
        Thread t2 = new Thread(f2);
        t2.start();
        //3、实现Callable接口
        MyThread3 f3 = new  MyThread3();
        Thread t3 = new Thread(new FutureTask(f3));
        t3.start();
        //4、线程池方式
        ExecutorService threadPool1 = Executors.newScheduledThreadPool(2);
        ExecutorService threadPool2 = Executors.newFixedThreadPool(3);
        ExecutorService threadPool3 = Executors.newSingleThreadExecutor();
        ExecutorService threadPool4 = Executors.newCachedThreadPool();
        /**
         * Executors提供的4种线程池都存在隐患，在底层都是通过ThreadPoolExecutor()类实现线程池
         * newScheduledThreadPool 和 newCachedThreadPool 实现线程池时最大线程数是Integer.MAX_VALUE，Integer.MAX_VALUE=2147483647,请求过多的时会创建大量的线程导致OOM
         * newFixedThreadPool 和 newSingleThreadExecutor 实现线程池时传入的阻塞队列 LinkedBlockingQueue<Runnable>(),而LinkedBlockingQueue是一个有界队列，而界限的最大值是Integer.MAX_VALUE，
         * MAX_VALUE=2147483647,请求过多的时会堆积大量请求到阻塞队列中导致OOM
         */
        /**
         * 现实公司使用线程池都是自定义的
         *  自定义线程池
         *  合理配置核心线程数：
         *  通过 Runtime.getRuntime().availableProcessors()获取服务器核心数
         *  cpu类型：核心线程数=cpu核心+1
         *  io 类型：第一种： 核心线程数=cpu核心*2
         *  第二种：核心线程数 = cpu核心数/（1-阻塞系数） 阻塞系数（0.8-0.9）
         *  ThreadPoolExecutor的7大参数：
         *  1、核心线程数
         *  2、最大线程数
         *  3、空闲时线程的存活时间
         *  4、空闲时线程的存活时间单位
         *  5、请求的阻塞队列
         *  6、线程工厂（默认）
         *  7、拒绝策略（4种，默认是AbortPolicy）
         *      ①AbortPolicy ：直接抛出RejectedExecutionException异常阻止系统正常运行。
         *      ②CallerRunsPolicy:"调用者运行"一种调节机制，该策略既不会抛弃任务，也不会抛出异常，二十将某些任务回退给调用者，从而降低新任务的流量。
         *      ③DiscardOldestPolicy:抛弃队列中等待最久的任务，然后把当前任务加入队列中尝试再次提交当前任务。
         *      ④DiscardPolicy:直接丢弃任务，不予任何处理也不抛出异常。如果允许任务丢失，这是最好的一种方案。
         */

        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                1L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(3) ,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 1; i <= 8; i++) {
            final int temp = i;
            threadPool.execute(()->{
                System.out.println(Thread.currentThread().getName()+"\t 执行任务"+temp);
            });
        }
    }
}


