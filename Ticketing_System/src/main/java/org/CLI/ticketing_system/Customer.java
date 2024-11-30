package org.CLI.ticketing_system;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Customer implements Runnable{


    private int customerId;
    private int retrievalInterval;
    private TicketPool ticketPool;

    private Logging logging;

    public Customer(int customerId, int retrievalInterval, TicketPool ticketPool) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;

        try{
            this.logging = Logging.getInstance("src/main/resources/Logging.txt");
        }catch(IOException e){
            System.out.println("Error");
        }
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
                    logging.log(out);
                }

                // Wait before attempting to retrieve another ticket
                sleep(retrievalInterval);
            }
            String custout = "Customer " + customerId + " has stopped purchasing as no more tickets are available or max reached.";
            System.out.println(custout);
            logging.log(custout);
        } catch (InterruptedException e) {
            System.out.println("Customer " + customerId + " interrupted.");
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }
}
