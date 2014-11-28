package com.sample.remoteintegration.restapi;

import java.net.URL;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.services.client.api.RemoteRestRuntimeFactory;
import org.kie.services.client.api.command.RemoteRuntimeEngine;

public class RemoteRuntimeEngineAPI {

    /*
     * Set the parameters according your installation
     * Need to create a project with G:A:V="com.sample":"TestProj":"1.5"
     * Then Add a process with process ID "org.jbpm.quickstarts.helloworldScript"
     * Maybe create start human tasks for user "jboss" as well for better testings
     */
    private static final String DEPLOYMENT_ID = "com.sample:TestProj:1.5";
    private static final String APP_URL = "http://localhost:8080/business-central/";
    private static final String USER = "jboss";
    private static final String PASSWORD = "123$qweR";

    public static void main(String[] args) {

        // Please follow this solution article :
        // https://access.redhat.com/solutions/784863
        // https://access.redhat.com/solutions/904033

        // Before running this test, please install your remote BRMS/BPMS server
        // and do the configuration accordingly

        try {
            URL url = new URL(APP_URL);
            
            // create a factory using the installation parameters
            RemoteRestRuntimeFactory factory = new RemoteRestRuntimeFactory(DEPLOYMENT_ID, url, USER, PASSWORD);
            RemoteRuntimeEngine engine = factory.newRuntimeEngine();
            
            
            
            
            // Use TaskService to get Task
            // The TaskService class allows we to access the server tasks
            TaskService taskService = engine.getTaskService();
            List<TaskSummary> tasks = taskService.getTasksOwned(USER, "en-UK");
            if (tasks.size() == 0) {
                System.out.printf("No tasks for user \"%s\" as owner...\n", USER);
            } else {
                System.out.printf("Tasks where user \"%s\" is a an owner...\n",
                        USER);
                for (TaskSummary t : tasks) {
                    System.out.printf("ID: %d\n", t.getId());
                    System.out.printf("Name: %s\n", t.getName());
                    System.out.printf("Actual Owner: %s\n", t.getActualOwner());
                    System.out.printf("Created by: %s\n", t.getCreatedBy());
                    System.out.printf("Created on: %s\n", t.getCreatedOn());
                    System.out.printf("Status: %s\n", t.getStatus());
                    System.out.printf("Description: %s\n", t.getDescription());
                    System.out.println("---------------");
                }
            }
            
            
            
            
            // Use KieSession to start process
            KieSession ksession = engine.getKieSession();
            
            //Start the process using its id
            ksession.startProcess("org.jbpm.quickstarts.helloworldScript");
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
