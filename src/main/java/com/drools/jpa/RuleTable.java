package com.drools.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "rule_table")
@Data
public class RuleTable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="rule_id")
	private Integer ruleId;

	@Column(name="channel_name")
	private String channelName;

	@Column(name="channel_zone")
	private String channelZone;

	@Column(name="channel_region")
	private String channelRegion;

	@Column(name="channel_branch")
	private String channelBranch;

	@Column(name="vehicle_type")
	private String vehicleType;

	@Column(name="policy_type")
	private String policyType;

	@Column(name="manufacture_year")
	private Integer manufactureYear=null;

	@Column(name="insurer_id", nullable=false)
	private Integer insurerId=null; 

	@Column
	private String make;

	@Column
	private String model;

	@Column(name="fuel_type")
	private String fuelType; 

	@Column
	private String variant;
	
	private String type;
	
	private String userName;

	private String teamName;

	@Column(name="cubic_capacity")
	private Integer cubicCapacity=null; 

	@Column(name="seating_capacity")
	private Integer seatingCapacity=null;

	@Column(name="effective_date")
	private String effectiveDate; 

	@Column(name="expiry_date")
	private String expiryDate;

	@Column(name="rule_status")
	private Boolean ruleStatus; 

	@Column(name="min_discount")
	private Integer minDiscount=null; 

	@Column(name="max_discount")
	private Integer maxDiscount=null;

}