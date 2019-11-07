package com.bsuir.stemsys.data.parser;

import com.bsuir.stemsys.api.data.DocumentParser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class WordsParser implements DocumentParser {

    @Override
    public List<String> parse(String text) {
        List<String> parsed;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(
                                text.getBytes(StandardCharsets.UTF_8))))) {
            parsed = new ArrayList<>();
            String line;
            Pattern pattern = Pattern.compile("([^\\w]|\\d)+", Pattern.UNICODE_CHARACTER_CLASS);
            while ((line = reader.readLine()) != null) {
                String[] words = pattern.split(line);
                for (String word : words) {
                    if (!"".equals(word)) {
                        parsed.add(word);
                    }
                }
            }
        } catch (IOException e) {
            throw new WordsParserException("Error when parse text: '" + text + "'", e);
        }
        return parsed;
    }
}
