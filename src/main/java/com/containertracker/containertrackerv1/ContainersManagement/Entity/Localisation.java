package com.containertracker.containertrackerv1.ContainersManagement.Entity;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@Builder
@Entity(name = "localisation")
@AllArgsConstructor
@NoArgsConstructor
public class Localisation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double latitude;
    private double longitude;
    private Date date;
    private String updaterFirstname;
    private String updaterLastname;
    private String updaterId;

    @ManyToOne
    @JoinColumn(name = "container")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ContainerEntity container;

    public String toLogString(){
        String output = "Latitude: "+latitude+" and Longitude: "+longitude;

        return output;
    }
}
