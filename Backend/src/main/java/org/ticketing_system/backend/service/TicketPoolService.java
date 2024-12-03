package org.ticketing_system.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ticketing_system.backend.model.Configuration;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class TicketPoolService {

    private final ConcurrentLinkedDeque<Integer> ticketPool = new ConcurrentLinkedDeque<>();

    private final int maxTicket;
    private int ticket_id = 1;
    private int availableTicket = 0;
    private final int totalTicket;

    @Autowired
    public TicketPoolService(ConfigurationService configurationService) throws IOException {
        Configuration configuration = configurationService.getData();
        this.maxTicket = configuration.getMaxTicketCapacity();
        this.totalTicket = configuration.getTotalTickets();
    }

    public synchronized boolean addTicket(int count,int vendor_id) throws InterruptedException {
        if(ticket_id>totalTicket){
            System.out.println("Maximum reached");
            return false;
        }

        for (int i = 1; i <= count; i++) {
            while (availableTicket>=maxTicket){
                System.out.println("Waiting");
                wait();
            }
            if(ticket_id>totalTicket){
                System.out.println("Maximum reached");
                return false;
            }

            ticketPool.add(ticket_id);
            System.out.println("Vendor "+vendor_id+" added ticket : "+ticket_id);
            ticket_id++;
            availableTicket++;
        }
        notifyAll();
        return true;
    }

    public synchronized boolean hasTickets() {
        return !ticketPool.isEmpty();
    }

    public synchronized boolean isCapacityFull() {
        return ticket_id> totalTicket;
    }

    public synchronized List<Integer> getTickets() {
        return new ArrayList<>(ticketPool);
    }
}
