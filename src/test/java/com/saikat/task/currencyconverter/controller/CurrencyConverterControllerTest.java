package com.saikat.task.currencyconverter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saikat.task.currencyconverter.model.CurrencyConversionReq;
import com.saikat.task.currencyconverter.model.CurrencyConversionRes;
import com.saikat.task.currencyconverter.service.CurrencyConverterService;
import com.saikat.task.currencyconverter.validator.CurrencyValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CurrencyConverterController.class)
public class CurrencyConverterControllerTest {

    @MockBean
    private CurrencyConverterService service;

    @MockBean
    private CurrencyValidator currencyValidator;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyConverterController controller;


    @Test
    public void gatesConvertedValue() throws Exception {
        CurrencyConversionReq request = new CurrencyConversionReq("EUR", "USD", BigDecimal.TEN);
        CurrencyConversionRes expected = new CurrencyConversionRes("$ 230", BigDecimal.valueOf(50), new Date());

        doNothing().when(currencyValidator).validateReq(request);
        when(service.convert(request)).thenReturn(expected);
        MvcResult mvcResult = mockMvc.perform(post("/api/convert")
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON, objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();
        //String actualResponseBody = mvcResult.getResponse().getContentAsString();
        //assertEquals(objectMapper.writeValueAsString(expected), actualResponseBody);
    }

    /*@Test
    public void testValidationError() throws Exception {
        CurrencyConversionReq request = new CurrencyConversionReq("EUR", "USD", BigDecimal.ZERO);

        doThrow(new InvalidDataException("Invalid amount")).when(currencyValidator).validateReq(request);
        mvc.perform(post("/api/convert", objectMapper.writeValueAsString(request))
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testExceptionFromService() throws Exception {
        CurrencyConversionReq request = new CurrencyConversionReq("XXY", "USD", BigDecimal.TEN);

        doNothing().when(currencyValidator).validateReq(request);
        when(service.convert(request)).thenThrow(new SourceNotConvertibleException("Not valid currency"));
        mvc.perform(post("/api/convert")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }*/
}
