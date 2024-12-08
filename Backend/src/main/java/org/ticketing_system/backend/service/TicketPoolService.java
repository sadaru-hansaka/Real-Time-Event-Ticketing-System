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
    private final List<String> logs = new ArrayList<>(); //to store logs

//    private final int maxTicket;
    private int ticket_id = 1;
    private int availableTicket = 0;
//    private final int totalTicket;

    @Autowired
    private ConfigurationService configurationService;

//    @Autowired
//    public TicketPoolService(ConfigurationService configurationService) throws IOException {
//        Configuration configuration = configurationService.getData();
//        this.maxTicket = configuration.getMaxTicketCapacity();
//        this.totalTicket = configuration.getTotalTickets();
//    }

    public synchronized boolean addTicket(int count,int vendor_id) throws InterruptedException, IOException {
        Configuration configuration = configurationService.getData();
        int maxTicket = configuration.getMaxTicketCapacity();
        int totalTicket = configuration.getTotalTickets();

        if(ticket_id>totalTicket){
            String out = "Maximum reached";
            System.out.println(out);
            logs.add(out);
            return false;
        }

        for (int i = 1; i <= count; i++) {
            while (availableTicket>=maxTicket){
                String out1 = "Waiting";
                System.out.println(out1);
                logs.add(out1);
                wait();
            }
            if(ticket_id>totalTicket){
                String out = "Maximum reached";
                System.out.println(out);
                logs.add(out);
                return false;
            }

            ticketPool.add(ticket_id);
            String ticketadd = "Vendor "+vendor_id+" added ticket : "+ticket_id;
            System.out.println(ticketadd);
            logs.add(ticketadd);
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
            logs.add(ticketbuy);
            availableTicket--;
            notifyAll();
        }else{
            String empty = "Ticket pool is empty, Customer "+customer_id+" stopped purchasing ticket";
            System.out.println(empty);
            logs.add(empty);
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

    public synchronized List<Integer> getTickets() {
        return new ArrayList<>(ticketPool);
    }

    public List<String> getLogs(){
        return new ArrayList<>(logs);
    }
}
