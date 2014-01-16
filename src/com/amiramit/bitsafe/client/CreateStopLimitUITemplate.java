package com.amiramit.bitsafe.client;

import org.gwtbootstrap3.client.ui.InputGroup;
import org.gwtbootstrap3.client.ui.InputGroupAddon;
import org.gwtbootstrap3.client.ui.TextBox;

import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.Exchange;
import com.google.gwt.user.client.Window;

public class CreateStopLimitUITemplate extends CreateNewRuleBaseUI{
	
	// Groups to encapsulate drop down buttons, menus and text boxes
	private InputGroup belowPriceValueInputGroup;
	private InputGroup abovePriceValueInputGroup;
	
	// buttons, input group addons
	private InputGroupAddon belowPriceValueInputGroupAddon;
	private InputGroupAddon abovePriceValueInputGroupAddon;
	
	// Drop down menus, text boxes, check boxes
	private TextBox belowPriceValueTextBox;
	private TextBox abovePriceValueTextBox;
	
	
	public CreateStopLimitUITemplate() {
		super(Exchange.values(), CurrencyPair.values(), "", "");
		init();
	}
	
	private void init(){
			
		initExchangeUIElement();
			
		belowPriceValueInputGroup = new InputGroup();
		abovePriceValueInputGroup = new InputGroup();
		belowPriceValueInputGroupAddon = new InputGroupAddon();
		abovePriceValueInputGroupAddon = new InputGroupAddon();
		belowPriceValueTextBox = new TextBox();
		abovePriceValueTextBox = new TextBox();	
		initPriceValueUIElement(belowPriceValueInputGroup, belowPriceValueInputGroupAddon, belowPriceValueTextBox);
		initPriceValueUIElement(abovePriceValueInputGroup, abovePriceValueInputGroupAddon, abovePriceValueTextBox);
				
		initActionAmountUIElement();		
				
		initActionUIElement();	
			
		initNotifyMeByUIElement();	
		
	}
	
	public InputGroup getAbovePriceValueInputGroup(){
		return abovePriceValueInputGroup;
	}
	
	public InputGroup getBelowPriceValueInputGroup(){
		return belowPriceValueInputGroup;
	}
	
	
	public boolean vlidateOnSubmission(){
		
		boolean isValid = true;
		isValid = validateTextBoxNumberInput(belowPriceValueTextBox.getSelectedText());
		if(!isValid){
			// correct the price trigger value to a valid number
			Window.alert("invalid below price!!");
			return isValid;
		}
		
		isValid = validateTextBoxNumberInput(abovePriceValueTextBox.getSelectedText());
		if(!isValid){
			// correct the price trigger value to a valid number
			Window.alert("invalid above price!!");
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
