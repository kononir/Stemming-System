package com.bsuir.stemsys.api.data;

import com.bsuir.stemsys.model.StemResult;

import java.util.List;

public interface StemResultsWriter {
    String write(List<StemResult> results, String path);
}
