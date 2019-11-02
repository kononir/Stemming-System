package com.bsuir.stemsys.stemmer;

import com.bsuir.stemsys.api.stemmer.Stemmer;
import com.bsuir.stemsys.api.stemmer.StemmerFactory;
import com.bsuir.stemsys.model.Language;

public class StemmerFactoryImpl implements StemmerFactory {
    @Override
    public Stemmer create(Language language) {
        switch (language) {
            case ENGLISH:
                return new EnglishStemmer();
            case FRENCH:
                return new FrenchStemmer();
            default:
                throw new EnumConstantNotPresentException(Language.class, language.name());
        }
    }
}
