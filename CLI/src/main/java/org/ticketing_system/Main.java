package org.ticketing_system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner=new Scanner(System.in);
//        create the Configuration object
        Configuration config = new Configuration();

//        create lists for vendors and customers
        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();

        int vendorId = 1;
        int customerId = 1;

//        get inputs

        while (true) {
            System.out.print("Enter total number of Tickets: ");
            try{
                int total = scanner.nextInt();
                if (total < 0 ) {
                    System.out.println("Total number of Tickets must be a positive number. Enter again.");
                    continue;
                }
                config.setTotalTickets(total);
                break;
            }catch (InputMismatchException e){
                System.out.println("Total number of Tickets must be a positive number. Enter again.");
                scanner.next();
            }

        }

        while (true) {
            System.out.print("Enter Max Number of Tickets: ");
            try{
                int max = scanner.nextInt();
                if (max < 0) {
                    System.out.println("Max Number of Tickets must be a positive number. Enter again.");
                    continue;
                }
                config.setmaxTicketCapacity(max);
                break;
            }catch (InputMismatchException e){
                System.out.println("Max number of tickets must be a positive number. Enter again.");
                scanner.next();
            }
        }



        while (true) {
            System.out.print("Enter Ticket Release Rate: ");
            try{
                int release = scanner.nextInt();
                if (release < 0) {
                    System.out.println("Ticket release rate must be a positive number. Enter again.");
                    continue;
                }
                config.setTicketReleaseRate(release);
                break;
            }catch (InputMismatchException e){
                System.out.println("Ticket release rate must be a positive number. Enter again.");
                scanner.next();
            }

        }

        while (true) {
            System.out.print("Enter Customer Retrieval Rate: ");
            try{
                int retrieval = scanner.nextInt();
                scanner.nextLine();
                if (retrieval < 0) {
                    System.out.println("Customer retrieval rate must be a positive number. Enter again.");
                    continue;
                }
                config.setCustomerRetrievalRate(retrieval);
                break;
            }catch (InputMismatchException e){
                System.out.println("Customer retrieval rate must be a positive number. Enter again.");
                scanner.next();
            }

        }


//        save configurations to the json file
        try{
            config.saveConfiguration("src/main/resources/config.json"); //file path
            System.out.println("Configuration Saved!\n**************************************************");
        }catch (IOException e){
            System.out.println("Configuration Saving Failed!\n**************************************************");
        }


        //        open saved json file
        Configuration jsonfile = Configuration.loadConfiguration("src/main/resources/config.json");

        //Ticket Pool
        TicketPool ticketPool = new TicketPool(jsonfile.getmaxTicketCapacity(),jsonfile.getTotalTickets());

        while(true){
            System.out.print("\nDo you want to continue? (y/n)");
            String line = scanner.nextLine();

            if(line.equalsIgnoreCase("y")){
                System.out.print("Are you a vendor or a customer (V/C) ? : ");
                String input = scanner.nextLine();

                if(input.equalsIgnoreCase("v")){
                    System.out.println("\nVendor "+vendorId);
                    System.out.print("Enter maximum tickets per release for Vendor "+vendorId+" : ");
                    int perRelease = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter number of tickets Vendor "+vendorId+" want to issue : ");
                    int numTickets = scanner.nextInt();
                    scanner.nextLine();

                    Vendor vendor = new Vendor(vendorId,perRelease,jsonfile.getTicketReleaseRate(),numTickets,ticketPool);
                    Thread vendorThread = new Thread(vendor);
                    vendorThreads.add(vendorThread);
                    vendorId++;

                } else if(input.equalsIgnoreCase("c")) {
                    System.out.println("\nCustomer "+customerId);
                    System.out.print("Enter number of tickets Customer "+customerId+" want to buy : ");
                    int numTickets = scanner.nextInt();
                    scanner.nextLine();

                    Customer customer = new Customer(customerId,jsonfile.getCustomerRetrievalRate(),numTickets,ticketPool);
                    Thread customerThread = new Thread(customer);
                    customerThreads.add(customerThread);
                    customerId++;
                }else {
                    System.out.println("Invalid option. Try again!.");
                }
            }else if(line.equalsIgnoreCase("n")) {
                System.out.print("\nDo you want to Start the System? (Y/N) : ");
                String choice = scanner.next();
                if (choice.equalsIgnoreCase("Y")) {
                    System.out.println("\n**************************************************\nStarting Ticketing System\n**************************************************\n");

                    for (Thread thread : vendorThreads) {
                        thread.start();
                    }
                    for (Thread thread : customerThreads) {
                        thread.start();
                    }
                } else if (choice.equalsIgnoreCase("N")) {
                    System.out.println("Exiting Ticketing System");
                }
                break;
            }else{
                System.out.println("\nInvalid Option! Try again.");
                continue;
            }

        }
    }
}