package org.ticketing_system.backend.model;

public class TicketPool {
    private int ticket_id;
    private int maxticket;

    public TicketPool(int ticket_id, int maxticket) {
        this.ticket_id = ticket_id;
        this.maxticket = maxticket;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public int getMaxticket() {
        return maxticket;
    }

    public void setMaxticket(int maxticket) {
        this.maxticket = maxticket;
    }
}
