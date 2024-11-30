package org.CLI.ticketing_system;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Customer implements Runnable{


    private int customerId;
    private int retrievalInterval;
    private TicketPool ticketPool;

    public Customer(int customerId, int retrievalInterval, TicketPool ticketPool) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
    }

//    run method
    @Override
    public void run() {
        try {
            while (ticketPool.hasTickets()) {
                Integer ticket = ticketPool.removeTickets(customerId);
                if (ticket == null) {
                    String out = "Customer " + customerId + " found no tickets available.";
                    System.out.println(out);
                }

                // Wait before attempting to retrieve another ticket
                sleep(retrievalInterval);
            }
            System.out.println("Customer " + customerId + " has stopped purchasing as no more tickets are available or max reached.");
        } catch (InterruptedException e) {
            System.out.println("Customer " + customerId + " interrupted.");
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }
}
