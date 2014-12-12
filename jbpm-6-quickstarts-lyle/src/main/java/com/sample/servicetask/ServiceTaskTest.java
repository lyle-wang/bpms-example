package com.sample.servicetask;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.process.core.context.variable.VariableScope;
import org.jbpm.process.instance.ProcessInstance;
import org.jbpm.process.instance.context.variable.VariableScopeInstance;
import org.jbpm.process.workitem.webservice.WebServiceWorkItemHandler;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.WorkItemHandler;

public class ServiceTaskTest {

    
    /*
     * This example shows how to use a "Service Task" inside a jbpm process to call a remote webservice
     * 
     * Using this public webservice as the target (Thanks http://www.webservicex.net/) :
     * http://www.webservicex.net/globalweather.asmx?op=GetWeather
     * 
     * The code in "com/sample/servicetask/wsclient" is the generated client code using the wsdl:
     * http://www.webservicex.net/globalweather.asmx?WSDL
     * 
     * It is convenient to generate a webservice client using remote wsdl file
     * If you search on google, can find many docs about this, for example:
     * http://www.eclipse.org/webtools/community/education/web/t320/Generating_a_client_from_WSDL.pdf
     * 
     * Class "com.sample.servicetask.wsclient.weather.clientsample.WeatherWSClient" can be executed as standalone java program
     * This shows how to call the remote service from Java code
     * 
     * The client requires "woodstox" 4.2.0 dependency (explicitly set in pom.xml), otherwise will get this error:
     * http://stackoverflow.com/questions/16741518/eclipse-web-service-client-wizard-and-cxf
     *
     */
    public static void main(String[] args) {
        
        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        KieSession ksession = kcontainer.newKieSession("defaultWSKieSession");
        
        // Need to setup WebService work item handler
        // In KCS: https://access.redhat.com/solutions/800663
        // This is setup via kmodule.xml, but I get ClassNotFound issue if I setup that in kmodule.xml, even if I included the dependency
        // This should be something classloader related
        // So I try to setup this in Java code here, learned this from example below:
        // https://developer.jboss.org/people/bpmn2user/blog/2013/12/29/jbpm6--custom-work-item-hello-process-example
        ksession.getWorkItemManager().registerWorkItemHandler("WebService", (WorkItemHandler) new WebServiceWorkItemHandler(ksession));

        
        // The webservice work item handler loads object whose name is "Parameter" in the process
        // and use it in the remote webservice invocation
        // So I add the parameter map here and in the process,
        // need to map this "paraMap" to "Parameter" in the service task's "I/O Parameters" setting
        // Note: the order of parameter does matter, check the invocation in class "com.sample.servicetask.wsclient.weather.clientsample.WeatherWSClient"
        Map<String, Object> params = new HashMap<String, Object>();
        Object[] objParam = {"brisbane","australia"};
        params.put("paraMap", objParam);
        
        
        // start a new process instance
        ProcessInstance processInstance = (ProcessInstance) ksession.startProcess("com.sample.servicetask.weather",params);
        
        // Get variables back
        VariableScopeInstance variableScope = (VariableScopeInstance) processInstance.getContextInstance(VariableScope.VARIABLE_SCOPE);

        Map<String, Object> variables = variableScope.getVariables();
        
        ksession.dispose();
        System.exit(0);
    }


}
