package com.amiramit.bitsafe.client.ruleUI;

import java.math.BigDecimal;
import java.util.logging.Logger;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.InputGroup;
import org.gwtbootstrap3.client.ui.InputGroupAddon;
import org.gwtbootstrap3.client.ui.InputGroupButton;
import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ButtonDismiss;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import com.amiramit.bitsafe.client.Bitsafe;
import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.Exchange;
import com.amiramit.bitsafe.shared.trigger.PriceTriggerType;
import com.amiramit.bitsafe.shared.trigger.TriggerAdvice;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;



public abstract class CreateNewRuleBaseUI{
	
	private static final Logger LOG = Logger.getLogger(Bitsafe.class.getName());

	protected RuleUIType type;
	protected Exchange[] exchanges;
	protected CurrencyPair[] currencyPairs;
	protected String userMainEmailAddress;
	protected String userPhoneNumber;
	
	protected String[] actionStrings = {"Alert Me","Buy", "Sell"};
	protected String[] StopLossTriggerStrings = {"Price Drops Below","Price Rises Above"};
	protected String[] LimitTriggerStrings = {"Price Is in the Range"};
	protected String[] NotificationStrings = {"Notify me by Mail","Notify me by SMS"};
	
	// Exchanges drop down elements
	protected ButtonGroup exchangeButtonGroup;
	protected Button exchangeDropDownButton;
	protected DropDownMenu exchangeDropdownMenu;
	protected ListItem[] exchangeListItems;
	
	// Action amount elements
	protected InputGroup actionAmountInputGroup;
	protected InputGroupAddon actionAmountGroupAddon;
	protected TextBox actionAmountTextBox;
	protected String actionAmount;
		
	protected TextBox priceTextBox;
	protected TextBox belowPriceValueTextBox;
	protected TextBox abovePriceValueTextBox;
	
	
	
	
	// Selected action elements 
	protected ButtonGroup actionButtonGroup;
	protected Button actionDropDownButton;
	protected DropDownMenu actionDropdownMenu;
	protected ListItem[] actionListItems;
	
	// Notification elements 
	protected InputGroup notifyMeByInputGroup;
	protected InputGroupButton notifyMeByGroupButton;
	protected CheckBox notifyMeByMail;
	protected CheckBox notifyMeBySMS;
	protected Button saveStopLossRuleButton = new Button("Save Changes");
	protected Button saveStopLimitRuleButton = new Button("Save Changes");
	private int index;

	public CreateNewRuleBaseUI(final Exchange[] exchanges, final CurrencyPair[] currencyPairs,
			String userMainEmailAddres, String userPhoneNumber) {
		initSaveRulesbuttons();
		this.exchanges = exchanges;
		this.currencyPairs = currencyPairs;
		this.userMainEmailAddress = userMainEmailAddres;
		this.userPhoneNumber = userPhoneNumber;
	}
	
	public void initSaveRulesbuttons(){
		saveStopLossRuleButton.setType(ButtonType.PRIMARY);
		saveStopLossRuleButton.setDismiss(ButtonDismiss.MODAL);
		saveStopLossRuleButton.setEnabled(false);
		saveStopLimitRuleButton.setType(ButtonType.PRIMARY);
		saveStopLimitRuleButton.setDismiss(ButtonDismiss.MODAL);
		saveStopLimitRuleButton.setEnabled(false);
	}
	
	
	protected void initExchangeElement(){
		
		exchangeButtonGroup = new ButtonGroup();
		exchangeDropDownButton = new Button();
		exchangeDropDownButton.setType(ButtonType.INFO);		
		
		if(this.exchanges.length > 1){
			
			exchangeDropdownMenu = new DropDownMenu();
			int numOfExchanges = this.exchanges.length;
			this.exchangeListItems = new ListItem[numOfExchanges];			
			exchangeDropDownButton.setToggle(Toggle.DROPDOWN);
			
		
			this.index = 0;
			for (Exchange ex : Exchange.values()){
				
				exchangeListItems[index] = new ListItem();
				exchangeListItems[index].setText(ex.getUIDisplayName());
				
				setOnClickEvent(exchangeListItems[index], exchangeDropDownButton);
				
				exchangeDropdownMenu.add(exchangeListItems[index]);
				++index;
			}
			
			// Set Default exchange name to display
			exchangeDropDownButton.setText(exchangeListItems[0].getText());
			exchangeButtonGroup.add(exchangeDropdownMenu);
		}
		else{
			
			for (Exchange ex : Exchange.values()){
				exchangeDropDownButton.setText(ex.getUIDisplayName());
			}
		}
		
		exchangeButtonGroup.add(exchangeDropDownButton);
		
	}
	
	
	protected void initPriceBoxElement(final InputGroup priceInputGroup, final InputGroupAddon priceInputGroupAddon,
			final TextBox textBox, final String placeHolderStr){
		
		textBox.setWidth("93");
		priceInputGroupAddon.setText("USD$");
		if(placeHolderStr.equals(""))
			textBox.setPlaceholder("0.00");
		else
			textBox.setPlaceholder(placeHolderStr);
		
		textBox.addFocusHandler(new FocusHandler(){
			public void onFocus(final FocusEvent event) {
				
			}
			
		});
		
		textBox.addBlurHandler(new BlurHandler(){
			public void onBlur(final BlurEvent event) {
				if(!vlidateTextBoxField(textBox)){
					Window.alert("Client:: addBlurHandler - Please insert a valid amount ");
					LOG.info("Client:: addBlurHandler - value is "+textBox.getValue());
					
				}
			}
			
		});
		
		textBox.addValueChangeHandler(new ValueChangeHandler<String>(){				
			public void onValueChange(final ValueChangeEvent<String> event){
				if(!vlidateTextBoxField(textBox)){
					Window.alert("Client:: ValueChangeHandler - Please insert a valid amount ");
					
				}
			}
			
		});	
		
		
		priceInputGroup.add(priceInputGroupAddon);
		priceInputGroup.add(textBox);
		
		
	}
	
	protected void initActionAmountPriceBox(){
	
		actionAmountInputGroup = new InputGroup();
		actionAmountGroupAddon = new InputGroupAddon();
		actionAmountTextBox = new TextBox();
		
		
		actionAmountTextBox.setWidth("100");
		actionAmountGroupAddon.setText("BTC");
		actionAmountTextBox.setPlaceholder("0.00");
		
		actionAmountTextBox.addFocusHandler(new FocusHandler(){
			public void onFocus(final FocusEvent event) {

			}
			
		});
		
		actionAmountTextBox.addBlurHandler(new BlurHandler(){
			public void onBlur(final BlurEvent event) {
				if(!vlidateTextBoxField(actionAmountTextBox)){
					Window.alert("Client:: ValueChangeHandler - Please insert a valid amount ");
					
				}
			}
			
			
		});
		
		actionAmountTextBox.addValueChangeHandler(new ValueChangeHandler<String>(){				
			public void onValueChange(final ValueChangeEvent<String> event){
				if(!vlidateTextBoxField(actionAmountTextBox)){
				Window.alert("Client:: ValueChangeHandler - Please insert a valid amount ");
				
				}
				
			}
			
		});	
		
		actionAmountInputGroup.add(actionAmountGroupAddon);
		actionAmountInputGroup.add(actionAmountTextBox);
		
		actionAmountInputGroup.setVisible(false);
		
	}
	
	protected void initActionDropDownElement(){
		
		actionButtonGroup = new ButtonGroup();
		actionDropDownButton = new Button();
		actionDropdownMenu = new DropDownMenu();
		actionListItems = new ListItem[actionStrings.length];
		
		actionDropDownButton.setType(ButtonType.INFO);
		
		// Set Default exchange name to display
		actionDropDownButton.setText(actionStrings[0]);
		
		if(actionStrings.length > 1){
						
			actionDropDownButton.setToggle(Toggle.DROPDOWN);
		
			this.index = 0;
			for (; index < actionStrings.length; index++){
				
				actionListItems[index] = new ListItem();
				actionListItems[index].setText(actionStrings[index]);
				
				setActionOnClickEvent(actionListItems[index], actionDropDownButton);
				
				actionDropdownMenu.add(actionListItems[index]);
			}
			
			actionButtonGroup.add(actionDropdownMenu);
		}
				
		actionButtonGroup.add(actionDropDownButton);
	}
	
	
	protected void initDropDownElement(final ButtonGroup bGroup, final Button ddButton, final DropDownMenu ddmenu,
			final ListItem[] listItemsArr, final String[] ddMenuStrings){
		
		ddButton.setType(ButtonType.INFO);
		
		// Set Default exchange name to display
		ddButton.setText(ddMenuStrings[0]);
		
		if(ddMenuStrings.length > 1){
						
			ddButton.setToggle(Toggle.DROPDOWN);
		
			this.index = 0;
			for (; index < ddMenuStrings.length; index++){
				
				listItemsArr[index] = new ListItem();
				listItemsArr[index].setText(ddMenuStrings[index]);
				
				setOnClickEvent(listItemsArr[index], ddButton);
				
				ddmenu.add(listItemsArr[index]);
			}
			
			bGroup.add(ddmenu);
		}
				
		bGroup.add(ddButton);
	}
	
	
	protected void initNotifyMeByElement(){
		
		notifyMeByInputGroup = new InputGroup();
		notifyMeByGroupButton = new InputGroupButton();		
		notifyMeByMail = new CheckBox();
		notifyMeBySMS = new CheckBox();
		
		
		notifyMeByMail.setText(NotificationStrings[0]);
		notifyMeBySMS.setText(NotificationStrings[1]);
		
		notifyMeByGroupButton.add(notifyMeByMail);
		notifyMeByMail.addClickHandler(new ClickHandler(){
			public void onClick(final ClickEvent event){
				if(!notifyMeByMail.getValue())
					LOG.info("CLIENT::Mail Notification status is now false");
					
				else
					LOG.info("CLIENT::Mail Notification status is now true");
			}
		});
		
		notifyMeBySMS.addClickHandler(new ClickHandler(){
			public void onClick(final ClickEvent event){
				if(!notifyMeBySMS.getValue())
					LOG.info("CLIENT::SMS Notification status is now false");
					
				else
					LOG.info("CLIENT::SMS Notification status is now true");
			}
		});
		
		
		notifyMeByGroupButton.add(notifyMeBySMS);
		notifyMeByInputGroup.add(notifyMeByGroupButton);
		
	}
	
	
	protected void setOnClickEvent(final ListItem listItem, final Button button){
		
		listItem.addClickHandler(new ClickHandler(){
			public void onClick(final ClickEvent event) {
				button.setText(listItem.getText());
			}
		});		
	}
	
	
	protected void setActionOnClickEvent(final ListItem listItem, final Button button){
		
		listItem.addClickHandler(new ClickHandler(){
			public void onClick(final ClickEvent event) {
				button.setText(listItem.getText());
				
					if(!listItem.getText().equals("Alert Me")){
					actionAmountInputGroup.setVisible(true);
					button.setWidth(Integer.toString(actionAmountInputGroup.getOffsetWidth()));
				}
				else{
					actionAmountInputGroup.setVisible(false);
				}
			}
		});
	}
	

	public Exchange[] getExchanges() {
		return exchanges;
	}

	public CurrencyPair[] getCurrencyPairs() {
		return currencyPairs;
	}
	
	public void setUserEmailAddress(String userMainEmailAddress){
		this.userMainEmailAddress = userMainEmailAddress; 
	}
	
	public void setUserPhoneNumber(String userPhoneNumber){
		this.userPhoneNumber = userPhoneNumber; 
	}
	
	public String getUserEmailAddress(){
		return userMainEmailAddress; 
	}
	
	public String getUserPhoneNumber(){
		return userPhoneNumber; 
	}
	
	
	public ButtonGroup getExchangeButtonGroup(){
		return exchangeButtonGroup;
	}
	
	public Exchange getSelectedExchange(){
		LOG.info("CLIENT::getSelectedExchange start");
		return exchanges[0].getExchangeByDisplayName(exchangeDropDownButton.getText());
	}
	
	public ButtonGroup getActionButtonGroup(){
		return actionButtonGroup;
	}
	
	public InputGroup getNotifyMeByInputGroup(){
		return notifyMeByInputGroup;
	}
	
	public InputGroup getAactionAmountInputGroup(){
		return actionAmountInputGroup;
	}
	
	public RuleUIType getType(){
		return type;
	}
	
	public Button getSaveStopLossRuleButton(){
		return saveStopLossRuleButton;
	}
	
	public Button getSaveStopLimitRuleButton(){
		return saveStopLimitRuleButton;
	}
	
		
	public BigDecimal getTextBoxDecimalValue(TextBoxIdentifier id){
		
		BigDecimal bg;

		switch (id){
		case STOP_PRICE:{
			if(vlidateTextBoxField(priceTextBox)){
				try {
					LOG.info("CLIENT::getTextBoxValue STOP_PRICE - priceTextBox value is: "+priceTextBox.getValue());
					bg = new BigDecimal(priceTextBox.getValue());
					return bg;
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}

		}break;
		case BELOW_LIMIT:{
			if(vlidateTextBoxField(belowPriceValueTextBox)){
				try {
					LOG.info("CLIENT::getTextBoxValue BELOW_LIMIT - belowPriceValueTextBox value is: "+belowPriceValueTextBox.getValue());
					bg = new BigDecimal(belowPriceValueTextBox.getValue());
					return bg;
				} catch (Exception e) {
					e.printStackTrace();
					break;

				}
			}

		}break;
		case ABOVE_LIMIT:{
			if(vlidateTextBoxField(abovePriceValueTextBox)){
				try {
					LOG.info("CLIENT::getTextBoxValue ABOVE_LIMIT - abovePriceValueTextBox value is: "+abovePriceValueTextBox.getValue());
					bg = new BigDecimal(abovePriceValueTextBox.getValue());
					return bg;
				} catch (Exception e) {
					e.printStackTrace();
					break;

				}
			}

		}break;

		case ACTION_AMOUNT:{
			if(vlidateTextBoxField(actionAmountTextBox)){
				try {
					LOG.info("CLIENT::getTextBoxValue ACTION_AMOUNT - actionAmountTextBox value is: "+actionAmountTextBox.getValue());
					bg = new BigDecimal(actionAmountTextBox.getValue());
					return bg;
				} catch (Exception e) {
					e.printStackTrace();
					break;

				}
			}

		}break;
		default:
			if(vlidateTextBoxField(actionAmountTextBox)){
				try {
					LOG.info("CLIENT::getTextBoxValue CASE DEFAULT - actionAmountTextBox value is: "+actionAmountTextBox.getValue());
					bg = new BigDecimal(actionAmountTextBox.getValue());
					return bg;
				} catch (Exception e) {
					e.printStackTrace();
					break;

				}
			}
		}
		bg = new BigDecimal(30);
		return bg;
	}

	public TriggerAdvice getSelectedTriggerAdvice(){
		String str = actionDropDownButton.getText();
		LOG.info("CLIENT::getSelectedTriggerAdvice start");
		if(str.equals(actionStrings[0]))
			return TriggerAdvice.SELL;
		if(str.equals(actionStrings[1]))
			return TriggerAdvice.BUY;
		else return TriggerAdvice.SELL;
	}
	
	public abstract PriceTriggerType getSelectedPriceTriggerType();
	
	public abstract boolean verify();
				
	
	protected boolean vlidateTextBoxField(TextBox tb){
		
		//TBD
		if(tb != null){
			if( !( tb.getValue().matches("[0-9]+") || tb.getValue().matches("0.[0-9]+") || tb.getValue().matches("[0-9]+.[0-9]+")) ){
				LOG.info("CLIENT::vlidateTextBoxField - "+tb+" FALSE with value - "  +tb.getValue());
				saveStopLossRuleButton.setEnabled(false);
				saveStopLimitRuleButton.setEnabled(false);
				return false;
			}
			else{
				LOG.info("CLIENT::vlidateTextBoxField - "+tb+" TRUE with value - "  +tb.getValue());
				saveStopLossRuleButton.setEnabled(true);
				saveStopLimitRuleButton.setEnabled(true);
				return true;
			}
		}
		saveStopLossRuleButton.setEnabled(true);
		saveStopLimitRuleButton.setEnabled(true);
		return true;
	}

}
