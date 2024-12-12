package org.ticketing_system.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ticketing_system.backend.model.Customer;
import org.ticketing_system.backend.model.Vendor;
import org.ticketing_system.backend.service.CustomerService;
import org.ticketing_system.backend.service.LoggingService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Customer")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoggingService loggingService;

    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Map<String,Integer> customerData) {
        int ticketCount = customerData.get("ticketCount");
        loggingService.log("Customer created with "+ticketCount+" tickets");
        return customerService.createCustomer(ticketCount);
    }

    @GetMapping("/nextId")
    public int nextID(){
        return customerService.getCustomerID();
    }

    @GetMapping("/allCustomers")
    public Map<Integer,Customer> getALLCustomers() {
        return customerService.getCustomers();
    }

//    @GetMapping("/totalTickets")
//    public int getTotalTickets() {
//        return customerService.getTotal();
//    }

//    run all customer threads
    @PostMapping("Start")
    public String startCustomers() {
        Map<Integer, Customer> customers = customerService.getCustomers();

        // Start a thread for each customer
        for (Integer customerId : customers.keySet()) {
            customerService.runCustomer(customerId);  // This will start a thread for each customer
        }
        loggingService.log("Customer started");
        return "Customers started.";
    }

    @GetMapping("/remain")
    public int remainTickets() {
        return customerService.getTotalAvailableTickets();
    }


//    run customer thread using customer id
    @PostMapping("/{customer_id}/run")
    public ResponseEntity<?> runCustomer(@PathVariable int customer_id) {
        Customer customer = customerService.getCustomers().get(customer_id);

        customerService.runCustomer(customer_id);
        loggingService.log("Customer "+customer_id+" run");
        return ResponseEntity.ok("Customer " + customer_id);
    }

    @GetMapping("/completedThreads")
    public List<Integer> getCompletedThreads() {
        return customerService.returnCompletedThreads();
    }

}
