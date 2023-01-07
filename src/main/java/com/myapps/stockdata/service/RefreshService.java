package com.myapps.stockdata.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.myapps.stockdata.model.StockWrapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RefreshService {

	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static final Duration refreshInterval = Duration.ofSeconds(10);

	private final Map<StockWrapper, Boolean> stockToRefresh;

	public RefreshService() {
		stockToRefresh = new HashMap<>();
		setRefreshWithDealy();
	}

	public boolean shouldRefresh(final StockWrapper stockWrapper) {

		log.debug("shouldRefresh : " + stockWrapper.getStock().getName() +" Value : "+ stockToRefresh.containsKey(stockWrapper));
		if (!stockToRefresh.containsKey(stockWrapper)) {
			stockToRefresh.put(stockWrapper, false);
			return true;
		}
		return stockToRefresh.get(stockWrapper);
	}

	private void setRefreshWithDealy() {

		scheduler.scheduleAtFixedRate(() ->

		stockToRefresh.forEach((stock, value) -> {
			log.debug("Stock name : " + stock.getStock().getName() +" Value : "+ value);
			if (stock.getLastAccessed().isBefore(LocalDateTime.now().minus(refreshInterval))) {
				log.info("Refreshing stock price : " + stock.getStock().getName());
				stockToRefresh.remove(stock);
				stockToRefresh.put(stock.withLastAccessed(LocalDateTime.now()), true);
			}

		}), 0, 15, TimeUnit.SECONDS);
	}
}
