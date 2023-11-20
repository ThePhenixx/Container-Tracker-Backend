package com.containertracker.containertrackerv1.ContainersManagement.Controller.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContainerLocalisationRequest {

    private ContainerRequest containerRequest;

    private LocalisationRequest localisationRequest;
}
