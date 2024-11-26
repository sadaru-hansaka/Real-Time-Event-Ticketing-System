package org.Backend.ticketing_system;

import java.util.concurrent.ConcurrentLinkedDeque;

public class TicketPool {

//    Used ConcurrentLinkedDeque to provide thread safe
    private ConcurrentLinkedDeque<Integer> tickets = new ConcurrentLinkedDeque<>();

    private int ticketID = 1;
    private final int maxTicket;//    this should change to maxNumOfTickets
    private int totalTicket;
    private int availableTicket = 0;

    public TicketPool(int maxTicket, int totalTicket) {
        this.maxTicket = maxTicket;
        this.totalTicket = totalTicket;
    }


    public synchronized boolean addTickets(int count,int vendor) throws InterruptedException {
            if(ticketID>totalTicket){
                System.out.println("\nMaximum capacity reached! No more tickets can be added.");
                return false;
            }
            for (int i = 1; i <= count; i++){
                while (availableTicket >= maxTicket){
                    System.out.println("\nWaiting for customers to buy available ticket************************************************************\n");
                    wait();
                }

                if(ticketID>totalTicket){
                    System.out.println("Reached the maximum number of tickets");
                    return false;
                }
                tickets.add(ticketID);
                System.out.println("Vendor "+vendor+" added ticket : " + ticketID);
                ticketID++;
                availableTicket++;


            }
            notifyAll();
            System.out.println("\n*******************************The number of available tickets is " + availableTicket +"\n");
           return true;
    }

    //    remove ticket function
    public synchronized Integer removeTickets(int customerID){
        Integer ticket = tickets.poll();
        if (ticket != null) {
            System.out.println("Customer "+customerID+" purchased ticket : " + ticket);
            availableTicket--;
            System.out.println("\n*******************************The number of available tickets is " + availableTicket +"\n");
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
