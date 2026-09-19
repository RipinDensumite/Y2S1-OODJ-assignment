/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import com.mycompany.assignment.enums.UserRole;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author arifi
 */
public abstract class User {
    private String userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private boolean active;

    protected User(
            String userId,
            String fullName,
            String email,
            String phoneNumber,
            String password,
            boolean active
    ) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.active = active;
    }

    public void editProfile(
            String fullName,
            String email,
            String phoneNumber
    ) {
        if (email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }

        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public boolean updateProfile(
            String fullName,
            String email,
            String phoneNumber
    ) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;

        return true;
    }

    public boolean changePassword(
            String oldPassword,
            String newPassword
    ) {
        if (!this.password.equals(oldPassword)) {
            return false;
        }

        this.password = newPassword;
        return true;
    }

    public String[] getProfile() {

        String[] profile = {
            userId,
            fullName,
            email,
            password,
            phoneNumber,
            getRole().toString(),
            String.valueOf(active)
        };

        return profile;
    }

    public boolean login(
            String username,
            String password
    ) throws FileNotFoundException, IOException {

        FileReader fr = new FileReader("accountlist.txt");
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String[]> accounts = new ArrayList<>();

        String line;

        while ((line = br.readLine()) != null) {
            accounts.add(line.split(","));
        }

        br.close();

        int i = 0;

        while (i < accounts.size()) {

            if (accounts.get(i)[1].equals(username)) {

                if (accounts.get(i)[3].equals(password)) {

                    if (accounts.get(i)[6].equals("true")) {
                        return true;
                    }
                }
            }

            i++;
        }

        return false;
    }

    public void logout() {
        this.userId = "";
        this.fullName = "";
        this.email = "";
        this.phoneNumber = "";
        this.password = "";
        this.active = false;
    }

    public String getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }

    public abstract UserRole getRole();
}