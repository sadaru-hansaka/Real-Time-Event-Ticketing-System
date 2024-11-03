package org.example.ticketing_system;

public class Vendor implements Runnable{
    private int vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;

    public Vendor(int vendorId, int ticketsPerRelease, int releaseInterval) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

//    run method
    @Override
    public void run() {

    }
}
