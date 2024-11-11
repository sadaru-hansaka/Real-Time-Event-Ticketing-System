package org.example.ticketing_system;

public class Vendor implements Runnable{
    private int vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;
    private TicketPool ticketPool;

    public Vendor(int vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool= ticketPool;
    }

//    run method
    @Override
    public void run() {
        try {
            while (!ticketPool.isMaxReached()) {
                synchronized (ticketPool) {
                    ticketPool.addTickets(ticketsPerRelease);
                }
                System.out.println("Vendor " + vendorId + " released " + ticketsPerRelease + " tickets.");
                Thread.sleep(releaseInterval);
            }
            System.out.println("Vendor " + vendorId + " has stopped issuing tickets.");
        } catch (InterruptedException e) {
            System.out.println("Vendor " + vendorId + " interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}
