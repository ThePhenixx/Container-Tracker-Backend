package com.containertracker.containertrackerv1.ContainersManagement.Repository;

import com.containertracker.containertrackerv1.ContainersManagement.Entity.ContainerEntity;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.Localisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LocalisationRepository extends JpaRepository<Localisation, Long> {


    @Query("SELECT l.container FROM localisation l WHERE l.updaterLastname LIKE :updaterLastname%")
    List<ContainerEntity> findByUpdater(String updaterLastname);

    @Query("SELECT l.container FROM localisation l WHERE l.updaterLastname LIKE :updaterLastname% AND l.container.archived = true")
    List<ContainerEntity> findArchivedByUpdater(String updaterLastname);

    @Query("SELECT l.container FROM localisation l WHERE l.updaterLastname LIKE :updaterLastname% AND l.container.archived = false")
    List<ContainerEntity> findRetrievedByUpdater(String updaterLastname);

    @Query("SELECT local.container FROM localisation local WHERE DATE(local.date) = :date")
    List<ContainerEntity> findByDate(@Param("date") LocalDate date);

    @Query("SELECT local.container FROM localisation local WHERE DATE(local.date) = :date AND local.container.archived = true")
    List<ContainerEntity> findArchivedByDate(@Param("date") LocalDate date);

    @Query("SELECT local.container FROM localisation local WHERE DATE(local.date) = :date AND local.container.archived = false")
    List<ContainerEntity> findRetrievedByDate(@Param("date") LocalDate date);

    @Query("SELECT l.container FROM localisation l WHERE DATE(l.date) <= :date ORDER BY l.date DESC")
    List<ContainerEntity> findBeforeDate(@Param("date") LocalDate date);

    @Query("SELECT l.container FROM localisation l WHERE DATE(l.date) >= :date ORDER BY l.date ASC")
    List<ContainerEntity> findAfterDate(@Param("date") LocalDate date);

    @Query("SELECT l.container FROM localisation l WHERE DATE(l.date) >= :date1 and DATE(l.date) <= :date2 ORDER BY l.date ASC")
    List<ContainerEntity> findInTimeInterval(@Param("date1") LocalDate firstDate, @Param("date2") LocalDate lastDate);

    @Query("SELECT l.container FROM localisation l WHERE DATE(l.date) >= :date1 and DATE(l.date) <= :date2  AND l.container.archived = true ORDER BY l.date ASC")
    List<ContainerEntity> findArchivedInTimeInterval(@Param("date1") LocalDate firstDate, @Param("date2") LocalDate lastDate);

    @Query("SELECT l.container FROM localisation l WHERE DATE(l.date) >= :date1 AND DATE(l.date) <= :date2  AND l.container.archived = false ORDER BY l.date ASC")
    List<ContainerEntity> findRetrievedInTimeInterval(@Param("date1") LocalDate firstDate, @Param("date2") LocalDate lastDate);


//    @Query("UPDATE localisation l SET l.container= :container WHERE l= :loc")
//    void setContainer(@Param("loc") Localisation localisation, @Param("container")ContainerEntity container);

    @Query("SELECT l FROM localisation l WHERE l.container= :container ORDER BY l.date DESC")
    List<Localisation> getLocalisationsByContainer(@Param("container") ContainerEntity container);

    @Query("SELECT l FROM localisation l WHERE l.container.identificationNumber= :identificationNumber ORDER BY l.date DESC")
    List<Localisation> getLocalisationsByContainerIdNumber(@Param("identificationNumber") String identificationNumber);

    @Query("SELECT l FROM localisation l WHERE l.container= :container AND l.date=(SELECT MAX(l.date) FROM localisation l WHERE l.container= :container)")
    Localisation getLastLocalisationByContainer(@Param("container") ContainerEntity container);

    @Query("SELECT l FROM localisation l WHERE l.container.identificationNumber= :identificationNumber AND l.date=(SELECT MAX(l.date) FROM localisation l WHERE l.container= :container)")
    Localisation getLastLocalisationByContainerIdNumber(@Param("identificationNumber") String identificationNumber);
}
