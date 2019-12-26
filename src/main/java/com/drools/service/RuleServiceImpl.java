package com.drools.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.Globals;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.drools.jpa.RuleDataConverter;
import com.drools.jpa.RuleRepository;
import com.drools.jpa.RuleTable;
import com.drools.model.ResponseDTO;
import com.drools.model.RuleSet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RuleServiceImpl implements RuleService{

	/*
	 * enum EnumConstatnts{ SUCCESS, FAILURE;
	 * 
	 * private String enumString;
	 * 
	 * @Override public String toString() { return this.enumString; } }
	 */
	@Autowired
	private KieBase kbase;

	@Autowired
	private RuleRepository ruleRepository;

	@Autowired
	private RuleDataConverter ruleDataConverter;
	
	/*
	 * @Autowired private DroolsConfig droolsConfig;
	 */

	@Metered
	@ExceptionMetered
	@Override
	public Object add(RuleSet ruleSet) {
		ResponseDTO responseDTO = new ResponseDTO();
			RuleTable ruleTable = ruleRepository.save(ruleDataConverter.convert(ruleSet));
			if(ruleTable == null)
				responseDTO.put("Failure", "false");
			refreshKieSession();
		/*
		 * try { droolsConfig.refreshKieMemory().getKieSessions(); } catch (IOException
		 * e) { log.info("Resource not found exception: "+e.getMessage()); }
		 */
		return new ResponseDTO().put("Success", "true");
	}

	private void refreshKieSession() {	
		KieServices kieServices = KieServices.Factory.get();
		ReleaseId releaseId = kieServices.getRepository().getDefaultReleaseId();
		KieContainer kcontainer = kieServices.newKieContainer(releaseId);
		KieScanner kscanner = kieServices.newKieScanner(kcontainer);
		kscanner.scanNow();
	}

	@Metered
	@ExceptionMetered(name = "executeDiscount", cause = Exception.class)
	@Override
	public ResponseDTO execute(RuleSet ruleSet) {
		ResponseDTO responseDTO = new ResponseDTO();
		KieSession ksession = kbase.newKieSession();
		Globals globals = ksession.getGlobals();
		log.info("global keysw "+globals.getGlobalKeys());
		// Setup globals varaiable
		List<HashMap<String, Object>> list = new ArrayList<>();
		ksession.setGlobal("globalList", list);

		log.info("RuleSet: " +ruleSet);
		if(ruleSet.getCaseType() != null) {
			ksession.insert(ruleSet.getCaseType());
		}else {
			ksession.insert(ruleSet.getVehicle());
			ksession.insert(ruleSet);
		}
		// Fire all rul es and destroy session.
		int i = ksession.fireAllRules();
		if(i==0){
			HashMap<String, Object> map = new HashMap<>();
			map.put("min", "0");
			map.put("max", "0");
			list.add(map);
		}
		log.info(i+" - "+list);
		ksession.destroy();
		responseDTO.put("response", list);
		// Return result
		return responseDTO;
	}

}