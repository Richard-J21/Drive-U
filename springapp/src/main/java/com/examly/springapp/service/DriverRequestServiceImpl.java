package com.examly.springapp.service;
 
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
 
import org.springframework.stereotype.Service;
 
import com.examly.springapp.exceptions.DuplicateDriverRequestException;
import com.examly.springapp.model.Driver;
import com.examly.springapp.model.DriverRequest;
import com.examly.springapp.model.DriverRequestDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.DriverRepo;
import com.examly.springapp.repository.DriverRequestRepo;
import com.examly.springapp.repository.UserRepo;
 
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class DriverRequestServiceImpl implements DriverRequestService {
 
    private final DriverRequestRepo driverRequestRepo;
 
    private final UserRepo userRepo;
 
    private final DriverRepo driverRepo;
 
    /**
     * Adds a new Driver Request
     *
     * @param driverRequest the Driver Request to add
     * @return the saved Driver Request
     * @throws DuplicateDriverRequestException if a request with the same User ID
     *                                         already exists
     */
    @Override
    public DriverRequest addDriverRequest(DriverRequestDTO driverRequestDTO) throws Exception {
        DriverRequest existsDriverRequest = driverRequestRepo
                .findByUserIdWithStatus(driverRequestDTO.getUserId()).orElse(null);
 
        if (existsDriverRequest != null) {
            throw new DuplicateDriverRequestException(
                    "Driver Request with User ID: " + driverRequestDTO.getUserId() + " already exists!");
        }
        DriverRequest driverRequest = new DriverRequest();
 
        User user = userRepo.findById(driverRequestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("user not found!"));
        driverRequest.setUser(user);
 
        Driver driver = driverRepo.findById(driverRequestDTO.getDriverId())
                .orElseThrow(() -> new EntityNotFoundException("Driver not found!"));
        driverRequest.setDriver(driver);
 
        driverRequest.setRequestDate(LocalDate.now());
        driverRequest.setTripDate(driverRequestDTO.getTripDate());
        driverRequest
                .setTimeSlot(LocalTime.parse(driverRequestDTO.getTimeSlot(), DateTimeFormatter.ofPattern("HH:mm")));
 
        driverRequest.setStatus("pending");
        driverRequest.setPickupLocation(driverRequestDTO.getPickupLocation());
        driverRequest.setDropLocation(driverRequestDTO.getDropLocation());
        driverRequest.setEstimatedDuration(driverRequestDTO.getEstimatedDuration());
        driverRequest.setComments(driverRequestDTO.getComments());
 
        return driverRequestRepo.save(driverRequest);
    }
 
    /**
     * Retrieves a Driver Request by its ID
     *
     * @param driverRequestId the ID of the Driver Request to retrieve
     * @return the Driver Request with the given ID
     * @throws EntityNotFoundException if no Driver Request with the given ID is
     *                                 found
     */
    @Override
    public DriverRequest getDriverRequestById(Long driverRequestId) throws EntityNotFoundException {
 
        return driverRequestRepo.findById(driverRequestId).orElseThrow(
                () -> new EntityNotFoundException("The Driver Request with ID: " + driverRequestId + " is not found"));
    }
 
    /**
     * Retrieves all Driver Requests
     *
     * @return a list of all Driver Requests
     */
    @Override
    public List<DriverRequest> getAllDriverRequests() {
        return driverRequestRepo.findAll();
 
    }
 
    /**
     * Updates an existing Driver Request
     *
     * @param driverRequestId the ID of the Driver Request to update
     * @param driverRequest   the new Driver Request details
     * @return the updated Driver Request
     * @throws EntityNotFoundException if no Driver Request with the given ID is
     *                                 found
     */
    @Override
    public DriverRequest updateDriverRequest(Long driverRequestId, DriverRequest driverRequest)
            throws EntityNotFoundException {
        DriverRequest existsDriverRequest = getDriverRequestById(driverRequestId);
 
        if (driverRequest.getRequestDate() != null)
            existsDriverRequest.setRequestDate(driverRequest.getRequestDate());
 
        if (driverRequest.getStatus() != null) {
            existsDriverRequest.setStatus(driverRequest.getStatus());
 
            Driver driver = existsDriverRequest.getDriver();
            if (driverRequest.getStatus().equalsIgnoreCase("approved")) {
                driver.setAvailabilityStatus("inactive");
            }
            if (driverRequest.getStatus().equalsIgnoreCase("rejected")
                    || driverRequest.getStatus().equalsIgnoreCase("pending")) {
                driver.setAvailabilityStatus("active");
            }
            if (driverRequest.getStatus().equalsIgnoreCase("tripend")) {
                driver.setAvailabilityStatus("active");
 
                existsDriverRequest.setActualDropDate(LocalDate.now());
                existsDriverRequest.setActualDropTime(LocalTime.now());
 
                if (existsDriverRequest.getTimeSlot() != null && existsDriverRequest.getActualDropTime() != null) {
                    Duration duration = Duration.between(existsDriverRequest.getTimeSlot(),
                            existsDriverRequest.getActualDropTime());
 
                    long hours = duration.toHours();
                    long minutes = duration.toMinutes() % 60;
 
                    existsDriverRequest.setActualDuration(String.format("%02d:%02d", hours, minutes));
 
                    Double hourlyRate = existsDriverRequest.getDriver().getHourlyRate();
                    double paymentAmount = (hours + (minutes / 60.0)) * hourlyRate;
 
                    existsDriverRequest.setPaymentAmount(paymentAmount);
 
                } else {
                    existsDriverRequest.setActualDuration(null);
                }
            }
        }
 
        if (driverRequest.getTripDate() != null)
            existsDriverRequest.setTripDate(driverRequest.getTripDate());
 
        if (driverRequest.getTimeSlot() != null)
            existsDriverRequest.setTimeSlot(driverRequest.getTimeSlot());
 
        if (driverRequest.getPickupLocation() != null)
            existsDriverRequest.setPickupLocation(driverRequest.getPickupLocation());
 
        if (driverRequest.getDropLocation() != null)
            existsDriverRequest.setDropLocation(driverRequest.getDropLocation());
 
        if (driverRequest.getEstimatedDuration() != null)
            existsDriverRequest.setEstimatedDuration(driverRequest.getEstimatedDuration());
 
        if (driverRequest.getPaymentAmount() != null)
            existsDriverRequest.setPaymentAmount(driverRequest.getPaymentAmount());
 
        if (driverRequest.getComments() != null)
            existsDriverRequest.setComments(driverRequest.getComments());
 
        if (driverRequest.getActualDropDate() != null)
            existsDriverRequest.setActualDropDate(driverRequest.getActualDropDate());
 
        if (driverRequest.getActualDropTime() != null)
            existsDriverRequest.setActualDropTime(driverRequest.getActualDropTime());
 
        if (driverRequest.getActualDuration() != null)
            existsDriverRequest.setActualDuration(driverRequest.getActualDuration());
 
        return driverRequestRepo.save(existsDriverRequest);
    }
 
    /**
     * Deletes a Driver Request by its ID
     *
     * @param driverRequestId the ID of the Driver Request to delete
     * @return the deleted Driver Request
     * @throws EntityNotFoundException if no Driver Request with the given ID is
     *                                 found
     */
    @Override
    public DriverRequest deleteDriverRequest(Long driverRequestId) throws EntityNotFoundException {
 
        DriverRequest existsDriverRequest = getDriverRequestById(driverRequestId);
        driverRequestRepo.deleteById(driverRequestId);
        return existsDriverRequest;
    }
 
    /**
     * Finds Driver Requests by User ID
     *
     * @param userId the User ID to search for
     * @return a list of Driver Requests with the given User ID
     * @throws EntityNotFoundException if no Driver Requests with the given User ID
     *                                 are found
     */
    @Override
    public List<DriverRequest> findDriverRequestsByUserId(Long userId) {
        List<DriverRequest> driverRequests = driverRequestRepo.findDriverRequestsByUserId(userId);
        if (driverRequests.isEmpty()) {
            throw new EntityNotFoundException("The Driver Requests with User ID: " + userId + " is not found.");
        }
        return driverRequests;
    }
 
    /**
     * Finds Driver Requests by Driver ID
     *
     * @param driverId the Driver ID to search for
     * @return a list of Driver Requests with the given Driver ID
     * @throws EntityNotFoundException if no Driver Requests with the given Driver
     *                                 ID are found
     */
    @Override
    public List<DriverRequest> findDriverRequestsByDriverId(Long driverId) {
        List<DriverRequest> driverRequests = driverRequestRepo.findDriverRequestsByDriverId(driverId);
        if (driverRequests.isEmpty()) {
            throw new EntityNotFoundException("The Driver Requests with Driver ID: " + driverId + " is not found.");
        }
        return driverRequests;
    }
 
}
 
 