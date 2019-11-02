package com.bsuir.stemsys;

import com.bsuir.stemsys.api.data.DocumentParser;
import com.bsuir.stemsys.api.data.DocumentReader;
import com.bsuir.stemsys.api.data.StemResultsWriter;
import com.bsuir.stemsys.api.service.LanguageDefinerService;
import com.bsuir.stemsys.api.service.StemmingService;
import com.bsuir.stemsys.api.service.StopwordsService;
import com.bsuir.stemsys.api.service.StopwordsServiceFactory;
import com.bsuir.stemsys.api.stemmer.Stemmer;
import com.bsuir.stemsys.api.stemmer.StemmerFactory;
import com.bsuir.stemsys.model.Language;
import com.bsuir.stemsys.model.StemResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Director {
    private DocumentReader reader;
    private DocumentParser parser;
    private LanguageDefinerService languageDefinerService;
    private StopwordsServiceFactory stopwordsServiceFactory;
    private StemmingService stemmingService;
    private StemResultsWriter writer;

    public Director(DocumentReader reader, DocumentParser parser,
                    LanguageDefinerService languageDefinerService,
                    StopwordsServiceFactory stopwordsServiceFactory,
                    StemmingService stemmingService, StemResultsWriter writer) {
        this.reader = reader;
        this.parser = parser;
        this.languageDefinerService = languageDefinerService;
        this.stopwordsServiceFactory = stopwordsServiceFactory;
        this.stemmingService = stemmingService;
        this.writer = writer;
    }

    public Map<String, List<StemResult>> handleWork(List<String> documentPath, String resultsPath) {
        Map<String, List<StemResult>> documentsStemResults = new HashMap<>();

        for (String path : documentPath) {
            String text = reader.read(path);
            List<String> words = parser.parse(text);
            Language language = languageDefinerService.defineLanguage(words);
            StopwordsService stopwordsService = stopwordsServiceFactory.create(language);
            List<String> nonStopwords = stopwordsService.excludeStopwords(words);
            List<StemResult> stemResults = stemmingService.stem(nonStopwords, language);
            writer.write(stemResults, resultsPath);

            documentsStemResults.put(path, stemResults);
        }

        return documentsStemResults;
    }
}
