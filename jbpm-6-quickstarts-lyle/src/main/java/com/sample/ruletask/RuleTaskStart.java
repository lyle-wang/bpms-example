package com.sample.ruletask;

import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

public class RuleTaskStart {

    public static void main(String[] args) {

        invoke_one();
        invoke_two();

        System.exit(0);
    }

    private static void invoke_one() {
        System.out.println("Start invoke_one...");
        KnowledgeBase kbase = readKnowledgeBase(
                "com/sample/ruletask/ruletaskprocess-rule.drl",
                "com/sample/ruletask/ruletaskprocess.bpmn");
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

        ksession.startProcess("org.jbpm.quickstarts.ruletaskprocess");
        ksession.fireAllRules();
        ksession.dispose();
        System.out.println("Finish invoke_one...");
    }

    private static KnowledgeBase readKnowledgeBase(String drl, String... process) {

        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        for (String p : process) {
            kbuilder.add(ResourceFactory.newClassPathResource(p), ResourceType.BPMN2);
        }

        if (drl != null) {
            kbuilder.add(ResourceFactory.newClassPathResource(drl), ResourceType.DRL);
        }

        if (kbuilder.hasErrors()) {
            if (kbuilder.getErrors().size() > 0) {
                throw new RuntimeException("Create KnowledgeBuilder Error,"
                        + kbuilder.getErrors().toString());
            }
        }
        return kbuilder.newKnowledgeBase();
    }

    private static void invoke_two() {
        
        System.out.println("Start invoke_two...");
        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();

        KieSession ksession = kcontainer.newKieSession();

        // Or, load specific session name
        // KieSession ksession = kcontainer.newKieSession("default-session");

        ksession.startProcess("org.jbpm.quickstarts.ruletaskprocess");

        ksession.fireAllRules();
        
        ksession.dispose();
        System.out.println("Finish invoke_two...");
    }
}
