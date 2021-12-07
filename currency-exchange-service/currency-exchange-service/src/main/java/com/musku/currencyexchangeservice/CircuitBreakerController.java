package com.musku.currencyexchangeservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger= LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping(path = "/sample-api")
    //@Retry(name = "sample-api",fallbackMethod = "hardcodedresponse")
    @CircuitBreaker(name = "default",fallbackMethod = "hardcodedresponse")
    public String sampleapi()
    {
        logger.info("Sample Api msg");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("localhost:8080/sample", String.class);
        return forEntity.getBody();
    }

    public String hardcodedresponse(Exception e)
    {
        return "fallbackresponse";
    }

}
