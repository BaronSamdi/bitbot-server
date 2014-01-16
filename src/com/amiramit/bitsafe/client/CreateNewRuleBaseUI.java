package com.amiramit.bitsafe.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.InputGroup;
import org.gwtbootstrap3.client.ui.InputGroupAddon;
import org.gwtbootstrap3.client.ui.InputGroupButton;
import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.Exchange;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public abstract class CreateNewRuleBaseUI{
	

	protected Exchange[] exchanges;
	protected CurrencyPair[] currencyPairs;
	protected String userMainEmailAddress;
	protected String userPhoneNumber;
	
	protected String[] actionStrings = {"Alert Me","Buy", "Sell"};
	protected String[] StopLossTriggerStrings = {"Price Drops Below","Price Rises Above"};
	protected String[] LimitTriggerStrings = {"Price Is in the Range"};
	
	
	// Exchanges drop down elements
	protected ButtonGroup exchangeButtonGroup;
	protected Button exchangeDropDownButton;
	protected DropDownMenu exchangeDropdownMenu;
	protected ListItem[] exchangeListItems;
	
	// Action amount elements
	protected InputGroup actionAmountInputGroup;
	protected InputGroupAddon actionAmountGroupAddon;
	protected TextBox actionAmountTextBox;
	
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
	
	private int index;

	public CreateNewRuleBaseUI(final Exchange[] exchanges, final CurrencyPair[] currencyPairs,
			String userMainEmailAddres, String userPhoneNumber) {
		this.exchanges = exchanges;
		this.currencyPairs = currencyPairs;
		this.userMainEmailAddress = userMainEmailAddres;
		this.userPhoneNumber = userPhoneNumber;
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
			final TextBox priceTextBox){
		
		priceTextBox.setWidth("93");
		priceInputGroupAddon.setText("USD$");
		priceTextBox.setPlaceholder("0.00");
		
		
		priceInputGroup.add(priceInputGroupAddon);
		priceInputGroup.add(priceTextBox);
		
		
	}
	
	protected void initActionAmountPriceBox(){
	
		actionAmountInputGroup = new InputGroup();
		actionAmountGroupAddon = new InputGroupAddon();
		actionAmountTextBox = new TextBox();
		
		actionAmountTextBox.setWidth("100");
		actionAmountGroupAddon.setText("BTC");
		actionAmountTextBox.setPlaceholder("0.00");
		
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
		
		
		notifyMeByMail.setText("Notify by Mail");
		notifyMeBySMS.setText("Notify by SMS");
		
		notifyMeByGroupButton.add(notifyMeByMail);
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
	
	
	public InputGroup getAactionAmountInputGroup(){
		return actionAmountInputGroup;
	}
		
	
	public ButtonGroup getActionButtonGroup(){
		return actionButtonGroup;
	}
	
	public InputGroup getNotifyMeByInputGroup(){
		return notifyMeByInputGroup;
	}
	
	
	protected boolean validateTextBoxNumberInput(String textBoxString){
		
		boolean isValid = true;
		if (isValid){
		      // textBoxString is a valid double
			return isValid;
		  }
		  else {
		      return isValid;
		  }
	}

}
