package com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.AuthEntity;

import com.containertracker.containertrackerv1.AuthenticationSecurityConfiguration.Controller.ControllerServices.UserUpdateRequest;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

//    @Query("SELECT U FROM userAccount U ORDER BY U.registrationNumber")
//    Page<User> findAll(PageRequest pageRequest);

    @Query("SELECT U FROM userAccount U ORDER BY U.registrationNumber ")
    List<User> findAllUsers();
    Optional<User> findByRegistrationNumber(String registrationNumber);

    @Query("SELECT DISTINCT U From userAccount U WHERE (U.registrationNumber LIKE :user%) OR (U.firstname LIKE :user%) OR (U.lastname LIKE :user%) ORDER BY U.registrationNumber ")
    List<User> findUsers(@Param("user") String user);

    Optional<User> findByEmail(String email);

//    @Query("UPDATE userAccount U SET U.archived=true WHERE U.registrationNumber LIKE :registrationNumber ")
//    void archiveUser(@Param("registrationNumber") String registrationNumber);
//
//    @Query("UPDATE userAccount U SET U.archived=false WHERE U.registrationNumber LIKE :registrationNumber ")
//    void retrieveUser(@Param("registrationNumber") String registrationNumber);

    @Query("SELECT U FROM userAccount U WHERE U.archived = false ORDER BY U.registrationNumber")
    List<User> findAllRetrievedUsers();

    @Query("SELECT U FROM userAccount U WHERE U.archived = true ORDER BY U.registrationNumber")
    List<User> findAllArchivedUsers();

    @Query("SELECT DISTINCT U FROM userAccount U WHERE (U.archived = false) AND ((U.registrationNumber LIKE :id%) OR (U.firstname LIKE :id%) OR (U.lastname LIKE :id%)) ORDER BY U.registrationNumber")
    List<User> findRetrievedUsers(@Param("id") String id);

    @Query("SELECT DISTINCT U FROM userAccount U WHERE (U.archived = true) AND ((U.registrationNumber LIKE :id%) OR (U.firstname LIKE :id%) OR (U.lastname LIKE :id%)) ORDER BY U.registrationNumber")
    List<User> findArchivedUsers(@Param("id") String id);

//    @Query("UPDATE userAccount U SET U.email= :email, U.phonenumber= :phonenumber, U.isAccountNonExpired= :accountNonExpired, U.isAccountNonLocked= :accountNonLocked, U.isCredentialsNonExpired= :credentialsNonExpired, U.isEnabled= :enabled, U.role= :role WHERE U.registrationNumber= :registrationNumber")
//    void updateUserBy( @Param("registrationNumber") String registrationNumber, @Param("email") String email, @Param("phonenumber") String phonenumber,@Param("accountNonExpired") boolean accountNonExpired, @Param("accountNonLocked") boolean accountNonLocked, @Param("credentialsNonExpired") boolean credentialsNonExpired, @Param("enabled") boolean enabled, @Param("role") Role role);
}
