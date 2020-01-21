package com.saikat.task.currencyconverter.service;

import com.saikat.task.currencyconverter.model.CurrencyConversionReq;
import com.saikat.task.currencyconverter.model.CurrencyConversionRes;

public interface CurrencyConverterService {

    CurrencyConversionRes convert(CurrencyConversionReq req);
}
