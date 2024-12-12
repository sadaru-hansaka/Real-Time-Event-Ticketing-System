package org.ticketing_system.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ticketing_system.backend.model.Customer;
import org.ticketing_system.backend.model.Vendor;
import org.ticketing_system.backend.service.LoggingService;
import org.ticketing_system.backend.service.VendorService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Vendor")
@CrossOrigin(origins = "http://localhost:5173")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @Autowired
    private LoggingService loggingService;

    @PostMapping("/create")
    public Vendor createVendor(@RequestBody Map<String,Integer> vendorData) {
        int ticketsPerRelease = vendorData.get("ticketsPerRelease");
        int numOfTickets = vendorData.get("numOfTickets");
        return vendorService.createVendor(ticketsPerRelease,numOfTickets);
    }


    @GetMapping("/nextID")
    public int nextId(){
        return vendorService.getNextID();
    }

    @GetMapping("/allVendors")
    public Map<Integer,Vendor> getAllVendors() {
        return vendorService.getVendors();
    }


    @GetMapping("/NumberOfSlots")
    public int getNumberOfSlots() {
        return vendorService.getFreeSlots();
    }

    @PostMapping("Start")
    public String startVendor() {
        Map<Integer,Vendor> vendors = vendorService.getVendors();

        for (Integer vendor_id : vendors.keySet()) {
            vendorService.runVendor(vendor_id);
        }
        loggingService.log("All vendors started");
        return "Vendors started.";
    }

    //    run customer thread using customer id
    @PostMapping("/{vendor_id}/run")
    public ResponseEntity<?> runVendor(@PathVariable int vendor_id) {
        Vendor vendor = vendorService.getVendors().get(vendor_id);

        vendorService.runVendor(vendor_id);
        loggingService.log("vendor "+vendor_id+" running");
        return ResponseEntity.ok("Vendor " + vendor_id);
    }

    @GetMapping("/completedVendors")
    public List<Integer> getCompletedVendors() {
        return vendorService.returnCompletedVendors();
    }
}
