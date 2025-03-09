package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.exceptions.DuplicateDriverRequestException;
import com.examly.springapp.model.DriverRequest;
import com.examly.springapp.model.DriverRequestDTO;

import jakarta.persistence.EntityNotFoundException;

public interface DriverRequestService {
    DriverRequest addDriverRequest(DriverRequestDTO driverRequestDTO) throws Exception;

    DriverRequest getDriverRequestById(Long driverRequestId) throws EntityNotFoundException;

    List<DriverRequest> getAllDriverRequests();

    DriverRequest updateDriverRequest(Long driverRequestId, DriverRequest driverRequest) throws EntityNotFoundException;

    DriverRequest deleteDriverRequest(Long driverRequestId) throws EntityNotFoundException;

    List<DriverRequest> findDriverRequestsByUserId(Long userId);

    List<DriverRequest> findDriverRequestsByDriverId(Long driverId);

}
