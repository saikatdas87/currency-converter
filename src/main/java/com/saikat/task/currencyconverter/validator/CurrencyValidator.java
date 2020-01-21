package com.saikat.task.currencyconverter.validator;

import com.saikat.task.currencyconverter.model.CurrencyConversionReq;

public interface CurrencyValidator {
    void validateReq(CurrencyConversionReq request);
}
