package org.ticketing_system.backend.service;

import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ticketing_system.backend.model.Configuration;
import org.ticketing_system.backend.model.Customer;
import org.ticketing_system.backend.model.multithreading.CustomerMultithreading;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CustomerService{

    private final Map<Integer, Customer> customers = new ConcurrentHashMap<>();
    private final Map<Integer, Thread> customerThreads = new ConcurrentHashMap<>();

    private final List<Integer> completedThreads = new ArrayList<>();

    private int customer_id = 1;

    @Autowired
    private TicketPoolService ticketPoolService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private LoggingService loggingService;

    @Autowired
    private VendorService vendorService;

    public CustomerService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    public Customer createCustomer(int ticketCount) {

        try{
            Configuration configuration = configurationService.getData();
            int retrievalRate = configuration.getCustomerRetrievalRate();
            int totalTickets = configuration.getTotalTickets();

            if(ticketCount > totalTickets){
                String error1 = "Number of tickets must be less than total tickets";
                loggingService.log(error1);
                throw new IllegalArgumentException(error1);
            }

            Customer customer = new Customer(customer_id,retrievalRate,ticketCount);
            customers.put(customer_id,customer);
            customer_id++;
            return customer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Method to start the customer thread
    public void runCustomer(int customerID) {
        Customer customer= customers.get(customerID);
        if(customer == null){
            String out = "Customer "+customerID+" not found";
            System.out.println(out);
            loggingService.log(out);
            return;
        }
        Thread customerThread = new Thread(new CustomerMultithreading(customer, ticketPoolService,loggingService, customerID,this));
        customerThreads.put(customerID, customerThread);
        customerThread.start();
    }

//    return running customers
    public Map<Integer, Thread> getRunningCustomers() {
        return customerThreads;
    }

//  Stop all customers
    public void stopCustomer() {
        for(Thread thread: customerThreads.values()){
            thread.interrupt();
        }
        customerThreads.clear();
    }

    public Map<Integer,Customer> getCustomers() {
        return customers;
    }

    public int getCustomerID() {
        return customer_id;
    }


    public int getTotalAvailableTickets(){
        int sumOfTickets = vendorService.issued();
        int tickets = customers.values().stream().mapToInt(Customer::getTicketCount).sum();
        int remain= sumOfTickets-tickets;
        return remain;
    }

    public void markCompletedThreads(int customer_id) {
        completedThreads.add(customer_id);
    }

    public List<Integer> returnCompletedThreads() {
        return completedThreads;
    }
}
