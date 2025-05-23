package edu.hw8.task1Test;

import edu.hw8.task1.server.ServerMain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование загрузки цитат из файла и работы ServerMain")
class QuotesLoadingTest {

    private static final String RESOURCE_PATH = "/hw8/phrases.txt";

    @Test
    @DisplayName("Загрузка цитат из файла и проверка содержимого")
    void testLoadQuotesFromFile() throws IOException {
        Map<String, String> quotes = ServerMain.loadQuotesFromFile(RESOURCE_PATH);

        assertFalse(quotes.isEmpty(), "Словарь цитат не должен быть пустым");

        assertEquals("Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами.",
                quotes.get("победа"), "Неверное значение для ключа 'победа'");

        assertEquals("Спасибо за оценку! Но твое мнение я оставлю без внимания — у меня аллергия на глупость.",
                quotes.get("мнение"), "Неверное значение для ключа 'мнение'");

        assertEquals("Ты так трогательно заботишься о моей самооценке… Жаль, что не о своей.",
                quotes.get("самооценка"), "Неверное значение для ключа 'самооценка'");

        assertEquals("Твои слова звучат так, будто их подбирал генератор случайных оскорблений.",
                quotes.get("генератор"), "Неверное значение для ключа 'генератор'");

        assertEquals(20, quotes.size(), "Неверное количество загруженных цитат. Ожидалось: 20");
    }

    @Test
    @DisplayName("Проверка наличия ключей в экземпляре ServerMain")
    void testQuotesInServerInstance() throws IOException {
        Map<String, String> quotes = ServerMain.loadQuotesFromFile(RESOURCE_PATH);
        ServerMain server = new ServerMain(8080, 5, quotes);

        String[] expectedKeys = {
                "победа", "мнение", "самооценка", "генератор",
                "интеллект", "остроумие", "деньги", "умно",
                "ошибка", "смысл", "тигр", "аргументы",
                "уверенность", "дуэль", "глупость", "ненависть",
                "предсказуемость", "шахматы", "рост", "интерес"
        };

        for (String key : expectedKeys) {
            assertTrue(server.quotes.containsKey(key), "Ключ '" + key + "' отсутствует в словаре цитат сервера");
        }
    }
}
