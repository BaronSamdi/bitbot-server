package com.amiramit.bitsafe.server.rule;

import java.util.logging.Logger;

import com.amiramit.bitsafe.client.uitypes.uibeans.UIBeanFactory;
import com.amiramit.bitsafe.client.uitypes.uibeans.UIRuleTriggerResult;
import com.amiramit.bitsafe.server.BLUser;
import com.amiramit.bitsafe.shared.action.Action;
import com.amiramit.bitsafe.shared.action.LogAction;
import com.amiramit.bitsafe.shared.trigger.TriggerAdvice;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;

public final class ActionRunner {
	private static final Logger LOG = Logger.getLogger(ActionRunner.class
			.getName());

	private ActionRunner() {
	};

	public static void run(final LogAction action, final Rule myRule,
			final TriggerAdvice triggerAdvice) {
		assert myRule.getAction().equals(action);
		// print to log, notify user, disable log

		// Since this rule has been triggered and we don't want it to
		// trigger again, make it inactive
		myRule.setActive(false);
		myRule.save();

		// Try to send rule trigger notification to user
		final BLUser blUser = BLUser.getUserFromId(myRule.getUserId());
		final String userChannelId = blUser.getChannelClientID();

		if (userChannelId != null) {
			LOG.info("Action: " + action.toString() + " triggered. Advice: "
					+ triggerAdvice + ". Notifing user on channel.");
			final com.amiramit.bitsafe.client.uitypes.uibeans.UIBeanFactory factory = AutoBeanFactorySource
					.create(UIBeanFactory.class);

			final AutoBean<UIRuleTriggerResult> triggerResultBean = factory
					.ruleTriggerResult();
			triggerResultBean.as().setRuleId(myRule.getKey());

			final String beanPayload = AutoBeanCodex.encode(triggerResultBean)
					.getPayload();
			ChannelServiceFactory.getChannelService().sendMessage(
					new ChannelMessage(userChannelId, beanPayload));
		} else {
			LOG.info("Action: " + action.toString()
					+ " triggered. User not connected.");
		}
	}

	public static void run(final Action action, final Rule myRule,
			final TriggerAdvice triggerAdvice) {
		if (action instanceof LogAction) {
			run((LogAction) action, myRule, triggerAdvice);
		} else {
			LOG.severe("Method not yet implemented for class: "
					+ action.getClass().getName());
		}
	}
}
