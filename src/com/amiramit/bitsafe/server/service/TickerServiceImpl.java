package com.amiramit.bitsafe.server.service;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

import com.amiramit.bitsafe.client.dto.HistoryTickerDTO;
import com.amiramit.bitsafe.client.service.TickerService;
import com.amiramit.bitsafe.client.uitypes.uibeans.UITicker;
import com.amiramit.bitsafe.server.BLHistoryTicker;
import com.amiramit.bitsafe.server.BLLastTicker;
import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.Exchange;
import com.amiramit.bitsafe.shared.FieldVerifier;
import com.amiramit.bitsafe.shared.UIVerifyException;
import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;

@SuppressWarnings("serial")
public class TickerServiceImpl extends XsrfProtectedServiceServlet implements
		TickerService {

	private static final Logger LOG = Logger.getLogger(TickerServiceImpl.class
			.getName());

	@Override
	public UITicker getLast(final Exchange exchange,
			final CurrencyPair currencyPair) throws UIVerifyException {
		FieldVerifier.verifyNotNull(exchange);
		FieldVerifier.verifyNotNull(currencyPair);

		return BLLastTicker.getLastTicker(exchange, currencyPair)
				.toUITickerBean().as();
	}

	@Override
	public HistoryTickerDTO[] getHistory(final Exchange exchange,
			final CurrencyPair currencyPair, final Date fromDate,
			final Date toDate) throws UIVerifyException {
		FieldVerifier.verifyNotNull(exchange);
		FieldVerifier.verifyNotNull(currencyPair);
		FieldVerifier.verifyDateRange(fromDate, toDate);

		final List<? extends BLHistoryTicker> tickerHistory = BLHistoryTicker
				.getHistory(exchange, currencyPair, fromDate, toDate);
		final HistoryTickerDTO[] result = new HistoryTickerDTO[tickerHistory
				.size()];

		int i = 0;
		for (final BLHistoryTicker item : tickerHistory) {
			result[i++] = new HistoryTickerDTO(item.getTimestamp(),
					item.getLast());
		}

		LOG.info("getHistory returns a total of " + i + " history items");

		return result;
	}

}
