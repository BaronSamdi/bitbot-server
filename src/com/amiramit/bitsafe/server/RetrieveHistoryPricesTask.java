package com.amiramit.bitsafe.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.amiramit.bitsafe.shared.Currency;
import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.Exchange;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class RetrieveHistoryPricesTask implements DeferredTask {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger
			.getLogger(RetrieveHistoryPricesTask.class.getName());

	private Exchange exchangeInfo;
	private CurrencyPair currencyPair;
	private Date fromDate;
	private Date toDate;

	@Override
	public String toString() {
		return "RetrieveHistoryPricesTask [exchangeInfo=" + exchangeInfo
				+ ", currencyPair=" + currencyPair + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + "]";
	}

	public RetrieveHistoryPricesTask(final Exchange exchangeInfo,
			final CurrencyPair currencyPair, final Date fromDate,
			final Date toDate) {
		if (!currencyPair.getBaseCurrency().equals(Currency.BTC)) {
			throw new RuntimeException(
					"Only BTC base currency is supported, and got: "
							+ currencyPair);
		}

		this.exchangeInfo = exchangeInfo;
		this.currencyPair = currencyPair;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	@Override
	public void run() {
		// First, start another instance to run in another 20 minutes to get the
		// updated data for last 20 min, as bitcoincharts is delayed in 15
		// minutes ...
		final RetrieveHistoryPricesTask task = new RetrieveHistoryPricesTask(
				exchangeInfo, currencyPair, new Date(), null);
		final Queue queue = QueueFactory.getQueue("GetHistoryData");
		final TaskOptions taskOptions = TaskOptions.Builder.withPayload(task)
				.countdownMillis(1000 * 60 * 20);
		queue.add(taskOptions);

		// btcexYAD trades after 1303100000:
		// http://api.bitcoincharts.com/v1/trades.csv?symbol=btcexYAD&start=1303100000
		final String urlStr = "http://api.bitcoincharts.com/v1/trades.csv?symbol="
				+ exchangeInfo.name().toLowerCase()
				+ currencyPair.getCounterCurrency().name().toUpperCase()
				+ "&start=" + (fromDate.getTime() / 1000);
		try {
			final URL url = new URL(urlStr);
			final URLConnection urlConn = url.openConnection();
			final InputStreamReader inStream = new InputStreamReader(
					urlConn.getInputStream());
			final BufferedReader buffReader = new BufferedReader(inStream);
			String curLine;
			int i = 0;
			while ((curLine = buffReader.readLine()) != null) {
				++i;
				final int firstComma = curLine.indexOf(",");
				final int secondComma = curLine.indexOf(",", firstComma + 1);
				if (firstComma == -1 || secondComma == -1) {
					LOG.severe("Error parsing line: " + curLine
							+ ": firstComma=" + firstComma + " secondComma="
							+ secondComma);
					break;
				}

				final String dateStr = curLine.substring(0, firstComma);
				final String priceStr = curLine.substring(firstComma + 1,
						secondComma);
				final String amountStr = curLine.substring(secondComma + 1);

				final long timestamp = Long.parseLong(dateStr) * 1000;
				final BigDecimal price = new BigDecimal(priceStr);
				// Just for sanity
				@SuppressWarnings("unused")
				final BigDecimal amount = new BigDecimal(amountStr);

				final BLHistoryTicker hTicker = BLHistoryTicker.fromPrice(
						exchangeInfo, currencyPair, timestamp, price);

				OfyService.ofy().save().entity(hTicker);
			}
			buffReader.close();
			inStream.close();
			LOG.info(this.toString() + ": Added " + i + " 	records to history");
		} catch (final IOException e) {
			LOG.log(Level.SEVERE, this.toString()
					+ ": Failed to get history data from URL: " + urlStr, e);
			e.printStackTrace();
		}
	}
}
