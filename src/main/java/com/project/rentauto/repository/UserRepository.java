package com.project.rentauto.repository;

import com.project.rentauto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("SELECT username FROM User WHERE username = :username")
    String checkUsername(@Param("username") String username);

    @Query("SELECT driversLicenseNumber FROM User WHERE driversLicenseNumber = :driversLicenseNumber")
    String checkDriversLicense(@Param("driversLicenseNumber") String driversLicenseNumber);

    @Query("SELECT phone FROM User WHERE phone = :phone")
    String checkPhone(@Param("phone") String phone);

    @Query("SELECT email FROM User WHERE email = :email")
    String checkEmail(@Param("email") String email);

}
