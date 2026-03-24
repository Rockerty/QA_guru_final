package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JsonParseTest {

    @Test
    public void testParseJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream jsonStream = getClass().getClassLoader().getResourceAsStream("datafiles/Classic books.json");
        List<Map<String, Object>> books = mapper.readValue(jsonStream, new TypeReference<List<Map<String, Object>>>() {});

        assertEquals(3, books.size());

        Map<String, Object> firstBook = books.get(0);
        assertEquals(1, firstBook.get("id"));
        assertEquals("Война и мир", firstBook.get("title"));
        assertEquals("Лев Толстой", firstBook.get("author"));
        assertEquals(1869, firstBook.get("year"));
        assertEquals(false, firstBook.get("isBoring"));

        // 6. Проверяем вторую книгу
        Map<String, Object> secondBook = books.get(1);
        assertEquals("Преступление и наказание", secondBook.get("title"));
    }

    @Test
    public void jsonWithJsonPathTest() throws Exception {
        InputStream jsonStream = getClass().getClassLoader().getResourceAsStream("datafiles/Classic books.json");
        String jsonString = new String(jsonStream.readAllBytes());

        String title = JsonPath.read(jsonString, "$[2].title");
        assertEquals("Мастер и Маргарита", title);

        String author = JsonPath.read(jsonString, "$[?(@.id == 3)].author").toString();
        assertTrue(author.contains("Булгаков"));

        int count = JsonPath.read(jsonString, "$.length()");
        assertEquals(3, count);
    }
}