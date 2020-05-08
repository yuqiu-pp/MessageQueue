package com.mq.cas;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private Lock lock = new ReentrantLock();
    private int balance = 0;
    private final int amount = 1;
    private CountDownLatch latch;
    private AtomicInteger atom = new AtomicInteger(this.balance);



    public void latchDown() {
        this.latch.countDown();
    }

    // 锁机制
    public void transferByLock(){
        lock.lock();
        this.balance += this.amount;
        lock.unlock();
        System.out.println(this.balance);
    }

    // CAS
    public void transferByCas(){
        for (;;){
            int curr = getBalance();
            int next = curr + getAmount();
            if (getAtom().compareAndSet(curr, next)){
                break;
            }
        }
        System.out.println(this.balance);

    }

    public static void main(String[] args) throws InterruptedException {
        Main m = new Main();

        final int count = 10;
        m.latch = new CountDownLatch(count);

        // 模拟并发转账
        for (int i = 0; i < count; i++) {
            new Thread(new TreadTransfer(m)).start();
        }

        // 等待所有转账完成
        // try {
        //     Thread.sleep(1);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        m.latch.await();

        // 打印账户余额
        System.out.println(m.balance);
    }


    public int getBalance() {
        return balance;
    }

    public int getAmount() {
        return amount;
    }

    public AtomicInteger getAtom() {
        return atom;
    }
}


class TreadTransfer implements Runnable{

    private Main main;

    public TreadTransfer(Main main){
        this.main = main;
    }

    @Override
    public void run() {
        // main.transferByLock();
        // main.transferByCas();
        main.latchDown();
    }
}
