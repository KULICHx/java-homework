package edu.hw8.task3Test;

import edu.hw8.task3.cracker.SingleThreadCracker;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class SingleThreadCrackerTest {
    @Test
    void testCrackSimplePasswords() throws Exception {
        // Используем очень короткие пароли для теста
        Path testFile = Path.of("test_hashes.txt");
        Files.write(testFile, List.of(
            "user1 0cc175b9c0f1b6a831c399e269772661", // "a"
            "user2 92eb5ffee6ae2fec3ad71c777531578f"  // "b"
        ));

        SingleThreadCracker cracker = new SingleThreadCracker(testFile);
        Map<String, String> result = cracker.crack(1); // Ищем только пароли длины 1

        assertEquals(2, result.size());
        assertEquals("a", result.get("user1"));
        assertEquals("b", result.get("user2"));

        Files.delete(testFile);
    }

    @Test
    void testCrackWithMaxLength() throws Exception {
        Path testFile = Path.of("test_hashes.txt");
        Files.write(testFile, List.of(
            "user1 d8578edf8458ce06fbc5bb76a58c5ca4"
        ));

        SingleThreadCracker cracker = new SingleThreadCracker(testFile);
        Map<String, String> result = cracker.crack(1);

        assertTrue(result.isEmpty(),
            "Не должно находить пароли длиннее 1 символа при maxLength=1");

        Files.delete(testFile);
    }

    @Test
    void testCrackWithSmallLength() throws Exception {
        Path testFile = Path.of("test_hashes.txt");
        Files.write(testFile, List.of(
            "user1 37b51d194a7513e45b56f6524f2d51f2", // "bar" (3 символа)
            "user2 acbd18db4cc2f85cedef654fccc4a4d8"  // "foo" (3 символа)
        ));

        SingleThreadCracker cracker = new SingleThreadCracker(testFile);
        Map<String, String> result = cracker.crack(3); // Ограничиваем длину 3

        assertEquals(2, result.size());
        assertEquals("bar", result.get("user1"));
        assertEquals("foo", result.get("user2"));

        Files.delete(testFile);
    }
}
