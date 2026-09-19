/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import com.mycompany.assignment.enums.UserRole;

/**
 *
 * @author arifi
 */
public class MedicalManager extends User {
    public MedicalManager(String userId, String fullName, String email, String phoneNumber, String password, boolean active) {
        super(userId, fullName, email, phoneNumber, password, active);
    }
    
    public MedicalManager(String fullName, String email, String phoneNumber, String password, boolean active) {
        super(fullName, email, phoneNumber, password, active);
    }

    @Override
    public UserRole getRole() {
        return UserRole.MEDICAL_MANAGER;
    }
}
