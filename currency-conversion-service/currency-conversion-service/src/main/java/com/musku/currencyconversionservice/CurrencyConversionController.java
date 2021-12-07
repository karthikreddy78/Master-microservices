package com.musku.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {


    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    @GetMapping(path = "/currency-exchange/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion retrieveCurrencyCost(@PathVariable String from,
                                                   @PathVariable String to,
                                                   @PathVariable BigDecimal quantity)
    {


        HashMap<String,String> urivariables=new HashMap<>();
        urivariables.put("from",from);
        urivariables.put("to",to);
        ResponseEntity<CurrencyConversion> forEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class, urivariables);
        CurrencyConversion body = forEntity.getBody();
        System.out.print(body);
        body.setQuantity(quantity);
        body.setTotalCalculatedAmount(body.getConversionMultiple().multiply(quantity));


        return body;
    }



    @GetMapping(path = "/currency-exchange-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion retrieveCurrencyCostFeign(@PathVariable String from,
                                                   @PathVariable String to,
                                                   @PathVariable BigDecimal quantity)
    {



        CurrencyConversion body = currencyExchangeProxy.retrieveDetails(from,to);
        System.out.print(body);
        body.setQuantity(quantity);
        body.setTotalCalculatedAmount(body.getConversionMultiple().multiply(quantity));


        return body;
    }


}
