/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import com.mycompany.assignment.enums.ServiceType;
import com.mycompany.assignment.enums.UserRole;
import java.io.IOException;

/**
 *
 * @author arifi
 */
public class Doctor extends User {

    public Doctor(String userId, String fullName, String email, String phoneNumber, String password, boolean active) {
        super(userId, fullName, email, phoneNumber, password, active);
    }

    public Doctor(String fullName, String email, String phoneNumber, String password, boolean active) {
        super(fullName, email, phoneNumber, password, active);
    }

    @Override
    public UserRole getRole() {
        return UserRole.DOCTOR;
    }

    public boolean createServiceRequest(String consultationId, ServiceType requestType, String reason) {
        try {
            MedicalServiceRequest request = new MedicalServiceRequest(getUserId(), consultationId, requestType, reason);
            request.addMedicalServiceRequest();

            return true;
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error creating medical service request: " + e.getMessage());
            return false;
        }
    }
}
