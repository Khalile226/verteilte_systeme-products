package de.hs_kl.vis.ss24.products.controller;

import de.hs_kl.vis.ss24.products.controller.dto.BookingPosition;
import de.hs_kl.vis.ss24.products.controller.dto.IncomingBooking;
import de.hs_kl.vis.ss24.products.persistence.RandomStringGenerator;
import de.hs_kl.vis.ss24.products.persistence.UnitsService;
import de.hs_kl.vis.ss24.products.controller.dto.Unit;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("units")
@Tag(name="Units API")
public class UnitsController {

    @Autowired
    private UnitsService unitsService;

    @Operation(summary = "Handle Order", description = "Remove the ordered units from the warehouse and return the removed units.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Please Contact the owner")
    })
    @PostMapping
    private ResponseEntity<?> handleBooking(@RequestBody IncomingBooking booking){
        try {
            List<BookingPosition> bookingPositions = booking.getBookingPositions();
            List<Unit> unitsToRemove = unitsService.removeUnits(bookingPositions);
            return ResponseEntity.ok().body(unitsToRemove);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @Operation(summary = "Handle Order", description = "Add units to the warehouse return the added units.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Please Contact the owner")
    })
    @PostMapping("/add")
    private ResponseEntity<?> handleAddUnits(@RequestBody IncomingBooking booking){
        try {

            List<BookingPosition> bookingPositions = booking.getBookingPositions();
            List<Unit> unitsToAdd = new ArrayList<>();
            for(int i=0; i < bookingPositions.size(); i++){
                for(int j = 0; j < bookingPositions.get(i).getAmount(); j++) {
                    Unit unit = new Unit();
                    unit.setProductId(bookingPositions.get(i).getProductId());
                    unit.setSerialNumber(RandomStringGenerator.generateRandomString());
                    unitsToAdd.add(unit);
                }
            }

            List<Unit> outgoingUnits = unitsService.addUnits(unitsToAdd);
            return ResponseEntity.ok().body(outgoingUnits);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}
