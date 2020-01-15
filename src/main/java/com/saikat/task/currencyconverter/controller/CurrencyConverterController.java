package com.saikat.task.currencyconverter.controller;

import com.saikat.task.currencyconverter.model.CurrencyConversionReq;
import com.saikat.task.currencyconverter.service.CurrencyConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CurrencyConverterController {

    private final CurrencyConverterService service;

    @Autowired
    public CurrencyConverterController( CurrencyConverterService service) {
        this.service = service;
    }

    @PostMapping("/api/convert")
    public String convert(@Valid @RequestBody CurrencyConversionReq req) {
        System.out.println("In Controller");
        service.convert(req);
        return "";
    }
}
