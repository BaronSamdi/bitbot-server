package com.amiramit.bitsafe.shared.trigger;

import java.math.BigDecimal;

public enum PriceTriggerType {
	LOWER, HIGHER;

	public boolean check(final BigDecimal lhs, final BigDecimal rhs) {
		switch (this) {
		case LOWER:
			return lhs.compareTo(rhs) < 0;
		case HIGHER:
			return lhs.compareTo(rhs) > 0;
		default:
			throw new RuntimeException("Unhandled PriceTriggerType: "
					+ this.name());
		}
	}

	public String toUiString() {
		switch (this) {
		case LOWER:
			return " drops below ";
		case HIGHER:
			return " rises above ";
		default:
			throw new RuntimeException("Unhandled PriceTriggerType: "
					+ this.name());
		}
	}

}
