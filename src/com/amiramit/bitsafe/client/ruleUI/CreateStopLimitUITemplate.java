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
import com.google.gwt.user.client.Window;

public class CreateStopLimitUITemplate extends CreateNewRuleBaseUI{
	
	private static final Logger LOG = Logger.getLogger(Bitsafe.class.getName());
	
	private ButtonGroup priceRangeTriggerButtonGroup;
	private Button priceRangeTriggerDropDownButton;
	private DropDownMenu priceRangeTriggerDropdownMenu;
	protected ListItem[] priceRangeListItems;
	

	private InputGroup belowPriceValueInputGroup;
	private InputGroup abovePriceValueInputGroup;
	
	private InputGroupAddon belowPriceValueInputGroupAddon;
	private InputGroupAddon abovePriceValueInputGroupAddon;
	
	
	private InputGroup igroup1 = new InputGroup();
	private InputGroup igroup2 = new InputGroup();
	
	public CreateStopLimitUITemplate() {
		super(Exchange.values(), CurrencyPair.values(), "", "");
		type = RuleUIType.STOPLIMIT;
		init();
	}
	
	private void init(){
		
		
		// init exchange dropdown button
		initExchangeElement();
		
		// init price range trigger button
		priceRangeTriggerButtonGroup = new ButtonGroup();
		priceRangeTriggerDropDownButton = new Button();
		priceRangeTriggerDropdownMenu = new DropDownMenu();
		priceRangeListItems = new ListItem[LimitTriggerStrings.length];
		initDropDownElement(priceRangeTriggerButtonGroup, priceRangeTriggerDropDownButton,
				priceRangeTriggerDropdownMenu, priceRangeListItems, LimitTriggerStrings);
		
		// init price limit boxes
		belowPriceValueInputGroup = new InputGroup();
		belowPriceValueInputGroupAddon = new InputGroupAddon();
		belowPriceValueTextBox = new TextBox();		
		abovePriceValueInputGroup = new InputGroup();
		abovePriceValueInputGroupAddon = new InputGroupAddon();
		abovePriceValueTextBox = new TextBox();		
		initPriceBoxElement(belowPriceValueInputGroup, belowPriceValueInputGroupAddon, belowPriceValueTextBox, "Below --");
		initPriceBoxElement(abovePriceValueInputGroup, abovePriceValueInputGroupAddon, abovePriceValueTextBox, "Above --");
		
		// init action amount box
		initActionAmountPriceBox();		
				
		// init action element (buy, sell, alert me)
		initActionDropDownElement();
		
		// init notification choice check boxes
		initNotifyMeByElement();
		
		igroup1.add(priceRangeTriggerButtonGroup);
		igroup1.add(belowPriceValueInputGroup);
		igroup1.add(abovePriceValueInputGroup);
		igroup2.add(actionButtonGroup);
		igroup2.add(actionAmountInputGroup);
		
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
	
	public InputGroup getPriceRangeInputGroup(){
		return igroup1;
	}
	
	public InputGroup getActionAmountInputGroup(){
		return igroup2;
	}
	
	public PriceTriggerType getSelectedPriceTriggerType(){
		/*String str = priceRangeTriggerDropDownButton.getText();
		if(str.equals(LimitTriggerStrings[0]))
			return PriceTriggerType.LOWER;
		*/
		LOG.info("CLIENT::getSelectedPriceTriggerType start");
		return PriceTriggerType.HIGHER;
		
	}
	
	public  boolean verify(){
		if(!vlidateTextBoxField(belowPriceValueTextBox))
			return false;
		
		if(!vlidateTextBoxField(abovePriceValueTextBox))
			return false;
		 //IF Below limit is not LESS OR EQAL to above limit
		if(getTextBoxDecimalValue(TextBoxIdentifier.BELOW_LIMIT).compareTo(getTextBoxDecimalValue(TextBoxIdentifier.ABOVE_LIMIT)) > 0){
			Window.alert("Client:: StopLimit.verify - Please insert a valid range ");
			return false;
		}	
		return true;		

	}
	
		
		
}
