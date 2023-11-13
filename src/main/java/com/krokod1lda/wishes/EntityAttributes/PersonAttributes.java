package com.krokod1lda.wishes.EntityAttributes;

public enum PersonAttributes {
    ROLE("role"),
    TITLE("title"),
    TYPE("type"),
    TITLE_ADDING_SELLER("Добавление продавца"), ROLE_SELLER("продавца"), TYPE_SELLER("seller"),
    TITLE_ADDING_BUYER("Добавление байера"), ROLE_BUYER("байера"), TYPE_BUYER("buyer"),
    TITLE_ADDING_CLIENT("Добавление клиента"), ROLE_CLIENT("клиента"), TYPE_CLIENT("client"),
    KEY_SELLERS_RU("Продавцы"), KEY_SELLERS_EN("sellers"),
    KEY_BUYERS_RU("Байеры"), KEY_BUYERS_EN("buyers"),
    KEY_CLIENTS_RU("Клиенты"), KEY_CLIENTS_EN("clients"),
    MAP("map"),
    TITLE_ALL_PARTICIPANTS("Все участники"),
    PERSON("person"),
    ID("id"),
    EDITING_PARTICIPANT("Редактирование участника"),
    EDITING_ARCHIVED_PARTICIPANT("Редактирование архивированного участника");

    private final String value;

    PersonAttributes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
