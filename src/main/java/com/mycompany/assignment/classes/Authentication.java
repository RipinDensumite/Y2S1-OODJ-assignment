/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import com.mycompany.assignment.classes.AdminStaff;
import com.mycompany.assignment.classes.User;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author HP
 */
public class Authentication {

    private String email;
    private String password;

    // Store the user who successfully logged in
    private User loggedInUser;

    public Authentication(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String login() throws IOException {

        FileReader fr = new FileReader("accountlist.txt");
        BufferedReader br = new BufferedReader(fr);

        String line;

        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",");

            // Make sure line has enough information
            if (data.length < 7) {
                continue;
            }
            
            String userId = data[0];
            String fullName = data[1];
            String storedEmail = data[2];
            String storedPassword = data[3];
            String phoneNumber = data[4];
            String role = data[5];
            boolean active = Boolean.parseBoolean(data[6]);

            // Check email and password
            if (storedEmail.equals(email)
                    && storedPassword.equals(password)) {

                // User must also be active
                if (!active) {
                    br.close();
                    fr.close();
                    return "FAIL";
                }
                
                System.out.println("ROLE" + role);

                // Create the correct user object
                switch (role) {

                    case "ADMIN":
                        loggedInUser = new AdminStaff(
                                userId,
                                fullName,
                                storedEmail,
                                phoneNumber,
                                storedPassword,
                                active
                        );
                        break;

                    // Add these later when your classes are ready

                    /*
                    case "DOCTOR":
                        loggedInUser = new Doctor(
                                userId,
                                fullName,
                                storedEmail,
                                phoneNumber,
                                storedPassword,
                                active
                        );
                        break;

                    case "PATIENT":
                        loggedInUser = new Patient(
                                userId,
                                fullName,
                                storedEmail,
                                phoneNumber,
                                storedPassword,
                                active
                        );
                        break;

                    case "MANAGER":
                        loggedInUser = new MedicalManager(
                                userId,
                                fullName,
                                storedEmail,
                                phoneNumber,
                                storedPassword,
                                active
                        );
                        break;
                     */
                }

                br.close();
                fr.close();

                return role;
            }
        }

        br.close();
        fr.close();

        return "FAIL";
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
