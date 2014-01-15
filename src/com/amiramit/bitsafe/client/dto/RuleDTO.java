package com.amiramit.bitsafe.client.dto;

import java.util.Date;

import com.amiramit.bitsafe.shared.FieldVerifier;
import com.amiramit.bitsafe.shared.UIVerifyException;
import com.amiramit.bitsafe.shared.action.Action;
import com.amiramit.bitsafe.shared.trigger.Trigger;

public final class RuleDTO implements BasicDTO {

	private static final long serialVersionUID = 1L;

	private Long key;

	private Date createDate;

	private String description;

	private boolean active;

	private Trigger trigger;

	private Action action;

	// Just for GWT serialize
	@SuppressWarnings("unused")
	private RuleDTO() {
	}

	// Create rule DTO from UI context (i.e. new rule)
	public RuleDTO(final String description, final boolean active,
			final Trigger trigger, final Action action) {
		this(null, null, description, active, trigger, action);
	}

	// Create rule DTO from server context (i.e. returned rule)
	public RuleDTO(final Long key, final Date createDate,
			final String description, final boolean active,
			final Trigger trigger, final Action action) {
		this.key = key;
		this.createDate = createDate;
		this.description = description;
		this.active = active;
		this.trigger = trigger;
		this.action = action;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(final Long key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	public Trigger getTrigger() {
		return trigger;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public String toString() {
		return "UserRule [key=" + key + ", createDate=" + createDate
				+ ", name=" + description + ", active=" + active + ",trigger="
				+ trigger + ", action=" + action + "]";
	}

	@Override
	public void verify() throws UIVerifyException {
		verify(true);
	}

	public void verify(final boolean isNew) throws UIVerifyException {
		if (isNew) {
			FieldVerifier.verifyIsNull(key);
			FieldVerifier.verifyIsNull(createDate);
		} else {
			FieldVerifier.verifyNotNull(key);
			FieldVerifier.verifyNotNull(createDate);
		}
		FieldVerifier.verifyNotNull(description);
		// No need to verify active
		trigger.verify();
		action.verify();
	}
}
