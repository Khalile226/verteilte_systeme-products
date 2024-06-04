package de.hs_kl.vis.ss24.products.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class IncomingBooking {

    @ArraySchema(schema = @Schema(name = "units", implementation = BookingPosition.class, description = "Each ordered Unit"))
    @JsonProperty("booking_positions")
    private List<BookingPosition> bookingPositions;

    public List<BookingPosition> getBookingPositions() {
        return bookingPositions;
    }

    public void setBookingPositions(List<BookingPosition> units) {
        this.bookingPositions = units;
    }
}
