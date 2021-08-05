package com.kritjo.ap.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Container that manages customer objects.
 */
public class CustomerContainer {
    /**
     * Data structure of customers, with GSM as key.
     */
    private final HashMap<String, Customer> container = new HashMap<>();

    /**
     * Method that returns the size of the container. In other words, how many customers
     * the container consists of
     * @return Size of container
     */
    public int getContainerSize(){
        return container.size();
    }

    /**
     * Method that returns all the customer containers.
     * @return the HashMap containing all customers
     */
    public HashMap<String, Customer> getAllCustomerContainers(){
        return container;
    }
    /**
     * @return Customers with deviations as per Customer spesification.
     */
    public ArrayList<Customer> getDeviations() {
        ArrayList<Customer> deviatingCustomers = new ArrayList<>();
        for (Customer c : container.values()) {
            if (c.hasDeviation()) {
                deviatingCustomers.add(c);
            }
        }
        return deviatingCustomers;
    }

    /**
     * Adds a new customer to the container. If the customer already exists the provision object is instead added to the
     * exsisting customer.
     *
     * @param type Actual or expected provision. The provision object is added to the according ProvisionContainer under
     *             the customer object.
     */
    public void addCustomer(String gsm, float provision, String product, String ref, String name, ProvisionFile.Type type) {
        Provision newProvision = new Provision(provision);
        newProvision.setRef(ref);
        newProvision.setProduct(product);
        newProvision.setProduct(product);
        if (container.containsKey(gsm)) {
            Customer existingCustomer = container.get(gsm);
            if (type == ProvisionFile.Type.ACTUAL) {
                existingCustomer.addActual(newProvision);
            } else {
                existingCustomer.addExpected(newProvision);
            }
        } else {
            Customer newCustomer = new Customer(gsm, name);
            if (type == ProvisionFile.Type.ACTUAL) {
                newCustomer.addActual(newProvision);
            } else {
                newCustomer.addExpected(newProvision);
            }
            container.put(gsm, newCustomer);
        }
    }
}
