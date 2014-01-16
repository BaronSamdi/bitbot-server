package com.amiramit.bitsafe.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.InputGroup;
import org.gwtbootstrap3.client.ui.InputGroupAddon;
import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.TextBox;

import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.Exchange;
import com.google.gwt.user.client.Window;

public class CreateStopLossUITemplate extends CreateNewRuleBaseUI{
	
	private ButtonGroup priceTriggerButtonGroup;
	private Button priceTriggerDropDownButton;
	private DropDownMenu priceTriggerDropdownMenu;
	private ListItem[] priceTriggerListItems;
	
	private InputGroup priceValueInputGroup;	
	private InputGroupAddon priceValueInputGroupAddon;	
	private TextBox priceValueTextBox;
	
	private Modal modal;
	private ModalBody modalBody;
	private ModalFooter modalFooter;
	
	public CreateStopLossUITemplate() {
		super(Exchange.values(), CurrencyPair.values(), "", "");
		init();
		createModal();
	}
	
	private void createModal(){
		
		modal = new Modal();
		modalBody = new ModalBody();
		modalFooter = new ModalFooter();
		
		//modalBody.add(priceTriggerButtonGroup);
		//modalBody.add(priceValueInputGroup);
		modal.add(modalBody);
		modal.add(modalFooter);
		
		
	}
	
	public Modal getModal(){
		return modal;
	}
	
	
	
	private void init(){
		
		// init exchange dropdown button	
		initExchangeElement();
		
		// init price trigger dropdown button
		priceTriggerButtonGroup = new ButtonGroup();
		priceTriggerDropDownButton = new Button();
		priceTriggerDropdownMenu = new DropDownMenu();
		priceTriggerListItems = new ListItem[this.StopLossTriggerStrings.length];		
		initDropDownElement(priceTriggerButtonGroup, priceTriggerDropDownButton, priceTriggerDropdownMenu,
				priceTriggerListItems, this.StopLossTriggerStrings);
		
		// init price box element for stop value
		priceValueInputGroup = new InputGroup();	
		priceValueInputGroupAddon = new InputGroupAddon();	
		priceValueTextBox = new TextBox();
		initPriceBoxElement(priceValueInputGroup, priceValueInputGroupAddon, priceValueTextBox);
		
		// init action element (buy, sell, alert me)
		initActionDropDownElement();
		
		// init action amount box
		initActionAmountPriceBox();	
			
		// init notification choice check boxes		
		initNotifyMeByElement();	
		
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
