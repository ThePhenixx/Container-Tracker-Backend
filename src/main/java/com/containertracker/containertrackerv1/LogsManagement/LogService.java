package com.containertracker.containertrackerv1.LogsManagement;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.ContainerEntity;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.Localisation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {

    private final LogsRepository logsRepository;

    public void CreateLog(User user, ContainerEntity container, Localisation localisation){
        String text = "Created container: " + container.toLogString()
                        + "With the localisation: " + localisation.toLogString();
        LogEntity logEntity = LogEntity.builder()
                .type("create")
                .text(text)
                .user(user)
                .container(container)
                .localisation(localisation)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void UpdateLog(User user, ContainerEntity container){
        String text = "Updated container: " + container.toLogString();
        LogEntity logEntity = LogEntity.builder()
                .type("update data")
                .text(text)
                .user(user)
                .container(container)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }
    public void UpdateLocalisationLog(User user, ContainerEntity container, Localisation localisation){
        String text = "Updated container: " + container.toLogString()
                        +"From: "+localisation.toLogString();
        LogEntity logEntity = LogEntity.builder()
                .type("update localisation")
                .text(text)
                .user(user)
                .container(container)
                .localisation(localisation)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void archiveContainerLog(User user, ContainerEntity container){
        String text = "Archived container: " + container.toLogString();
        LogEntity logEntity = LogEntity.builder()
                .type("archive container")
                .text(text)
                .user(user)
                .container(container)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void retrieveContainerLog(User user, ContainerEntity container){
        String text = "Retrieved container: " + container.toLogString();
        LogEntity logEntity = LogEntity.builder()
                .type("retrieve container")
                .text(text)
                .user(user)
                .container(container)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void searchContainer(User user, String search){
        String text = "Search for: " + search;
        LogEntity logEntity = LogEntity.builder()
                .type("search")
                .text(text)
                .user(user)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void searchArchivedContainer(User user, String search){
        String text = "Search in archive for: " + search;
        LogEntity logEntity = LogEntity.builder()
                .type("search in archive")
                .user(user)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void accessContainer(User user, ContainerEntity container){
        String text = "Checked container: " + container.toLogString();
        LogEntity logEntity = LogEntity.builder()
                .type("access container")
                .text(text)
                .user(user)
                .container(container)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void searchInTimeIntervalContainer(User user, LocalDate D1, LocalDate D2) {
        String text = "Searched containers between: " + D1.toString() + " AND " + D2.toString();
        LogEntity logEntity = LogEntity.builder()
                .type("search container")
                .text(text)
                .user(user)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void searchInTimeIntervalArchivedContainer(User user, LocalDate D1, LocalDate D2){
        String text = "Searched archived containers between: " + D1.toString() + " AND " + D2.toString();
        LogEntity logEntity = LogEntity.builder()
                .text(text)
                .type("search in archive container")
                .user(user)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }


    public void loggedInUser(User user){
        String text = "Logged in successfully.";
        LogEntity logEntity = LogEntity.builder()
                .type("login")
                .text(text)
                .user(user)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void loggedInFailedUser(User user){
        String text = "Login trial failed due to wrong credentials";
        LogEntity logEntity = LogEntity.builder()
                .type("login failed")
                .text(text)
                .user(user)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void loggedOutUser(User user){
        String text = "Logged out successfully.";
        LogEntity logEntity = LogEntity.builder()
                .type("logout")
                .text(text)
                .user(user)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void passwordRecoveryDemand(User user) {
        String text = "Password recovery demande has been made.";
        LogEntity logEntity = LogEntity.builder()
                .user(user)
                .text(text)
                .type("password recovery demande")
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void passwordChange(User user){
        String text = "Password email has been sent successfully.";
        LogEntity logEntity = LogEntity.builder()
                .user(user)
                .text(text)
                .type("password recovery demande")
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void searchUser(User user, String search){
        String text = "Search for: " + search;
        LogEntity logEntity = LogEntity.builder()
                .type("search user")
                .text(text)
                .user(user)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void searchArchivedUser(User user, String search){
        String text = "Search in archive for: " + search;
        LogEntity logEntity = LogEntity.builder()
                .type("search user in archive")
                .text(text)
                .user(user)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void accessUser(User admin, User user){
        if(admin.getRegistrationNumber() != user.getRegistrationNumber()){
            String text = "Checked user: " + user.getRegistrationNumber()+" in the name of: "
                    +user.getFirstname() + " " + user.getLastname();
            LogEntity logEntity = LogEntity.builder()
                    .type("access user")
                    .text(text)
                    .user(admin)
                    .date(new Date())
                    .build();
            logsRepository.save(logEntity);
        }
    }

    public void archiveUserLog(User admin, User user){
        String text = "Archived user: " + user.getRegistrationNumber()+" in the name of: "
                +user.getFirstname() + " " + user.getLastname();
        LogEntity logEntity = LogEntity.builder()
                .type("archive user")
                .text(text)
                .user(admin)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public void retrieveUserLog(User admin, User user){
        String text = "Retrieved user: " + user.getRegistrationNumber()+" in the name of: "
                +user.getFirstname() + " " + user.getLastname();
        LogEntity logEntity = LogEntity.builder()
                .type("retrieve user")
                .text(text)
                .user(admin)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }


    public void UpdateUserLog(User admin, User user){
        String text = "Updated user: " + user.getRegistrationNumber()+" From: "
                        +user.toLogString();
        LogEntity logEntity = LogEntity.builder()
                .type("update user")
                .text(text)
                .user(admin)
                .date(new Date())
                .build();
        logsRepository.save(logEntity);
    }

    public List<LogEntity> logsByUser(User user){
        return logsRepository.findByUser(user);
    }
}
