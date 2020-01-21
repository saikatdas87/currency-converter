package com.saikat.task.currencyconverter.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class CurrencyConverterProperties {

    @Value("${currency.converter.api}")
    private String currencyConverterAPI;

    @Value("${currency.formatter.locale}")
    private String localeConfig;

    public String getCurrencyConverterAPI() {
        return currencyConverterAPI;
    }

    public String getLocaleConfig() {
        return localeConfig;
    }
}
