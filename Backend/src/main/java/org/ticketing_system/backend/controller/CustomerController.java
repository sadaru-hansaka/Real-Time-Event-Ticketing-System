package org.ticketing_system.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ticketing_system.backend.model.Customer;
import org.ticketing_system.backend.service.CustomerService;

import java.util.Map;

@RestController
@RequestMapping("/Customer")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Map<String,Integer> customerData) {
        int ticketCount = customerData.get("ticketCount");
        return customerService.createCustomer(ticketCount);
    }

    @GetMapping("/nextId")
    public int nextID(){
        return customerService.getCustomerID();
    }

//    @PostMapping("/{customer_id}/run")
//    public String runCustomer(@PathVariable int customer_id) {
//        customerService.run(customer_id);
//        return "Customer " + customer_id + " has been run!";
//    }


}
