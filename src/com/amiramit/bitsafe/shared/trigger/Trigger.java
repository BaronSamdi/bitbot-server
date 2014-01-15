package com.amiramit.bitsafe.shared.trigger;

import com.amiramit.bitsafe.client.dto.BasicDTO;
import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.Exchange;
import com.amiramit.bitsafe.shared.FieldVerifier;
import com.amiramit.bitsafe.shared.UIVerifyException;

public abstract class Trigger implements BasicDTO {
	private static final long serialVersionUID = 1L;

	private Exchange exchange;
	private CurrencyPair currencyPair;

	// For GWT serialization
	protected Trigger() {
	}

	public Trigger(final Exchange exchange, final CurrencyPair currencyPair) {
		super();
		this.exchange = exchange;
		this.currencyPair = currencyPair;
	}

	public Exchange getExchange() {
		return exchange;
	}

	public CurrencyPair getCurrencyPair() {
		return currencyPair;
	}

	@Override
	public void verify() throws UIVerifyException {
		FieldVerifier.verifyNotNull(exchange);
		if (!exchange.isSupportedCurrencyPair(currencyPair)) {
			throw new UIVerifyException("Currency pair: " + currencyPair
					+ " is not supported at exchange: " + exchange);
		}
	}
}
