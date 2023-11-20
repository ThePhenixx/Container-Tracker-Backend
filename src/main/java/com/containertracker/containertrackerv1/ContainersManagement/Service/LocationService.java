package com.containertracker.containertrackerv1.ContainersManagement.Service;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.ContainerEntity;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.Localisation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface LocationService {

    public List<ContainerEntity> findByDate(LocalDate date);

    List<ContainerEntity> findArchivedByDate(LocalDate date);

    List<ContainerEntity> findRetrievedByDate(LocalDate date);

    public List<ContainerEntity> findBeforeDate(LocalDate date);

    public List<ContainerEntity> findAfterDate(LocalDate date);

    public List<ContainerEntity> findInTimeInterval(LocalDate firstDate, LocalDate lastDate);

    public List<ContainerEntity> findArchivedInTimeInterval(LocalDate firstDate, LocalDate lastDate, User user);

    public List<ContainerEntity> findRetrievedInTimeInterval(LocalDate firstDate, LocalDate lastDate, User user);

    public List<ContainerEntity> findByUpdater(String updaterLastname);

    public List<ContainerEntity> findArchivedByUpdater(String updaterLastname, User user);

    public List<ContainerEntity> findRetrievedByUpdater(String updaterLastname, User user);
    public Localisation createLocalisation(User user, ContainerEntity container, Localisation localisation);

    public List<Localisation> getLocalisationsByContainer(ContainerEntity container);

    public Localisation getLastLocalisationByContainer(ContainerEntity container);


    public void deleteLocalisation(Localisation localisation);

    public void save(Localisation localisation);
}
