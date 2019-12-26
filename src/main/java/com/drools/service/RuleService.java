package com.drools.service;

import com.drools.model.ResponseDTO;
import com.drools.model.RuleSet;

public interface RuleService {

    public Object add(RuleSet ruleSet);

    public ResponseDTO execute(RuleSet ruleSet);

}