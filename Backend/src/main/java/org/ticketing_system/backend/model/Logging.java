package org.ticketing_system.backend.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logging {
    private LocalDateTime timeStamp;
    private String txt;
    private static Logging log;

    public Logging(LocalDateTime timeStamp, String txt) {
        this.timeStamp = timeStamp;
        this.txt = txt;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Override
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatdateTime = timeStamp.format(format);
        return String.format("[%s] %s", formatdateTime, txt);
    }
}
