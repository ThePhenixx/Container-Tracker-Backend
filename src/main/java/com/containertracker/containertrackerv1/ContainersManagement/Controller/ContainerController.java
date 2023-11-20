package com.containertracker.containertrackerv1.ContainersManagement.Controller;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.UserRepository;
import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Security.Jwt.JwtService;
import com.containertracker.containertrackerv1.LogsManagement.LogService;
import com.containertracker.containertrackerv1.ContainersManagement.Controller.models.ContainerLocalisationRequest;
import com.containertracker.containertrackerv1.ContainersManagement.Controller.models.ContainerLocalisationResponse;
import com.containertracker.containertrackerv1.ContainersManagement.Controller.models.ContainerRequest;
import com.containertracker.containertrackerv1.ContainersManagement.Controller.models.LocalisationRequest;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.ContainerEntity;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.Localisation;
import com.containertracker.containertrackerv1.ContainersManagement.Service.ContainerService;
import com.containertracker.containertrackerv1.ContainersManagement.Service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/container")
@RequiredArgsConstructor
@Slf4j

public class ContainerController {

    private final ContainerService containerService;

    private final LocationService locationService;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final LogService logService;


    @GetMapping("container-and-localisation/all-archived-containers")
    public List<ContainerLocalisationResponse> findArchivedContainerAndLocalisations(){
        List<ContainerEntity> containers =  containerService.findAllArchivedContainers();
        List<ContainerLocalisationResponse> response = new ArrayList<>();
        for(ContainerEntity container : containers){
            Localisation localisation = locationService.getLastLocalisationByContainer(container);
            ContainerLocalisationResponse object = ContainerLocalisationResponse.builder()
                    .container(container)
                    .localisation(localisation)
                    .build();
            response.add(object);
        }
        return response;
    }

    @GetMapping("container-and-localisation/all-retirieved-containers")
    public List<ContainerLocalisationResponse> findAllRetrievedContainerAndLocalisations(){
        List<ContainerEntity> containers =  containerService.findAllRetrievedContainers();
        List<ContainerLocalisationResponse> response = new ArrayList<>();
        for(ContainerEntity container : containers){
            Localisation localisation = locationService.getLastLocalisationByContainer(container);
            ContainerLocalisationResponse object = ContainerLocalisationResponse.builder()
                    .container(container)
                    .localisation(localisation)
                    .build();
            response.add(object);
        }
        return response;
    }

    @GetMapping("container-and-localisation/archived/{idStartingWith}")
    public List<ContainerLocalisationResponse> findAllArchivedContainerAndLocalisations(@PathVariable("idStartingWith") String id, HttpServletRequest httpRequest){
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber = jwtService.extractRegistrationNumber(token);
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if(user0.isPresent()){
            User user = user0.get();
            List<ContainerEntity> containers =  containerService.findArchivedContainers(id, user);
            List<ContainerLocalisationResponse> response = new ArrayList<>();
            for(ContainerEntity container : containers){
                Localisation localisation = locationService.getLastLocalisationByContainer(container);
                ContainerLocalisationResponse object = ContainerLocalisationResponse.builder()
                        .container(container)
                        .localisation(localisation)
                        .build();
                response.add(object);
            }
            return response;
        }
        return null;
    }

    @GetMapping("container-and-localisation/retirieved/{idStartingWith}")
    public List<ContainerLocalisationResponse> findRetrievedContainerAndLocalisations(@PathVariable("idStartingWith") String id, HttpServletRequest httpRequest){
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber = jwtService.extractRegistrationNumber(token);
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if(user0.isPresent()){
            User user = user0.get();
            List<ContainerEntity> containers =  containerService.findRetrievedContainers(id, user);
            List<ContainerLocalisationResponse> response = new ArrayList<>();
            for(ContainerEntity container : containers){
                Localisation localisation = locationService.getLastLocalisationByContainer(container);
                ContainerLocalisationResponse object = ContainerLocalisationResponse.builder()
                        .container(container)
                        .localisation(localisation)
                        .build();
                response.add(object);
            }
            return response;
        }
        return null;
    }


    @GetMapping("/container-and-localisation/interval/archived/{firstDate}/{lastDate}")
    public List<ContainerLocalisationResponse> findArchivedInTimeInterval(@PathVariable("firstDate") LocalDate firstDate, @PathVariable("lastDate") LocalDate lastDate, HttpServletRequest httpRequest){
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber = jwtService.extractRegistrationNumber(token);
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if(user0.isPresent()){
            User user = user0.get();
            List<ContainerEntity> containers =  locationService.findArchivedInTimeInterval(firstDate, lastDate, user);
            List<ContainerLocalisationResponse> response = new ArrayList<ContainerLocalisationResponse>();
            for (ContainerEntity container : containers){
                Localisation localisation = locationService.getLastLocalisationByContainer(container);
                ContainerLocalisationResponse object = ContainerLocalisationResponse.builder()
                        .container(container)
                        .localisation(localisation)
                        .build();
                response.add(object);
            }
        }
        return null;
    }

    @GetMapping("/container-and-localisation/interval/retrieved/{firstDate}/{lastDate}")
    public List<ContainerLocalisationResponse> findRetrievedInTimeInterval(@PathVariable("firstDate") LocalDate firstDate, @PathVariable("lastDate") LocalDate lastDate, HttpServletRequest httpRequest){
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber = jwtService.extractRegistrationNumber(token);
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if(user0.isPresent()){
            User user = user0.get();
            List<ContainerEntity> containers =  locationService.findRetrievedInTimeInterval(firstDate, lastDate, user);
            List<ContainerLocalisationResponse> response = new ArrayList<ContainerLocalisationResponse>();
            for (ContainerEntity container : containers){
                Localisation localisation = locationService.getLastLocalisationByContainer(container);
                ContainerLocalisationResponse object = ContainerLocalisationResponse.builder()
                        .container(container)
                        .localisation(localisation)
                        .build();
                response.add(object);
            }
        }
        return null;
    }

    @GetMapping("/container-and-localisation/updater/archived/{updater}")
    public List<ContainerLocalisationResponse> findArchivedByUpdater(@PathVariable("updater") String updater, HttpServletRequest httpRequest){
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber = jwtService.extractRegistrationNumber(token);
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if(user0.isPresent()){
            User user = user0.get();
            List<ContainerEntity> containers = locationService.findArchivedByUpdater(updater, user);
            List<ContainerLocalisationResponse> response = new ArrayList<>();
            for (ContainerEntity container : containers) {
                Localisation localisation = locationService.getLastLocalisationByContainer(container);
                ContainerLocalisationResponse object = ContainerLocalisationResponse.builder()
                        .container(container)
                        .localisation(localisation)
                        .build();
                response.add(object);
            }
        }
        return null;
    }

    @GetMapping("/container-and-localisation/updater/retrieved/{updater}")
    public List<ContainerLocalisationResponse> findRetrievedByUpdater(@PathVariable("updater") String updater, HttpServletRequest httpRequest){
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber = jwtService.extractRegistrationNumber(token);
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if(user0.isPresent()){
            User user = user0.get();
            List<ContainerEntity> containers = locationService.findRetrievedByUpdater(updater, user);
            List<ContainerLocalisationResponse> response = new ArrayList<>();
            for (ContainerEntity container : containers){
                Localisation localisation = locationService.getLastLocalisationByContainer(container);
                ContainerLocalisationResponse object = ContainerLocalisationResponse.builder()
                        .container(container)
                        .localisation(localisation)
                        .build();
                response.add(object);
            }
            return response;
        }
        return null;
    }

    @GetMapping("/archive-container/{id}")
    public void archiveContainer(@PathVariable("id") String id, HttpServletRequest httpRequest){
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber = jwtService.extractRegistrationNumber(token);
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if(user0.isPresent()){
            User user = user0.get();
            ContainerEntity container = containerService.findContainerByIdNumber(id);
            container.setArchived(true);
            containerService.save(container);
            logService.archiveContainerLog(user, container);
        }
    }

    @GetMapping("/retrieve-container/{id}")
    public void retrieveContainer(@PathVariable("id") String id, HttpServletRequest httpRequest){
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber = jwtService.extractRegistrationNumber(token);
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if(user0.isPresent()){
            User user = user0.get();
            ContainerEntity container = containerService.findContainerByIdNumber(id);
            container.setArchived(false);
            containerService.save(container);
            logService.retrieveContainerLog(user, container);
        }
    }

    @PostMapping("/create")
    public void createContainer(@RequestBody ContainerLocalisationRequest request, HttpServletRequest httpRequest) throws Exception {
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber = jwtService.extractRegistrationNumber(token);
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if(user0.isPresent()){
            User user = user0.get();
            String id = request.getContainerRequest().getIdentificationNumber();
            ContainerEntity container0 = containerService.findContainerByIdNumber(id);
            if(container0==null){
                ContainerEntity container = request.getContainerRequest().toContainerEntity();
                containerService.save(container);
                Localisation localisation = request.getLocalisationRequest().toLocalistionEntity(container);
                locationService.save(localisation);
                containerService.createContainer(user, container, localisation);
            }
            else if(container0!=null){
                throw new Exception("Container already exists !");
            }
        }
    }

    @PutMapping("/updateContainerData/{identificationNumber}")
    public void updateContainerData(@RequestBody ContainerRequest request, @PathVariable("identificationNumber") String identificationNumber,  HttpServletRequest httpRequest){
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber = jwtService.extractRegistrationNumber(token);
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if(user0.isPresent()){
            User user = user0.get();
            containerService.updateContainerData(user, identificationNumber, request.toContainerEntity());
        }
    }

    @PutMapping("/updateContainerLocalisation/{identificationNumber}")
    public void updateContainerLocalisation(@RequestBody LocalisationRequest request, @PathVariable("identificationNumber") String identificationNumber, HttpServletRequest httpRequest){
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber = jwtService.extractRegistrationNumber(token);
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if(user0.isPresent()){
            User user = user0.get();
            ContainerEntity container = containerService.findContainerByIdNumber(identificationNumber);
            locationService.createLocalisation(user, container, request.toLocalistionEntity(container));
        }
    }

    @GetMapping("container-and-localisation/{id}")
    public ContainerLocalisationResponse containerAndLocalisation(@PathVariable("id") String id, HttpServletRequest httpRequest){
        String authHeader = httpRequest.getHeader("Authorization");
        final String token = authHeader.substring(7);
        String registrationNumber = jwtService.extractRegistrationNumber(token);
        Optional<User> user0 = userRepository.findByRegistrationNumber(registrationNumber);
        if(user0.isPresent()){
            User user = user0.get();
            ContainerEntity container = containerService.accessContainerByIdNumber(id, user);
            Localisation localisation = locationService.getLastLocalisationByContainer(container);
            ContainerLocalisationResponse response = ContainerLocalisationResponse.builder().container(container).localisation(localisation).build();
            return response;
        }
        return null;
    }

}
