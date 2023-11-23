package com.krokod1lda.wishes.EntityAttributes;

public enum ProjectAttributes {
    TITLE("title"),
    ADDING_PROJECT("Добавление проекта"), ALL_PROJECTS("Все проекты"),
    PROJECTS("projects"), PROJECT("project"),
    EDIT_PROJECT("Редактирование проекта"), PHONE_NUMBERS("phone_numbers");
    private final String value;
    ProjectAttributes(String value) {this.value = value;}
    public String getValue() {return value;}
}
