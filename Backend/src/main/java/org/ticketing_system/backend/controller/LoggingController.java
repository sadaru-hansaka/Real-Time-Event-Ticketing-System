package org.ticketing_system.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ticketing_system.backend.service.LoggingService;

import java.util.List;

@RestController
@RequestMapping("/Logging")
@CrossOrigin(origins = "http://localhost:5173")
public class LoggingController {

    @Autowired
    private LoggingService loggingService;

    @GetMapping("/logs")
    public List<String> getLogs() {
        return loggingService.getLogs();
    }
}
