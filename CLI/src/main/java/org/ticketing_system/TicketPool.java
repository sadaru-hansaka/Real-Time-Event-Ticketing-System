package org.ticketing_system;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TicketPool {

//    Used ConcurrentLinkedDeque to provide thread safe
    private ConcurrentLinkedDeque<Integer> tickets = new ConcurrentLinkedDeque<>();

    private int ticketID = 1;
    private final int maxTicket;//    this should change to maxNumOfTickets
    private int totalTicket;
    private int availableTicket = 0;

    private Logging logging;

    public TicketPool(int maxTicket, int totalTicket) {
        this.maxTicket = maxTicket;
        this.totalTicket = totalTicket;

        try{
            this.logging = Logging.getInstance("src/main/resources/Logging.txt");
        }catch(IOException e){
            System.out.println("Error");
        }
    }


//    adding tickets to the ticket pool
    public synchronized boolean addTickets(int count,int vendor) throws InterruptedException {
//        checks the ticketPool capacity
        if (ticketID > totalTicket) {
            String maxMsg = "\nMaximum capacity reached! No more tickets can be added.";
            System.out.println(maxMsg);
            logging.log(maxMsg);
            return false;
        }
        for (int i = 1; i <= count; i++) {
//            waiting for free up ticketpool space
            while (availableTicket >= maxTicket) {
                String waitingmsg = "\nWaiting for customers to buy available ticket\n";
                System.out.println(waitingmsg);
                logging.log(waitingmsg);
                wait();
            }

            if (ticketID > totalTicket) {
                System.out.println("Reached the maximum number of tickets");
                return false;
            }
            tickets.add(ticketID);
            String out1 = "Vendor " + vendor + " added ticket : " + ticketID;
            System.out.println(out1);
            logging.log(out1);
            ticketID++;
            availableTicket++;
        }
        notifyAll();
        System.out.println("\nThe number of available tickets : " + availableTicket + "\n");
        return true;
    }


    //    remove ticket function
    public synchronized Integer removeTickets(int customerID){
        Integer ticket = tickets.poll();
        if (ticket != null) {
            String customerOut="Customer "+customerID+" purchased ticket : " + ticket;
            System.out.println(customerOut);
            logging.log(customerOut);
            availableTicket--;
            System.out.println("\nThe number of available tickets : " + availableTicket +"\n");
            notifyAll();
        } else {
            System.out.println("No tickets available for purchase.");
        }
        return ticket;

    }

    //    Method to check if tickets are available
    public synchronized boolean hasTickets() {
        return !tickets.isEmpty();
    }

    public synchronized boolean isCapacityFull() {
        return ticketID > totalTicket;
    }



}
