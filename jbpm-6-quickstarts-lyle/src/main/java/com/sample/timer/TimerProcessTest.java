package com.sample.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class TimerProcessTest {

	public static void main(String[] args) {
		new TimerProcessTest().test();
	}

	public void test(){
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieBase kieBase = kContainer.getKieBase("kbase-timer");
        KieSession ksession = kieBase.newKieSession();
        
        Calendar calNow = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String txtDate = format.format(calNow.getTime());
        
        System.out.println("------------------------------");
        System.out.println("The \"Timer Start Event\" in the process will start a process instance every 20 seconds");
        System.out.println("And the \"Intermediate Timer Event\" in the process will hold up to 10 seconds before executing Task-2");
        System.out.println("Try to use the process editor in `business-central` if JBDS's editor doesn't work well with timer event");
        
        System.out.println("------------------------------");
        System.out.println("Starting Process Now:=="+txtDate+"==");
        System.out.println("------------------------------");
        
        ksession.startProcess("com.sample.quickstart.timer");
        
        // Wait for 2 rounds and then terminate it.
        try {
            Thread.sleep(50000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ksession.dispose();
        }
	}

}
