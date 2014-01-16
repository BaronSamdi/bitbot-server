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

public class CreateStopLimitUITemplate extends CreateNewRuleBaseUI{
	
	private ButtonGroup priceRangeTriggerButtonGroup;
	private Button priceRangeTriggerDropDownButton;
	private DropDownMenu priceRangeTriggerDropdownMenu;
	protected ListItem[] priceRangeListItems;
	
	
	private InputGroup belowPriceValueInputGroup;
	private InputGroup abovePriceValueInputGroup;
	
	private InputGroupAddon belowPriceValueInputGroupAddon;
	private InputGroupAddon abovePriceValueInputGroupAddon;
	
	private TextBox belowPriceValueTextBox;
	private TextBox abovePriceValueTextBox;
	
	
	public CreateStopLimitUITemplate() {
		super(Exchange.values(), CurrencyPair.values(), "", "");
		init();
	}
	
	private void init(){
		
		// init exchange dropdown button
		initExchangeElement();
		
		// init price range trigger button
		priceRangeTriggerButtonGroup = new ButtonGroup();
		priceRangeTriggerDropDownButton = new Button();
		priceRangeTriggerDropdownMenu = new DropDownMenu();
		priceRangeListItems = new ListItem[this.LimitTriggerStrings.length];
		initDropDownElement(priceRangeTriggerButtonGroup, priceRangeTriggerDropDownButton,
				priceRangeTriggerDropdownMenu, priceRangeListItems, this.LimitTriggerStrings);
		
		// init price limit boxes
		belowPriceValueInputGroup = new InputGroup();
		belowPriceValueInputGroupAddon = new InputGroupAddon();
		belowPriceValueTextBox = new TextBox();		
		abovePriceValueInputGroup = new InputGroup();
		abovePriceValueInputGroupAddon = new InputGroupAddon();
		abovePriceValueTextBox = new TextBox();		
		initPriceBoxElement(belowPriceValueInputGroup, belowPriceValueInputGroupAddon, belowPriceValueTextBox);
		initPriceBoxElement(abovePriceValueInputGroup, abovePriceValueInputGroupAddon, abovePriceValueTextBox);
		
		// init action amount box
		initActionAmountPriceBox();		
				
		// init action element (buy, sell, alert me)
		initActionDropDownElement();
		
		// init notification choice check boxes
		initNotifyMeByElement();	
		
	}
	
	
	public ButtonGroup getPriceRangeTriggerButtonGroup(){
		return priceRangeTriggerButtonGroup;
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
