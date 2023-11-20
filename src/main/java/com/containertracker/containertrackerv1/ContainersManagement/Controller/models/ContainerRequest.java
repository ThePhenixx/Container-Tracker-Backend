package com.containertracker.containertrackerv1.ContainersManagement.Controller.models;

import com.containertracker.containertrackerv1.ContainersManagement.Entity.ContainerEntity;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ContainerRequest {
     String identificationNumber;
     String sizeAndTypeCode;
     double maximumGrossWeight_Kg;
     double maximumGrossWeight_Lbs;
     double tareWeight_Kg;
     double tareWeight_Lbs;
     double maximumPayload_Kg;
     double maximumPayload_Lbs;
     double capacity_CUM;
     double capacity_CUFT;

     public ContainerEntity toContainerEntity() {
         return ContainerEntity.builder()
                 .identificationNumber(identificationNumber)
                 .sizeAndTypeCode(sizeAndTypeCode)
                 .maximumGrossWeight_Kg(maximumGrossWeight_Kg)
                 .maximumPayload_Lbs(maximumPayload_Lbs)
                 .tareWeight_Kg(tareWeight_Kg)
                 .tareWeight_Lbs(tareWeight_Lbs)
                 .maximumPayload_Kg(maximumPayload_Kg)
                 .maximumGrossWeight_Lbs(maximumGrossWeight_Lbs)
                 .capacity_CUM(capacity_CUM)
                 .capacity_CUFT(capacity_CUFT)
                 .archived(false)
                 .build();
     }

}