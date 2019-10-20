package com.bsuir.stemsys.api;

import com.bsuir.stemsys.entity.StemResult;

import java.util.List;

public interface StemResultsWriter {
    String write(List<StemResult> results, String path);
}
