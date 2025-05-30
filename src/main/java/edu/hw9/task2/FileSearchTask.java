package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public class FileSearchTask extends RecursiveTask<List<Path>> {
    private final Path dir;
    private final Predicate<Path> predicate;

    public FileSearchTask(Path dir, Predicate<Path> predicate) {
        this.dir = dir;
        this.predicate = predicate;
    }

    @Override
    protected List<Path> compute() {
        List<Path> result = new ArrayList<>();
        List<FileSearchTask> subTasks = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    FileSearchTask task = new FileSearchTask(entry, predicate);
                    task.fork();
                    subTasks.add(task);
                } else if (predicate.test(entry)) {
                    result.add(entry);
                }
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }

        for (FileSearchTask task : subTasks) {
            result.addAll(task.join());
        }

        return result;
    }
}
