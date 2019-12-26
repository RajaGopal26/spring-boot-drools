package com.drools.jpa;

import org.springframework.stereotype.Component;

import com.drools.model.RuleSet;


@Component
public class RuleDataConverter {

	public RuleTable convert(RuleSet ruleSet){
		RuleTable ruleTable = new RuleTable();
		ruleTable.setChannelZone(ruleSet.getChannelZone());
		ruleTable.setChannelBranch(ruleSet.getChannelBranch());
		ruleTable.setChannelName(ruleSet.getChannelName());
		ruleTable.setChannelRegion(ruleSet.getChannelRegion());
		ruleTable.setMake(ruleSet.getVehicle().getMake());
		ruleTable.setModel(ruleSet.getVehicle().getModel());
		ruleTable.setFuelType(ruleSet.getVehicle().getFuelType());
		ruleTable.setVariant(ruleSet.getVehicle().getVariant());
		ruleTable.setCubicCapacity(ruleSet.getVehicle().getCubicCapacity());
		ruleTable.setSeatingCapacity(ruleSet.getVehicle().getSeatingCapacity());
		ruleTable.setEffectiveDate(ruleSet.getEffectiveDate());
		ruleTable.setExpiryDate(ruleSet.getExpiryDate());
		ruleTable.setInsurerId(ruleSet.getInsurerId());
		ruleTable.setManufactureYear(ruleSet.getManufactureYear());
		ruleTable.setMaxDiscount(ruleSet.getMaxDiscount());
		ruleTable.setMinDiscount(ruleSet.getMinDiscount());
		ruleTable.setPolicyType(ruleSet.getPolicyType());
		ruleTable.setVehicleType(ruleSet.getVehicleType());
		ruleTable.setType(ruleSet.getCaseType().getType());
		ruleTable.setTeamName(ruleSet.getCaseType().getTeamName());
		ruleTable.setUserName(ruleSet.getCaseType().getUserName());
		ruleTable.setRuleStatus(true);
		return ruleTable;
	}
}