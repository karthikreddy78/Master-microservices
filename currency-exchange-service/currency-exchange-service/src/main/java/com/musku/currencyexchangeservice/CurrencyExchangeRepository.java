package com.musku.currencyexchangeservice;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CurrencyExchangeRepository extends MongoRepository<CurrencyExchange,Long> {

    CurrencyExchange findByFromAndTo(String from,String to);
}
