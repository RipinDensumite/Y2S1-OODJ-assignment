/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import com.mycompany.assignment.classes.Appointment;
import com.mycompany.assignment.classes.Feedback;
import com.mycompany.assignment.classes.MedicalRecord;
import com.mycompany.assignment.classes.User;
import com.mycompany.assignment.classes.doctorShift;
import com.mycompany.assignment.enums.UserRole;

import java.time.LocalDate;
import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author update for xuheng later
 */
public class Patient extends User {
    private LocalDate dateOfBirth;
    private String gender;
    public Patient(String userId, String fullName, String email, String phoneNumber, String password, boolean active) {
        super(userId, fullName, email, phoneNumber, password, active);
    }

    public Patient(String fullName, String email, String phoneNumber, String password, boolean active) {
        super(fullName, email, phoneNumber, password, active);
    }
    
    public Patient(String userID, String fullName, String email,String phoneNumber,String password,boolean active,LocalDate DOB, String gender){
        super(userID,fullName,email,phoneNumber,password,active);
        this.dateOfBirth = DOB;
        this.gender = gender;
    }

    @Override
    public UserRole getRole() {
        return UserRole.PATIENT;
    }
    
    public String getGender(){
        return gender;
    }
    
    public LocalDate getDOB(){
        return dateOfBirth;
    }
    
    public void updateProfile() throws IOException{
        FileReader fr = new FileReader("accountlist.txt");
        BufferedReader br = new BufferedReader(fr);
        ArrayList<String[]> accounts = new ArrayList<>();
        String allData = "";
        String line;
        
        while ((line = br.readLine()) != null) {
            accounts.add(line.split(","));
            String[] data = line.split(",");
            if (data[0].equals(this.getUserId())){
                data[1] = this.getFullName();
                data[2] = this.getEmail();
                data[3] = this.getPhoneNumber();
                
                line = String.join(",", data);
            }
            
            allData += line + "\n";
            }
        br.close();
        fr.close();
        
        FileWriter fw = new FileWriter("accountlist.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(allData);
            bw.close();
            fw.close();
        }
    
    public void updatePassword() throws FileNotFoundException, IOException{
        FileReader fr = new FileReader("accountlist.txt");
        BufferedReader br = new BufferedReader(fr);
        ArrayList<String[]> accounts = new ArrayList<>();
        String allData = "";
        String line;
        
        while ((line = br.readLine()) != null) {
            accounts.add(line.split(","));
            String[] data = line.split(",");
            if (data[0].equals(this.getUserId())){
                data[4] = this.getPassword();
                
                line = String.join(",", data);
            }
            
            allData += line + "\n";
            }
        br.close();
        fr.close();
        
        FileWriter fw = new FileWriter("accountlist.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(allData);
            bw.close();
            fw.close();
    }
    
    public void bookAppointment(Appointment appointment) throws IOException{
        appointment.schedule();
    }
    
    
    
    public void rescheduleAppointment(Appointment appointment,LocalDate newDate,LocalTime StartTime, LocalTime EndTime){
        appointment.reschedule(newDate,StartTime,EndTime);
    }
    
    public void cancelAppointment(Appointment appointment){
        appointment.cancel();
    }
    
    public void viewMedicalHistory() throws IOException{
        MedicalRecord.viewConsultationHistory();
    }
    
    public ArrayList<String[]> viewPrescriptions(String ID) throws IOException{
        ArrayList<String[]> prescriptions = new ArrayList<>();

        FileReader fr = new FileReader("PatientPrescriptions.txt");
        BufferedReader br = new BufferedReader(fr);

        String line;

        while ((line = br.readLine()) != null) {
            String[] value = line.split(",");

            if (value[1].equals(ID)) {
                String[] data = {
                    value[2],
                    value[3],
                    value[4],
                    value[5]
                };

                prescriptions.add(data);
            }
        }

            br.close();
            fr.close();

            return prescriptions;
    }
        
    
            
    
    
    public void submitFeedback(int Rating, String Comment,String doctorID) throws IOException{
        Feedback F1 = new Feedback(this.getUserId(), Rating, Comment,doctorID);
        F1.submit();
    }
    
    public void editFeedback(String FeedbackId, String Comment){
        Feedback.editComment(FeedbackId,Comment);
    }
    
    public static List<doctorShift> viewAvailableSlots() throws FileNotFoundException, IOException{
        List<doctorShift> availableSlots = new ArrayList<>();
        FileReader fr = new FileReader("doctorShift.txt");
        BufferedReader br = new BufferedReader(fr);

        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");

            if (data[5].equals("SCHEDULED")) {
                    doctorShift shift = new doctorShift(data[0]);
                    availableSlots.add(shift);
                }
            }
        

        br.close();
        fr.close();
        return availableSlots;

    }}



