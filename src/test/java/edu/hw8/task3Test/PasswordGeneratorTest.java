package edu.hw8.task3Test;

import edu.hw8.task3.generator.PasswordGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {
    @Test
    void testConstructorThrowsForInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> new PasswordGenerator(0));
        assertThrows(IllegalArgumentException.class, () -> new PasswordGenerator(7));
    }

    @Test
    void testPasswordGenerationLength1() {
        PasswordGenerator generator = new PasswordGenerator(1);
        char[] charset = PasswordGenerator.getCharset();

        for (char c : charset) {
            assertTrue(generator.hasNext());
            assertEquals(String.valueOf(c), generator.nextPassword());
        }

        assertFalse(generator.hasNext());
        assertThrows(IllegalStateException.class, generator::nextPassword);
    }

    @Test
    void testPasswordGenerationLength2() {
        PasswordGenerator generator = new PasswordGenerator(2);
        assertEquals("a", generator.nextPassword()); // a
        assertEquals("b", generator.nextPassword()); // b
        for (int i = 2; i < PasswordGenerator.getCharset().length - 1; i++) {
            generator.nextPassword();
        }
        assertEquals("9", generator.nextPassword()); // 9
        assertEquals("aa", generator.nextPassword()); // aa
    }

    @Test
    void testHasNext() {
        PasswordGenerator generator = new PasswordGenerator(1);
        assertTrue(generator.hasNext());
        for (int i = 0; i < PasswordGenerator.getCharset().length; i++) {
            generator.nextPassword();
        }
        assertFalse(generator.hasNext());
    }
}
