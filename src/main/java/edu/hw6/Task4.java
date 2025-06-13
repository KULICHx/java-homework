package edu.hw6;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;


public class Task4 {
    private Task4() {
    }

    private static final Logger LOGGER = Logger.getLogger(Task4.class.getName());

    public static void createDecoratedFileWriter(Path path) {
        try (OutputStream fileStream = Files.newOutputStream(path);
             CheckedOutputStream checkedOutputStream = new CheckedOutputStream(fileStream, new CRC32());
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream,
                 StandardCharsets.UTF_8);
             PrintWriter printWriter = new PrintWriter(outputStreamWriter)) {

            printWriter.print("Programming is learned by writing programs. ― Brian Kernighan\n");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed", e);
        }
    }
}
