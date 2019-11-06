package com.bsuir.stemsys.data.parser;

import com.bsuir.stemsys.api.data.DocumentParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class HtmlParser implements DocumentParser {
    private DocumentParser wordsParser;

    public HtmlParser(DocumentParser wordsParser) {
        this.wordsParser = wordsParser;
    }

    public List<String> parse(String text) {
        Document doc = Jsoup.parse(text);
        List<String> words;

        String titleText = doc.title();
        words = new ArrayList<>(wordsParser.parse(titleText));

        String bodyText = doc.body().text();
        words.addAll(wordsParser.parse(bodyText));

        return words;
    }
}