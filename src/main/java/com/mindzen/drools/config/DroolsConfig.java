package com.mindzen.drools.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.mindzen.drools.jpa.RuleTable;
import com.mindzen.drools.jpa.RuleRepository;

/**
 * Created by Stephan on 17-06-14.
 */
@Configuration
@ComponentScan(basePackages = {"com.mindzen.drools"})
public class DroolsConfig {
	
	@Autowired
	private RuleRepository discountRepository;
	
	private Resource[] listRules() throws IOException {
		PathMatchingResourcePatternResolver pmrs = new PathMatchingResourcePatternResolver();
		Resource[] resources = pmrs.getResources("classpath*:com/mindzen/**/*.drl");
		return resources;
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
		Resource[] files = listRules();

/*		for(Resource file : files) {
			String myString = IOUtils.toString(file.getInputStream(), "UTF-8");
			kfs.write("src/main/resources/"+ file.getFilename(), myString);
		}
*/
		//String myString = IOUtils.toString(file.getInputStream(), "UTF-8");
		kfs.write("src/main/resources/com/mindzen/drools/rules/sd.drl", getTemplateRule());
		//kfs.write("D:/workspace/git-projects/spring-boot-drools/src/main/resources/com/mindzen/drools/rules/sd.drl", getTemplateRule());
		KieBuilder kb = ks.newKieBuilder(kfs);

		Results results = kb.getResults();

		if (results.hasMessages(Message.Level.ERROR)) {
			StringBuffer buf=new StringBuffer();
			for (Message message : results.getMessages(Message.Level.ERROR)) {
				buf.append("ERROR: "+message.toString().trim()+"/r/n");
			}
		}

		kb.buildAll(); // kieModule is automatically deployed to KieRepository if successfully built.
		KieContainer kContainer = ks.newKieContainer(kr.getDefaultReleaseId());
		return kContainer;
	}

	@Bean
	public KieBase kieBase() throws IOException {
		KieBase kieBase = kieContainer().getKieBase();
		return kieBase;
	}
	
	public String getTemplateRule(){
		String drl = "";
		Iterable<RuleTable> iterable = null;
		List<RuleTable> list = new ArrayList<>();
		try {
			iterable = discountRepository.findAll();
			iterable.forEach(list::add);
			System.out.println("list: "+list);
			ObjectDataCompiler converter = new ObjectDataCompiler();
			drl = converter.compile(list, getRulesStream());
			System.out.println("Generated drl file:\n"+drl);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return drl;
	}

	private static InputStream getRulesStream(){
		InputStream inputStream = null;
		try{
			inputStream = new FileInputStream("D:/workspace/git-projects/spring-boot-drools/src/main/resources/com/mindzen/drools/rules/rule_template.drt");
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		return inputStream;
	}

}