package com.bsuir.stemsys.service;

import com.bsuir.stemsys.api.service.StopwordsService;
import com.bsuir.stemsys.api.service.StopwordsServiceFactory;
import com.bsuir.stemsys.model.Language;

public class StopwordsServiceFactoryImpl implements StopwordsServiceFactory {

    @Override
    public StopwordsService create(Language language) {
        switch (language) {
            case ENGLISH:
                return StopwordsServiceImpl.english();
            case FRENCH:
                return StopwordsServiceImpl.french();
            default:
                throw new EnumConstantNotPresentException(Language.class, language.name());
        }
    }
}
