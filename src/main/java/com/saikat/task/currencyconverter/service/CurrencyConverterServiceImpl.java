package com.saikat.task.currencyconverter.service;

import com.saikat.task.currencyconverter.exception.SourceNotConvertibleException;
import com.saikat.task.currencyconverter.model.CurrencyConversionReq;
import com.saikat.task.currencyconverter.model.CurrencyConversionRes;
import com.saikat.task.currencyconverter.model.ExternalResponseParam;
import com.saikat.task.currencyconverter.properties.CurrencyConverterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    Logger logger = LoggerFactory.getLogger(CurrencyConverterServiceImpl.class);

    private final RestTemplateBuilder restTemplateBuilder;
    private final CurrencyConverterProperties properties;

    @Autowired
    public CurrencyConverterServiceImpl(RestTemplateBuilder restTemplateBuilder, CurrencyConverterProperties properties) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.properties = properties;
    }

    /**
     * Service method to fetch exchange rate from external service and convert and format response
     *
     * @param req
     * @return CurrencyConversionRes
     */
    @Override
    public CurrencyConversionRes convert(CurrencyConversionReq req) {
        final String CURRENCY_CONVERTER_API = properties.getCurrencyConverterAPI();
        try {
            ExternalResponseParam param = fetch(ExternalResponseParam.class, CURRENCY_CONVERTER_API, req.getTargetCurrency(), req.getSourceCurrency());
            BigDecimal rate = param.getRates().computeIfAbsent(req.getTargetCurrency(), v -> {
                throw new SourceNotConvertibleException("Can not get exchange rate : " + v);
            });
            return convertAndFormat(rate, req.getAmount(), req.getTargetCurrency(), param.getExchangeDate());
        } catch (RuntimeException re) {
            logger.error("Exception occurred while retrieving and converting : {}", re.getMessage());
            throw new SourceNotConvertibleException(re.getMessage());
        }
    }

    /**
     * The method to call Any API and store the required response data in corresponding POJO
     *
     * @param uriParams
     * @param responseType
     * @param url
     * @param <T>
     * @return <T>
     * @throws RestClientException
     */
    private <T> T fetch(Class<T> responseType, String url, Object... uriParams) throws RestClientException {
        URI exchangeUrl = new UriTemplate(url).expand(uriParams);
        RequestEntity<String> request = new RequestEntity<>(HttpMethod.GET, exchangeUrl);
        RestTemplate template = restTemplateBuilder.build();
        ResponseEntity<T> forecastResponse = template.exchange(exchangeUrl, HttpMethod.GET, request, responseType);
        return forecastResponse.getBody();
    }


    /**
     * Method to convert and format output response
     *
     * @param multiplier
     * @param targetAmount
     * @param targetCurrency
     * @return CurrencyConversionRes
     */
    private CurrencyConversionRes convertAndFormat(BigDecimal multiplier, BigDecimal targetAmount, String targetCurrency, Date date) {
        BigDecimal convertedAmount = multiplier.multiply(targetAmount);
        NumberFormat numberFormatter = getNumberFormat(targetCurrency);

        return new CurrencyConversionRes(numberFormatter.format(convertedAmount), convertedAmount, date);
    }

    /**
     * Method returns number format for formatting value
     *
     * @param targetCurrency
     * @return NumberFormat
     */
    private NumberFormat getNumberFormat(String targetCurrency) {
        final Optional<String> maybeLocaleConfig = Optional.ofNullable(properties.getLocaleConfig());
        String[] config = maybeLocaleConfig.map(locale -> locale.split("-")).orElse(new String[]{"en", "GB"});
        if(config.length < 2) {
            logger.info("Setting default locale as no configuration found or invalid configuration");
            config = new String[]{"en", "GB"};
        }
        Locale locale = new Locale(config[0], config[1]);
        Currency currency = Currency.getInstance(targetCurrency);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setCurrency(currency);

        return numberFormat;
    }
}
