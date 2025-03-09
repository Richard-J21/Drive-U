package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.exceptions.DriverDeletionException;
import com.examly.springapp.exceptions.DuplicateDriverException;
import com.examly.springapp.model.Driver;

import jakarta.persistence.EntityNotFoundException;

public interface DriverService {

    /**
     * Adds a new driver to the repository.
     * 
     * @param driver The driver to be added.
     * @return The added driver.
     * @throws DuplicateDriverException if a driver with the same license number already exists.                                
     */
    Driver addDriver(Driver driver) throws DuplicateDriverException;

    /**
     * Retrieves all drivers from the repository.
     * @return A list of all drivers.
     */
    List<Driver> getAllDriver();

    /**
     * Retrieves a driver by their ID.
     * @param driverId The ID of the driver to be retrieved.
     * @return The driver with the specified ID.
     * @throws EntityNotFoundException if the driver is not found.
     */
    Driver getDriverById(Long driverId) throws EntityNotFoundException;

    /**
     * Updates the details of an existing driver.
     * @param driverId The ID of the driver to be updated.
     * @param driver   The updated driver details.
     * @return The updated driver.
     * @throws EntityNotFoundException if the driver is not found.
     */
    Driver updateDriver(Long driverId, Driver driver) throws EntityNotFoundException;

    /**
     * Deletes a driver by their ID.
     * @param driverId The ID of the driver to be deleted.
     * @return A confirmation message of the deletion.
     * @throws DriverDeletionException if the driver cannot be deleted.
     */
    String deleteDriver(Long driverId) throws DriverDeletionException;

}
