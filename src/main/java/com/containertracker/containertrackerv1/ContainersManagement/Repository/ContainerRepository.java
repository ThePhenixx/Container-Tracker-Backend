package com.containertracker.containertrackerv1.ContainersManagement.Repository;

import com.containertracker.containertrackerv1.ContainersManagement.Entity.ContainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ContainerRepository extends JpaRepository<ContainerEntity, Long> {

    ContainerEntity findByIdentificationNumber(String identificationNumber);

    @Query("SELECT c FROM container c WHERE c.identificationNumber LIKE :idNumber%")
    List<ContainerEntity> findAllContainersStartingWith(@Param("idNumber") String idNumber);

    @Query("SELECT c FROM container c WHERE (c.identificationNumber LIKE :idNumber%) AND (c.archived = false)")
    List<ContainerEntity> findRetrievedContainers(@Param("idNumber") String idNumber);

    @Query("SELECT c FROM container c WHERE (c.identificationNumber LIKE :idNumber%) AND (c.archived = true)")
    List<ContainerEntity> findArchivedContainers(@Param("idNumber") String idNumber);

    @Query("SELECT c FROM container c WHERE (c.archived = false)")
    List<ContainerEntity> findAllRetrievedContainers();

    @Query("SELECT c FROM container c WHERE (c.archived = true)")
    List<ContainerEntity> findAllArchivedContainers();

}
