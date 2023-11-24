package com.krokod1lda.wishes.EntityAttributes;

public enum WantyAttributes {
    TITLE("title"),
    MAP("map"),
    WANTY("wanty"), WANTIES("wanties"), WANTY_PHOTO("wantyPhoto"),
    STATISTICS("Статистика"),
    MAIN("Главная"),
    ADDING_REQUEST("Добавление запроса"), REQUEST_CARD("Карточка запроса"), EDITING_REQUEST("Редактирование запроса"),
    SELLER_NAME("sellerName"), CURRENT_SELLER("curSeller"), WANTIES_SELLER("wantiesSeller"),
    BUYER_NAME("buyerName"), CURRENT_BUYER("curBuyer"), WANTIES_BUYER("wantiesBuyer"),
    CLIENT_NAME("clientName"), CURRENT_CLIENT("curClient"), WANTIES_CLIENT("wantiesClient"),
    PROJECT_NAME("projectName"), CURRENT_PROJECT("curProject"), WANTIES_PROJECT("wantiesProject"),
    PROJECTS("projects"),
    IS_PURCHASED("isPurchased"), PURCHASED("был куплен"), NOT_PURCHASED("не был куплен"),
    DATE1("date1"), DATE2("date2");

    private final String value;

    WantyAttributes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
