package com.saikat.task.currencyconverter.model;

public enum SupportedCurrencies {

    AUD("AUD"),
    BGN("BGN"),
    BRL("BRL"),
    CAD("CAD"),
    CHF("CHF"),
    CNY("CNY"),
    CZK("CZK"),
    DKK("DKK"),
    EUR("EUR"),
    GBP("GBP"),
    HKD("HKD"),
    HRK("HRK"),
    HUF("HUF"),
    IDR("IDR"),
    INR("INR"),
    ISK("ISK"),
    JPY("JPY"),
    KRW("KRW"),
    MXN("MXN"),
    MYR("MYR"),
    NOK("NOK"),
    NZD("NZD"),
    PHP("PHP"),
    PLN("PLN"),
    RON("RON"),
    RUB("RUB"),
    SEK("SEK"),
    SGD("SGD"),
    THB("THB"),
    TRY("TRY"),
    USD("USD"),
    ZAR("ZAR");

    private String currencyCode = "";

    SupportedCurrencies(String code) {
        this.currencyCode = code;
    }

    @Override
    public String toString() {
        return currencyCode;
    }

}
