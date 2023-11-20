package com.containertracker.containertrackerv1.ContainersManagement.Service;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.UserRepository;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.ContainerEntity;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.Localisation;
import com.containertracker.containertrackerv1.ContainersManagement.Repository.ContainerRepository;
import com.containertracker.containertrackerv1.ContainersManagement.Repository.LocalisationRepository;
import com.containertracker.containertrackerv1.LogsManagement.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContainerServiceImplementation implements ContainerService {

    private final ContainerRepository containerRepository;

    private final LocalisationRepository localisationRepository;

    private final LogService logService;

    private final UserRepository userRepository;

    @Override
    public ContainerEntity createContainer(User user, ContainerEntity container, Localisation localisation) {
        logService.CreateLog(user, container, localisation);
        return container;
    }

    @Override
    public ContainerEntity updateContainerData(User user, String identificationNumber, ContainerEntity updatingContainer) {
        ContainerEntity container = containerRepository.findByIdentificationNumber(identificationNumber);
        logService.UpdateLog(user, container);
        container.setCapacity_CUM(updatingContainer.getCapacity_CUM());
        container.setMaximumGrossWeight_Kg(updatingContainer.getMaximumGrossWeight_Kg());
        container.setMaximumPayload_Kg(updatingContainer.getMaximumPayload_Kg());
        container.setTareWeight_Kg(updatingContainer.getTareWeight_Kg());
        container.setSizeAndTypeCode(updatingContainer.getSizeAndTypeCode());
        container.setCapacity_CUFT(updatingContainer.getCapacity_CUFT());
        container.setMaximumGrossWeight_Lbs(updatingContainer.getMaximumGrossWeight_Lbs());
        container.setMaximumPayload_Lbs(updatingContainer.getMaximumPayload_Lbs());
        container.setTareWeight_Lbs(updatingContainer.getTareWeight_Lbs());
        return containerRepository.save(container);
    }

    @Override
    public List<ContainerEntity> findRetrievedContainers(String id, User user) {
        logService.searchContainer(user, id);
        return containerRepository.findRetrievedContainers(id);
    }

    @Override
    public List<ContainerEntity> findArchivedContainers(String id, User user) {
        logService.searchArchivedContainer(user, id);
        return containerRepository.findArchivedContainers(id);
    }

    @Override
    public List<ContainerEntity> findAllRetrievedContainers() {
        return containerRepository.findAllRetrievedContainers();
    }

    @Override
    public List<ContainerEntity> findAllArchivedContainers() {
        return containerRepository.findAllArchivedContainers();
    }


    @Override
    public ContainerEntity findContainerByIdNumber(String identificationNumber) {
        ContainerEntity container = containerRepository.findByIdentificationNumber(identificationNumber);
        return container;
    }

    @Override
    public ContainerEntity accessContainerByIdNumber(String identificationNumber, User user) {
        ContainerEntity container = containerRepository.findByIdentificationNumber(identificationNumber);
        logService.accessContainer(user, container);
        return container;
    }

    @Override
    public void deleteContainerByIdNumber(String identificationNumber) {
        containerRepository.delete(findContainerByIdNumber(identificationNumber));
    }

    @Override
    public List<ContainerEntity> findAllContainersStartingWith(String idNumber) {
        return containerRepository.findAllContainersStartingWith(idNumber);
    }

    @Override
    public void deleteContainer(ContainerEntity container) {
        containerRepository.delete(container);
    }

    @Override
    public List<ContainerEntity> findAllContainers() {
        return containerRepository.findAll();
    }

    @Override
    public void save( ContainerEntity container){
        containerRepository.save(container);
    }

}
