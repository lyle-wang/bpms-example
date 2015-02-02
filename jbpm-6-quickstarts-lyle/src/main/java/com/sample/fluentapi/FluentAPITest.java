package com.sample.fluentapi;

import org.jbpm.bpmn2.xml.XmlBPMNProcessDumper;
import org.jbpm.ruleflow.core.RuleFlowProcess;
import org.jbpm.ruleflow.core.RuleFlowProcessFactory;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message.Level;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;


/**
 * 
 * This test is to show how to use "Fluent API" to create a process definition on-the-fly with Java API
 * Please note there are some *internal* / *deprecated* API are used.
 * 
 * Introduced in jbpm community doc: http://docs.jboss.org/jbpm/v6.0/userguide/jBPMBPMN2.html#d0e3568
 * 
 * Updated sample "usingPublicAPI" follows example from:
 * https://github.com/droolsjbpm/jbpm/blob/master/jbpm-bpmn2/src/test/java/org/jbpm/bpmn2/ProcessFactoryTest.java
 * 
 */
public class FluentAPITest {

	public static void main(String[] args) {

	    System.out.println("=== Starting Test with example code from jbpm v6 doc ===");
	    System.out.println("=== Following: http://docs.jboss.org/jbpm/v6.0/userguide/jBPMBPMN2.html#d0e3568 ===");
	    System.out.println("=== Using deprecated and internal API ===");
	    
	    exampleUsingDeprecatedAndInternalAPI();
	    
	    
	    
	    System.out.println();
	    System.out.println();
	    
	    
	    
	    
	    System.out.println("=== Starting Test with upgraded code ===");
	    System.out.println("=== Following: https://github.com/droolsjbpm/jbpm/blob/master/jbpm-bpmn2/src/test/java/org/jbpm/bpmn2/ProcessFactoryTest.java ===");
	    System.out.println("=== Using public KIE API ===");
        
	    exampleUsingPublicAPI();
	}
	
	private static void exampleUsingPublicAPI() {
	    RuleFlowProcessFactory factory = RuleFlowProcessFactory.createProcess("org.jbpm.HelloWorld");

	    factory

	        // Header
	        .name("HelloWorldProcess")
	        .version("1.0")
	        .packageName("org.jbpm")

	        // Nodes
	        .startNode(1).name("Start").done()
	        .actionNode(2).name("Action")
	            .action("java", "System.out.println(\"Hello World\");").done()
	            .endNode(3).name("End").done()

	        // Connections
	        .connection(1, 2)
	        .connection(2, 3);

	    RuleFlowProcess process = factory.validate().getProcess();
            
	    Resource res = ResourceFactory.newByteArrayResource(XmlBPMNProcessDumper.INSTANCE.dump(process).getBytes());
        
	    // source path or target path must be set to be added into kbase
	    res.setSourcePath("/tmp/FluentAPISample.bpmn2"); 
        
	    KieBase kieBase = createKnowledgeBaseFromResources(res);
	    KieSession ksession = kieBase.newKieSession();
        
	    ksession.startProcess("org.jbpm.HelloWorld");

	    ksession.dispose();
	}
	
    protected static KieBase createKnowledgeBaseFromResources(Resource... process) {
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();
        if (process.length > 0) {
            KieFileSystem kfs = ks.newKieFileSystem();
            for (Resource p : process) {
                kfs.write(p);
            }
            
            KieBuilder kb = ks.newKieBuilder(kfs);
            
            // kieModule is automatically deployed to KieRepositoryif successfully built.
            kb.buildAll(); 
            if (kb.getResults().hasMessages(Level.ERROR)) {
                throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
            }
        }
        KieContainer kContainer = ks.newKieContainer(kr.getDefaultReleaseId());
        
        return kContainer.getKieBase();
    }
	
	

    private static void exampleUsingDeprecatedAndInternalAPI() {
        
        RuleFlowProcessFactory factory = RuleFlowProcessFactory.createProcess("org.jbpm.HelloWorld");

        factory

            // Header
            .name("HelloWorldProcess")
            .version("1.0")
            .packageName("org.jbpm")

            // Nodes
            .startNode(1).name("Start").done()
            .actionNode(2).name("Action")
                .action("java", "System.out.println(\"Hello World\");").done()
            .endNode(3).name("End").done()

            // Connections
            .connection(1, 2)
            .connection(2, 3);

        RuleFlowProcess process = factory.validate().getProcess();
        
        
        // Use of Internal API
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        kbuilder.add(ResourceFactory.newByteArrayResource(
            XmlBPMNProcessDumper.INSTANCE.dump(process).getBytes()), ResourceType.BPMN2);

        
        // Use of Deprecated API
        KnowledgeBase kbase = kbuilder.newKnowledgeBase();
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        ksession.startProcess("org.jbpm.HelloWorld");
        
        ksession.dispose();
    }
    
}