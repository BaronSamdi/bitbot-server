package com.amiramit.bitsafe.server;

import static com.amiramit.bitsafe.server.OfyService.ofy;

import java.util.logging.Logger;

import com.amiramit.bitsafe.server.rule.ActionRunner;
import com.amiramit.bitsafe.server.rule.Rule;
import com.amiramit.bitsafe.server.rule.TriggerChecker;
import com.amiramit.bitsafe.shared.trigger.TriggerAdvice;
import com.google.appengine.api.taskqueue.DeferredTask;

class DoRuleTriggerTask implements DeferredTask {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(DoRuleTriggerTask.class
			.getName());

	private Long dbKey;

	public DoRuleTriggerTask(final Long dbKey) {
		this.dbKey = dbKey;
	}

	@Override
	public void run() {
		final Rule dbRule = ofy().load().type(Rule.class).id(dbKey).safe();

		// Check get active again - we lazy load so something might have
		// changed ...
		if (dbRule.getActive()) {
			final TriggerAdvice triggerAdvice = TriggerChecker.check(dbRule
					.getTrigger());
			if (!triggerAdvice.equals(TriggerAdvice.NONE)) {
				LOG.severe("ProcessRulesServlet processing rule: " + dbRule
						+ " - triggering; advice " + triggerAdvice);
				ActionRunner.run(dbRule.getAction(), dbRule, triggerAdvice);
			} else {
				LOG.severe("ProcessRulesServlet processing rule: " + dbRule
						+ " not triggered any more!");
			}
		} else {
			LOG.severe("ProcessRulesServlet processing rule: " + dbRule
					+ " not active any more!");
		}
	}
};