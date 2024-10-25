package org.example.dao;

import org.example.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    @Query("SELECT t FROM Training t " +
            "JOIN t.trainer trainer " +
            "JOIN t.trainees trainee " +
            "WHERE trainer.user.username = :username " +
            "AND t.trainingDate BETWEEN :fromDate AND :toDate " +
            "AND (trainee.name = :traineeName OR :traineeName IS NULL) " +
            "ORDER BY t.trainingDate DESC")
    List<Training> findTrainingsByTrainerAndCriteria(
            @Param("username") String username,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            @Param("traineeName") String traineeName);

    @Query("SELECT t FROM Training t " +
            "JOIN t.trainees trainee " +
            "JOIN t.trainer trainer " +
            "WHERE trainee.user.username = :username " +
            "AND t.trainingDate BETWEEN :fromDate AND :toDate " +
            "AND (trainer.user.username = :trainerName OR :trainerName IS NULL) " +
            "AND (t.specialization.type = :trainingType OR :trainingType IS NULL) " +
            "ORDER BY t.trainingDate DESC")
    List<Training> findTrainingsByTraineeAndCriteria(
            @Param("username") String username,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            @Param("trainerName") String trainerName,
            @Param("trainingType") String trainingType);
}
