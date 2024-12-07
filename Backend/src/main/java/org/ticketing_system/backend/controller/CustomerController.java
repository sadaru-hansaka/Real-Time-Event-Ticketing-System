package org.ticketing_system.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ticketing_system.backend.model.Customer;
import org.ticketing_system.backend.service.CustomerService;

import java.util.Map;

@RestController
@RequestMapping("/Customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public Customer createCustomer(
            @RequestParam int ticketCount) {
        return customerService.createCustomer(ticketCount);
    }

//    @PostMapping("/{customer_id}/run")
//    public String runCustomer(@PathVariable int customer_id) {
//        customerService.run(customer_id);
//        return "Customer " + customer_id + " has been run!";
//    }


}
