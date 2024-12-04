package org.ticketing_system.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ticketing_system.backend.model.TicketPool;
import org.ticketing_system.backend.model.Vendor;
import org.ticketing_system.backend.service.TicketPoolService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketPoolController {

    @Autowired
    private TicketPoolService ticketPoolService;

    @GetMapping("/status")
    public List<Integer> viewTickets() {
        return ticketPoolService.getTickets();
    }
}