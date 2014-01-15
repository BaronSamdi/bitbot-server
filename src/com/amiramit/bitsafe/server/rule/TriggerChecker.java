package com.amiramit.bitsafe.server.rule;

import java.util.logging.Logger;

import com.amiramit.bitsafe.server.BLLastTicker;
import com.amiramit.bitsafe.shared.trigger.PriceTrigger;
import com.amiramit.bitsafe.shared.trigger.Trigger;
import com.amiramit.bitsafe.shared.trigger.TriggerAdvice;

public final class TriggerChecker {

	private static final Logger LOG = Logger.getLogger(TriggerChecker.class
			.getName());

	private TriggerChecker() {
	};

	public static TriggerAdvice check(final PriceTrigger trigger) {
		final BLLastTicker lastTicker = BLLastTicker.getLastTicker(
				trigger.getExchange(), trigger.getCurrencyPair());

		final TriggerAdvice res = trigger.getType().check(lastTicker.getLast(),
				trigger.getAtPrice()) ? trigger.getAdvice()
				: TriggerAdvice.NONE;

		LOG.info("PriceTrigger: " + trigger.toString() + " at ticker: "
				+ lastTicker + " return advice " + res);
		return res;
	}

	public static TriggerAdvice check(final Trigger trigger) {
		if (trigger instanceof PriceTrigger) {
			return check((PriceTrigger) trigger);
		} else {
			LOG.severe("Method not yet implemented for class: "
					+ trigger.getClass().getName());
		}
		return TriggerAdvice.NONE;
	}
}
