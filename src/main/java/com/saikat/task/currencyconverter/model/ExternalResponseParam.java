package com.saikat.task.currencyconverter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class ExternalResponseParam {

    private Map<String, BigDecimal> rates;
    private Date exchangeDate;

    public Date getExchangeDate() {
        return exchangeDate;
    }

    @JsonSetter("date")
    public void setExchangeDate(Date exchangeDate) {
        System.out.println("Date : " + exchangeDate);
        this.exchangeDate = exchangeDate;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    @JsonProperty("rates")
    public void setRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }


}
