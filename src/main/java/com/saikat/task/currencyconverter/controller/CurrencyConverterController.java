package com.saikat.task.currencyconverter.controller;

import com.saikat.task.currencyconverter.model.CurrencyConversionReq;
import com.saikat.task.currencyconverter.model.CurrencyConversionRes;
import com.saikat.task.currencyconverter.service.CurrencyConverterService;
import com.saikat.task.currencyconverter.validator.CurrencyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CurrencyConverterController {

    private final CurrencyConverterService service;
    private final CurrencyValidator validator;

    @Autowired
    public CurrencyConverterController(CurrencyConverterService service, CurrencyValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    @PostMapping("/api/convert")
    public CurrencyConversionRes convert(@Valid @RequestBody CurrencyConversionReq req) {
        validator.validateReq(req);
        return service.convert(req);
    }
}
