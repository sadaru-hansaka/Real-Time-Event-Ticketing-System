package org.CLI.ticketing_system;

public class Vendor implements Runnable{
    private int vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;
    private int numOfTickets;
    private final TicketPool ticketPool;

    public Vendor(int vendorId, int ticketsPerRelease, int releaseInterval,int numOfTickets,TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.numOfTickets = numOfTickets;
        this.ticketPool= ticketPool;
    }
    
//  run method
    @Override
    public void run() {
        try {
            while (numOfTickets > 0) {
                synchronized (ticketPool) {
                    // Determine how many tickets can be released
                    int ticketsToRelease = Math.min(ticketsPerRelease, numOfTickets);
                    boolean added = ticketPool.addTickets(ticketsToRelease,vendorId);
                    if (!added) {
                        System.out.println("Vendor " + vendorId + " stopped as no tickets can be added.");
                        break; // Stop if tickets can't be added
                    }
                    // calculate how many were actually added
                    int actualReleased = Math.min(ticketsToRelease, numOfTickets);
                    numOfTickets -= actualReleased;

                    if(ticketPool.isCapacityFull()){
                        break;
                    }
                }
                Thread.sleep(releaseInterval);
            }
            System.out.println("Vendor " + vendorId + " has stopped issuing tickets.");
        } catch (InterruptedException e) {
            System.out.println("Vendor " + vendorId + " interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}
