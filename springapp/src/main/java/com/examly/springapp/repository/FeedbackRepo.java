package com.examly.springapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Feedback;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Long> {

    /**
     * Retrieves feedback by the specified user ID and driver ID.
     *
     * @param userId the ID of the user whose feedback is to be retrieved.
     * @param driverId the ID of the driver whose feedback is to be retrieved.
     * @return an Optional containing the `Feedback` entity if found.
     */
    @Query("select f from Feedback f where f.user.userId = ?1 and f.driver.driverId = ?2")
    Optional<Feedback> getFeedbackByUserAndDriverId(long userId, long driverId);

    /**
     * Retrieves all feedbacks by the specified user ID.
     *
     * @param userId the ID of the user whose feedbacks are to be retrieved.
     * @return a list of `Feedback` entities matching the user ID.
     */
    @Query("select f from Feedback f where f.user.userId = ?1")
    List<Feedback> getFeedbacksByUserId(Long userId);

    @Modifying
    @Query("delete from Feedback f where f.driver.driverId=?1")
    void deleteFeedbackByDriverId(long driverId);

}
