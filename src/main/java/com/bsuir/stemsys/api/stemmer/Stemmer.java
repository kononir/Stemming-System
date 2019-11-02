package com.bsuir.stemsys.api.stemmer;

import com.bsuir.stemsys.model.Language;

public interface Stemmer {
    String stem(String word);

    Language supportedLanguage();
}
