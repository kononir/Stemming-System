package com.bsuir.stemsys.service;

import com.bsuir.stemsys.api.service.LanguageDefinerService;
import com.bsuir.stemsys.data.reader.JSONReader;
import com.bsuir.stemsys.model.Language;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FrequencyLanguageDefinerService implements LanguageDefinerService {
    private static final String ENGLISH_FILE = "english_frequent_words.json";
    private static final String FRENCH_FILE = "french_frequent_words.json";

    private Set<String> englishWords = new HashSet<>(new JSONReader().read(ENGLISH_FILE));
    private Set<String> frenchWords = new HashSet<>(new JSONReader().read(FRENCH_FILE));

    @Override
    public Language defineLanguage(List<String> textWords) {
        int englishFound = 0;
        int frenchFound = 0;
        for (String word : textWords) {
            if (englishWords.contains(word)) {
                englishFound++;
            }
            if (frenchWords.contains(word)) {
                frenchFound++;
            }
        }

        double englishProbability = calculateProbability(englishFound, englishWords.size());
        double frenchProbability = calculateProbability(frenchFound, frenchWords.size());

        return englishProbability > frenchProbability ? Language.ENGLISH : Language.FRENCH;
    }

    private double calculateProbability(int foundNum, int allNum) {
        return (double) foundNum / (double) allNum;
    }
}
