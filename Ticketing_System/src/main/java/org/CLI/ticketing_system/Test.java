package org.CLI.ticketing_system;

import org.Backend.ticketing_system.Customer;
import org.Backend.ticketing_system.TicketPool;
import org.Backend.ticketing_system.Vendor;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws InterruptedException, IOException {
        Configuration config = Configuration.loadConfiguration("src/main/resources/config.json");

//        Ticket Pool
        TicketPool ticketPool = new TicketPool(config.getMaxNUmOfTickets(),config.getTotalTickets());

        Vendor vendor1 = new Vendor(1,6,config.getTicketReleaseRate(),8,ticketPool);
        Thread thread1 = new Thread(vendor1);

        Vendor vendor2 = new Vendor(2,5,config.getTicketReleaseRate(),7,ticketPool);
        Thread thread2 = new Thread(vendor2);

        Customer customer1 = new Customer(1,config.getCustomerRetrievalRate(),ticketPool);
        Thread thread3 = new Thread(customer1);

        Customer customer2 = new Customer(2, config.getCustomerRetrievalRate(), ticketPool);
        Thread thread4 = new Thread(customer2);

//        System.out.println("Available tickets : "+ticketPool.getAllTickets());

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();


    }
}
