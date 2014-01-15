package com.amiramit.bitsafe.client.dto;

import java.math.BigDecimal;

import com.amiramit.bitsafe.shared.FieldVerifier;
import com.amiramit.bitsafe.shared.UIVerifyException;

public class HistoryTickerDTO implements BasicDTO {

	private static final long serialVersionUID = 1L;

	private Long timestamp;

	private BigDecimal last;

	public HistoryTickerDTO(final Long timestamp, final BigDecimal last) {
		super();
		this.timestamp = timestamp;
		this.last = last;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public BigDecimal getLast() {
		return last;
	}

	@Override
	public void verify() throws UIVerifyException {
		FieldVerifier.verifyNotNull(timestamp);
		FieldVerifier.verifyNotNull(last);
	}
}
