package org.ticketing_system.backend.model;

public class Customer {
    private int customer_id;
    private int retrievalInterval;
    private int ticketCount;

    public Customer(int customer_id, int retrievalInterval, int ticketCount) {
        this.customer_id = customer_id;
        this.retrievalInterval = retrievalInterval;
        this.ticketCount = ticketCount;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }

    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
}
