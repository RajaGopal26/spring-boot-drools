package com.mindzen.drools.service;

import com.mindzen.drools.model.ResponseDTO;
import com.mindzen.drools.model.RuleSet;

public interface RuleService {

    public ResponseDTO add(RuleSet ruleSet);

    public ResponseDTO execute(RuleSet ruleSet);

}