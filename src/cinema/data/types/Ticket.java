package cinema.data.types;

import java.util.Objects;

public class Ticket {
    Integer row;
    Integer column;

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return getRow().equals(ticket.getRow()) && getColumn().equals(ticket.getColumn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn());
    }
}
