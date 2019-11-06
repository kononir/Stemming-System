package com.bsuir.stemsys.stemmer;

import com.bsuir.stemsys.api.stemmer.Stemmer;
import com.bsuir.stemsys.model.Language;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.frenchStemmer;

public class FrenchStemmer implements Stemmer {
    private SnowballStemmer stemmer = new frenchStemmer();

    @Override
    public String stem(String word) {
        stemmer.setCurrent(word);
        stemmer.stem();
        return stemmer.getCurrent();
    }

    @Override
    public Language supportedLanguage() {
        return Language.FRENCH;
    }
}
