package org.ticketing_system.backend.service;

import org.springframework.stereotype.Service;
import org.ticketing_system.backend.model.Logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoggingService {

    private final List<String> logs = new ArrayList<>(); //to store logs

    private BufferedWriter bufferedWriter;

    public LoggingService() {
        try{
            this.bufferedWriter = new BufferedWriter(new FileWriter("log.txt"));
        }catch(IOException e){
            System.out.println("Error creating logging file");
        }
    }


    public synchronized void log(String txt) {
        logs.add(txt);
        try{
            Logging logging = new Logging(LocalDateTime.now(),txt);
            if(bufferedWriter != null){
                bufferedWriter.write(logging.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        }catch (IOException e){
            System.out.println("Error writing to logging file");
        }
    }

    public synchronized void close() throws IOException {
        if (bufferedWriter != null) {
            bufferedWriter.close();
        }
    }

    public List<String> getLogs(){
        return new ArrayList<>(logs);
    }
}
