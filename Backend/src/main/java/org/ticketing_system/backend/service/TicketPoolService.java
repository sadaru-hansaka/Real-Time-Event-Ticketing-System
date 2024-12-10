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

    private List<String> ticketPoolUpdates = new ArrayList<>();

    private int ticket_id = 1;
    private int availableTicket = 0;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private LoggingService loggingService;

    public synchronized boolean addTicket(int count,int vendor_id) throws InterruptedException, IOException {
        Configuration configuration = configurationService.getData();
        int maxTicket = configuration.getMaxTicketCapacity();
        int totalTicket = configuration.getTotalTickets();

        if(ticket_id>totalTicket){
            String out = "\nReached maximum ticket count";
            System.out.println(out);
            loggingService.log(out);
            addUpdates(out);
            return false;
        }

        for (int i = 1; i <= count; i++) {
            while (availableTicket>=maxTicket){
                String out1 = "\nTicket Pool reached maximum capacity.";
                System.out.println(out1);
                loggingService.log(out1);
                addUpdates(out1);
                wait();
            }
            if(ticket_id>totalTicket){
                String out = "\nReached maximum ticket count";
                System.out.println(out);
                loggingService.log(out);
                addUpdates(out);
                return false;
            }

            ticketPool.add(ticket_id);
            String ticketadd = "Vendor "+vendor_id+" added ticket : "+ticket_id;
            System.out.println(ticketadd);
            loggingService.log(ticketadd);
            addUpdates(ticketadd);
            ticket_id++;
            availableTicket++;
        }
        notifyAll();
        return true;
    }

    public synchronized Integer removeTickets(int customer_id) throws InterruptedException {
        Integer ticket = ticketPool.poll();
        if(ticket!=null){
            String ticketbuy = "Customer "+customer_id+" purchased ticket : "+ticket;
            System.out.println(ticketbuy);
            loggingService.log(ticketbuy);
            addUpdates(ticketbuy);
            availableTicket--;
            notifyAll();
        }else{
            String empty = "Ticket pool is empty\nCustomer "+customer_id+" stopped purchasing ticket";
            System.out.println(empty);
            loggingService.log(empty);
            addUpdates(empty);
            return null;
        }
        return ticket;
    }


    public synchronized boolean isCapacityFull() {
        try{
            Configuration configuration = configurationService.getData();
            int totalTicket = configuration.getTotalTickets();
            return ticket_id>totalTicket;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private synchronized void addUpdates(String update){
        ticketPoolUpdates.add(update);
    }

    public List<String> getLogs(){
        return new ArrayList<>(ticketPoolUpdates);
    }

//    returns available ticket count in the ticketpool
//    public int getAvailableTicket(){
//        return availableTicket;
//    }

}
