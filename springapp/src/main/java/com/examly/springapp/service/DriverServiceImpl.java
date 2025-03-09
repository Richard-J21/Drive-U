package com.examly.springapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.exceptions.DriverDeletionException;
import com.examly.springapp.exceptions.DuplicateDriverException;
import com.examly.springapp.model.Driver;
import com.examly.springapp.repository.DriverRepo;
import com.examly.springapp.repository.DriverRequestRepo;
import com.examly.springapp.repository.FeedbackRepo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {


    private final DriverRepo driverRepo;

    
    private final FeedbackRepo feedbackRepo;

    
    private final DriverRequestRepo driverRequestRepo;


     /**
     * Adds a new driver to the repository.
     * @param driver The driver to be added.
     * @return The added driver.
     * @throws DuplicateDriverException if a driver with the same license number already exists.
     */
    @Override
    public Driver addDriver(Driver driver) throws DuplicateDriverException {
        if (driverRepo.existsByLicenseNumber(driver.getLicenseNumber())) {
            throw new DuplicateDriverException(
                    "Driver With the License Number " + driver.getLicenseNumber() + " Already Exists");
        }
        return driverRepo.save(driver);
    }

    /**
     * Retrieves all drivers from the repository.
     * @return A list of all drivers.
     */
    @Override
    public List<Driver> getAllDriver() {
        return driverRepo.findAll();
    }

    /**
     * Retrieves a driver by their ID.
     * @param driverId The ID of the driver to be retrieved.
     * @return The driver with the specified ID.
     * @throws EntityNotFoundException if the driver is not found.
     */
    @Override
    public Driver getDriverById(Long driverId) throws EntityNotFoundException {
        Driver driver = driverRepo.findById(driverId).orElse(null);
        if (driver == null) {
            throw new EntityNotFoundException("Driver with Id " + driverId + " is Not Exists");
        }
        return driver;
    }

     /**
     * Updates the details of an existing driver.
     * @param driverId The ID of the driver to be updated.
     * @param driver The updated driver details.
     * @return The updated driver.
     * @throws EntityNotFoundException if the driver is not found.
     */
    @Override
    public Driver updateDriver(Long driverId, Driver driver) throws EntityNotFoundException {
        Driver existsDriver = driverRepo.findById(driverId).orElse(null);
        if (existsDriver == null) {
            throw new EntityNotFoundException("Driver with Id " + driverId + " is Not Exists");
        }
        driver.setDriverId(driverId);
        return driverRepo.save(driver);

    }

    /**
     * Deletes a driver by their ID.
     * This method is annotated with @Transactional to ensure that
     * all associated operations (deleting feedback, requests, and the driver) 
     * are completed successfully. If any of these operations fail, the transaction 
     * will be rolled back to maintain data integrity.
     * 
     * @param driverId The ID of the driver to be deleted.
     * @return A confirmation message of the deletion.
     * @throws DriverDeletionException if the driver cannot be deleted.
     */
    @Override
    @Transactional
    public String deleteDriver(Long driverId) throws DriverDeletionException {
        if (driverRepo.existsById(driverId)) {
            feedbackRepo.deleteFeedbackByDriverId(driverId);
            driverRequestRepo.deleteRequestByDriverId(driverId);
            driverRepo.deleteById(driverId);
            return "Driver With driverId " + driverId + " Deleted Successfully.";
        }
        throw new DriverDeletionException("Failed to Delete Driver Due to System Constraints.");

    }
}