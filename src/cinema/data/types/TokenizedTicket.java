package cinema.data.types;

import java.util.UUID;

public class TokenizedTicket {
    UUID token;
    Ticket ticket;

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public TokenizedTicket(UUID token, Ticket ticket) {
        this.token = token;
        this.ticket = ticket;
    }
}
