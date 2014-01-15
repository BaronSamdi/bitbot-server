package com.amiramit.bitsafe.shared.trigger;

import java.math.BigDecimal;

import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.Exchange;
import com.amiramit.bitsafe.shared.FieldVerifier;
import com.amiramit.bitsafe.shared.UIVerifyException;

public class PriceTrigger extends Trigger {
	private static final long serialVersionUID = 1L;

	private PriceTriggerType type;
	private BigDecimal atPrice;
	private TriggerAdvice advice;

	// For GWT serialization
	@SuppressWarnings("unused")
	private PriceTrigger() {
		super();
	}

	public PriceTrigger(final Exchange exchange,
			final CurrencyPair currencyPair, final PriceTriggerType type,
			final BigDecimal atPrice, final TriggerAdvice advice) {
		super(exchange, currencyPair);
		this.type = type;
		this.atPrice = atPrice;
		this.advice = advice;
	}

	public PriceTriggerType getType() {
		return type;
	}

	public BigDecimal getAtPrice() {
		return atPrice;
	}

	@Override
	public void verify() throws UIVerifyException {
		super.verify();

		FieldVerifier.verifyNotNull(type);

		if (atPrice.compareTo(BigDecimal.ZERO) <= 0) {
			throw new UIVerifyException("PriceTriggerDTO price <= 0");
		}
	}

	@Override
	public String toString() {
		return "price for " + getCurrencyPair().getBaseCurrency() + " at "
				+ getExchange() + " " + type.toUiString() + " " + atPrice + " "
				+ getCurrencyPair().getCounterCurrency() + "[advice = "
				+ advice + "]";
	}

	public TriggerAdvice getAdvice() {
		return advice;
	}

}
