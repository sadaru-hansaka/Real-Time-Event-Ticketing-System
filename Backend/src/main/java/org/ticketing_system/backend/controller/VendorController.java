package org.ticketing_system.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ticketing_system.backend.model.Vendor;
import org.ticketing_system.backend.service.VendorService;

@RestController
@RequestMapping("/Vendor")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @PostMapping("/create")
    public Vendor createVendor(
            @RequestParam int vendor_id,
            @RequestParam int ticketsPerRelease,
            @RequestParam int numOfTickets) {
        return vendorService.createVendor(vendor_id, ticketsPerRelease,numOfTickets);
    }

    @PostMapping("/{vendor_id}/run")
    public String runVendor(@PathVariable int vendor_id) {
        vendorService.runVendor(vendor_id);
        return "Vendor " + vendor_id + " started.";
    }

    @PostMapping("/start")
    public String startVendorThreads() {
        for (Vendor vendor:vendorService.getAllVendors().values()){
            vendorService.runVendor(vendor.getVendor_id());
        }
        return "Vendor threads started.";
    }
}
