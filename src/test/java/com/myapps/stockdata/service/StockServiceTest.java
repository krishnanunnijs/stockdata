package com.myapps.stockdata.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myapps.stockdata.model.StockWrapper;

@SpringBootTest
public class StockServiceTest {

	@Autowired
	private StockService stockService;

	@Test
	public void testGetStockPrice() throws IOException {

		StockWrapper stockInfo = stockService.findStock("RELIANCE.NS");
		System.out.println(stockInfo.getStock());
		BigDecimal price = stockService.findPrice(stockInfo);
		System.out.println(price);

	}

}
