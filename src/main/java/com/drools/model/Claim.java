package com.drools.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@JsonInclude
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Claim {

    private String conditionCategory;
    private boolean coverage;
    private String coverageType;
    private String serviceType;
    private String serviceSubType;
    private String relations;
    private int duration;
    private int claimLimit;
    private int copayPercent;
    private String hospitalType;
    private int age;
    private String policy;
    private String conditionName;

}