package org.ticketing_system.backend.model.multithreading;

import org.springframework.beans.factory.annotation.Autowired;
import org.ticketing_system.backend.model.Customer;
import org.ticketing_system.backend.service.LoggingService;
import org.ticketing_system.backend.service.TicketPoolService;

import static java.lang.Thread.sleep;

public class CustomerMultithreading implements Runnable{
    private int customer_id;
    private final Customer customer;
    private final TicketPoolService ticketPoolService;
    private final LoggingService loggingService;

    public CustomerMultithreading(Customer customer, TicketPoolService ticketPoolService,LoggingService loggingService, int customer_id) {
        this.customer = customer;
        this.ticketPoolService = ticketPoolService;
        this.loggingService = loggingService;
        this.customer_id = customer_id;
    }

    @Override
    public void run() {
        int purchasedTickets = 0;
        try {
            while (customer.getTicketCount()>purchasedTickets) {

                if(Thread.currentThread().isInterrupted()) {
                    String interrupt = "Thread is interrupted";
                    System.out.println(interrupt);
                    loggingService.log(interrupt);
                    break;
                }

                Integer ticket = ticketPoolService.removeTickets(customer_id);
                purchasedTickets++;
                if (ticket == null) {
                    String out = "Customer " + customer_id + " found no tickets available.";
                    System.out.println(out);
                    loggingService.log(out);
                    break;
                }

                // Wait before attempting to retrieve another ticket
                Thread.sleep(customer.getRetrievalInterval());
            }
            String custout = "Customer " + customer_id + " has stopped purchasing as no more tickets are available or max reached.";
            System.out.println(custout);
            loggingService.log(custout);
        } catch (InterruptedException e) {
            System.out.println("Customer " + customer_id + " interrupted.");
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }
}
