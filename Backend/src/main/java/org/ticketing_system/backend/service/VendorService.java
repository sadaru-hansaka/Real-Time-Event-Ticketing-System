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

//    create vendors using numOfTickets and ticketsPerRelease
    public Vendor createVendor(int ticketsPerRelease, int numOfTickets) {
        try{
            Configuration configuration = configurationService.getData();
            int totalTickets = configuration.getTotalTickets();

            if(numOfTickets > totalTickets){
                throw new IllegalArgumentException("Number of tickets must be less than total tickets");
            }

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

//    run vendor threads
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

//    returns all created vendors
    public Map<Integer,Vendor> getVendors() {
        return vendors;
    }

//    returen next vendor's id to frontend
    public int getNextID(){
        return vendor_id;
    }

//    return total of vendor issued tickets
    public int issued(){
        return vendors.values().stream().mapToInt(Vendor::getNumOfTickets).sum();
    }

//calculate the number of tickets vendors can further add
    public int getFreeSlots(){
        try{
            Configuration configuration = configurationService.getData();
            int total = configuration.getTotalTickets();
            int sumResult = vendors.values().stream().mapToInt(Vendor::getNumOfTickets).sum();
            return total-sumResult;
        }catch (IOException e){
            throw new RuntimeException("Load configuration failed");
        }
    }
}
