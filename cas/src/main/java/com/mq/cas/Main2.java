package com.mq.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main2 {

    public static void main(String[] args) throws InterruptedException {
        final int count = 10;
        final CountDownLatch latch = new CountDownLatch(count);
        final Lock lock = new ReentrantLock();
        int balance = 0;
        final int amount = 1;
        final Main2 main = new Main2();

        // 模拟并发转账
        for (int i = 0; i < count; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    main.transferByLock(amount);
                    latch.countDown();
                }
            }).start();
        }

        // 等待所有转账完成
        latch.await();

        // 打印账户余额
        System.out.println(balance);
    }


    public void transferByLock(int amount){
        System.out.println(amount);
    }
}
