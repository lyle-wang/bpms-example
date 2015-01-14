package com.sample.sendtask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.bpmn2.handler.SendTaskHandler;
import org.jbpm.process.core.context.variable.VariableScope;
import org.jbpm.process.instance.ProcessInstance;
import org.jbpm.process.instance.context.variable.VariableScopeInstance;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.WorkflowProcessInstance;

public class SendTaskTest {

	public static void main(String[] args) {
		new SendTaskTest().test();
	}

	public void test() {
	    

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieBase kieBase = kContainer.getKieBase("kbase-sendtask");
        KieSession ksession = kieBase.newKieSession();
        
        
        ksession.getWorkItemManager().registerWorkItemHandler("Send Task", new CustomSendTaskTestHandler());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("s", "john");
        WorkflowProcessInstance processInstance = (WorkflowProcessInstance) ksession.startProcess("com.sample.quickstart.sendtask", params);
        
        
        
        ksession.dispose();
	}

}
