package com.saikat.task.currencyconverter.validator;

import com.saikat.task.currencyconverter.exception.InvalidDataException;
import com.saikat.task.currencyconverter.model.CurrencyConversionReq;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Service
public class CurrencyValidatorImpl implements CurrencyValidator {

    /**
     * Method to validate the request parameters
     *
     * @param request
     */
    public void validateReq(CurrencyConversionReq request) {
        validateCurrency(request.getTargetCurrency());
        validateCurrency(request.getSourceCurrency());
        validateAmount(request.getAmount());
    }

    /**
     * Validates currency if valid or not
     *
     * @param currency
     */
    private void validateCurrency(String currency) {
        try {
            Currency.getInstance(currency);
        } catch (IllegalArgumentException ie) {
            throw new InvalidDataException("Invalid currency : " + currency);
        }
    }

    /**
     * Validates if passed amount is valid
     *
     * @param amount
     */
    private void validateAmount(BigDecimal amount) {
        if (null == amount || 0 == amount.compareTo(BigDecimal.ZERO)) {
            throw new InvalidDataException("Invalid amount : " + amount);
        }
    }
}
