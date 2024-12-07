package org.ticketing_system.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ticketing_system.backend.model.Vendor;
import org.ticketing_system.backend.service.VendorService;

import java.util.Map;

@RestController
@RequestMapping("/Vendor")
@CrossOrigin(origins = "http://localhost:5173")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @PostMapping("/create")
    public Vendor createVendor(@RequestBody Map<String,Integer> vendorData) {
        int ticketsPerRelease = vendorData.get("ticketsPerRelease");
        int numOfTickets = vendorData.get("numOfTickets");
        return vendorService.createVendor(ticketsPerRelease,numOfTickets);
    }

    @PostMapping("/{vendor_id}/run")
    public String runVendor(@PathVariable int vendor_id) {
        vendorService.runVendor(vendor_id);
        return "Vendor " + vendor_id + " started.";
    }

    @GetMapping("/nextID")
    public int nextId(){
        return vendorService.getNextID();
    }

//    @PostMapping("Start")
//    public String startVendor() {
//        Map<Integer,Vendor> vendors = vendorService.getVendors();
//
//        for (Integer vendor_id : vendors.keySet()) {
//            vendorService.runVendor(vendor_id);
//        }
//        return "Vendors started.";
//    }
}
