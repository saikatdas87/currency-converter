package com.saikat.task.currencyconverter.validator;

import com.saikat.task.currencyconverter.exception.InvalidDataException;
import com.saikat.task.currencyconverter.model.CurrencyConversionReq;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CurrencyValidatorTest {

    private CurrencyValidator validator = new CurrencyValidatorImpl();

    @Test
    public void testSuccess() {
        CurrencyConversionReq request = new CurrencyConversionReq("EUR", "USD", BigDecimal.TEN);
        try {
            validator.validateReq(request);
        } catch (Exception e) {
            fail("Should not have reached");
        }
    }

    @Test
    public void failsIfAmountIsZero() {
        CurrencyConversionReq request = new CurrencyConversionReq("EUR", "USD", BigDecimal.ZERO);
        try {
            validator.validateReq(request);
            fail("Should have thrown exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidDataException);
            assertEquals(e.getMessage(), "Invalid amount : 0");
        }
    }

    @Test
    public void shouldFailForInvalidCurrency() {
        CurrencyConversionReq request = new CurrencyConversionReq("EXXUR", "USD", BigDecimal.TEN);
        try {
            validator.validateReq(request);
            fail("Should have thrown exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidDataException);
            assertEquals(e.getMessage(), "Invalid currency : EXXUR");
        }
    }
}
