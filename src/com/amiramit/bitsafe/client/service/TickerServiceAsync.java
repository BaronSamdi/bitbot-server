package com.amiramit.bitsafe.client.service;

import java.sql.Date;

import com.amiramit.bitsafe.client.dto.HistoryTickerDTO;
import com.amiramit.bitsafe.client.uitypes.uibeans.UITicker;
import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.Exchange;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TickerServiceAsync {

	void getHistory(Exchange exchange, CurrencyPair currencyPair,
			Date fromDate, Date toDate,
			AsyncCallback<HistoryTickerDTO[]> callback);

	void getLast(Exchange exchange, CurrencyPair currencyPair,
			AsyncCallback<UITicker> callback);

}
