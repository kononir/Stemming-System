package com.bsuir.stemsys.api.stemmer;

import com.bsuir.stemsys.model.Language;

public interface StemmerFactory {
    Stemmer create(Language language);
}
