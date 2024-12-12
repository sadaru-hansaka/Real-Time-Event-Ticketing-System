package org.ticketing_system.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ticketing_system.backend.model.Configuration;
import org.ticketing_system.backend.service.ConfigurationService;
import org.ticketing_system.backend.service.LoggingService;

import java.io.IOException;

@RestController
@RequestMapping("/Configuration")
@CrossOrigin(origins = "http://localhost:5173")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private LoggingService loggingService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping("/Save")
    public ResponseEntity<Configuration> save(@RequestBody Configuration configuration) {
        try{
            configurationService.saveParameters(configuration);
            loggingService.log("Configuration Saved");
            return ResponseEntity.ok(configuration);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("Load")
    public ResponseEntity<Configuration> load(){
        try{
            Configuration configuration = configurationService.getData();
            return ResponseEntity.ok(configuration);

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("display")
    public Configuration getConfiguration() {
        try {
            return configurationService.getData();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle errors appropriately in production
        }
    }

    @GetMapping("/max")
    public int getMaxConfiguration() {
        try{
            return configurationService.getData().getMaxTicketCapacity();
        }catch (IOException e){
            e.printStackTrace();
            return 0;
        }
    }
}
