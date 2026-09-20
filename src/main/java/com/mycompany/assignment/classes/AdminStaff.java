/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import com.mycompany.assignment.enums.AssetStatus;
import com.mycompany.assignment.enums.AssetType;
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

                    UserRole role = UserRole.valueOf(data[5]);

                    boolean active = Boolean.parseBoolean(data[6]);

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

                UserRole role = UserRole.valueOf(data[5]);

                boolean active = Boolean.parseBoolean(data[6]);

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
            System.out.println("Error generating User ID: " + e.getMessage());
        }

        int nextId = highestId + 1;

        return String.format("%03d", nextId);
    }

    public ArrayList<InsuranceNetwork> getInsuranceNetworks() {
        try {
            return InsuranceNetwork.getAllInsuranceNetworks();
        } catch (IOException e) {
            System.out.println("Error reading insurance networks: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean createInsuranceNetwork(String providerName, double coverageRate, boolean accepted) {
        try {
            InsuranceNetwork insurance = new InsuranceNetwork(
                    providerName,
                    coverageRate,
                    accepted
            );

            insurance.addInsuranceNetwork();

            return true;
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error creating insurance network: " + e.getMessage());
            return false;
        }
    }

    public boolean updateInsuranceNetwork(String insuranceId, String providerName, double coverageRate, boolean accepted) {
        try {
            InsuranceNetwork insurance = new InsuranceNetwork(
                    insuranceId,
                    providerName,
                    coverageRate,
                    accepted
            );

            insurance.updateDetails(
                    providerName,
                    coverageRate,
                    accepted
            );

            return true;

        } catch (IOException | IllegalArgumentException e) {
            System.out.println(
                    "Error updating insurance network: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public ArrayList<CheckUpType> getCheckUpTypes() {
        try {
            return CheckUpType.getAllCheckUpTypes();
        } catch (IOException e) {
            System.out.println("Error reading check up types: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean createCheckUpType(String name, String description, double baseRate, int duration) {
        try {
            CheckUpType checkUpType = new CheckUpType(
                    name,
                    description,
                    baseRate,
                    duration
            );

            checkUpType.insertFile();

            return true;
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error creating check up type: " + e.getMessage());
            return false;
        }
    }

    public boolean updateCheckUpType(String checkUpTypeId, String name, String description, double baseRate, int duration, boolean active) {
        try {
            CheckUpType checkUpType = new CheckUpType(
                    checkUpTypeId,
                    name,
                    description,
                    baseRate,
                    duration,
                    active
            );

            checkUpType.updateDetails(
                    name,
                    description,
                    baseRate,
                    duration,
                    active
            );

            return true;
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error updating check up type: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<HospitalAsset> getHospitalAssets() {
        try {
            return HospitalAsset.getAllHospitalAssets();
        } catch (IOException e) {
            System.out.println("Error reading hospital assets: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean createHospitalAsset(
            String assetName,
            AssetType assetType,
            AssetStatus status,
            String departmentId
    ) {
        try {
            HospitalAsset asset = new HospitalAsset(
                    assetName,
                    assetType,
                    status,
                    departmentId
            );

            asset.addHospitalAsset();

            return true;
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error creating hospital asset: " + e.getMessage());
            return false;
        }
    }

    public boolean updateHospitalAsset(
            String assetId,
            String assetName,
            AssetType assetType,
            AssetStatus status,
            String departmentId
    ) {
        try {
            HospitalAsset asset = new HospitalAsset(
                    assetId,
                    assetName,
                    assetType,
                    status,
                    departmentId
            );

            asset.updateDetails(
                    assetName,
                    status
            );

            return true;

        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error updating hospital asset: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteHospitalAsset(
            String assetId,
            String assetName,
            AssetType assetType,
            AssetStatus status,
            String departmentId
    ) {
        try {
            HospitalAsset asset = new HospitalAsset(
                    assetId,
                    assetName,
                    assetType,
                    status,
                    departmentId
            );

            asset.deleteHospitalAsset();

            return true;

        } catch (IOException e) {
            System.out.println("Error deleting hospital asset: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Department> getDepartments() {
        try {
            return Department.getAllDepartments();
        } catch (IOException e) {
            System.out.println("Error reading departments: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean allocateHospitalAsset(
            String assetId,
            String assetName,
            AssetType assetType,
            AssetStatus status,
            String currentDepartmentId,
            Department department
    ) {
        try {
            HospitalAsset asset = new HospitalAsset(
                    assetId,
                    assetName,
                    assetType,
                    status,
                    currentDepartmentId
            );

            asset.allocateTo(department);

            return true;
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error allocating hospital asset: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Doctor> getDoctors() {
        ArrayList<Doctor> doctorList = new ArrayList<>();
        ArrayList<User> users = getUsers();

        for (User user : users) {
            if (user instanceof Doctor) {
                doctorList.add((Doctor) user);
            }
        }

        return doctorList;
    }

    public ArrayList<MedicalManager> getMedicalManagers() {
        ArrayList<MedicalManager> managerList = new ArrayList<>();
        ArrayList<User> users = getUsers();

        for (User user : users) {
            if (user instanceof MedicalManager) {
                managerList.add((MedicalManager) user);
            }
        }

        return managerList;
    }

    public boolean assignDoctor(String doctorId, String medicalManagerId) {
        File file = new File("DoctorAssignment.txt");

        try {
            // Check whether doctor is already assigned
            if (file.exists()) {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                String line;

                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    String[] data = line.split(",");

                    if (data[0].equals(doctorId)) {
                        br.close();
                        fr.close();

                        System.out.println("Doctor is already assigned.");

                        return false;
                    }
                }

                br.close();
                fr.close();
            }

            FileWriter fw = new FileWriter("DoctorAssignment.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(doctorId + "," + medicalManagerId);
            bw.newLine();

            bw.close();
            fw.close();

            return true;
        } catch (IOException e) {
            System.out.println("Error assigning doctor: " + e.getMessage());
            return false;
        }
    }

    public boolean unassignDoctor(String doctorId) {
        File originalFile = new File("DoctorAssignment.txt");

        if (!originalFile.exists()) {
            return false;
        }

        File tempFile = new File("DoctorAssignment_temp.txt");
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

                if (data[0].equals(doctorId)) {
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

            return found;

        } catch (IOException e) {
            System.out.println("Error unassigning doctor: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<String[]> getDoctorAssignments() {
        ArrayList<String[]> assignmentList = new ArrayList<>();
        File file = new File("DoctorAssignment.txt");

        if (!file.exists()) {
            return assignmentList;
        }

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data.length >= 2) {
                    assignmentList.add(
                            new String[]{
                                data[0],
                                data[1]
                            }
                    );
                }
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Error reading doctor assignments: " + e.getMessage());
        }

        return assignmentList;
    }

    public ArrayList<MedicalServiceRequest> getMedicalServiceRequests() {
        try {
            return MedicalServiceRequest.getAllMedicalServiceRequests();
        } catch (IOException e) {
            System.out.println("Error reading medical service requests: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public MedicalServiceRequest getMedicalServiceRequest(String requestId) {
        try {
            return MedicalServiceRequest.getMedicalServiceRequest(requestId);
        } catch (IOException e) {
            System.out.println("Error reading medical service request: " + e.getMessage());
            return null;
        }
    }

    public boolean approveServiceRequest(String requestId, HospitalAsset asset) {
        try {
            MedicalServiceRequest request = MedicalServiceRequest.getMedicalServiceRequest(requestId);

            if (request == null) {
                return false;
            }

            request.assignAsset(asset);
            request.approve();

            return true;
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error approving service request: " + e.getMessage());
            return false;
        }
    }

    public boolean rejectServiceRequest(String requestId) {
        try {
            MedicalServiceRequest request = MedicalServiceRequest.getMedicalServiceRequest(requestId);

            if (request == null) {
                return false;
            }

            request.reject();
            return true;
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error rejecting service request: " + e.getMessage());
            return false;
        }
    }

    public boolean recordServiceResult(String requestId, String result, double serviceFee) {
        try {
            MedicalServiceRequest request = MedicalServiceRequest.getMedicalServiceRequest(requestId);

            if (request == null) {
                return false;
            }

            request.recordResult(result, serviceFee);
            return true;
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error recording service result: " + e.getMessage());
            return false;
        }
    }
}
