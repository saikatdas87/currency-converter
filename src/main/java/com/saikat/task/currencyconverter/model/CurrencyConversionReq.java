package com.saikat.task.currencyconverter.model;

import javax.validation.constraints.NotNull;

public class CurrencyConversionReq {

    @NotNull
    private String sourceCurrency;
    @NotNull
    private String targetCurrency;
    @NotNull
    private String amount;

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }



}
