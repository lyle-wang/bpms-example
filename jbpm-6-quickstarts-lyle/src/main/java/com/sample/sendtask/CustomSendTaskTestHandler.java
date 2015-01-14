package com.sample.sendtask;

import org.jbpm.bpmn2.handler.SendTaskHandler;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;



public class CustomSendTaskTestHandler extends SendTaskHandler {
    

    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        String message = (String) workItem.getParameter("Message");
        System.out.println("Custom Sending message: "+message);
        
        // TODO
        // Send your message out here, using whatever technology you like, Webservices, JMS, EJB...
        
        manager.completeWorkItem(workItem.getId(), null);
    }

    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        // Do nothing, cannot be aborted
    }
}



