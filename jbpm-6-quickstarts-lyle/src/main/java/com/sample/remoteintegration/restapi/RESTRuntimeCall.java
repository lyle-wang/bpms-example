package com.sample.remoteintegration.restapi;

import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.client.ClientResponse;
import org.kie.services.client.api.RestRequestHelper;

public class RESTRuntimeCall {

    
    private static final String APP_URL = "http://localhost:8080/business-central/rest";
    private static final String USER = "jboss";
    private static final String PASSWORD = "123$qweR";
    
    private static final String DEPLOYMENT_ID = "com.sample:TestProj:1.5";
    private static final String RUNTIMEROOT = "/runtime/" + DEPLOYMENT_ID;
    

    
    public static void main(String[] args) {

        // Please follow this doc:
        // https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.0/html/Development_Guide/sect-Runtime_REST_API.html
        // Kylin's demo: https://github.com/kylinsoong/jBPM-Drools-Example/tree/master/jbpm-6-examples/rest
        
        // Before running this test, please install your remote BRMS/BPMS server
        // and create/deploy project with G:A:V="com.sample":"TestProj":"1.5"
        // "resources/helloworldScriptTask.bpmn" is used for testing (upload it to remote BPMS project)

        try {
            
            String processDefId = "org.jbpm.quickstarts.helloworldScript";
            
            String startProcessURL = APP_URL + RUNTIMEROOT + "/process/" + processDefId + "/start";
            System.out.println("Start Process via " + startProcessURL);
            ClientRequest clientRequest = getClientRequestFactory().createRequest(startProcessURL);
            ClientResponse<?> responseObj = checkResponse(clientRequest.post());
            System.out.println("Done");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    public static ClientResponse<?> checkResponse(ClientResponse<?> responseObj) throws Exception {
        responseObj.resetStream();
        int status = responseObj.getStatus();
        if (status != 200) {
        throw new Exception("Response with exception:\n" + responseObj.getEntity(String.class));
        }
        return responseObj;
    }
    

    private static ClientRequestFactory getClientRequestFactory() throws MalformedURLException {
        return RestRequestHelper.createRequestFactory(new URL(APP_URL), USER, PASSWORD);
    }
}