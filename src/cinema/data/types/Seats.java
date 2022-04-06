package cinema.data.types;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Seats {
    @JsonProperty("total_rows")
    Integer totalRows = 9;
    @JsonProperty("total_columns")
    Integer totalColumns = 9;
    @JsonProperty("available_seats")
    List<PricedTicket> availableSeats = new ArrayList<>();

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(Integer totalColumns) {
        this.totalColumns = totalColumns;
    }

    public List<PricedTicket> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Map<Ticket, UUID> availableSeats) {
        availableSeats.forEach((ticket, UUID) -> {
            this.availableSeats.add(new PricedTicket(ticket));
        });
    }

    public Seats(Map<Ticket, UUID> availableSeats) {
        this.setAvailableSeats(availableSeats);
    }
}
