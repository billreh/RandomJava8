package net.tralfamadore;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Class: ForkJoinTest
 * Created by billreh on 12/25/16.
 */
public class ForkJoinTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Hello", "there", "how", "are", "you", "doing", "today", "I", "am", "fine",
                "thank", "you", "very", "much");
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int result = forkJoinPool.invoke(new ProcessListTask(list));
        System.out.println(result);
        System.out.println(list.stream().mapToInt(String::length).sum());
    }
}

class ProcessListTask extends RecursiveTask<Integer> {
    private List<String> list;

    ProcessListTask(List<String> list) {
        this.list = list;
    }

    @Override
    protected Integer compute() {
        int sum = 0;

        if(list.size() > 2) {
            ProcessListTask left = new ProcessListTask(list.subList(0, list.size() / 2));
            ProcessListTask right = new ProcessListTask(list.subList(list.size() / 2, list.size()));
            left.fork();
            right.fork();
            sum += left.join();
            sum += right.join();
            return sum;
        } else {
            for(String s : list)
                sum += s.length();
            return sum;
        }
    }
}
