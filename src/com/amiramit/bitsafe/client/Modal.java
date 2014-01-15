package com.amiramit.bitsafe.client;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;


public class Modal  {
	
		// // Button Groups List to contain all button groups//
		public List<ButtonGroup> buttonGroupList;
	
		// Button Groups to encapsulate drop down buttons and menus
		public ButtonGroup exchangeButtonGroup;
		public ButtonGroup ruleTypeButtonGroup;
		public ButtonGroup triggerButtonGroup;
		public ButtonGroup actionButtonGroup;
		
		// Drop down buttons
		public Button exchangeDropDownButton;
		public Button ruleTypeDropDownButton;
		public Button triggerDropDownButton;
		public Button actionDropDownButton;
		
		// Drop down menus
		public DropDownMenu exchangeDropdownMenu;
		public DropDownMenu ruleTypeDropdownMenu;
		public DropDownMenu triggerDropdownMenu;
		public DropDownMenu actionDropdownMenu;
		
		

		public Modal(){
			initButtonGroup();
		}
		
		
		public void initButtonGroup(){
					
			// // Button Groups List to contain all button groups//
			buttonGroupList = new ArrayList<ButtonGroup>();
		
			// Button Groups to encapsulate drop down buttons and menus
			exchangeButtonGroup = new ButtonGroup();
			ruleTypeButtonGroup = new ButtonGroup();
			triggerButtonGroup = new ButtonGroup();
			actionButtonGroup = new ButtonGroup();
			
			// Drop down buttons
			exchangeDropDownButton = new Button("Choose Exchange");
			ruleTypeDropDownButton = new Button("Rule Type");
			triggerDropDownButton = new Button("Choose Condition");
			actionDropDownButton = new Button("Choose Action");
			
			// Drop down menus
			exchangeDropdownMenu = new DropDownMenu();
			ruleTypeDropdownMenu = new DropDownMenu();
			triggerDropdownMenu = new DropDownMenu();
			actionDropdownMenu = new DropDownMenu();
		
			exchangeDropDownButton.setType(ButtonType.INFO);
			exchangeDropDownButton.setToggle(Toggle.DROPDOWN);
			ruleTypeDropDownButton.setType(ButtonType.INFO);
			ruleTypeDropDownButton.setToggle(Toggle.DROPDOWN);
			triggerDropDownButton.setType(ButtonType.INFO);
			triggerDropDownButton.setToggle(Toggle.DROPDOWN);
			actionDropDownButton.setType(ButtonType.INFO);
			actionDropDownButton.setToggle(Toggle.DROPDOWN);
		
	
			// exchanges drop down list items //
			final ListItem exlistItem1 = new ListItem();
			final ListItem exlistItem2 = new ListItem();
			// Rule type drop down list items //
			final ListItem rtlistItem1 = new ListItem();
			final ListItem rtlistItem2 = new ListItem();
			final ListItem rtlistItem3 = new ListItem();
			// trigger type list items
			final ListItem trglistItem1 = new ListItem();
			final ListItem trglistItem2 = new ListItem();
			final ListItem trglistItem3 = new ListItem();
			// Action list items
			final ListItem actionlistItem1 = new ListItem();
			final ListItem actionlistItem2 = new ListItem();
			final ListItem actionlistItem3 = new ListItem();
			
			
			 // encapsulate list items into drop down menus		
			exchangeDropdownMenu.add(exlistItem1);
			exchangeDropdownMenu.add(exlistItem2);
			
			ruleTypeDropdownMenu.add(rtlistItem1);
			ruleTypeDropdownMenu.add(rtlistItem2);
			ruleTypeDropdownMenu.add(rtlistItem3);
			
			triggerDropdownMenu.add(trglistItem1);
			triggerDropdownMenu.add(trglistItem2);
			triggerDropdownMenu.add(trglistItem3);
			
			actionDropdownMenu.add(actionlistItem1);
			actionDropdownMenu.add(actionlistItem2);
			actionDropdownMenu.add(actionlistItem3);
			
			
			exchangeButtonGroup.add(exchangeDropDownButton);
			exchangeButtonGroup.add(exchangeDropdownMenu);
						
			
			// init exchange list items //
			exlistItem1.setText("MtGox");
			exlistItem1.addClickHandler(new ClickHandler(){
				public void onClick(final ClickEvent event) {
					
					ruleTypeButtonGroup.add(ruleTypeDropDownButton);
					ruleTypeButtonGroup.add(ruleTypeDropdownMenu);
					exchangeDropDownButton.setText(exlistItem1.getText());
				}
			});		
			
			exlistItem2.setText("Bitstamp");
			exlistItem2.addClickHandler(new ClickHandler(){
				public void onClick(final ClickEvent event) {
					
					ruleTypeButtonGroup.add(ruleTypeDropDownButton);
					ruleTypeButtonGroup.add(ruleTypeDropdownMenu);
					exchangeDropDownButton.setText(exlistItem2.getText());
				}
			});
		
		
			// init rule type list items //
			rtlistItem1.setText("Price Trigger");
			rtlistItem1.addClickHandler(new ClickHandler(){
				public void onClick(final ClickEvent event) {
					
					ruleTypeDropDownButton.setText(rtlistItem1.getText());
					
					triggerButtonGroup.add(triggerDropDownButton);
					triggerButtonGroup.add(triggerDropdownMenu);
					triggerDropDownButton.setText("Choose Condition");
					trglistItem1.setText("Price Drops Below");
					trglistItem2.setText("Price Rises Above");
					triggerDropdownMenu.add(trglistItem2);
					triggerDropdownMenu.remove(trglistItem3);
					trglistItem3.setText("");
					
					
				}
			});
			
			
			rtlistItem2.setText("Price Range Trigger");
			rtlistItem2.addClickHandler(new ClickHandler(){
				public void onClick(final ClickEvent event) {
					
					ruleTypeDropDownButton.setText(rtlistItem2.getText());
					
					triggerButtonGroup.add(triggerDropDownButton);
					triggerButtonGroup.add(triggerDropdownMenu);
					triggerDropDownButton.setText("Choose Condition");
					trglistItem1.setText("Price is between");
					triggerDropdownMenu.remove(trglistItem2);
					triggerDropdownMenu.remove(trglistItem3);
					trglistItem2.setText("");
					trglistItem3.setText("");
				}
			});
		
			rtlistItem3.setText("Trend Change Trigger");
			rtlistItem3.addClickHandler(new ClickHandler(){
				public void onClick(final ClickEvent event) {
					
					ruleTypeDropDownButton.setText(rtlistItem3.getText());
					
					triggerButtonGroup.add(triggerDropDownButton);
					triggerButtonGroup.add(triggerDropdownMenu);
					triggerDropDownButton.setText("Choose Condition");
					trglistItem1.setText("Price begins to Drop");
					trglistItem2.setText("Price begins to Rise");
					triggerDropdownMenu.add(trglistItem2);
					triggerDropdownMenu.remove(trglistItem3);
					trglistItem3.setText("");
				}
			});
			
		   
			// init exchange list items (depending on rule type - so no initial text)
			trglistItem1.setText("");
			trglistItem1.addClickHandler(new ClickHandler(){
				public void onClick(final ClickEvent event) {
					
					actionButtonGroup.add(actionDropDownButton);
					actionButtonGroup.add(actionDropdownMenu);
					triggerDropDownButton.setText(trglistItem1.getText());
				}
			});
			
			
			trglistItem2.setText("");
			trglistItem2.addClickHandler(new ClickHandler(){
				public void onClick(final ClickEvent event) {
					
					if(!trglistItem2.getText().equals("")){
						triggerDropDownButton.setText(trglistItem2.getText());
						actionButtonGroup.add(actionDropDownButton);
						actionButtonGroup.add(actionDropdownMenu);
					}
				}
			});
		
			trglistItem3.setText("");
			trglistItem3.addClickHandler(new ClickHandler(){
				public void onClick(final ClickEvent event) {
										
					if(!trglistItem3.getText().equals("")){
						triggerDropDownButton.setText(trglistItem3.getText());
						actionButtonGroup.add(actionDropDownButton);
						actionButtonGroup.add(actionDropdownMenu);
					}
				}
			});			
			

			// init action list items //
			actionlistItem1.setText("Buy");
			actionlistItem1.addClickHandler(new ClickHandler(){
				public void onClick(final ClickEvent event) {
					
					actionDropDownButton.setText(actionlistItem1.getText());
				}
			});
		
			actionlistItem2.setText("Sell");
			actionlistItem2.addClickHandler(new ClickHandler(){
				public void onClick(final ClickEvent event) {
					
					actionDropDownButton.setText(actionlistItem2.getText());
				}
			});
			
			actionlistItem3.setText("Alert Me");
			actionlistItem3.addClickHandler(new ClickHandler(){
				public void onClick(final ClickEvent event) {
					
					actionDropDownButton.setText(actionlistItem3.getText());
				}
			});
				   
			
		
			buttonGroupList.add(exchangeButtonGroup);
			buttonGroupList.add(ruleTypeButtonGroup);
			buttonGroupList.add(triggerButtonGroup);
			buttonGroupList.add(actionButtonGroup);
			
	}
		
	public void cleanModal(){
		// currently do nothing
	}
		
	public int getNumOfButtonGroups(){
		return buttonGroupList.size();
	}
		
	public ButtonGroup getButtonGroup(int index){
		return buttonGroupList.get(index);
	}
}