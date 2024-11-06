package org.example.ticketing_system;

import jakarta.persistence.criteria.CriteriaBuilder;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        TicketPool ticketPool = new TicketPool();

        Vendor vendor1 = new Vendor(1,5,100,ticketPool);
        Thread thread1 = new Thread(vendor1);

        Vendor vendor2 = new Vendor(2,3,100,ticketPool);
        Thread thread2 = new Thread(vendor2);

        Customer customer1 = new Customer(1,50,ticketPool);
        Thread thread3 = new Thread(customer1);

        Customer customer2 = new Customer(2,80,ticketPool);
        Thread thread4 = new Thread(customer2);

        System.out.println("Available tickets : "+ticketPool.getAllTickets());

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

//        added delay to check ticket availability
        Thread.sleep(500);

        System.out.println("Available tickets : "+ticketPool.getAllTickets());
    }
}
