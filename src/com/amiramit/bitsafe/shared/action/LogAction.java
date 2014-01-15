package com.amiramit.bitsafe.shared.action;

import com.amiramit.bitsafe.shared.UIVerifyException;

public class LogAction extends Action {
	private static final long serialVersionUID = 1L;

	@Override
	public void verify() throws UIVerifyException {
		// no data - no need to verify
	}

	@Override
	public String toString() {
		return "write advice in server log";
	}
}
