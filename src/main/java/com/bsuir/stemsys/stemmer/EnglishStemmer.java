package com.bsuir.stemsys.stemmer;

import com.bsuir.stemsys.api.stemmer.Stemmer;
import com.bsuir.stemsys.model.Language;

public class EnglishStemmer implements Stemmer {

    @Override
    public String stem(String word) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Language supportedLanguage() {
        return Language.ENGLISH;
    }
}
