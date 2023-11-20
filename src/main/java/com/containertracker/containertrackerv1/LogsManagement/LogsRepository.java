package com.containertracker.containertrackerv1.LogsManagement;


import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogsRepository extends JpaRepository<LogEntity, Integer> {

    @Query("SELECT L FROM LogEntity L WHERE L.user= :user ORDER BY L.date DESC")
    List<LogEntity> findByUser(@Param("user") User user);
}
