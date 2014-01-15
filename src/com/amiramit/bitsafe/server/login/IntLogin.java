package com.amiramit.bitsafe.server.login;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amiramit.bitsafe.server.BLUser;
import com.amiramit.bitsafe.shared.FieldVerifier;
import com.amiramit.bitsafe.shared.UIVerifyException;

public class IntLogin extends LoginProvider {
	private static final Logger LOG = Logger
			.getLogger(IntLogin.class.getName());

	@Override
	public void doLoginCallback(final HttpServletRequest request,
			final HttpServletResponse response, final HttpSession session,
			final String afterLoginUrl) throws IOException, UIVerifyException {
		// Login / registration are all done in first stage
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	@Override
	public void doLoginFirstStage(final HttpServletRequest request,
			final HttpServletResponse response, final HttpSession session,
			final String afterLoginUrl, final String callbackUrl)
			throws IOException, UIVerifyException {
		final boolean isNew = request.getParameter("isNew") != null;
		String email = request.getParameter("email");
		final String userId = request.getParameter("userId");
		final String candidate = request.getParameter("candidate");
		FieldVerifier.verifyString(candidate);
		if (email != null && email.isEmpty()) {
			email = null;
		}
		final SocialUser socialUser = new SocialUser(userId, email);
		BLUser user = socialUser.getExistingBLUser();
		if (isNew) {
			if (user != null) {
				LOG.severe("Got login request from user; but user id already exists");
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			user = socialUser.newBLUser(candidate);
			LOG.info("Got login request from new user: " + user);
		} else {
			if (user == null || !user.checkPassword(candidate)) {
				LOG.info("Got login request from user: " + user
						+ "; invalid user name or password");
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			LOG.info("Got login request from internal user: " + user);
		}

		doLogin(response, session, user, afterLoginUrl);
	}
}
