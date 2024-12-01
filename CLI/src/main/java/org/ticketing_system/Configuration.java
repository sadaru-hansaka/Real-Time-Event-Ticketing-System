package org.ticketing_system;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Configuration {
    private int totalTickets;   //number of tickets release for the event
    private int ticketReleaseRate;      // how often vendors release tickets
    private int customerRetrievalRate;    //how often customers buy tickets
    private int maxTicketCapacity;    //maximum ticket capacity of the ticket pool

    public Configuration(int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = 0;  //total number of tickets vendors can Issue for the event
        this.ticketReleaseRate = ticketReleaseRate;    //how frequently each vendor releases a batch of tickets into the system
        this.customerRetrievalRate = customerRetrievalRate;    //how frequently each customer attempts to retrieve (purchase) a ticket
        this.maxTicketCapacity = maxTicketCapacity;      //maximum number of tickets system can hold
    }
    public Configuration(){}

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getmaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public void setmaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }


//    save parameters to the json file
    public void saveConfiguration(String filename) throws IOException {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(this, writer);
        }
    }

//    get data from json file
    public static Configuration loadConfiguration(String filename) throws IOException {
        Gson gson = new Gson();
        String json = new String(Files.readAllBytes(Paths.get(filename)));
        return gson.fromJson(json, Configuration.class);
    }


}