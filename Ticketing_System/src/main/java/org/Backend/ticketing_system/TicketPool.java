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
    private int releasedTickets = 0;

    public TicketPool(int maxTicket, int totalTicket) {
        this.maxTicket = maxTicket;
        this.totalTicket = totalTicket;
    }


    public synchronized void addTickets(int count,int numOfTickets){
            for (int i = 1; i <= count; i++){
                tickets.add(ticketID);
                System.out.println("Ticket added : " + ticketID);
                ticketID++;
                releasedTickets++;
            }
            notifyAll();
            System.out.println("\n");
    }

    //    remove ticket function
    public synchronized Integer removeTickets(){
        Integer ticket = tickets.poll();
        if (ticket != null) {
            System.out.println("Ticket purchased: " + ticket);
        } else {
            System.out.println("No tickets available for purchase.");
        }
        System.out.println("\n");
        return ticket;
    }

    //    Method to check if tickets are available
    public synchronized boolean hasTickets() {
        return !tickets.isEmpty();
    }

    public boolean isMaxReached() {
        return ticketID >= totalTicket;
    }

    public List<Integer> getAllTickets() {
        return tickets.stream().collect(Collectors.toList());
    }

    // Method to display the current status of tickets for debugging or information
    public void displayTicketStatus() {
        System.out.println("Tickets in pool: " + getAllTickets());
        System.out.println("Total tickets issued so far: " + ticketID);
    }

}
