package com.saikat.task.currencyconverter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class ExternalResponseParam {

    private BigDecimal exchangeRate;
    private Map<String, Double> rates;
    private Date exchangeDate;

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }


    public Date getExchangeDate() {
        return exchangeDate;
    }

    @JsonSetter("date")
    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    @JsonProperty("rates")
    public void setRates(Map<String, Double> rates) {
        System.out.println("Rates : " + rates );
        this.rates = rates;
    }


}
