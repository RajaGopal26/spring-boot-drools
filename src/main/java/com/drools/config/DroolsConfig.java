package com.drools.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.drools.jpa.RuleRepository;
import com.drools.jpa.RuleTable;

import lombok.extern.slf4j.Slf4j;


/**
 * Created by MRG
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.drools"})
public class DroolsConfig {

	@Autowired
	private RuleRepository discountRepository;

	private Resource[] listRules() throws IOException {
		PathMatchingResourcePatternResolver pmrs = new PathMatchingResourcePatternResolver();
		return pmrs.getResources("classpath*:com/drools/**/*.drl");
	}

	@Bean
	public KieContainer kieContainer() throws IOException {
		KieServices ks = KieServices.Factory.get();
		final KieRepository kr = ks.getRepository();
		kr.addKieModule(new KieModule() {
			@Override
			public ReleaseId getReleaseId() {
				return kr.getDefaultReleaseId();
			}
		});
		KieFileSystem kfs = ks.newKieFileSystem();
		listRules();
		kfs.write("src/main/resources/com/drools/rules/sd.drl", getTemplateRule());
		KieBuilder kb = ks.newKieBuilder(kfs);

		Results results = kb.getResults();

		if (results.hasMessages(Message.Level.ERROR)) {
			StringBuilder buf=new StringBuilder();
			for (Message message : results.getMessages(Message.Level.ERROR)) {
				buf.append("ERROR: "+message.toString().trim()+"/r/n");
			}
		}

		kb.buildAll(); // kieModule is automatically deployed to KieRepository if successfully built.
		KieContainer kcontainer = ks.newKieContainer(kr.getDefaultReleaseId());
		KieScanner kscanner = ks.newKieScanner(kcontainer);
		kscanner.start(120000l);
//		KieContainer kc =;
//		ks.newKieScanner(kc).start(1000l);
		log.info("repo release id: "+kr.getDefaultReleaseId());
		return   ks.newKieContainer(kr.getDefaultReleaseId());
	}

	@Bean
	public KieBase kieBase() throws IOException {
		return kieContainer().getKieBase();
	}

	/*
	 * public KieBase refreshKieMemory() throws IOException { return kieBase(); }
	 */
	
	public String getTemplateRule(){
		String drl = "";
		Iterable<RuleTable> iterable = null;
		List<RuleTable> list = new ArrayList<>();
		try {
			iterable = discountRepository.findAll();
			iterable.forEach(list::add);
			log.info("list: "+list);
			ObjectDataCompiler converter = new ObjectDataCompiler();
			drl = converter.compile(list, getRulesStream());
			log.info("Generated drl file:\n"+drl);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return drl;
	}

	private static InputStream getRulesStream(){
		InputStream inputStream = null;
		try{
			File isPresent=new File("C:\\Users\\0050\\Desktop\\Project Refference\\spring-boot-drools\\src\\main\\resources\\com\\drools\\rules\\new_rule_template.drt");
			if(isPresent.exists()) 
				inputStream = new FileInputStream(isPresent);
			else
				inputStream = new FileInputStream("C:\\Users\\0050\\Desktop\\Project Refference\\spring-boot-drools\\src\\main\\resources\\com\\drools\\rules\\rule_template.drt");
		}
		catch(FileNotFoundException e){
			log.info(e.getMessage());
		}
		return inputStream;
	}

}