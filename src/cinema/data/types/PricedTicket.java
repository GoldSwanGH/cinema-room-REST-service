package cinema.data.types;

public class PricedTicket extends Ticket {
    Integer price;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public PricedTicket(Ticket ticket) {
        this.row = ticket.row;
        this.column = ticket.column;

        if (this.row <= 4) {
            price = 10;
        } else {
            price = 8;
        }
    }

    public PricedTicket(Ticket ticket, Integer price) {
        this.row = ticket.row;
        this.column = ticket.column;
        this.price = price;
    }
}
