package org.example.ticketing_system.controller;

import org.example.ticketing_system.config.Configuration;

import java.io.IOException;
import java.util.Scanner;

public class ConfigurationController {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        Configuration config = new Configuration();

        System.out.print("Enter Total Tickets: ");
        config.setTotalTickets(scanner.nextInt());

        System.out.print("Enter Ticket Release Rate: ");
        config.setTicketReleaseRate(scanner.nextInt());

        System.out.print("Enter Customer Retrieval Rate: ");
        config.setCustomerRate(scanner.nextInt());

        System.out.print("Enter Max Number of Tickets: ");
        config.setMaxNUmOfTickets(scanner.nextInt());

        if(config.isValid()){
            try{
                config.saveConfiguration("src/main/resources/config.json");
                System.out.println("Configuration Saved!");
            }catch (IOException e){
                System.out.println("Configuration Saving Failed!");
            }
        }else{
            System.out.println("Configuration parameters wrong!");
        }


    }
}
