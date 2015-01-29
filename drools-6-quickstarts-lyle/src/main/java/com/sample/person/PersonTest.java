package com.sample.person;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class PersonTest {

    public static final void main(String[] args) {
        try {

	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-person");

        	
        	
        	Person lyle = new Person();
        	lyle.setAge(32);
        	lyle.setName("Lyle");
        	lyle.setRoleName("Technical Support Engineer");
        	
        	System.out.println("============ Initialize Fact ============");
        	System.out.println("Initial fact inserted:"+lyle.getName()+" / "+lyle.getAge()+" / "+lyle.getRoleName() + " ======");
        	System.out.println("");


            kSession.insert(lyle);
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static class Person {

        private String name;
        private int age;
        private String roleName;
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public String getRoleName() {
            return roleName;
        }
        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }
    }
}
