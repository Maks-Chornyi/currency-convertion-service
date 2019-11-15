package nl.mycubes.currencyconvertionservice.controller;


import nl.mycubes.currencyconvertionservice.domain.ExchangeValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConvertionController {

    private static final String URL_TO_EXCHANGE_SERVICE = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public ExchangeValue exchangeValue(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {
        Map<String, String> variables = new HashMap<>();
        variables.put("from", from);
        variables.put("to", to);
        ResponseEntity<ExchangeValue> responseEntity = new RestTemplate().getForEntity(
                URL_TO_EXCHANGE_SERVICE,
                ExchangeValue.class,
                variables);
        final ExchangeValue res = responseEntity.getBody();


        return new ExchangeValue(
                res.getId(),
                res.getFrom(),
                res.getTo(),
                res.getConversionMultiple(),
                quantity,
                quantity.multiply(res.getConversionMultiple()),
                res.getPort());
    }
}
