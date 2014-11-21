package com.sample.remotemaven;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class RemoteMavenIntegrationTest {

    public static void main(String[] args) {
       
        // Please follow this solution article :   https://access.redhat.com/solutions/710763
        // Before running this test, please install your remote BRMS/BPMS server and create/deploy project with G:A="com.sample":"TestProj"
        // "helloworldScriptTask.bpmn" is used for testing (upload it to remote BPMS project)
        
        loadWithReleaseId();

        try {
            usingKieScanner();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    private static void loadWithReleaseId() {

        System.out.println("Using ReleaseId to load kjar once ----------- START -----------");
        KieServices kServices = KieServices.Factory.get();
        ReleaseId releaseId = kServices.newReleaseId("com.sample", "TestProj", "LATEST");
        KieContainer kContainer = kServices.newKieContainer(releaseId);
        KieBase kbase = kContainer.getKieBase();
        KieSession ksession = kbase.newKieSession();

        ksession.startProcess("org.jbpm.quickstarts.helloworldScript");
        System.out.println("Using ReleaseId to load kjar once ----------- END -----------");
        System.out.println();
        System.out.println();
    }
    

    private static void usingKieScanner() throws InterruptedException {

        System.out.println("Using KieScanner ----------- START -----------");
        KieServices kServices = KieServices.Factory.get();
        ReleaseId releaseId = kServices.newReleaseId("com.sample", "TestProj", "LATEST");
        KieContainer kContainer = kServices.newKieContainer(releaseId);
        KieScanner kScanner = kServices.newKieScanner(kContainer);
        kScanner.start(1000);

        int i = 1;
        while (i < 100) {
            System.out.println("In Loop ----------- "+i+" -----------");
            KieSession ksession = kContainer.newKieSession();
            ksession.startProcess("org.jbpm.quickstarts.helloworldScript");

            Thread.sleep(5000);
            i++;
        }
    }
}