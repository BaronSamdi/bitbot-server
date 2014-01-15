package com.amiramit.bitsafe.client.uitypes.uibeans;

import java.math.BigDecimal;
import java.util.Date;

import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.Exchange;

public interface UITicker {

	Exchange getExchange();

	CurrencyPair getCurrencyPair();

	BigDecimal getLast();

	BigDecimal getBid();

	BigDecimal getAsk();

	BigDecimal getHigh();

	BigDecimal getLow();

	BigDecimal getVolume();

	Date getTimestamp();

	void setExchange(Exchange exchange);

	void setCurrencyPair(CurrencyPair currencyPair);

	void setLast(BigDecimal b);

	void setBid(BigDecimal b);

	void setAsk(BigDecimal b);

	void setHigh(BigDecimal b);

	void setLow(BigDecimal b);

	void setVolume(BigDecimal b);

	void setTimestamp(Date d);

	void setAtExchange(Exchange e);

}