package org.ticketing_system.backend.controller;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ticketing_system.backend.model.TicketPool;
import org.ticketing_system.backend.model.Vendor;
import org.ticketing_system.backend.service.TicketPoolService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "http://localhost:5173")
public class TicketPoolController {

    @Autowired
    private TicketPoolService ticketPoolService;

    @GetMapping("/available")
    public ResponseEntity<Integer> availableTickets() {
        int avalableTickets = ticketPoolService.getAvailableTicket();
        return ResponseEntity.ok(avalableTickets);
    }

    @GetMapping("/logs")
    public List<String> getLogs() {
        return ticketPoolService.getLogs();
    }

}
