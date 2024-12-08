package org.ticketing_system.backend.model.multithreading;

import org.ticketing_system.backend.model.Vendor;
import org.ticketing_system.backend.service.TicketPoolService;
import org.ticketing_system.backend.service.VendorService;

import java.io.IOException;

public class VendorMultithreading implements Runnable {
    private int vendor_id;
    private final Vendor vendor;
    private final TicketPoolService ticketPoolService;

    public VendorMultithreading(Vendor vendor, TicketPoolService ticketPoolService, int vendor_id) {
        this.vendor = vendor;
        this.ticketPoolService = ticketPoolService;
        this.vendor_id = vendor_id;
    }

    @Override
    public void run() {
        int numOfTickets = vendor.getNumOfTickets();
        try {
            while (numOfTickets > 0) {
                synchronized (ticketPoolService) {
                    // Determine how many tickets can be released
                    int ticketsToRelease = Math.min(vendor.getTicketsPerRelease(),numOfTickets);
                    boolean added = ticketPoolService.addTicket(ticketsToRelease,vendor_id);
                    if (!added) {
                        String out2 = "Vendor " + vendor_id + " stopped as no tickets can be added.";
                        System.out.println(out2);
                        break; // Stop if tickets can't be added
                    }
                    // calculate how many were actually added
                    int actualReleased = Math.min(ticketsToRelease,numOfTickets);
                    numOfTickets -= actualReleased;

                    if(ticketPoolService.isCapacityFull()){
                        break;
                    }
                }
                Thread.sleep(vendor.getReleaseinterval());
            }
            String stopped = "Vendor " + vendor_id + " has stopped issuing tickets.";
            System.out.println(stopped);
        } catch (InterruptedException e) {
            System.out.println("Vendor " + vendor_id + " interrupted.");
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
