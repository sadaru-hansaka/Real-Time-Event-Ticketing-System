package org.ticketing_system.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.ticketing_system.backend.model.Configuration;
import org.ticketing_system.backend.model.Vendor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class VendorService {
    private final Map<Integer, Vendor> vendors = new HashMap<>();

    @Autowired
    private TicketPoolService ticketPoolService;

    @Autowired
    private ConfigurationService configurationService;

    public Vendor createVendor(int vendor_id, int ticketsPerRelease, int numOfTickets) {
        try{
            Configuration configuration = configurationService.getData();
            int releaseInterval =configuration.getTicketReleaseRate();
            Vendor vendor = new Vendor(vendor_id, ticketsPerRelease, releaseInterval, numOfTickets);
            vendors.put(vendor_id, vendor);
            return vendor;
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Load configuration failed");
        }

    }

    public void runVendor(int vendor_id) {
        Vendor vendor = vendors.get(vendor_id);
        if (vendor == null) {
            System.out.println("Vendor not found!");
            return;
        }

        new Thread(() -> {
            try {
                while (vendor.getNumOfTickets() > 0) {
                    synchronized (ticketPoolService) {
                        int ticketsToRelease = Math.min(vendor.getTicketsPerRelease(), vendor.getNumOfTickets());
                        boolean added = ticketPoolService.addTicket(ticketsToRelease, vendor_id);

                        if (!added || ticketPoolService.isCapacityFull()) {
                            break;
                        }
                        vendor.setNumOfTickets(vendor.getNumOfTickets() - ticketsToRelease);
                    }
                    Thread.sleep(vendor.getReleaseinterval());
                }
                System.out.println("Vendor " + vendor_id + " stopped issuing tickets.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public Map<Integer,Vendor> getAllVendors() {
        return vendors;
    }
}
