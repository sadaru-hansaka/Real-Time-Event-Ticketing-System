package org.example.ticketing_system;

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
            while (ticketPool.hasTickets() || !ticketPool.isMaxReached()) {
                synchronized (ticketPool) {
                    if (ticketPool.hasTickets()) {
                        Integer ticket = ticketPool.removeTickets();
                        if (ticket != null) {
                            System.out.println("Customer " + customerId + " purchased ticket " + ticket);
                        }
                    } else {
                        System.out.println("Customer " + customerId + " found no tickets available.");
                    }
                }
                sleep(retrievalInterval);
            }
        } catch (InterruptedException e) {
            System.out.println("Customer " + customerId + " interrupted.");
        }
    }
}
