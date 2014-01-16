package com.amiramit.bitsafe.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.InputGroup;
import org.gwtbootstrap3.client.ui.InputGroupAddon;
import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.TextBox;

import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.Exchange;
import com.google.gwt.user.client.Window;

public class CreateStopLossUITemplate extends CreateNewRuleBaseUI{
	
	private String[] triggers;
	
	private ButtonGroup priceTriggerButtonGroup;
	private InputGroup priceValueInputGroup;
	private Button priceTriggerDropDownButton;
	private InputGroupAddon priceValueInputGroupAddon;
	private DropDownMenu priceTriggerDropdownMenu;
	private TextBox priceValueTextBox;
	
	public CreateStopLossUITemplate() {
		super(Exchange.values(), CurrencyPair.values(), "", "");
		this.triggers = this.StopLossTriggerStrings;
		init();
	}
	
	private void init(){
		
			
		initExchangeUIElement();
			
		priceTriggerButtonGroup = new ButtonGroup();
		priceTriggerDropDownButton = new Button();
		priceTriggerDropdownMenu = new DropDownMenu();
		priceTriggerListItems = new ListItem[triggers.length];		
		initTriggerTypeUIElement(priceTriggerButtonGroup, priceTriggerDropDownButton, priceTriggerDropdownMenu,
				priceTriggerListItems, triggers);
			
		
		priceValueInputGroup = new InputGroup();
		priceValueInputGroupAddon = new InputGroupAddon();
		priceValueTextBox = new TextBox();		
		initPriceValueUIElement(priceValueInputGroup, priceValueInputGroupAddon, priceValueTextBox);
				
		
		initActionAmountUIElement();	
			
		initActionUIElement();	
				
		initNotifyMeByUIElement();	
		
	}
		
	public ButtonGroup getPriceTriggerButtonGroup(){
		return priceTriggerButtonGroup;
	}
	
	public InputGroup getPriceValueInputGroup(){
		return priceValueInputGroup;
	}
	
	
	public boolean vlidateOnSubmission(){
		
		boolean isValid = true;
		isValid = validateTextBoxNumberInput(priceValueTextBox.getSelectedText());
		if(!isValid){
			// correct the price trigger value to a valid number
			Window.alert("invalid price!!");
			return isValid;
		}
		
		if(actionAmountInputGroup.isVisible()){
			isValid = validateTextBoxNumberInput(actionAmountTextBox.getSelectedText());
			if(!isValid){
				// correct the price trigger value to a valid number
				Window.alert("invalid amount!!");
				return isValid;
			}
		}
		
		return isValid;		
	}
}
