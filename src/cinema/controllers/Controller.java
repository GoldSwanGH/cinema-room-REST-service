package cinema.controllers;

import cinema.data.types.PricedTicket;
import cinema.data.types.Seats;
import cinema.data.types.Ticket;
import cinema.data.types.TokenizedTicket;
import cinema.exceptions.TicketNotFoundException;
import cinema.exceptions.WrongPasswordException;
import cinema.exceptions.WrongRowColumnNumberException;
import cinema.exceptions.WrongTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@RestController
public class Controller {
    Logger logger = LoggerFactory.getLogger(Controller.class);

    static final Map<Ticket, UUID> availableTickets = new ConcurrentHashMap<>();
    static final Map<UUID, PricedTicket> boughtTickets = new ConcurrentHashMap<>();

    static {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                Ticket ticket = new Ticket();
                ticket.setRow(i);
                ticket.setColumn(j);
                availableTickets.put(ticket, UUID.randomUUID());
            }
        }
    }

    @PostMapping("/purchase")
    public TokenizedTicket purchaseTicket(@RequestBody Ticket ticket) {
        logger.info("Before ticket purchase:" + availableTickets.size() + " available, " +
                boughtTickets.size() + " purchased.");
        if (ticket.getColumn() < 1 || ticket.getColumn() > 9 || ticket.getRow() < 1 || ticket.getRow() > 9) {
            throw new WrongRowColumnNumberException();
        }
        UUID token = availableTickets.remove(ticket);
        if (token == null) {
            throw new TicketNotFoundException();
        }
        PricedTicket pricedTicket = new PricedTicket(ticket);
        boughtTickets.put(token, pricedTicket);
        logger.info("Ticket was purchased: " + availableTickets.size() + " available, " +
                boughtTickets.size() + " purchased.");
        return new TokenizedTicket(token, pricedTicket);
    }

    @PostMapping("/return")
    public Map<String, PricedTicket> returnTicket(@RequestBody TokenizedTicket token) {
        logger.info("Before ticket return: " + availableTickets.size() + " available, " +
                boughtTickets.size() + " purchased.");
        PricedTicket ticket = boughtTickets.remove(token.getToken());
        if (ticket == null) {
            throw new WrongTokenException();
        }
        availableTickets.put(ticket, token.getToken());
        logger.info("After ticket return: " + availableTickets.size() + " available, " +
                boughtTickets.size() + " purchased.");
        return Map.of("returned_ticket", ticket);
    }

    @PostMapping("/stats")
    public Map<String, Integer> getStatistics(@RequestParam(required = false) String password) {
        if (!"super_secret".equals(password)) {
            throw new WrongPasswordException();
        }
        Integer income = 0;
        for (var ticket:
             boughtTickets.values()) {
            income += ticket.getPrice();
        }
        logger.info("Stats: " + availableTickets.size() + " available, " +
                boughtTickets.size() + " purchased, " + income + " income.");
        return Map.of("current_income", income,
                "number_of_available_seats", availableTickets.size(),
                "number_of_purchased_tickets", boughtTickets.size());
    }

    @GetMapping("/seats")
    public Seats getSeats() {
        return new Seats(availableTickets);
    }
}
