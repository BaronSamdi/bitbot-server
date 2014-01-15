package com.amiramit.bitsafe.server;

import java.math.BigDecimal;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;

@Entity
@Cache
public class BLHistoryTickerMtgoxBTCUSD extends BLHistoryTicker {
	@Id
	private Long timestamp;

	@Serialize
	private BigDecimal last;

	/**
	 * This constructor exists for frameworks (e.g. Objectify) that require it
	 * for serialization purposes. It should not be called explicitly.
	 */
	@SuppressWarnings("unused")
	private BLHistoryTickerMtgoxBTCUSD() {
	}

	public BLHistoryTickerMtgoxBTCUSD(final Long timestamp,
			final BigDecimal last) {
		this.timestamp = timestamp;
		this.last = last;
	}

	@Override
	public BigDecimal getLast() {

		return last;
	}

	@Override
	public Long getTimestamp() {

		return timestamp;
	}

	@Override
	public String toString() {
		return "BLLastTicker [id=" + timestamp + ", last=" + last
				+ ", timestamp=" + timestamp + "]";
	}
}
