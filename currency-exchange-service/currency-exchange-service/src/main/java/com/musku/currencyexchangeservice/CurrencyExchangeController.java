package com.musku.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;


    @GetMapping(path = "/currency-exchange-list")
    public List<CurrencyExchange> retrieve()
    {
        List<CurrencyExchange> v = currencyExchangeRepository.findAll();
        return v;
    }

    @PostMapping(path = "/add-currency-exchange")
    public CurrencyExchange addCurrency(@RequestBody CurrencyExchange c)
    {
        String port= environment.getProperty("local.server.port");
        c.setEnvironment(port);
        return currencyExchangeRepository.save(c);
    }




    @GetMapping(path = "/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveDetails(@PathVariable String from,@PathVariable String to)
    {
        String port= environment.getProperty("local.server.port");

       // CurrencyExchange currencyExchange = new CurrencyExchange(1L, from, to, BigDecimal.valueOf(50));
        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from,to);

        if(currencyExchange==null)
        {
            throw new RuntimeException("Unable to find data from"+from+" to "+to);
        }
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
