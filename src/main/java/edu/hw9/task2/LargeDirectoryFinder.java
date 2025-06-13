package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

public class LargeDirectoryFinder extends RecursiveTask<List<Path>> {
    private final Path dir;
    private static final int THRESHOLD = 1000;

    public LargeDirectoryFinder(Path dir) {
        this.dir = dir;
    }

    @Override
    protected List<Path> compute() {
        List<Path> result = new ArrayList<>();
        List<LargeDirectoryFinder> subTasks = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    LargeDirectoryFinder task = new LargeDirectoryFinder(entry);
                    task.fork();
                    subTasks.add(task);
                }
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }

        try (Stream<Path> files = Files.list(dir)) {
            long count = files.count();
            if (count > THRESHOLD) {
                result.add(dir);
            }
        } catch (IOException e) {

        }

        for (LargeDirectoryFinder task : subTasks) {
            result.addAll(task.join());
        }

        return result;
    }
}
