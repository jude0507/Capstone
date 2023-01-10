package com.example.learnmoto.Model;

public class WordAlphabetModel {
    private final Integer iconId;
    private final String letterName;

    public WordAlphabetModel(Integer iconId, String letterName) {
        this.iconId = iconId;
        this.letterName = letterName;
    }

    public Integer getIconId() {
        return iconId;
    }

    public String getLetterName() {
        return letterName;
    }
}
