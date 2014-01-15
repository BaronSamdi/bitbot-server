package com.amiramit.bitsafe.client.service;

import com.amiramit.bitsafe.client.NotLoggedInException;
import com.amiramit.bitsafe.client.dto.RuleDTO;
import com.amiramit.bitsafe.shared.UIVerifyException;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.XsrfProtectedService;

@RemoteServiceRelativePath("rule")
public interface RuleService extends XsrfProtectedService {
	Long addRule(RuleDTO rule) throws NotLoggedInException, UIVerifyException;

	void removeRule(Long id) throws NotLoggedInException, UIVerifyException;

	RuleDTO[] getRules() throws NotLoggedInException, UIVerifyException;

	void updateRule(RuleDTO uiRule) throws NotLoggedInException,
			UIVerifyException;
}
