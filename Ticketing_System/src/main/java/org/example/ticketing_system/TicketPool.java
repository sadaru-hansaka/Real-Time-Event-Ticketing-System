package org.example.ticketing_system;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class TicketPool {

//    Used ConcurrentLinkedDeque to provide thread safe
    private ConcurrentLinkedDeque<Integer> tickets = new ConcurrentLinkedDeque<>();
    private int ticketID = 0;
    private final int maxTicket;  //    this should change to maxNumOfTickets
    private final int totalTicket;

    public TicketPool(int maxTicket, int totalTicket) {
        this.maxTicket = maxTicket;
        this.totalTicket = totalTicket;
    }

    //    add ticket function
    public synchronized void addTickets(int count){
        for (int i = 0; i <= count && ticketID <=totalTicket; i++){
            tickets.add(ticketID);
            System.out.println("Ticket added : " + ticketID);
            ticketID++;
        }
        notifyAll();
    }

//    remove ticket function
    public synchronized Integer removeTickets(){
        Integer ticket = tickets.poll();
        if (ticket != null) {
            System.out.println("Ticket purchased: " + ticket);
        } else {
            System.out.println("No tickets available for purchase.");
        }
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
