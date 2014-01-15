package com.amiramit.bitsafe.server;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.Exchange;
import com.googlecode.objectify.cmd.Query;
import com.xeiam.xchange.dto.marketdata.Ticker;

public abstract class BLHistoryTicker {
	private static final Logger LOG = Logger.getLogger(BLHistoryTicker.class
			.getName());

	public abstract BigDecimal getLast();

	public abstract Long getTimestamp();

	public static BLHistoryTicker fromPrice(final Exchange exchange,
			final CurrencyPair currencyPair, final Long timestamp,
			final BigDecimal last) {
		switch (exchange) {
		case MtGox:
			switch (currencyPair) {
			case BTCUSD:
				return new BLHistoryTickerMtgoxBTCUSD(timestamp, last);
			default:
				break;
			}

		default:
			break;
		}

		final String msg = "Unrecognized exchange / CurrencyPair: " + exchange
				+ "/" + currencyPair;
		LOG.severe(msg);
		throw new RuntimeException(msg);
	}

	public static BLHistoryTicker fromTicker(final Exchange exchange,
			final CurrencyPair currencyPair, final Ticker ticker) {
		return BLHistoryTicker.fromPrice(exchange, currencyPair, ticker
				.getTimestamp().getTime(), ticker.getLast().getAmount());
	}

	public static List<? extends BLHistoryTicker> getHistory(
			final Exchange exchange, final CurrencyPair currencyPair,
			final Date fromDate, final Date toDate) {
		final BLHistoryTicker example = fromPrice(exchange, currencyPair, null,
				null);

		final Query<? extends BLHistoryTicker> query = OfyService.ofy().load()
				.type(example.getClass()).filterKey(">", fromDate.getTime());
		if (toDate != null) {
			query.filterKey("<", toDate.getTime());
		}
		return query.list();
	}
}
