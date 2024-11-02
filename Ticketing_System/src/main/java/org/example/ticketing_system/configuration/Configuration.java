package org.example.ticketing_system.configuration;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRate;
    private int maxNUmOfTickets;

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRate, int maxNUmOfTickets) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRate = customerRate;
        this.maxNUmOfTickets = maxNUmOfTickets;
    }
    public Configuration(){}

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRate() {
        return customerRate;
    }

    public int getMaxNUmOfTickets() {
        return maxNUmOfTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerRate(int customerRate) {
        this.customerRate = customerRate;
    }

    public void setMaxNUmOfTickets(int maxNUmOfTickets) {
        this.maxNUmOfTickets = maxNUmOfTickets;
    }

    public void displayInfo() {
        System.out.println(totalTickets);
        System.out.println(ticketReleaseRate);
        System.out.println(customerRate);
        System.out.println(maxNUmOfTickets);
    }


    public void saveConfiguration(String filename) throws IOException {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(this, writer);
        }
    }

    public static Configuration loadConfiguration(String filename) throws IOException {
        Gson gson = new Gson();
        String json = new String(Files.readAllBytes(Paths.get(filename)));
        return gson.fromJson(json, Configuration.class);
    }


}