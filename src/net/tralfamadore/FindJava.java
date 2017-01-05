package net.tralfamadore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Class: FindJava
 * Created by billreh on 1/4/17.
 */
public class FindJava {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<File> files = forkJoinPool.invoke(new FindJavaTask("/Users/billreh/IdeaProjects/"));
        System.out.println(files.size() + " java files");
        System.out.println(forkJoinPool.invoke(new ReadLinesTask(files)) + " lines");
        forkJoinPool.shutdown();
    }
}

class ReadLinesTask extends RecursiveTask<Long> {
    private final List<File> files;

    ReadLinesTask(List<File> files) {
        this.files = files;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if(files.size() > 10) {
            ReadLinesTask left = new ReadLinesTask(files.subList(0, files.size() / 2));
            ReadLinesTask right = new ReadLinesTask((files.subList(files.size() / 2, files.size())));
            left.fork();
            right.fork();
            sum += left.join();
            sum += right.join();
        } else {
            try {
                for(File file : files) {
                    sum += Files.readAllLines(file.toPath()).size();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sum;
    }
}

class FindJavaTask extends RecursiveTask<List<File>> {
    private final String path;
    private final List<FindJavaTask> tasks = new ArrayList<>();
    private final List<File> files = new ArrayList<>();

    FindJavaTask(String path) {
        this.path = path;
    }

    @Override
    protected List<File> compute() {
        File file = new File(path);
        if(!file.isDirectory())
            return new ArrayList<>();
        //noinspection ConstantConditions
        for(File f : file.listFiles()) {
            if(f.isDirectory()) {
                FindJavaTask task = new FindJavaTask(f.getAbsolutePath());
                task.fork();
                tasks.add(task);
            } else {
                if(f.getName().endsWith(".java"))
                    files.add(f);
            }
        }
        for(FindJavaTask task : tasks) {
            files.addAll(task.join());
        }
        return files;
    }
}
