package store.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import store.exception.ErrorMessage;
import store.exception.StoreException;

public class FileLoader {
    private final static String FILE_NAME = "";



    private static List<String> loadMarkdownFile(String fileName) {
        try (InputStream inputStream = FileLoader.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            return reader.lines().collect(Collectors.toList());

        } catch (IOException e) {
            throw new StoreException(ErrorMessage.INVALID_FILE_CONTENT);
        }
    }
}
