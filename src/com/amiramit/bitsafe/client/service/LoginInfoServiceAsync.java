package com.amiramit.bitsafe.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginInfoServiceAsync {

	void getLoginInfo(AsyncCallback<UILoginInfo> callback);

	void logout(AsyncCallback<String> callback);

}
