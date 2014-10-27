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

public class RuntimeErrorTest {

    public static void main(String[] args) {
        
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieBase kieBase = kContainer.getKieBase("kbase-errorhandling");
        KieSession ksession = kieBase.newKieSession();
        
        Booking objBooking = new Booking();
        objBooking.setGuestNumber(6);
        objBooking.setId("001");
                
        // pass in parameter
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("booking", objBooking);
        params.put("ex_process", null);
         
        // start a new process instance
        ProcessInstance processInstance = (ProcessInstance) ksession.startProcess("com.sample.errorhandling.parent",params);
        
        // Get variables back
        VariableScopeInstance variableScope = (VariableScopeInstance) processInstance.getContextInstance(VariableScope.VARIABLE_SCOPE);

        Map<String, Object> variables = variableScope.getVariables();
        Booking rtnBooking = (Booking) variables.get("booking");
        
        // See if we could fetch the exception thrown from process
        RuntimeException exDuringProcess = (RuntimeException) variables.get("ex_process");
        if (null == exDuringProcess) {
            System.out.println("Bedroom Numbers::"+rtnBooking.getBedrmNumber());
        } else {
            System.out.println("Caught Exception in process:"+exDuringProcess.getMessage());
            exDuringProcess.printStackTrace();
        }
        
        ksession.dispose();
        System.exit(0);
    }


}
