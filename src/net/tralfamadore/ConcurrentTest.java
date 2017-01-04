package net.tralfamadore;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Class: ConcurrentTest
 * Created by billreh on 12/25/16.
 */
public class ConcurrentTest {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        Producer producer = new Producer(queue, "producer 1", "Payload");
        Producer producer2 = new Producer(queue, "producer 2", "Payload 2");
        Producer producer3 = new Producer(queue, "producer 3", "Payload 3");
        Consumer consumer = new Consumer(queue, "consumer 1");
        Consumer consumer2 = new Consumer(queue, "consumer 2");
        Consumer consumer3 = new Consumer(queue, "consumer 3");
        new Thread(producer).start();
        new Thread(producer2).start();
        new Thread(producer3).start();
        new Thread(consumer).start();
        new Thread(consumer2).start();
        new Thread(consumer3).start();
    }
}

class Producer implements Runnable {
    private final BlockingQueue<String> queue;
    private final String name;
    private final String product;

    Producer(BlockingQueue<String> queue, String name, String product) {
        this.queue = queue;
        this.name = name;
        this.product = product;
    }

    @Override
    public void run() {
        System.out.println("Running thread " + name);
        try {
            Thread.sleep(1000);
            System.out.println("Inserting " + product);
            queue.put(product);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<String> queue;
    private final String name;

    Consumer(BlockingQueue<String> queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Running thread " + name);
        try {
            String product = queue.take();
            System.out.println("Received " + product);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
