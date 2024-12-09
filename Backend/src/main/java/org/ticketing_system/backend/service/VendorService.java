package org.ticketing_system.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.ticketing_system.backend.model.Configuration;
import org.ticketing_system.backend.model.Vendor;
import org.ticketing_system.backend.model.multithreading.VendorMultithreading;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VendorService {
    private final Map<Integer, Vendor> vendors = new ConcurrentHashMap<>();
    private final Map<Integer, Thread>  vendorThreads = new ConcurrentHashMap<>();
    private int vendor_id = 1;

    @Autowired
    private TicketPoolService ticketPoolService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private LoggingService loggingService;

    public Vendor createVendor(int ticketsPerRelease, int numOfTickets) {
        try{
            Configuration configuration = configurationService.getData();
            int releaseInterval =configuration.getTicketReleaseRate();
            Vendor vendor = new Vendor(vendor_id, ticketsPerRelease, releaseInterval, numOfTickets);
            vendors.put(vendor_id, vendor);
            vendor_id++;
            return vendor;
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Load configuration failed");
        }

    }

    public void runVendor(int vendor_id) {
        Vendor vendor = vendors.get(vendor_id);
        if (vendor == null) {
            System.out.println("Vendor " + vendor_id + " not found");
            return;
        }
        Thread vendorThread = new Thread(new VendorMultithreading(vendor,ticketPoolService,loggingService,vendor_id));
        vendorThreads.put(vendor_id, vendorThread);
        vendorThread.start();
    }

//    Stop all vendors
    public void stopVendor() {
        for(Thread thread : vendorThreads.values()) {
            thread.interrupt();
        }
        vendorThreads.clear();
    }

    public Map<Integer,Vendor> getVendors() {
        return vendors;
    }

    public int getNextID(){
        return vendor_id;
    }
}
