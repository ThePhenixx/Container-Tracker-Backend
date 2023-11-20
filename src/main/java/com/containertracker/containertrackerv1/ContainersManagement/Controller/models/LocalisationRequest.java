package com.containertracker.containertrackerv1.ContainersManagement.Controller.models;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.ContainerEntity;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.Localisation;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class LocalisationRequest {

    private double latitude;
    private double longitude;
    @Builder.Default
    private Date date = new Date();
    private String updaterFirstame;
    private String updaterLastname;
    private String updaterId;

    public Localisation toLocalistionEntity(ContainerEntity container) {
        return Localisation.builder()
                .latitude(latitude)
                .longitude(longitude)
                .date(date)
                .container(container)
                .updaterFirstname(updaterFirstame)
                .updaterLastname(updaterLastname)
                .updaterId(updaterId)
                .build();
    }
}
