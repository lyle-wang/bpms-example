package com.sample.fluentapi;

import org.jbpm.bpmn2.xml.XmlBPMNProcessDumper;
import org.jbpm.ruleflow.core.RuleFlowProcess;
import org.jbpm.ruleflow.core.RuleFlowProcessFactory;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

/**
 * 
 * This test is to show how to use "Fluent API" to create a process definition on-the-fly with Java API
 * Please note some *internal* API are used.
 * 
 */
public class FluentAPITest {

	public static void main(String[] args) {
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

	        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

	        kbuilder.add(ResourceFactory.newByteArrayResource(

	            XmlBPMNProcessDumper.INSTANCE.dump(process).getBytes()), ResourceType.BPMN2);

//	        KnowledgeBase kbase = kbuilder.newKnowledgeBase();
	        KieBase kbase = kbuilder.newKnowledgeBase();

//	        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
	        KieSession ksession = kbase.newKieSession();

	        ksession.startProcess("org.jbpm.HelloWorld");
	}


}