package com.bsuir.stemsys.model;

public class StemResult {
    private String base;
    private int frequency;
    private Language language;

    public StemResult(String base, int frequency, Language language) {
        this.base = base;
        this.frequency = frequency;
        this.language = language;
    }

    public String getBase() {
        return base;
    }

    public int getFrequency() {
        return frequency;
    }

    public Language getLanguage() {
        return language;
    }
}
