package com.containertracker.containertrackerv1.ContainersManagement.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@Builder
@Entity(name = "container")
@AllArgsConstructor
@NoArgsConstructor
public class ContainerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String identificationNumber;
    private String sizeAndTypeCode;
    private double maximumGrossWeight_Kg;
    private double maximumGrossWeight_Lbs;
    private double tareWeight_Kg;
    private double tareWeight_Lbs;
    private double maximumPayload_Kg;
    private double maximumPayload_Lbs;
    private double capacity_CUM;
    private double capacity_CUFT;
    private boolean archived;
    private byte[] image;

    public String toLogString(){
        String output = "Identification number: "+identificationNumber+".\n"
                        +"Size and type code: "+sizeAndTypeCode + ".\n"
                        +"Maximum gross weight: "+maximumGrossWeight_Kg+" kg/ "
                        +maximumGrossWeight_Lbs+" Lbs.\n"
                        +"Tare weight: "+tareWeight_Kg+" Kg/ "+tareWeight_Lbs+" Lbs.\n"
                        +"Maximum pauload: "+maximumPayload_Kg+" Kg/ "
                        +maximumPayload_Lbs+" Lbs.\n"
                        +"Capacity: "+capacity_CUM+" CUM/ "+capacity_CUFT+" CUFT.\n";
        return output;
    }
}
