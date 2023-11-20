package com.containertracker.containertrackerv1.ContainersManagement.Service;


import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import com.containertracker.containertrackerv1.LogsManagement.LogService;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.ContainerEntity;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.Localisation;
import com.containertracker.containertrackerv1.ContainersManagement.Repository.LocalisationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImplementation implements LocationService {

    private final LocalisationRepository localisationRepository;

    private final LogService logService;

    @Override
    public List<ContainerEntity> findByDate(LocalDate date) {
        return localisationRepository.findByDate(date);
    }

    @Override
    public List<ContainerEntity> findArchivedByDate(LocalDate date) {
        return localisationRepository.findArchivedByDate(date);
    }

    @Override
    public List<ContainerEntity> findRetrievedByDate(LocalDate date) {
        return localisationRepository.findRetrievedByDate(date);
    }

    @Override
    public List<ContainerEntity> findBeforeDate(LocalDate date) {
        return localisationRepository.findBeforeDate(date);
    }

    @Override
    public List<ContainerEntity> findAfterDate(LocalDate date) {
        return localisationRepository.findAfterDate(date);
    }

    @Override
    public List<ContainerEntity> findInTimeInterval(LocalDate firstDate, LocalDate lastDate) {
        return localisationRepository.findInTimeInterval(firstDate, lastDate);
    }

    @Override
    public List<ContainerEntity> findArchivedInTimeInterval(LocalDate firstDate, LocalDate lastDate, User user) {
        logService.searchInTimeIntervalArchivedContainer(user, firstDate, lastDate);
        return localisationRepository.findArchivedInTimeInterval(firstDate, lastDate);
    }

    @Override
    public List<ContainerEntity> findRetrievedInTimeInterval(LocalDate firstDate, LocalDate lastDate, User user) {
        logService.searchInTimeIntervalContainer(user, firstDate, lastDate);
        return localisationRepository.findRetrievedInTimeInterval(firstDate, lastDate);
    }

    @Override
    public List<ContainerEntity> findByUpdater(String updaterLastname) {
        return localisationRepository.findByUpdater( updaterLastname);
    }

    @Override
    public List<ContainerEntity> findArchivedByUpdater(String updaterLastname, User user) {
        logService.searchArchivedContainer(user, updaterLastname);
        return localisationRepository.findArchivedByUpdater(updaterLastname);
    }

    @Override
    public List<ContainerEntity> findRetrievedByUpdater(String updaterLastname, User user) {
        logService.searchContainer(user, updaterLastname);
        return localisationRepository.findRetrievedByUpdater(updaterLastname);
    }

    @Override
    public Localisation createLocalisation(User user, ContainerEntity container, Localisation localisation) {
        localisationRepository.save(localisation);
        logService.UpdateLocalisationLog(user, container, localisation);
        return localisation;
    }

    @Override
    public List<Localisation> getLocalisationsByContainer(ContainerEntity container) {
        return localisationRepository.getLocalisationsByContainer(container);
    }

    @Override
    public Localisation getLastLocalisationByContainer(ContainerEntity container) {
        return localisationRepository.getLastLocalisationByContainer(container);
    }

    @Override
    public void deleteLocalisation(Localisation localisation) {
        localisationRepository.delete(localisation);
    }

    @Override
    public void save(Localisation localisation) {
        localisationRepository.save(localisation);
    }


}
