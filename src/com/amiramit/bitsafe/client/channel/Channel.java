package com.amiramit.bitsafe.client.channel;

import java.util.ArrayList;
import java.util.List;

public class Channel {
	private final List<ChannelListener> channelListeners;

	public Channel() {
		channelListeners = new ArrayList<ChannelListener>();
	}

	private void onMessage(final String message) {
		for (int i = 0; i < channelListeners.size(); i++) {
			channelListeners.get(i).onMessage(message);
		}
	}

	private void onOpen() {
		for (int i = 0; i < channelListeners.size(); i++) {
			channelListeners.get(i).onOpen();
		}
	}

	private void onError(final int code, final String description) {
		for (int i = 0; i < channelListeners.size(); i++) {
			channelListeners.get(i).onError(code, description);
		}
	}

	private void onClose() {
		for (int i = 0; i < channelListeners.size(); i++) {
			channelListeners.get(i).onClose();
		}
	}

	public void addChannelListener(final ChannelListener channelListener) {
		channelListeners.add(channelListener);
	}

	public void removeChannelListener(final ChannelListener channelListener) {
		channelListeners.remove(channelListener);
	}

	public native void join(final String channelKey)
	/*-{
		var channel = new $wnd.goog.appengine.Channel(channelKey);
		var socket = channel.open();
		var self = this;

		socket.onmessage = function(evt) {
			var data = evt.data;
			self.@com.amiramit.bitsafe.client.channel.Channel::onMessage(Ljava/lang/String;)(data);
		};

		socket.onopen = function() {
			self.@com.amiramit.bitsafe.client.channel.Channel::onOpen()();
		};

		socket.onerror = function(error) {
			self.@com.amiramit.bitsafe.client.channel.Channel::onError(ILjava/lang/String;)(error.code, error.description);
		};

		socket.onclose = function() {
			self.@com.amiramit.bitsafe.client.channel.Channel::onClose()();
		};
	}-*/;
}
