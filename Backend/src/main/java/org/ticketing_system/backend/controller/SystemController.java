package org.ticketing_system.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ticketing_system.backend.model.Customer;
import org.ticketing_system.backend.model.Vendor;
import org.ticketing_system.backend.service.CustomerService;
import org.ticketing_system.backend.service.VendorService;

import java.util.Map;

@RestController
@RequestMapping("/System")
@CrossOrigin(origins = "http://localhost:5173")
public class SystemController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/runAll")
    public String runAllCustomers() {
        Map<Integer,Vendor> vendors = vendorService.getVendors();
        Map<Integer, Customer> customers = customerService.getCustomers();

        for (Integer vendor_id : vendors.keySet()) {
            vendorService.runVendor(vendor_id);
        }

        // Start a thread for each customer
        for (Integer customerId : customers.keySet()) {
            customerService.runCustomer(customerId);  // This will start a thread for each customer
        }

        return "All customers and Vendors are now running in separate threads.";
    }

    @PostMapping("/stop")
    public ResponseEntity<Void> stopAll(){
        customerService.stopCustomer();
        vendorService.stopVendor();
        return ResponseEntity.ok().build();
    }


}
