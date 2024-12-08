package org.ticketing_system.backend.model.multithreading;

import org.ticketing_system.backend.model.Customer;
import org.ticketing_system.backend.service.TicketPoolService;

import static java.lang.Thread.sleep;

public class CustomerMultithreading implements Runnable{
    private int customer_id;
    private final Customer customer;
    private final TicketPoolService ticketPoolService;

    public CustomerMultithreading(Customer customer, TicketPoolService ticketPoolService, int customer_id) {
        this.customer = customer;
        this.ticketPoolService = ticketPoolService;
        this.customer_id = customer_id;
    }

    @Override
    public void run() {
        int purchasedTickets = 0;
        try {
            while (customer.getTicketCount()>purchasedTickets) {

                Integer ticket = ticketPoolService.removeTickets(customer_id);
                purchasedTickets++;
                if (ticket == null) {
                    String out = "Customer " + customer_id + " found no tickets available.";
                    System.out.println(out);
                    break;
                }

                // Wait before attempting to retrieve another ticket
                Thread.sleep(customer.getRetrievalInterval());
            }
            String custout = "Customer " + customer_id + " has stopped purchasing as no more tickets are available or max reached.";
            System.out.println(custout);
        } catch (InterruptedException e) {
            System.out.println("Customer " + customer_id + " interrupted.");
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }
}
