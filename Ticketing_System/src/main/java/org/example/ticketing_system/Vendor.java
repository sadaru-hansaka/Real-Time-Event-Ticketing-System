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
        try{
            while (!ticketPool.isMaxReached()){
                ticketPool.addTickets(ticketsPerRelease);
                System.out.println("Vendor "+vendorId+" released "+ticketsPerRelease+" tickets per release");
                Thread.sleep(releaseInterval);
            }
        }catch (InterruptedException e){
            System.out.println("Vendor "+vendorId+" interrupted.");
        }
    }
}
