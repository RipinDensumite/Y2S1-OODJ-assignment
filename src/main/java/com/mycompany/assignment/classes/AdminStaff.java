/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import com.mycompany.assignment.enums.UserRole;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author arifi
 */
public class AdminStaff extends User {
    public AdminStaff(String userId, String fullName, String email, String phoneNumber, String password, boolean active) {
        super(userId, fullName, email, phoneNumber, password, active);
    }

    public AdminStaff(String fullName, String email, String phoneNumber, String password, boolean active) {
        super(fullName, email, phoneNumber, password, active);
    }

    @Override
    public UserRole getRole() {
        return UserRole.ADMIN;
    }

    public boolean createUser(User user) {
        if (user == null) {
            return false;
        }

        String newUserId = generateNextUserId();
        user.setUserId(newUserId);

        try {
            FileWriter fw = new FileWriter("accountlist.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(
                    user.getUserId() + ","
                    + user.getFullName() + ","
                    + user.getEmail() + ","
                    + user.getPhoneNumber() + ","
                    + user.getPassword() + ","
                    + user.getRole() + ","
                    + user.isActive()
            );

            bw.newLine();

            bw.close();
            fw.close();

            return true;
        } catch (IOException e) {
            System.out.println("Error creating user: " + e.getMessage());
            return false;
        }
    }

    public User getUser(String userId) {
        try {
            FileReader fr = new FileReader("accountlist.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data[0].equals(userId)) {

                    String id = data[0];
                    String fullName = data[1];
                    String email = data[2];
                    String phoneNumber = data[3];
                    String password = data[4];

                    UserRole role
                            = UserRole.valueOf(data[5]);

                    boolean active
                            = Boolean.parseBoolean(data[6]);

                    br.close();
                    fr.close();

                    switch (role) {
                        case ADMIN:
                            return new AdminStaff(
                                    id,
                                    fullName,
                                    email,
                                    phoneNumber,
                                    password,
                                    active
                            );

                        case DOCTOR:
                            return new Doctor(
                                    id,
                                    fullName,
                                    email,
                                    phoneNumber,
                                    password,
                                    active
                            );

                        case PATIENT:
                            return new Patient(
                                    id,
                                    fullName,
                                    email,
                                    phoneNumber,
                                    password,
                                    active
                            );

                        case MEDICAL_MANAGER:
                            return new MedicalManager(
                                    id,
                                    fullName,
                                    email,
                                    phoneNumber,
                                    password,
                                    active
                            );
                    }
                }
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Error reading user: " + e.getMessage());
        }

        return null;
    }

    public boolean updateUser(User user) {
        if (user == null) {
            return false;
        }

        File originalFile = new File("accountlist.txt");
        File tempFile = new File("accountlist_temp.txt");

        try {
            FileReader fr = new FileReader(originalFile);
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);

            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data[0].equals(user.getUserId())) {

                    bw.write(
                            user.getUserId() + ","
                            + user.getFullName() + ","
                            + user.getEmail() + ","
                            + user.getPhoneNumber() + ","
                            + user.getPassword() + ","
                            + user.getRole() + ","
                            + user.isActive()
                    );

                } else {

                    bw.write(line);
                }
                bw.newLine();
            }

            br.close();
            fr.close();

            bw.close();
            fw.close();

            originalFile.delete();
            tempFile.renameTo(originalFile);

            return true;
        } catch (IOException e) {
            System.out.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            FileReader fr = new FileReader("accountlist.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                String id = data[0];
                String fullName = data[1];
                String email = data[2];
                String phoneNumber = data[3];
                String password = data[4];

                UserRole role
                        = UserRole.valueOf(data[5]);

                boolean active
                        = Boolean.parseBoolean(data[6]);

                User user = null;

                switch (role) {
                    case ADMIN:
                        user = new AdminStaff(
                                id,
                                fullName,
                                email,
                                phoneNumber,
                                password,
                                active
                        );
                        break;

                    case DOCTOR:
                        user = new Doctor(
                                id,
                                fullName,
                                email,
                                phoneNumber,
                                password,
                                active
                        );
                        break;

                    case PATIENT:
                        user = new Patient(
                                id,
                                fullName,
                                email,
                                phoneNumber,
                                password,
                                active
                        );
                        break;

                    case MEDICAL_MANAGER:
                        user = new MedicalManager(
                                id,
                                fullName,
                                email,
                                phoneNumber,
                                password,
                                active
                        );
                        break;
                }

                if (user != null) {
                    userList.add(user);
                }
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Error reading users: " + e.getMessage());
        }

        return userList;
    }

    public boolean deleteUser(String userId) {
        File originalFile = new File("accountlist.txt");
        File tempFile = new File("accountlist_temp.txt");
        boolean found = false;

        try {
            FileReader fr = new FileReader(originalFile);
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);

            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data[0].equals(userId)) {
                    found = true;
                    continue;
                }

                bw.write(line);
                bw.newLine();
            }

            br.close();
            fr.close();

            bw.close();
            fw.close();

            originalFile.delete();
            tempFile.renameTo(originalFile);
        } catch (IOException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }

        return found;
    }

    public String generateNextUserId() {

        int highestId = 0;

        try {
            FileReader fr = new FileReader("accountlist.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                try {
                    int currentId = Integer.parseInt(data[0]);

                    if (currentId > highestId) {
                        highestId = currentId;
                    }

                } catch (NumberFormatException e) {
                    // Ignore invalid IDs
                }
            }

            br.close();
            fr.close();

        } catch (IOException e) {
            System.out.println(
                    "Error generating User ID: "
                    + e.getMessage()
            );
        }

        int nextId = highestId + 1;

        return String.format("%03d", nextId);
    }
}
