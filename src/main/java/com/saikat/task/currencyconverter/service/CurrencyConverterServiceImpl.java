package com.saikat.task.currencyconverter.service;

import com.saikat.task.currencyconverter.model.CurrencyConversionReq;
import com.saikat.task.currencyconverter.model.ExternalResponseParam;
import com.saikat.task.currencyconverter.properties.CurrencyConverterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    private final RestTemplateBuilder restTemplateBuilder;
    private final CurrencyConverterProperties properties;

    @Autowired
    public CurrencyConverterServiceImpl(RestTemplateBuilder restTemplateBuilder, CurrencyConverterProperties properties) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.properties = properties;
    }

    @Override
    public void convert(CurrencyConversionReq req) {
        final String CURRENCY_CONVERTER_API = properties.getCurrencyConverterAPI();
        System.out.println("API : " + CURRENCY_CONVERTER_API);
        try {
            ExternalResponseParam param = fetch(ExternalResponseParam.class, CURRENCY_CONVERTER_API, req.getTargetCurrency(), req.getSourceCurrency());
            System.out.println("Rates : " + param.getRates());
            System.out.println("Date: " + Locale.getDefault());
            Currency usd = java.util.Currency.getInstance("INR");
            NumberFormat format = java.text.NumberFormat.getCurrencyInstance(Locale.ENGLISH);
            format.setCurrency(usd);
            System.out.println(format.format(23));
            //Locale locale = new Locale.Builder().s
        } catch (RestClientException re) {
            System.err.println("Exp" + re);
        }

    }

    /**
     * The method to call the Weather API and store the required response data in corresponding POJO
     *
     * @param uriParams
     * @param responseType
     * @param url
     * @param <T>
     * @return <T>
     * @throws RestClientException
     */
    private <T> T fetch(Class<T> responseType, String url, Object... uriParams) throws RestClientException {
        URI forecastUrl = new UriTemplate(url).expand(uriParams);
        RequestEntity<String> request = new RequestEntity<>(HttpMethod.GET, forecastUrl);
        RestTemplate template = restTemplateBuilder.build();
        ResponseEntity<T> forecastResponse = template.exchange(forecastUrl, HttpMethod.GET, request, responseType);
        return forecastResponse.getBody();
    }
}
