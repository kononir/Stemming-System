package com.bsuir.stemsys.api.service;

import com.bsuir.stemsys.model.Language;

import java.util.List;

public interface LanguageDefinerService {
    Language defineLanguage(List<String> textWords);
}
