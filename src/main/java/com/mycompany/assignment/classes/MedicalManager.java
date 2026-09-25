/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import com.mycompany.assignment.enums.UserRole;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author harith
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
    public void createDepartment(String Departmentname,String Specialty,Doctor[] Doctor) throws IOException{
        Department tempdepartment= new Department(Departmentname,Specialty,Doctor);
    }
    public void updateDepartment(String departmentId,String NewDepartmentname,String NewSpecialty) throws IOException{
        Department changeddepartment= new Department(departmentId);
        changeddepartment.updateDetails(NewDepartmentname, NewSpecialty);
    }
    public void createdoctorshift(LocalDate shiftDate, LocalTime startTime,LocalTime endTime, Doctor doctor) throws IOException{
        doctorShift tempdoctorshift=new doctorShift( shiftDate,  startTime, endTime, doctor);
    }
    public void updatedoctorshift(String shiftId,LocalDate newshiftDate, LocalTime newstartTime,LocalTime newendTime, Doctor doctor) throws IOException{
        doctorShift changeddoctorshift= new doctorShift(shiftId);
        changeddoctorshift.updateShift(newshiftDate, newstartTime, newendTime,doctor);
    }    
    public HospitalReport viewhospitalreport(LocalDate starttime,LocalDate endtime) throws IOException{
        HospitalReport HospitalcurrentReport = new HospitalReport(starttime,endtime);
        return HospitalcurrentReport;
    }
    public void assigndoctortodepartment(Doctor doctor, Department department) throws IOException{
        department.addDoctor(doctor);
    } 
    private void saveAccount() throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("accountlist.txt"));
    String allData = "";
    String line;

    while ((line = br.readLine()) != null) {
        String[] data = line.split(",", -1);

        if (data[0].equals(getUserId())) {
            data[1] = getFullName();
            data[2] = getEmail();
            data[3] = getPassword();
            data[4] = getPhoneNumber();

            line = String.join(",", data);
        }

        allData += line + "\n";
    }

    br.close();

    BufferedWriter bw = new BufferedWriter(new FileWriter("accountlist.txt"));
    bw.write(allData);
    bw.close();
}
    @Override
public void editProfile(String fullName, String email, String phoneNumber) {
    try {
        super.editProfile(fullName, email, phoneNumber);
        saveAccount();
    } catch (IOException e) {
        throw new RuntimeException("Failed to save profile.", e);
    }
}
@Override
public boolean changePassword(String oldPassword, String newPassword) {
    boolean changed = super.changePassword(oldPassword, newPassword);

    if (!changed) {
        return false;
    }

    try {
        saveAccount();
    } catch (IOException e) {
        throw new RuntimeException("Failed to save password.", e);
    }

    return true;
}
}
