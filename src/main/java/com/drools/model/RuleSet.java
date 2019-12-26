package com.drools.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleSet {

    private String channelName;
    private String channelZone;
    private String channelRegion;
    private String channelBranch;
    private Integer insurerId=null;
    //private String insurerName;
    private String vehicleType;
    private String policyType;
    private Integer manufactureYear=null;
    private VehicleInfo vehicle;
    private CaseType caseType;
    //private Integer salesVolume=null;
    private String effectiveDate;
    private String expiryDate;
    private Integer minDiscount=null;
    private Integer maxDiscount=null;
    private boolean ruleStatus;

}