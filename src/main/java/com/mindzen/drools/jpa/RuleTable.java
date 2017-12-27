package com.mindzen.drools.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(
		name="rule_table",
		indexes={
				@Index(
						columnList="channel_name,channel_zone,channel_region,channel_branch,insurer_id,make,model,fuel_type,variant,cubic_capacity,seating_capacity,vehicle_type,policy_type,manufacture_year,effective_date,expiry_date",
						name="unique_index_rule")
		}
		)
@Data
public class RuleTable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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