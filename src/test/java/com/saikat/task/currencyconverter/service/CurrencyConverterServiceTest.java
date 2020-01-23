package com.saikat.task.currencyconverter.service;

import com.saikat.task.currencyconverter.exception.SourceNotConvertibleException;
import com.saikat.task.currencyconverter.model.CurrencyConversionReq;
import com.saikat.task.currencyconverter.model.CurrencyConversionRes;
import com.saikat.task.currencyconverter.model.ExternalResponseParam;
import com.saikat.task.currencyconverter.properties.CurrencyConverterProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CurrencyConverterServiceTest {

    @MockBean
    private RestTemplateBuilder restTemplateBuilder;

    @MockBean
    private CurrencyConverterProperties properties;

    private CurrencyConverterService service;
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        service = new CurrencyConverterServiceImpl(restTemplateBuilder, properties);
        restTemplate = mock(RestTemplate.class);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        when(properties.getCurrencyConverterAPI()).thenReturn("https://api.exchangeratesapi.io/latest?symbols={targetCurrency}&&base={baseCurrency}");
        when(properties.getLocaleConfig()).thenReturn("en-US");
    }

    @Test
    public void testSuccessConversion() {
        CurrencyConversionReq request = new CurrencyConversionReq("EUR", "USD", BigDecimal.TEN);
        ResponseEntity responseEntity = mock(ResponseEntity.class);
        ExternalResponseParam externalResponseParam = new ExternalResponseParam();
        externalResponseParam.setExchangeDate(new Date());
        externalResponseParam.setRates(new HashMap<String, BigDecimal>() {{
            put("USD", BigDecimal.TEN);
        }});


        when(restTemplate.exchange(Mockito.<URI>any(), Mockito.<HttpMethod>eq(HttpMethod.GET),
                Mockito.<HttpEntity<?>>any(), Mockito.<Class<Object>>any())).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(externalResponseParam);

        CurrencyConversionRes res = service.convert(request);
        assertEquals(res.getConvertedFormattedAmount(), "$100.00");
    }

    @Test
    public void testSuccessWithDefaultLocale() {
        CurrencyConversionReq request = new CurrencyConversionReq("EUR", "USD", BigDecimal.TEN);
        ResponseEntity responseEntity = mock(ResponseEntity.class);
        ExternalResponseParam externalResponseParam = new ExternalResponseParam();
        externalResponseParam.setExchangeDate(new Date());
        externalResponseParam.setRates(new HashMap<String, BigDecimal>() {{
            put("USD", BigDecimal.TEN);
        }});

        when(properties.getLocaleConfig()).thenReturn(null);
        when(restTemplate.exchange(Mockito.<URI>any(), Mockito.<HttpMethod>eq(HttpMethod.GET),
                Mockito.<HttpEntity<?>>any(), Mockito.<Class<Object>>any())).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(externalResponseParam);

        CurrencyConversionRes res = service.convert(request);
        assertEquals(res.getConvertedFormattedAmount(), "USD100.00");
    }

    @Test
    public void testExceptionFromExternalService() {
        CurrencyConversionReq request = new CurrencyConversionReq("XXX", "USD", BigDecimal.TEN);
        ResponseEntity responseEntity = mock(ResponseEntity.class);

        when(restTemplate.exchange(Mockito.<URI>any(), Mockito.<HttpMethod>eq(HttpMethod.GET),
                Mockito.<HttpEntity<?>>any(), Mockito.<Class<Object>>any())).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenThrow(new RestClientException("Not Valid Currency"));

        try {
            service.convert(request);
            fail("Should not have succeed");
        } catch (SourceNotConvertibleException e) {
            assertEquals(e.getMessage(), "Not Valid Currency");
        }

    }

    @Test
    public void testResponseNotContainingConversionRate() {
        CurrencyConversionReq request = new CurrencyConversionReq("EUR", "USD", BigDecimal.TEN);
        ResponseEntity responseEntity = mock(ResponseEntity.class);
        ExternalResponseParam externalResponseParam = new ExternalResponseParam();
        externalResponseParam.setExchangeDate(new Date());
        externalResponseParam.setRates(new HashMap<String, BigDecimal>());
        when(restTemplate.exchange(Mockito.<URI>any(), Mockito.<HttpMethod>eq(HttpMethod.GET),
                Mockito.<HttpEntity<?>>any(), Mockito.<Class<Object>>any())).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(externalResponseParam);

        try {
            service.convert(request);
            fail("Should not have succeed");
        } catch (SourceNotConvertibleException e) {
            assertEquals(e.getMessage(), "Can not get exchange rate : USD");
        }
    }
}
