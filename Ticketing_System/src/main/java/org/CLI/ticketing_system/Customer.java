package org.CLI.ticketing_system;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Customer implements Runnable{


    private int customerId;
    private int retrievalInterval;
    private TicketPool ticketPool;
    private int ticketCount;

    private Logging logging;

    public Customer(int customerId, int retrievalInterval,int ticketCount, TicketPool ticketPool) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
        this.ticketCount = ticketCount;


        try{
            this.logging = Logging.getInstance("src/main/resources/Logging.txt");
        }catch(IOException e){
            System.out.println("Error");
        }
    }

    int purchasedTickets = 0;

//    run method
    @Override
    public void run() {
        try {
            while (ticketCount>purchasedTickets) {
                Integer ticket = ticketPool.removeTickets(customerId);
                purchasedTickets++;
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
