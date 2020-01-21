package com.saikat.task.currencyconverter.model;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CurrencyConversionReq {

    @NotNull
    private String sourceCurrency;
    @NotNull
    private String targetCurrency;
    @NotNull
    private BigDecimal amount;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }



}
