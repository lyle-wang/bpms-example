package com.sample.orderdiscount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


/**
 * This is a sample class to launch a rule.
 */
public class OrderItemDiscountTest {

    public static final void main(String[] args) {
        try {

	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-discount");

        	
        	
        	Order order = new Order();
        	
        	Item item_1 = new Item("TestItem-1");
        	item_1.setItemValue(new BigDecimal("100.01"));
        	
        	Item item_2 = new Item("TestItem-2");
        	item_2.setItemValue(new BigDecimal("99.98"));
        	
        	Item item_3 = new Item("TestItem-3");
        	item_3.setItemValue(new BigDecimal("105"));
        	
        	
        	List<Item> lstItems = new ArrayList<Item>();
        	lstItems.add(item_1);
        	lstItems.add(item_2);
        	lstItems.add(item_3);
        	
        	order.setItems(lstItems);
        	order.setOrderID("001");
        	order.setAddress("Test Address");
        	
        	
        	System.out.println("============ Insert Fact and fireAllRules() ============");

            kSession.insert(order);
            kSession.fireAllRules();
            
            System.out.println("============ Rule Fired ============");
            
            for (Item item : order.getItems()) {
                System.out.println("Item:"+item.getItemName()+": Item Value:"+item.getItemValue()+": Discount:"+item.getItemDiscount().toString()+": ============");
            }
            
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
