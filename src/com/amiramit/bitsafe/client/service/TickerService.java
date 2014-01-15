package com.amiramit.bitsafe.client.service;

import java.sql.Date;

import com.amiramit.bitsafe.client.dto.HistoryTickerDTO;
import com.amiramit.bitsafe.client.uitypes.uibeans.UITicker;
import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.Exchange;
import com.amiramit.bitsafe.shared.UIVerifyException;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.XsrfProtectedService;

@RemoteServiceRelativePath("ticker")
public interface TickerService extends XsrfProtectedService {
	UITicker getLast(Exchange exchange, CurrencyPair currencyPair)
			throws UIVerifyException;

	HistoryTickerDTO[] getHistory(Exchange exchange, CurrencyPair currencyPair,
			Date fromDate, Date toDate) throws UIVerifyException;
}
