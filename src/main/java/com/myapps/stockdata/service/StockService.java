package com.myapps.stockdata.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.myapps.stockdata.model.StockWrapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import yahoofinance.YahooFinance;

@Service
@Slf4j
@AllArgsConstructor
public class StockService {

	public StockWrapper findStock(final String ticker) {

		try {

			return new StockWrapper(YahooFinance.get(ticker));

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

		return null;

	}

	public BigDecimal findPrice(StockWrapper stockWrapper) throws IOException {

		return stockWrapper.getStock().getQuote(true).getPrice();
	}

}
