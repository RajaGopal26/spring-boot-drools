package com.drools.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drools.model.ResponseDTO;
import com.drools.model.RuleSet;
import com.drools.service.RuleServiceImpl;

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
    public ResponseEntity<ResponseDTO> addRule(@RequestBody RuleSet ruleSet) {
        return new ResponseEntity<>( (ResponseDTO) discountService.add(ruleSet), HttpStatus.OK);
    }

}