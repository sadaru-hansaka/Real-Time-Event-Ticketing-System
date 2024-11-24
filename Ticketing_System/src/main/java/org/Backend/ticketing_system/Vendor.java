package org.Backend.ticketing_system;

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

//    run method
    @Override
    public void run() {
        try {
            while (numOfTickets>0) {
                synchronized (ticketPool) {

                    int ticketsToRelease = Math.min(ticketsPerRelease, numOfTickets);
                    synchronized (ticketPool) {
                        ticketPool.addTickets(ticketsToRelease, numOfTickets);
                        numOfTickets -= ticketsToRelease;
                    }
                    System.out.println("Vendor "+vendorId+" has released "+ticketsToRelease+" tickets");
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
