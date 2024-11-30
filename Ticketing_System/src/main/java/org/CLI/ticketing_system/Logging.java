package org.CLI.ticketing_system;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logging {

    private static Logging log;
    private BufferedWriter bufferedWriter;

    private Logging(String filename){
        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter(filename, true));
        }catch (IOException e){
            System.out.println("Error creating logging file");
        }
    }

    public static synchronized Logging getInstance(String filename) throws IOException {
        if (log == null) {
            log = new Logging(filename);
        }
        return log;
    }

    public synchronized void log(String txt) {
        try{
            bufferedWriter.newLine();
            bufferedWriter.write(txt);

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
