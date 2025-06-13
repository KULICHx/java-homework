package edu.hw6.task3;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.regex.Pattern;

public interface AbstractFilter extends DirectoryStream.Filter<Path> {

    AbstractFilter REGULAR_FILE = Files::isRegularFile;
    AbstractFilter READABLE = Files::isReadable;

    default AbstractFilter and(AbstractFilter other) {
        return path -> this.accept(path) && other.accept(path);
    }

    static AbstractFilter largerThan(long size) {
        return path -> {
            try {
                return Files.size(path) > size;
            } catch (IOException | SecurityException e) {
                return false;
            }
        };
    }

    static AbstractFilter magicNumber(int... expectedBytes) {
        return path -> {
            try (InputStream is = Files.newInputStream(path)) {
                for (int expectedByte : expectedBytes) {
                    if (is.read() != expectedByte) {
                        return false;
                    }
                }
                return true;
            } catch (IOException | SecurityException e) {
                return false;
            }
        };
    }

    static AbstractFilter globMatches(String glob) {
        return path -> {
            try {
                if (path == null || !Files.exists(path)) {
                    return false;
                }
                Path filename = path.getFileName();
                if (filename == null) {
                    return false;
                }
                PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
                return matcher.matches(filename);
            } catch (Exception e) {
                return false;
            }
        };
    }

    static AbstractFilter regexContains(String regex) {
        return path -> {
            try {
                if (path == null || !Files.exists(path)) {
                    return false;
                }
                Path fileNamePath = path.getFileName();
                if (fileNamePath == null) {
                    return false;
                }
                return Pattern.compile(regex).matcher(fileNamePath.toString()).find();
            } catch (Exception e) {
                return false;
            }
        };
    }
}
