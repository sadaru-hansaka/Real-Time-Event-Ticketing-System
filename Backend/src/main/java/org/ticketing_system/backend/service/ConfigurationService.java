package org.ticketing_system.backend.service;

import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.stereotype.Service;
import org.ticketing_system.backend.model.Configuration;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ConfigurationService {
    public void saveParameters(Configuration configuration) throws IOException {
        Gson gson = new Gson();
        try(FileWriter fileWriter = new FileWriter("configuration.json")){
            fileWriter.write(gson.toJson(configuration));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Configuration getData() throws IOException {
        Gson gson = new Gson();
        String json = new String(Files.readAllBytes(Paths.get("configuration.json")));
        return gson.fromJson(json,Configuration.class);
    }
}
