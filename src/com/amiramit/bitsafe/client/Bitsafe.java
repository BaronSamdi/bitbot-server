package com.amiramit.bitsafe.client;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.ButtonType;

import com.amiramit.bitsafe.client.channel.Channel;
import com.amiramit.bitsafe.client.channel.ChannelListener;
import com.amiramit.bitsafe.client.dto.RuleDTO;
import com.amiramit.bitsafe.client.service.LoginInfoService;
import com.amiramit.bitsafe.client.service.LoginInfoServiceAsync;
import com.amiramit.bitsafe.client.service.PushService;
import com.amiramit.bitsafe.client.service.PushServiceAsync;
import com.amiramit.bitsafe.client.service.RuleService;
import com.amiramit.bitsafe.client.service.RuleServiceAsync;
import com.amiramit.bitsafe.client.service.UILoginInfo;
import com.amiramit.bitsafe.client.uitypes.uibeans.UIBeanFactory;
import com.amiramit.bitsafe.client.uitypes.uibeans.UITicker;
import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.UIVerifyException;
import com.amiramit.bitsafe.shared.action.LogAction;
import com.amiramit.bitsafe.shared.trigger.PriceTrigger;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellTable.Style;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.HasRpcToken;
import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.XsrfToken;
import com.google.gwt.user.client.rpc.XsrfTokenService;
import com.google.gwt.user.client.rpc.XsrfTokenServiceAsync;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Bitsafe implements EntryPoint {
	private static final Logger LOG = Logger.getLogger(Bitsafe.class.getName());

	/**
	 * Create a remote service proxy to talk to the server-side.
	 */
	private final RuleServiceAsync ruleService = GWT.create(RuleService.class);
	private final PushServiceAsync pushService = GWT.create(PushService.class);
	private final LoginInfoServiceAsync loginInfoService = GWT
			.create(LoginInfoService.class);
	private final UIBeanFactory uiBeanFactory = GWT.create(UIBeanFactory.class);
	private final CellTableResources cellTableResources = GWT
			.create(CellTableResources.class);

	private final Label priceLabel = new Label("Waiting for server ...");
	private final Label lastUpdatedLabel = new Label("Waiting for server ...");
	private final Label errorLabel = new Label("");

	private UILoginInfo loginInfo = null;
	private Anchor signOutLink = new Anchor("Sign Out");
	
	CreateStopLossUITemplate stpLoss;
	CreateStopLimitUITemplate stpLimit;

	// Create a list data provider.
	private ListDataProvider<RuleDTO> rulesDataProvider;
	private CellTable<RuleDTO> rulesTable;

	interface CellTableResources extends CellTable.Resources {
		@Override
		@Source("css/CellTableStyle.css")
		// { CellTable.Style.DEFAULT_CSS, "css/CellTableStyle.css" })
		Style cellTableStyle();
	}

	private void initRulesTable() {
		// Key provider for the table
		final ProvidesKey<RuleDTO> keyProvider = new ProvidesKey<RuleDTO>() {
			@Override
			public Object getKey(final RuleDTO item) {
				// Always do a null check as per CellTable example
				return (item == null) ? null : item.getKey();
			}
		};

		// TODO: create infiniscroll with page size
		rulesTable = new CellTable<RuleDTO>(15, cellTableResources,
				keyProvider, null, false, true);

		rulesTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		// Add a column to show the "if".
		final TextColumn<RuleDTO> ifColumn = new TextColumn<RuleDTO>() {
			@Override
			public String getValue(final RuleDTO object) {
				return "if";
			}
		};
		rulesTable.addColumn(ifColumn);

		// Add a column to show the "trigger".
		final TextColumn<RuleDTO> triggerColumn = new TextColumn<RuleDTO>() {
			@Override
			public String getValue(final RuleDTO object) {
				return object.getTrigger().toString();
			}
		};
		rulesTable.addColumn(triggerColumn);

		// Add a column to show the "then".
		final TextColumn<RuleDTO> thenColumn = new TextColumn<RuleDTO>() {
			@Override
			public String getValue(final RuleDTO object) {
				return "then";
			}
		};
		rulesTable.addColumn(thenColumn);

		// Add a column to show the "action".
		final TextColumn<RuleDTO> actionColumn = new TextColumn<RuleDTO>() {
			@Override
			public String getValue(final RuleDTO object) {
				return object.getAction().toString();
			}
		};
		rulesTable.addColumn(actionColumn);

		// Add a column for active
		addRulesTableIconColumn("fa-power-off", "Turn Rule On",
				new FieldUpdater<RuleDTO, String>() {
					@Override
					public void update(final int index, final RuleDTO rule,
							final String value) {
						toggleRuleActive(rule);
					}
				});

		// Add a column for edit
		addRulesTableIconColumn("fa-gear", "Edit rule",
				new FieldUpdater<RuleDTO, String>() {
					@Override
					public void update(final int index, final RuleDTO rule,
							final String value) {
						Window.alert("You clicked EDIT rule " + rule.getKey()
								+ " at index " + index + "; value = " + value);
					}
				});

		// Add a column for delete
		addRulesTableIconColumn("fa-times", "Delete rule",
				new FieldUpdater<RuleDTO, String>() {
					@Override
					public void update(final int index, final RuleDTO rule,
							final String value) {
						removeRule(rule);
					}
				});

		// Do not refresh the headers and footers every time the data is
		// updated.
		rulesTable.setAutoHeaderRefreshDisabled(true);
		rulesTable.setAutoFooterRefreshDisabled(true);

		// TODO: Create a Pager to control the table with infiniscroll

		// Add the rules table to its dataProvider.
		rulesDataProvider = new ListDataProvider<RuleDTO>(keyProvider);
		rulesDataProvider.addDataDisplay(rulesTable);

		initRuleTableStyle();
	}

	private void initRuleTableStyle() {
		rulesTable.setStyleName("table table-striped");

		// THIS NO WORK!! WHY ???
		rulesTable.setRowStyles(new RowStyles<RuleDTO>() {

			@Override
			public String getStyleNames(final RuleDTO row, final int rowIndex) {
				if (row.getActive()) {
					return "text-success";
				}

				return "text-info";
			}
		});
	}

	private void addRulesTableIconColumn(final String fontAwsomeIconName,
			final String altText,
			final FieldUpdater<RuleDTO, String> fieldUpdater) {
		final Column<RuleDTO, String> newColumn = new Column<RuleDTO, String>(
				new ButtonFontAwsomeIconCell(fontAwsomeIconName, altText)) {
			@Override
			public String getValue(final RuleDTO object) {
				return null;
			}
		};

		newColumn.setFieldUpdater(fieldUpdater);
		rulesTable.addColumn(newColumn);
	}

	private void toggleRuleActive(final RuleDTO rule) {
		// Assume the operation will succeed, and re-add rule if it fails to get
		// faster UI response.
		rule.setActive(!rule.getActive());
		rulesTable.redraw();

		// TODO: make a specific command instead of sending the full rule again?
		ruleService.updateRule(rule, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(final Void result) {
			}

			@Override
			public void onFailure(final Throwable caught) {
				rule.setActive(!rule.getActive());
				rulesTable.redraw();
				handleError("ruleService.removeRule", caught);
			}
		});
	}

	private void removeRule(final RuleDTO rule) {
		// Assume the operation will succeed, and re-add rule if it fails to get
		// faster UI response.
		rulesDataProvider.getList().remove(rule);

		ruleService.removeRule(rule.getKey(), new AsyncCallback<Void>() {

			@Override
			public void onSuccess(final Void result) {
			}

			@Override
			public void onFailure(final Throwable caught) {
				rulesDataProvider.getList().add(rule);
				handleError("ruleService.removeRule", caught);
			}
		});
	}

	private void addRule(CreateNewRuleBaseUI ruleUI) {
		final RuleDTO newRule = new RuleDTO("Test", true, new PriceTrigger(
				ruleUI.getSelectedExchange(), CurrencyPair.BTCUSD, ruleUI.getSelectedPriceTriggerType(),
				ruleUI.getPriceTextBoxValue(ruleUI.getType()), ruleUI.getSelectedTriggerAdvice()), new LogAction());

		// Assume the operation will succeed, and remove rule if it fails to get
		// faster UI response.
		rulesDataProvider.getList().add(newRule);
		try {
			newRule.verify();
		} catch (UIVerifyException e) {
			e.printStackTrace();
		}
		ruleService.addRule(newRule, new AsyncCallback<Long>() {
			@Override
			public void onSuccess(final Long key) {
				newRule.setKey(key);
				// TODO: does not match server side; is it important?
				newRule.setCreateDate(new Date());
			}

			@Override
			public void onFailure(final Throwable caught) {
				rulesDataProvider.getList().remove(newRule);
				handleError("ruleService.addRule", caught);
			}
		});
	}

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		getXSRFToken();
	}

	private void getLoginInfo() {
		// Check login status using login service.
		loginInfoService.getLoginInfo(new AsyncCallback<UILoginInfo>() {
			@Override
			public void onFailure(final Throwable error) {
				handleError("loginInfoService.getLoginInfo", error);
			}

			@Override
			public void onSuccess(final UILoginInfo result) {
				loginInfo = result;
				loadWelcomePage();
			}
		});
	}

	private void getXSRFToken() {
		final XsrfTokenServiceAsync xsrf = (XsrfTokenServiceAsync) GWT
				.create(XsrfTokenService.class);
		((ServiceDefTarget) xsrf).setServiceEntryPoint(GWT.getModuleBaseURL()
				+ "xsrf");
		final AsyncCallback<XsrfToken> asyncCallback = new AsyncCallback<XsrfToken>() {

			@Override
			public void onSuccess(final XsrfToken token) {
				((HasRpcToken) ruleService).setRpcToken(token);
				((HasRpcToken) pushService).setRpcToken(token);
				((HasRpcToken) loginInfoService).setRpcToken(token);

				getLoginInfo();
			}

			@Override
			public void onFailure(final Throwable caught) {
				try {
					throw caught;
				} catch (final RpcTokenException e) {
					handleError("xsrf.getNewXsrfToken RpcTokenException", e);
				} catch (final Throwable e) {
					handleError("xsrf.getNewXsrfToken Throwable", e);
				}
			}
		};
		xsrf.getNewXsrfToken(asyncCallback);
	}

	private void loadWelcomePage() {
		initRulesTable();

		RootPanel.get("emailContainer").add(
				new InlineLabel(" " + loginInfo.getEmailAddress()));
		RootPanel.get("rulesContainer").add(rulesTable);
		RootPanel.get("priceContainer").add(priceLabel);
		RootPanel.get("lastUpdatedContainer").add(lastUpdatedLabel);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		//stpLoss = new CreateStopLossUITemplate();
		stpLimit = new CreateStopLimitUITemplate();
		
		/*
		RootPanel.get("dropDownButtonGroup1").add(stpLoss.getExchangeButtonGroup());
		RootPanel.get("dropDownButtonGroup2").add(stpLoss.getPriceValueInputGroup());
		RootPanel.get("dropDownButtonGroup3").add(stpLoss.getActionAmountInputGroup());
		RootPanel.get("dropDownButtonGroup4").add(stpLoss.getNotifyMeByInputGroup());
		*/
		
		///*
		RootPanel.get("dropDownButtonGroup1").add(stpLimit.getExchangeButtonGroup());
		RootPanel.get("dropDownButtonGroup2").add(stpLimit.getPriceRangeInputGroup());
		RootPanel.get("dropDownButtonGroup3").add(stpLimit.getActionAmountInputGroup());
		RootPanel.get("dropDownButtonGroup4").add(stpLimit.getNotifyMeByInputGroup());
		//*/
		
		final Button saveRuleButton = new Button("Save Changes");
		saveRuleButton.setType(ButtonType.PRIMARY);
		saveRuleButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				addRule(stpLimit);
				//addRule(stpLoss);
			}
		});
		RootPanel.get("addNewRuleButton").add(saveRuleButton);
		

		// Set up sign out hyperlink.
		signOutLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				loginInfoService.logout(new AsyncCallback<String>() {

					@Override
					public void onFailure(final Throwable caught) {
						handleError("loginInfoService.logout");
					}

					@Override
					public void onSuccess(final String result) {
						Window.Location.replace(result);
					}
				});
			}
		});
		RootPanel.get("ticker").add(signOutLink);

		// Setup channel listener for ticker information
		final Channel incommingChannel = new Channel();
		incommingChannel.addChannelListener(new ChannelListener() {

			@Override
			public void onOpen() {
				LOG.info("tickerChannel open");
			}

			@Override
			public void onMessage(final String tickerAsJson) {
				LOG.info("incommingChannel onMessage: " + tickerAsJson);
				final UITicker ticker = AutoBeanCodex.decode(uiBeanFactory,
						UITicker.class, tickerAsJson).as();

				priceLabel.setText(ticker.getExchange() + ": "
						+ ticker.getLast().toString());
				lastUpdatedLabel.setText(DateTimeFormat.getFormat(
						PredefinedFormat.DATE_TIME_SHORT).format(
						ticker.getTimestamp()));
				errorLabel.setText("");
			}

			@Override
			public void onError(final int code, final String description) {
				handleError("incommingChannel error code: " + code
						+ " description: " + description);
			}

			@Override
			public void onClose() {
				LOG.info("incommingChannel onClose(), trying to open a new one ...");
				pushService.getChannelKey(new AsyncCallback<String>() {

					@Override
					public void onFailure(final Throwable caught) {
						handleError("pushService.getChannelKey", caught);
					}

					@Override
					public void onSuccess(final String result) {
						LOG.info("Success. joining ...");
						incommingChannel.join(result);
					}
				});
			}
		});

		LOG.info("Tring to join channel: " + loginInfo.getChannelToken());
		incommingChannel.join(loginInfo.getChannelToken());

		loadRules();
	}

	private void handleError(final String error) {
		LOG.severe(error);
		errorLabel.setText(error);
	}

	private void handleError(final String location, final Throwable error) {
		handleError("at: " + location + " error: " + error.toString());
		if (error instanceof NotLoggedInException) {
			// TODO: redirect to login page
			Window.Location.replace("/");
		}
	}

	private void loadRules() {
		ruleService.getRules(new AsyncCallback<RuleDTO[]>() {
			@Override
			public void onFailure(final Throwable error) {
				handleError("ruleService.getRules", error);
			}

			@Override
			public void onSuccess(final RuleDTO[] rules) {
				final List<RuleDTO> list = rulesDataProvider.getList();
				list.clear();
				list.addAll(Arrays.asList(rules));
			}
		});
	}
}
