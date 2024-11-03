package org.example.ticketing_system;

public class Customer implements Runnable{
    private int customerId;
    private int retrievalInterval;

    public Customer(int customerId, int retrievalInterval) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
    }

//    run method
    @Override
    public void run() {

    }
}
