package com.bsuir.stemsys.service;

import com.bsuir.stemsys.api.service.StopwordsService;
import com.bsuir.stemsys.data.read.JSONReader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StopwordsServiceImpl implements StopwordsService {
    private static final String ENGLISH_FILE = "english_stopwords.json";
    private static final String FRENCH_FILE = "french_stopwords.json";

    private Set<String> stopwords;

    private StopwordsServiceImpl(Set<String> stopwords) {
        this.stopwords = stopwords;
    }

    public static StopwordsServiceImpl english() {
        return new StopwordsServiceImpl(new HashSet<>(new JSONReader().read(ENGLISH_FILE)));
    }

    public static StopwordsServiceImpl french() {
        return new StopwordsServiceImpl(new HashSet<>(new JSONReader().read(FRENCH_FILE)));
    }

    @Override
    public List<String> excludeStopwords(List<String> allWords) {
        List<String> excluded = new ArrayList<>(allWords);
        return excluded.stream()
                .filter(word -> !containsIgnoreCase(word))
                .collect(Collectors.toList());
    }

    private boolean containsIgnoreCase(String word) {
        return stopwords.stream()
                .anyMatch(s -> s.equalsIgnoreCase(word));
    }
}
