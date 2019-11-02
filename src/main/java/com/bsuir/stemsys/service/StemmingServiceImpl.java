package com.bsuir.stemsys.service;

import com.bsuir.stemsys.api.stemmer.Stemmer;
import com.bsuir.stemsys.api.service.StemmingService;
import com.bsuir.stemsys.api.stemmer.StemmerFactory;
import com.bsuir.stemsys.model.Language;
import com.bsuir.stemsys.model.StemResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StemmingServiceImpl implements StemmingService {
    private StemmerFactory stemmerFactory;

    public StemmingServiceImpl(StemmerFactory stemmerFactory) {
        this.stemmerFactory = stemmerFactory;
    }

    public List<StemResult> stem(List<String> textWords, Language language) {
        Stemmer stemmer = stemmerFactory.create(language);

        Map<String, Integer> wordBasisWithFrequency = new HashMap<>();
        for (String word : textWords) {
            String basis = stemmer.stem(word);
            if (wordBasisWithFrequency.containsKey(basis)) {
                wordBasisWithFrequency.put(basis, wordBasisWithFrequency.get(basis) + 1);
            } else {
                wordBasisWithFrequency.put(basis, 1);
            }
        }
        return formResults(wordBasisWithFrequency, stemmer.supportedLanguage());
    }

    private List<StemResult> formResults(Map<String, Integer> wordBasisWithFrequency, Language language) {
        return wordBasisWithFrequency.entrySet()
                .stream()
                .map(entry -> new StemResult(entry.getKey(), entry.getValue(), language))
                .collect(Collectors.toList());
    }
}
