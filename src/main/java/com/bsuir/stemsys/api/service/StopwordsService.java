package com.bsuir.stemsys.api.service;

import java.util.List;

public interface StopwordsService {
    List<String> excludeStopwords(List<String> allWords);
}
