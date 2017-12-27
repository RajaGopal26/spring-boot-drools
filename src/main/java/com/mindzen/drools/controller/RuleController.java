package com.mindzen.drools.controller;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindzen.drools.model.ResponseDTO;
import com.mindzen.drools.model.RuleSet;
import com.mindzen.drools.service.RuleServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "salesDiscount", tags= "Sales discount service API")
@RestController
@RequestMapping("/rule")
@CrossOrigin(origins="*")
public class RuleController {

	@Autowired
	private RuleServiceImpl discountService;

    @ApiOperation("Get sales discount")
    @PostMapping("/execute")
    public ResponseDTO execute(@RequestBody RuleSet ruleSet) {
        return discountService.execute(ruleSet);
    }

    @ApiOperation("Add rule")
    @PostMapping("/add")
    public ResponseDTO addRule(@RequestBody RuleSet ruleSet) {
        return discountService.add(ruleSet);
    }

}