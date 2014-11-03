package com.sample.errorhandling;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.process.core.context.variable.VariableScope;
import org.jbpm.process.instance.ProcessInstance;
import org.jbpm.process.instance.context.variable.VariableScopeInstance;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class CatchErrorEmbeddedSubProcessTest {

    public static void main(String[] args) {
        
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieBase kieBase = kContainer.getKieBase("kbase-errorhandling");
        KieSession ksession = kieBase.newKieSession();
        
                
        // pass in parameter
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ex", null);
        
        // start a new process instance
        ProcessInstance processInstance = (ProcessInstance) ksession.startProcess("com.sample.errorhandling.embedded",params);
        
        // Get variables back
        VariableScopeInstance variableScope = (VariableScopeInstance) processInstance.getContextInstance(VariableScope.VARIABLE_SCOPE);

        Map<String, Object> variables = variableScope.getVariables();
        
        // See if we could fetch the exception thrown from process
        RuntimeException exDuringProcess = (RuntimeException) variables.get("ex");
        if (null == exDuringProcess) {
            System.out.println("No exception was caught");
        } else {
            System.out.println("Caught Exception in process:"+exDuringProcess.getMessage());
        }
        
        ksession.dispose();
        System.exit(0);
    }


}
