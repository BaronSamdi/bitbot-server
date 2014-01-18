package com.amiramit.bitsafe.client.ruleUI;

import java.util.logging.Logger;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.InputGroup;
import org.gwtbootstrap3.client.ui.InputGroupAddon;
import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.TextBox;

import com.amiramit.bitsafe.client.Bitsafe;
import com.amiramit.bitsafe.shared.CurrencyPair;
import com.amiramit.bitsafe.shared.Exchange;
import com.amiramit.bitsafe.shared.trigger.PriceTriggerType;

public class CreateStopLossUITemplate extends CreateNewRuleBaseUI{
	
	private static final Logger LOG = Logger.getLogger(Bitsafe.class.getName());
	
	private InputGroup igroup1;
	private InputGroup igroup2;
	
	private ButtonGroup priceTriggerButtonGroup;
	private Button priceTriggerDropDownButton;
	private DropDownMenu priceTriggerDropdownMenu;
	private ListItem[] priceTriggerListItems;
	
	private InputGroup priceValueInputGroup;	
	private InputGroupAddon priceValueInputGroupAddon;	
	
	public CreateStopLossUITemplate() {
		super(Exchange.values(), CurrencyPair.values(), "", "");
		type = RuleUIType.STOPLOSS;
		init();
	}
	
	private void init(){
		
		// init exchange dropdown button
		initExchangeElement();
		
		// init price trigger dropdown button
		priceTriggerButtonGroup = new ButtonGroup();
		priceTriggerDropDownButton = new Button();
		priceTriggerDropdownMenu = new DropDownMenu();
		priceTriggerListItems = new ListItem[StopLossTriggerStrings.length];		
		initDropDownElement(priceTriggerButtonGroup, priceTriggerDropDownButton, priceTriggerDropdownMenu,
				priceTriggerListItems, this.StopLossTriggerStrings);
		
		// init price box element for stop value
		priceValueInputGroup = new InputGroup();	
		priceValueInputGroupAddon = new InputGroupAddon();	
		priceTextBox = new TextBox();
		initPriceBoxElement(priceValueInputGroup, priceValueInputGroupAddon, priceTextBox, "");
		
		// init action element (buy, sell, alert me)
		initActionDropDownElement();
		
		// init action amount box
		initActionAmountPriceBox();
		
			
		// init notification choice check boxes		
		initNotifyMeByElement();	
		
		igroup1 = new InputGroup();
		igroup2 = new InputGroup();
		igroup1.add(priceTriggerButtonGroup);
		igroup1.add(priceValueInputGroup);
		igroup2.add(actionButtonGroup);
		igroup2.add(actionAmountInputGroup);
		
		
	}
		
	public ButtonGroup getPriceTriggerButtonGroup(){
		return priceTriggerButtonGroup;
	}
	
	public InputGroup getPriceValueInputGroup(){
		return igroup1;
	}
	
	public InputGroup getActionAmountInputGroup(){
		return igroup2;
	}
	
	public PriceTriggerType getSelectedPriceTriggerType(){
		String str = priceTriggerDropDownButton.getText();
		if(str.equals(StopLossTriggerStrings[0]))
			return PriceTriggerType.LOWER;
		
		return PriceTriggerType.HIGHER;
		
	}
	
	public boolean verify(){
		if(!vlidateTextBoxField(belowPriceValueTextBox))
			return false;
		return true;
	}
	
	
}
