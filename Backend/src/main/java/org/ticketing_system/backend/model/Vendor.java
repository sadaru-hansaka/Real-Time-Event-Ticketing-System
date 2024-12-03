package org.ticketing_system.backend.model;

import java.util.List;

public class Vendor {
    private int vendor_id;
    private int ticketsPerRelease;
    private int releaseinterval;
    private int numOfTickets;

    public Vendor(int vendor_id, int ticketsPerRelease, int releaseinterval, int numOfTickets) {
        this.vendor_id = vendor_id;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseinterval = releaseinterval;
        this.numOfTickets = numOfTickets;
    }

    public int getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }

    public int getTicketsPerRelease() {
        return ticketsPerRelease;
    }

    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }

    public int getReleaseinterval() {
        return releaseinterval;
    }

    public void setReleaseinterval(int releaseinterval) {
        this.releaseinterval = releaseinterval;
    }

    public int getNumOfTickets() {
        return numOfTickets;
    }

    public void setNumOfTickets(int numOfTickets) {
        this.numOfTickets = numOfTickets;
    }
}
