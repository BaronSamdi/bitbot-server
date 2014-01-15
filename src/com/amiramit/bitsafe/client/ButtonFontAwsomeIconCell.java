package com.amiramit.bitsafe.client;

import java.util.logging.Logger;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class ButtonFontAwsomeIconCell extends ButtonCell {
	private static final Logger LOG = Logger
			.getLogger(ButtonFontAwsomeIconCell.class.getName());

	private String fontAwsomeIconName;
	private String altText;

	public ButtonFontAwsomeIconCell(final String fontAwsomeIconName,
			final String altText) {
		super();
		this.fontAwsomeIconName = fontAwsomeIconName;
		this.altText = altText;
	}

	@Override
	public void render(final com.google.gwt.cell.client.Cell.Context context,
			final String data, final SafeHtmlBuilder sb) {
		// always check for null data!
		final SafeHtml html = SafeHtmlUtils
				.fromTrustedString("<a class=\"btn btn-default\"><i title=\""
						+ altText + "\" class=\"fa " + fontAwsomeIconName
						+ "\"></i></a>");
		sb.append(html);
	}
}