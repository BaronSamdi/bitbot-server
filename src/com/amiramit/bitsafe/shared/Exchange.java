package com.amiramit.bitsafe.shared;

public enum Exchange {	
	
	MtGox(new CurrencyPair[] { CurrencyPair.BTCUSD }, new String("Mt. Gox"));

	private CurrencyPair[] supportedCurrencyPairs;
	private String uiDispalyName;

	Exchange(final CurrencyPair[] supportedCurrencyPairs, String uiDisplayName) {
		this.supportedCurrencyPairs = supportedCurrencyPairs;
		this.uiDispalyName = uiDisplayName;
	}

	public CurrencyPair[] getSupportedCurrencyPairs() {
		return supportedCurrencyPairs;
	}

	public boolean isSupportedCurrencyPair(final CurrencyPair currencyPair) {
		for (final CurrencyPair cp : supportedCurrencyPairs) {
			if (cp.equals(currencyPair)) {
				return true;
			}
		}

		return false;
	}
	
	public String getUIDisplayName(){
		return uiDispalyName;
	}
}
