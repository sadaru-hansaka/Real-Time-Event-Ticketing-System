package org.Backend.ticketing_system;

import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

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


    public synchronized void addTickets(int count) throws InterruptedException {

            for (int i = 1; i <= count; i++){
                while (availableTicket >= maxTicket){
                    System.out.println("\nWaiting for customers to buy available ticket************************************************************\n");
                    wait();
                }
                tickets.add(ticketID);
                System.out.println("Ticket added : " + ticketID);
                ticketID++;
                availableTicket++;
            }
            notifyAll();
            System.out.println("\n*******************************The number of available tickets is " + availableTicket +"\n");
    }

    //    remove ticket function
    public synchronized Integer removeTickets(){
        Integer ticket = tickets.poll();
        if (ticket != null) {
            System.out.println("Ticket purchased: " + ticket);
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


}
