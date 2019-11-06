package com.bsuir.stemsys.stemmer;

import com.bsuir.stemsys.api.stemmer.Stemmer;
import com.bsuir.stemsys.model.Language;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

public class EnglishStemmer implements Stemmer {
    SnowballStemmer stemmer = new englishStemmer();

    @Override
    public String stem(String word) {
        stemmer.setCurrent(word);
        stemmer.stem();
        return stemmer.getCurrent();
    }

    @Override
    public Language supportedLanguage() {
        return Language.ENGLISH;
    }
}
