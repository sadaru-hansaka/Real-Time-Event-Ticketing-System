package org.CLI.ticketing_system;

import java.io.IOException;
import java.util.Scanner;

public class ConfigurationController {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);

//        create the Configuration object
        Configuration config = new Configuration();

//        get inputs
        while (true) {
            System.out.print("Enter Max Number of Tickets: ");
            int max = scanner.nextInt();
            if (max < 0) {
                System.out.println("Max Number of Tickets must be a positive number. Enter again.");
                continue;
            }
            config.setmaxTicketCapacity(max);
            break;

        }

        while (true) {
            System.out.print("Enter total number of Tickets: ");
            int total = scanner.nextInt();
            if (total < 0 ) {
                System.out.println("Total number of Tickets must be a positive number. Enter again.");
                continue;
            }
            config.setTotalTickets(total);
            break;
        }

        while (true) {
            System.out.print("Enter Ticket Release Rate: ");
            int release = scanner.nextInt();
            if (release < 0) {
                System.out.println("Ticket release rate must be a positive number. Enter again.");
                continue;
            }
            config.setTicketReleaseRate(release);
            break;
        }

        while (true) {
            System.out.print("Enter Customer Retrieval Rate: ");
            int retrieval = scanner.nextInt();
            if (retrieval < 0) {
                System.out.println("Customer retrieval rate must be a positive number. Enter again.");
                continue;
            }
            config.setCustomerRetrievalRate(retrieval);
            break;
        }





//        save configurations to the json file
        try{
            config.saveConfiguration("src/main/resources/config.json"); //file path
            System.out.println("Configuration Saved!");
        }catch (IOException e){
            System.out.println("Configuration Saving Failed!");
        }


    }
}
