package com.krokod1lda.wishes.EntityAttributes;

public enum SearchAttributes {
    TITLE("title"),
    MAP("map"),
    RESULTS("results"),
    SEARCH("Поиск"),
    QUERY("query"),
    PERSON_ID("personId");

    private final String value;

    SearchAttributes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
