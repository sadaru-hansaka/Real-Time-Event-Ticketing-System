package org.ticketing_system;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logging {

    private static Logging log; //holds a single instance of the class
    private BufferedWriter bufferedWriter;

    private Logging(String filename){
        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter(filename));
        }catch (IOException e){
            System.out.println("Error creating logging file");
        }
    }

//    if there's no file , creates a new fil
    public static synchronized Logging getInstance(String filename) throws IOException {
        if (log == null) {
            log = new Logging(filename);
        }
        return log;
    }


    public synchronized void log(String txt) {
        try{
            bufferedWriter.write(txt);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        }catch (IOException e){
            System.out.println("Error");
        }
    }

    public synchronized void close() throws IOException {
        if (bufferedWriter != null) {
            bufferedWriter.close();
        }
    }

}
