package com.myapps.stockdata.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myapps.stockdata.model.StockWrapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class StockServiceTest {

	@Autowired
	private StockService stockService;

	@Test
	public void testFindStockData() throws IOException {

		final StockWrapper stockInfo = stockService.findStock("RELIANCE.NS");
		BigDecimal price = stockService.findPrice(stockInfo);
		log.info(stockInfo.getStock().getName()+" : "+ price);

		final BigDecimal changePercent = stockService.findLastChangePercent(stockInfo);
		log.info(changePercent.toString());

		final BigDecimal change200MeanPercent = stockService.findChangeFrom200MeanPercent(stockInfo);
		log.info(change200MeanPercent.toString());

	}

	@Test
	public void testGetStocks() throws IOException, InterruptedException {

		final List<StockWrapper> findStocks = stockService.findStocks(Arrays.asList("SBIN.NS", "TCS.NS"));
		findStockPrices(findStocks);
		
		Thread.sleep(16000);
		
		final StockWrapper stockNew = stockService.findStock("RELIANCE.NS");
		findStocks.add(stockNew);
		log.info(stockNew.getStock().getName()+" : "+ stockService.findPrice(stockNew));
		findStockPrices(findStocks);

	}

	private void findStockPrices(List<StockWrapper> wrapper) {

		wrapper.forEach(stock -> {
			try {
				log.info(stock.getStock().getName()+" : "+stockService.findPrice(stock));
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		});

	}

}
