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

import com.examly.springapp.model.DriverRequest;
import com.examly.springapp.model.DriverRequestDTO;
import com.examly.springapp.service.DriverRequestServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/driverRequest")
@Tag(name = "DriverRequestController", description = "Driver Request controller used for CRUD operations")
public class DriverRequestController {

    private final DriverRequestServiceImpl driverRequestServiceImpl;

    /**
     * Adds a new DriverRequest.
     * 
     * @param driverRequest - the DriverRequest object to add.
     * @return ResponseEntity containing the created DriverRequest.
 * @throws Exception 
     */
    @Operation(summary = "Add new DriverRequest", description = "Adds a new DriverRequest to the system. Throws DuplicateDriverRequestException if a duplicate DriverRequest is detected.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "DriverRequest created successfully"),
            @ApiResponse(responseCode = "409", description = "Duplicate DriverRequest detected")
    })
    @PostMapping
    public ResponseEntity<DriverRequest> addDriverRequest(@Valid @RequestBody DriverRequestDTO driverRequestDTO)
            throws Exception {
        return ResponseEntity.status(201).body(driverRequestServiceImpl.addDriverRequest(driverRequestDTO));
    }

    /**
     * Retrieves a DriverRequest by its ID.
     * 
     * @param driverRequestId - the ID of the DriverRequest to retrieve.
     * @return ResponseEntity containing the DriverRequest.
     * @throws EntityNotFoundException if the DriverRequest is not found.
     */
    @Operation(summary = "Retrieve DriverRequest by ID", description = "Retrieves a DriverRequest by its ID. Throws EntityNotFoundException if the DriverRequest is not found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DriverRequest retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "DriverRequest not found")
    })
    @GetMapping("/{driverRequestId}")
    public ResponseEntity<DriverRequest> getDriverRequestById(@PathVariable Long driverRequestId)
            throws EntityNotFoundException {
        return ResponseEntity.status(200).body(driverRequestServiceImpl.getDriverRequestById(driverRequestId));
    }

    /**
     * Retrieves all DriverRequests.
     * 
     * @return ResponseEntity containing a list of all DriverRequests.
     */
    @Operation(summary = "Retrieve all DriverRequests", description = "Retrieves a list of all DriverRequests in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All DriverRequests retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<DriverRequest>> getAllDriverRequests() {
        return ResponseEntity.status(200).body(driverRequestServiceImpl.getAllDriverRequests());
    }

    /**
     * Retrieves DriverRequests by User ID.
     * 
     * @param userId - the ID of the user to retrieve DriverRequests for.
     * @return ResponseEntity containing a list of DriverRequests for the specified
     *         user.
     */
    @Operation(summary = "Retrieve DriverRequests by User ID", description = "Retrieves DriverRequests for a specified user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DriverRequests retrieved successfully")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DriverRequest>> findDriverRequestsByUserId(@PathVariable Long userId) {
        List<DriverRequest> response = driverRequestServiceImpl.findDriverRequestsByUserId(userId);
        return ResponseEntity.status(200).body(response);
    }

    /**
     * Updates an existing DriverRequest.
     * 
     * @param driverRequestId - the ID of the DriverRequest to update.
     * @param driverRequest   - the updated DriverRequest object.
     * @return ResponseEntity containing the updated DriverRequest.
     * @throws EntityNotFoundException if the DriverRequest to update is not found.
     */
    @Operation(summary = "Update existing DriverRequest", description = "Updates an existing DriverRequest by its ID. Throws EntityNotFoundException if the DriverRequest to update is not found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DriverRequest updated successfully"),
            @ApiResponse(responseCode = "404", description = "DriverRequest not found")
    })
    @PutMapping("/{driverRequestId}")
    public ResponseEntity<DriverRequest> updateDriverRequest(@PathVariable Long driverRequestId,
            @RequestBody DriverRequest driverRequest) throws EntityNotFoundException {

        DriverRequest response = driverRequestServiceImpl.updateDriverRequest(driverRequestId, driverRequest);
        return ResponseEntity.status(200).body(response);
    }

    /**
     * Retrieves DriverRequests by Driver ID.
     * 
     * @param driverId - the ID of the driver to retrieve DriverRequests for.
     * @return ResponseEntity containing a list of DriverRequests for the specified
     *         driver.
     */
    @Operation(summary = "Retrieve DriverRequests by Driver ID", description = "Retrieves DriverRequests for a specified driver by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DriverRequests retrieved successfully")
    })
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<DriverRequest>> findDriverRequestsByDriverId(@PathVariable Long driverId) {
        List<DriverRequest> response = driverRequestServiceImpl.findDriverRequestsByDriverId(driverId);
        return ResponseEntity.status(200).body(response);
    }

    /**
     * Deletes an existing DriverRequest.
     * 
     * @param driverRequestId - the ID of the DriverRequest to delete.
     * @return ResponseEntity containing the deleted DriverRequest.
     */
    @Operation(summary = "Delete DriverRequest", description = "Deletes an existing DriverRequest by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DriverRequest deleted successfully")
    })
    @DeleteMapping("/{driverRequestId}")
    public ResponseEntity<DriverRequest> deleteDriverRequest(@PathVariable Long driverRequestId) {
        DriverRequest response = driverRequestServiceImpl.deleteDriverRequest(driverRequestId);
        return ResponseEntity.status(200).body(response);
    }

}
