package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.exceptions.DriverDeletionException;
import com.examly.springapp.exceptions.DuplicateDriverException;
import com.examly.springapp.model.Driver;
import com.examly.springapp.service.DriverService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/driver")
@RequiredArgsConstructor
@Tag(name = "DriverController", description = "Driver controller used for CRUD operations")
public class DriverController {

    private final DriverService driverService;

    /**
     * Adds a new driver.
     * 
     * @param driver The driver to be added.
     * @return The added driver.
     * @throws DuplicateDriverException if the driver already exists.
     */
    @Operation(
        summary = "Add new Driver",
        description = "Adds a new driver to the system. Throws DuplicateDriverException if the driver already exists."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Driver created successfully"),
        @ApiResponse(responseCode ="403",description = "Customer cannot add a driver"),
        @ApiResponse(responseCode = "409", description = "Duplicate driver detected")
    })
    @PostMapping
    public ResponseEntity<Driver> addDriver(@Valid @RequestBody Driver driver) throws DuplicateDriverException {
        return ResponseEntity.status(201).body(driverService.addDriver(driver));
    }

    /**
     * Retrieves all drivers.
     * 
     * @return A list of all drivers.
     */
    @Operation(
        summary = "Retrieve all Drivers",
        description = "Retrieves a list of all drivers in the system."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All drivers retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<Driver>> getAllDriver() {
        List<Driver> drivers = driverService.getAllDriver();
        return ResponseEntity.status(200).body(drivers);
    }

    /**
     * Retrieves a driver by their ID.
     * 
     * @param driverId The ID of the driver to be retrieved.
     * @return The driver with the specified ID.
     * @throws EntityNotFoundException if the driver is not found.
     */
    @Operation(
        summary = "Retrieve Driver by ID",
        description = "Retrieves a driver by their ID. Throws EntityNotFoundException if the driver is not found."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Driver retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Driver not found")
    })
    @GetMapping("/{driverId}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long driverId) throws EntityNotFoundException {
        return ResponseEntity.status(200).body(driverService.getDriverById(driverId));
    }

    /**
     * Updates the details of an existing driver.
     * 
     * @param driverId The ID of the driver to be updated.
     * @param driver   The updated driver details.
     * @return The updated driver.
     */
    @Operation(
        summary = "Update existing Driver",
        description = "Updates the details of an existing driver. Throws EntityNotFoundException if the driver to update is not found."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Driver updated successfully"),
        @ApiResponse(responseCode = "404", description = "Driver not found")
    })
    @PutMapping("/{driverId}")
    public ResponseEntity<Driver> updateDriver(@PathVariable Long driverId, @RequestBody Driver driver) {
        return ResponseEntity.status(200).body(driverService.updateDriver(driverId, driver));
    }

    /**
     * Deletes a driver by their ID.
     * 
     * @param driverId The ID of the driver to be deleted.
     * @return A confirmation message of the deletion.
     * @throws DriverDeletionException if the driver cannot be deleted.
     */
    @Operation(
        summary = "Delete Driver",
        description = "Deletes a driver by their ID. Throws DriverDeletionException if the driver cannot be deleted."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Driver deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Driver not found"),
        @ApiResponse(responseCode = "500", description = "Driver deletion failed")
    })
    @DeleteMapping("/{driverId}")
    public ResponseEntity<String> deleteDriver(@PathVariable Long driverId) throws DriverDeletionException {
        return ResponseEntity.status(200).body(driverService.deleteDriver(driverId));
    }

}
