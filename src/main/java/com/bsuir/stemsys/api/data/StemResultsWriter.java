package com.bsuir.stemsys.api.data;

import com.bsuir.stemsys.model.StemResult;

import java.util.List;
import java.util.Map;

public interface StemResultsWriter {
    void write(Map<String, List<StemResult>> results, String path);
}
