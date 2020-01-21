package com.saikat.task.currencyconverter.model;

import java.math.BigDecimal;
import java.util.Date;

public class CurrencyConversionRes {

    private String convertedFormattedAmount;

    private BigDecimal rawConvertedAmount;

    private Date conversionDate;

    public CurrencyConversionRes(String convertedFormattedAmount, BigDecimal rawConvertedAmount, Date date) {
        this.convertedFormattedAmount = convertedFormattedAmount;
        this.rawConvertedAmount = rawConvertedAmount;
        this.conversionDate = date;
    }

    public String getConvertedFormattedAmount() {
        return convertedFormattedAmount;
    }

    public void setConvertedFormattedAmount(String convertedFormattedAmount) {
        this.convertedFormattedAmount = convertedFormattedAmount;
    }

    public BigDecimal getRawConvertedAmount() {
        return rawConvertedAmount;
    }

    public void setRawConvertedAmount(BigDecimal rawConvertedAmount) {
        this.rawConvertedAmount = rawConvertedAmount;
    }

    public Date getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(Date conversionDate) {
        this.conversionDate = conversionDate;
    }
}
