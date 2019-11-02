package com.bsuir.stemsys.api.service;

import com.bsuir.stemsys.model.Language;

public interface StopwordsServiceFactory {
    StopwordsService create(Language language);
}
