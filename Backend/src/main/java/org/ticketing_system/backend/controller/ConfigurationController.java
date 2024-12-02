package org.ticketing_system.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ticketing_system.backend.model.Configuration;
import org.ticketing_system.backend.service.ConfigurationService;

import java.io.IOException;

@RestController
@RequestMapping("/Configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping("/Save")
    public ResponseEntity<Configuration> save(@RequestBody Configuration configuration) {
        try{
            configurationService.saveParameters(configuration);
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
}
