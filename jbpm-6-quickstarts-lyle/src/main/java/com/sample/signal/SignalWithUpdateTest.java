package com.sample.signal;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.process.instance.ProcessInstance;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class SignalWithUpdateTest {

	public static void main(String[] args) throws InterruptedException {

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieBase kieBase = kContainer.getKieBase("kbase-signal");
        KieSession ksession = kieBase.newKieSession();
        
        
        Item testItem = new Item();
        testItem.setName("start item");
        testItem.setHasError(true);
        
        // pass in parameter
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("testItem", testItem);
         
        // start a new process instance
        ProcessInstance processInstance = (ProcessInstance) ksession.startProcess("com.sample.signal.test",params);
        
        
        
        Thread.sleep(2000);
        Item item_update_1 = new Item();
        item_update_1.setName("udpate - 1");
        item_update_1.setHasError(true);
        ksession.signalEvent("usersigupdate", item_update_1, processInstance.getId());
        
        
        Thread.sleep(2000);
        Item item_update_2 = new Item();
        item_update_2.setName("udpate - 2");
        item_update_2.setHasError(true);
        ksession.signalEvent("usersigupdate", item_update_2, processInstance.getId());
        
        
        Thread.sleep(2000);
        Item item_cancel = new Item();
        item_cancel.setName("PASS");
        item_cancel.setHasError(false);
        ksession.signalEvent("usersigpass", item_cancel, processInstance.getId());
        
        
        
        ksession.dispose();
        System.exit(0);
	}


}