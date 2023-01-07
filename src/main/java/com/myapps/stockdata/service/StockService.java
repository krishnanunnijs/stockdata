package com.myapps.stockdata.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myapps.stockdata.model.StockWrapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import yahoofinance.YahooFinance;

@Service
@Slf4j
@AllArgsConstructor
public class StockService {

	private final RefreshService refreshService;

	public StockWrapper findStock(final String ticker) {

		try {

			return new StockWrapper(YahooFinance.get(ticker));

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

		return null;

	}

	public List<StockWrapper> findStocks(final List<String> tickers) {

		return tickers.stream().map(this::findStock).filter(Objects::nonNull).collect(Collectors.toList());

	}

	public BigDecimal findPrice(final StockWrapper stockWrapper) throws IOException {

		return stockWrapper.getStock().getQuote(refreshService.shouldRefresh(stockWrapper)).getPrice();
	}

	public BigDecimal findLastChangePercent(final StockWrapper stockWrapper) throws IOException {

		return stockWrapper.getStock().getQuote(refreshService.shouldRefresh(stockWrapper)).getChangeInPercent();

	}

	public BigDecimal findChangeFrom200MeanPercent(final StockWrapper stockWrapper) throws IOException {

		return stockWrapper.getStock().getQuote(refreshService.shouldRefresh(stockWrapper))
				.getChangeFromAvg200InPercent();

	}

}
