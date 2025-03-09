package com.examly.springapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.DriverRequest;


@Repository
public interface DriverRequestRepo extends JpaRepository<DriverRequest, Long> {

    /**
     * Finds all driver requests by the specified user ID.
     * 
     * @param userId the ID of the user whose driver requests are to be retrieved.
     * @return a list of `DriverRequest` entities matching the user ID.
     */
    @Query("select d from DriverRequest d where d.user.userId = ?1")
    List<DriverRequest> findDriverRequestsByUserId(Long userId);

    /**
     * Finds all driver requests by the specified driver ID.
     * 
     * @param driverId the ID of the driver whose requests are to be retrieved.
     * @return a list of `DriverRequest` entities matching the driver ID.
     */
    @Query("select d from DriverRequest d where d.driver.driverId = ?1")
    List<DriverRequest> findDriverRequestsByDriverId(Long driverId);

    /**
     * Finds driver requests by user ID that have a status of 'pending' or
     * 'approved'.
     * 
     * @param userId the ID of the user whose driver requests are to be retrieved.
     * @return an Optional containing the `DriverRequest` entities matching the user
     *         ID and status.
     */
    @Query("select d from DriverRequest d where d.user.userId = ?1 and d.status in ('pending', 'approved')")
    Optional<DriverRequest> findByUserIdWithStatus(Long userId);

    /**
     * Deletes all DriverRequest entities associated with the specified driver ID.
     * This method is annotated with @Modifying and @Query to indicate
     * that it modifies the database and to provide a custom query for deletion.
     *
     * @param driverId The ID of the driver whose requests are to be deleted.
     */

    @Modifying
    @Query("delete from DriverRequest d where d.driver.driverId=?1")
    void deleteRequestByDriverId(Long driverId);

}
