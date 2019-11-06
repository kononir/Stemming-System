package com.bsuir.stemsys.data.reader.text;

import com.bsuir.stemsys.api.data.DocumentReader;

import java.io.*;

public class TextDocumentReader implements DocumentReader {
    public String read(String path) {
        StringBuilder textBuilder = new StringBuilder();

        File file = new File(path);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String firstLine = bufferedReader.readLine();
            if (firstLine != null) {
                textBuilder.append(firstLine);

                String nextLine = bufferedReader.readLine();
                while (nextLine != null) {
                    textBuilder.append('\n');
                    textBuilder.append(nextLine);

                    nextLine = bufferedReader.readLine();
                }
            }
        } catch (IOException e) {
            throw new TextDocumentReaderException("Reading problems", e);
        }

        return textBuilder.toString();
    }
}
