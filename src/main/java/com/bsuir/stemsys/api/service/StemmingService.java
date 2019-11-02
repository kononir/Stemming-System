package com.bsuir.stemsys.api.service;

import com.bsuir.stemsys.model.Language;
import com.bsuir.stemsys.model.StemResult;

import java.util.List;

public interface StemmingService {
    List<StemResult> stem(List<String> textWords, Language language);
}
