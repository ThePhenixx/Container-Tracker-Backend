package com.containertracker.containertrackerv1.ContainersManagement.Service;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.ContainerEntity;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.Localisation;

import java.util.List;

public interface ContainerService {

    ContainerEntity createContainer(User user, ContainerEntity container, Localisation localisation);

    ContainerEntity updateContainerData(User user, String identificationNumber, ContainerEntity updatedContainer);

    List<ContainerEntity> findRetrievedContainers(String id, User user);

    List<ContainerEntity> findArchivedContainers(String id, User user);
    List<ContainerEntity> findAllRetrievedContainers();

    List<ContainerEntity> findAllArchivedContainers();

    ContainerEntity findContainerByIdNumber(String identificationNumber);

    ContainerEntity accessContainerByIdNumber(String identificationNumber, User user);

    void deleteContainerByIdNumber(String identificationNumber);

    List<ContainerEntity> findAllContainersStartingWith(String idNumber);

    void deleteContainer(ContainerEntity container);

    List<ContainerEntity> findAllContainers();

    void save(ContainerEntity container);

}
