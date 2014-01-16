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
	protected String[] LimitTriggerStrings = {"Price Is Above","Price is Below"};
	
	
	// Groups to encapsulate drop down buttons, menus and text boxes
	protected ButtonGroup exchangeButtonGroup;
	protected InputGroup actionAmountInputGroup;
	protected ButtonGroup actionButtonGroup;
	protected InputGroup notifyMeByInputGroup;

	// buttons, input group addons
	protected Button exchangeDropDownButton;
	protected InputGroupAddon actionAmountGroupAddon;
	protected Button actionDropDownButton;
	protected InputGroupButton notifyMeByGroupButton;

	// Drop down menus, text boxes, check boxes
	protected DropDownMenu exchangeDropdownMenu;
	protected DropDownMenu actionDropdownMenu;
	protected TextBox actionAmountTextBox;
	protected CheckBox notifyMeByMail;
	protected CheckBox notifyMeBySMS;

	// List items for drop down menus
	protected ListItem[] exchangeListItems;
	protected ListItem[] priceTriggerListItems;
	protected ListItem[] actionListItems;

	private int index;

	public CreateNewRuleBaseUI(final Exchange[] exchanges, final CurrencyPair[] currencyPairs,
			String userMainEmailAddres, String userPhoneNumber) {
		this.exchanges = exchanges;
		this.currencyPairs = currencyPairs;
		this.userMainEmailAddress = userMainEmailAddres;
		this.userPhoneNumber = userPhoneNumber;
	}
	
	
	protected void initExchangeUIElement(){
		
		exchangeButtonGroup = new ButtonGroup();
		exchangeDropDownButton = new Button();
		exchangeDropdownMenu = new DropDownMenu();
		int numOfExchanges = this.exchanges.length;
		this.exchangeListItems = new ListItem[numOfExchanges];
		exchangeDropDownButton.setType(ButtonType.INFO);
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
		
		exchangeButtonGroup.add(exchangeDropDownButton);
		exchangeButtonGroup.add(exchangeDropdownMenu);
	}
	
	
	protected void initTriggerTypeUIElement(ButtonGroup triggerButtonGroup, Button triggerDropDownButton,
			DropDownMenu triggerDropdownMenu, ListItem[] triggerListItems, String[] triggers){
		
		triggerDropDownButton.setType(ButtonType.INFO);
		
		// Set Default exchange name to display
		triggerDropDownButton.setText(triggers[0]);
		
		if(triggers.length > 1){		
			
			triggerDropDownButton.setToggle(Toggle.DROPDOWN);
		
			this.index = 0;
			for (; index < triggers.length; index++){
				
				triggerListItems[index] = new ListItem();
				triggerListItems[index].setText(triggers[index]);
				
				setOnClickEvent(triggerListItems[index], triggerDropDownButton);
				
				triggerDropdownMenu.add(triggerListItems[index]);
			}
			
			triggerButtonGroup.add(triggerDropdownMenu);
		}
		
		triggerButtonGroup.add(triggerDropDownButton);
		
	}
	
	protected void initPriceValueUIElement(InputGroup priceValueInputGroup, InputGroupAddon priceValueInputGroupAddon,
			TextBox priceValueTextBox){
				
		priceValueTextBox.setWidth("93");
		priceValueInputGroupAddon.setText("USD$");
		priceValueTextBox.setPlaceholder("0.00");
		
		
		priceValueInputGroup.add(priceValueInputGroupAddon);
		priceValueInputGroup.add(priceValueTextBox);
		
		
	}
	
	protected void initActionAmountUIElement(){
	
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
	
	protected void initActionUIElement(){

		actionDropdownMenu = new DropDownMenu();
		actionButtonGroup = new ButtonGroup();
		actionDropDownButton = new Button();
		actionListItems = new ListItem[actionStrings.length];
		
		actionDropDownButton.setType(ButtonType.INFO);
		actionDropDownButton.setToggle(Toggle.DROPDOWN);
		index = 0;
		for (; index < actionStrings.length; index++){
			
			actionListItems[index] = new ListItem();
			actionListItems[index].setText(actionStrings[index]);
			
			setActionOnClickEvent(actionListItems[index], actionDropDownButton, actionAmountInputGroup);
			
			actionDropdownMenu.add(actionListItems[index]);
		}
		
		// Set Default exchange name to display
		actionDropDownButton.setText(actionListItems[0].getText());
		actionButtonGroup.add(actionDropDownButton);
		actionButtonGroup.add(actionDropdownMenu);
	}
	
	protected void initNotifyMeByUIElement(){
		
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
	
	
	protected void setActionOnClickEvent(final ListItem listItem, final Button button, final InputGroup actionAmountInputGroup){
		
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
