package org.ticketing_system.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ticketing_system.backend.model.Configuration;
import org.ticketing_system.backend.model.Customer;
import org.ticketing_system.backend.model.multithreading.CustomerMultithreading;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CustomerService{
    private Map<Integer, Customer> customers = new ConcurrentHashMap<>();
    private int customerID;

    @Autowired
    private TicketPoolService ticketPoolService;

    @Autowired
    private ConfigurationService configurationService;


    public void setCustomersID(int customerID){
        this.customerID = customerID;
    }

    public Customer createCustomer(int customer_id,int ticketCount) {
        try{
            Configuration configuration = configurationService.getData();
            int retrievalRate = configuration.getCustomerRetrievalRate();
            Customer customer = new Customer(customer_id,retrievalRate,ticketCount);
            customers.put(customer_id,customer);
            return customer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Method to start the customer thread
    public void runCustomer(int customerID) {
        Customer customer= customers.get(customerID);
        if(customer == null){
            System.out.println("Customer not found");
            return;
        }
        Thread customerThread = new Thread(new CustomerMultithreading(customer, ticketPoolService, customerID));
        customerThread.start();
    }

    public Map<Integer,Customer> getCustomers() {
        return customers;
    }


}