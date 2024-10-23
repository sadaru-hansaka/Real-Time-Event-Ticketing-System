public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRate;
    private int maxNumOfTickets;

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRate, int maxNumOfTickets) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRate = customerRate;
        this.maxNumOfTickets = maxNumOfTickets;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRate() {
        return customerRate;
    }

    public int getMaxNumOfTickets() {
        return maxNumOfTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerRate(int customerRate) {
        this.customerRate = customerRate;
    }

    public void setMaxNumOfTickets(int maxNumOfTickets) {
        this.maxNumOfTickets = maxNumOfTickets;
    }


    public void displayConfiguration() {
        System.out.println("Total Tickets: " + totalTickets);
    }
}
