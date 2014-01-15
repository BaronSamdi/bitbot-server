package com.amiramit.bitsafe.client.dto;

import java.io.Serializable;

import com.amiramit.bitsafe.shared.UIVerifyException;

public interface BasicDTO extends Serializable {
	void verify() throws UIVerifyException;
}
