package net.tralfamadore;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class: IntAdder
 * Created by billreh on 12/25/16.
 */
public class IntAdder {
    private static Random rand = new Random();

    public static void main(String[] args) {
        List<Integer> list = Stream.generate(() -> rand.nextInt()).limit(100000).collect(Collectors.toList());
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println("Pool size: " + forkJoinPool.getParallelism());
        long sum = 0;
        long start;
        long stop;

        // Iterate
        start = System.nanoTime();
        for(int i = 0; i < 10000; i++)
            sum = iterate(list);
        stop = System.nanoTime();
        System.out.println("Iterate time:\t\t\t" + (stop - start));
        System.out.println("Answer: " + sum);

        // Fork Join
        start = System.nanoTime();
        for(int i = 0; i < 10000; i++)
            sum = forkJoinPool.invoke(new IntAddTask(list));
        stop = System.nanoTime();
        System.out.println("Fork Join time:\t\t\t" + (stop - start));
        System.out.println("Answer: " + sum);

        // Stream
        start = System.nanoTime();
        for(int i = 0; i < 10000; i++)
            sum = list.stream().mapToLong(Integer::intValue).sum();
        stop = System.nanoTime();
        System.out.println("Stream time:\t\t\t" + (stop - start));
        System.out.println("Answer: " + sum);

        // Parallel Stream
        start = System.nanoTime();
        for(int i = 0; i < 10000; i++)
            sum = list.parallelStream().mapToLong(Integer::intValue).sum();
        stop = System.nanoTime();
        System.out.println("Parallel Stream time:\t" + (stop - start));
        System.out.println("Answer: " + sum);
    }

    private static long iterate(List<Integer> list) {
        long sum = 0;
        for(Integer i : list)
            sum += i;
        return sum;
    }
}

class IntAddTask extends RecursiveTask<Long> {
    private List<Integer> list;

    IntAddTask(List<Integer> list) {
        this.list = list;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if(list.size() > 753) {
            IntAddTask left = new IntAddTask(list.subList(0, list.size() / 2));
            IntAddTask right = new IntAddTask(list.subList(list.size() / 2, list.size()));
            left.fork();
            right.fork();
            sum += left.join();
            sum += right.join();
            return sum;
        } else {
            for(Integer i : list)
                sum += i;
            return sum;
        }
    }
}
