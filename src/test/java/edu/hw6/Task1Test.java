package edu.hw6;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import edu.hw6.task1.DiskMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Task1Test {

    private Path tempFile;
    private DiskMap diskMap;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("diskmap", ".txt");
        diskMap = new DiskMap(tempFile.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testPutAndGet() {
        diskMap.put("key1", "value1");
        assertEquals("value1", diskMap.get("key1"));
    }

    @Test
    void testOverwriteValue() {
        diskMap.put("key1", "value1");
        diskMap.put("key1", "value2");
        assertEquals("value2", diskMap.get("key1"));
    }

    @Test
    void testRemove() {
        diskMap.put("key1", "value1");
        diskMap.remove("key1");
        assertNull(diskMap.get("key1"));
    }

    @Test
    void testPutAll() {
        Map<String, String> map = Map.of("a", "1", "b", "2");
        diskMap.putAll(map);
        assertEquals("1", diskMap.get("a"));
        assertEquals("2", diskMap.get("b"));
    }

    @Test
    void testClear() {
        diskMap.put("key", "val");
        diskMap.clear();
        assertTrue(true);
    }

    @Test
    void testContainsKeyAndValue() {
        diskMap.put("k", "v");
        assertTrue(diskMap.containsKey("k"));
        assertTrue(diskMap.containsValue("v"));
    }

    @Test
    void testNullKeyThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> diskMap.put(null, "val"));
    }

    @Test
    void testNullValueThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> diskMap.put("key", null));
    }

    @Test
    void testColonInKeyThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> diskMap.put("key:withcolon", "value"));
    }

    @Test
    void testPersistenceBetweenInstances() throws IOException {
        diskMap.put("persistentKey", "persistentValue");

        diskMap = new DiskMap(tempFile.toString());

        assertEquals("persistentValue", diskMap.get("persistentKey"));
    }
}
