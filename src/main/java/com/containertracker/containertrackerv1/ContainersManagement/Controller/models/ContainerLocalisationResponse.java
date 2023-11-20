package com.containertracker.containertrackerv1.ContainersManagement.Controller.models;

import com.containertracker.containertrackerv1.ContainersManagement.Entity.ContainerEntity;
import com.containertracker.containertrackerv1.ContainersManagement.Entity.Localisation;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContainerLocalisationResponse {

    private ContainerEntity container;

    private Localisation localisation;
}
