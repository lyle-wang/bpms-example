package com.sample.subprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.process.core.context.variable.VariableScope;
import org.jbpm.process.instance.ProcessInstance;
import org.jbpm.process.instance.context.variable.VariableScopeInstance;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class ReusableSubProcessTest {

	public static void main(String[] args) {
		new ReusableSubProcessTest().test();
	}

	public void test() {
	    

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieBase kieBase = kContainer.getKieBase("kbase-subprocess");
        KieSession ksession = kieBase.newKieSession();
        
        
		List<String> messages = new ArrayList<String>();
        messages.add("message 1");
        messages.add("message 2");
        messages.add("message 3");
        
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("messages", messages); 
        
        //Start the process using its id
        ProcessInstance process = (ProcessInstance) ksession.startProcess("org.jbpm.quickstarts.reusablesubprocessparent",parameters);
        
        
        VariableScopeInstance variableScope = (VariableScopeInstance) process.getContextInstance(VariableScope.VARIABLE_SCOPE);

        Map<String, Object> variables = variableScope.getVariables();
        messages = (List<String>) variables.get("messages");
      
        
        for (String message : messages) {
            System.out.println("Message = "+message);
        }
        ksession.dispose();
	}

}
