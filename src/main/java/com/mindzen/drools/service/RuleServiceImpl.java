package com.mindzen.drools.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.mindzen.drools.jpa.RuleDataConverter;
import com.mindzen.drools.jpa.RuleRepository;
import com.mindzen.drools.jpa.RuleTable;
import com.mindzen.drools.model.ResponseDTO;
import com.mindzen.drools.model.RuleSet;

@Service
public class RuleServiceImpl implements RuleService{

	@Autowired
    private KieBase kbase;
    
	@Autowired
    private RuleRepository ruleRepository;
	
	@Autowired
    private RuleDataConverter ruleDataConverter;

	@Metered
    @ExceptionMetered
    @Override
    public ResponseDTO add(RuleSet ruleSet) {
    	ResponseDTO responseDTO = new ResponseDTO();
    	try{
        RuleTable ruleTable = ruleRepository.save(ruleDataConverter.convert(ruleSet));
        if(ruleTable!=null)
        	responseDTO.put("success", true);
        else
        	responseDTO.put("success", false);
    	}
    	catch(Exception e){
    		responseDTO.put("success", false);
    		responseDTO.put("message", e.getMessage());
    	}
        return responseDTO;
    }

    @Metered
    @ExceptionMetered
    @Override
    public ResponseDTO execute(RuleSet ruleSet) {
    	ResponseDTO responseDTO = new ResponseDTO();
        KieSession ksession = kbase.newKieSession();

        // Setup globals
        List<HashMap<String, Object>> list = new ArrayList<>();
        ksession.setGlobal("globalList", list);

        System.out.println(ruleSet);
        ksession.insert(ruleSet.getVehicle());
        ksession.insert(ruleSet);
        
        // Fire all rules and destroy session.
        int i = ksession.fireAllRules();
        if(i==0){
            HashMap<String, Object> map = new HashMap<>();
        	map.put("min", "0");
        	map.put("max", "0");
        	list.add(map);
        }
        System.out.println(i+" - "+list);
        ksession.destroy();
        responseDTO.put("response", list);
        // Return result
        return responseDTO;
    }

}