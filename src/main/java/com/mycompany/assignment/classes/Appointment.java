/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author abel
 */
package com.mycompany.assignment.classes;

import com.mycompany.assignment.enums.AppointmentStatus;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
public class Appointment {
    private String appointmentId;
    private String PatientId;
    private String DoctorId;
    private LocalDate appointmentDate;
    private LocalTime appointmentStartTime;
    private LocalTime appointmentEndTime;
    private AppointmentStatus status;//?
    
    public Appointment(String PatientId,String DoctorId,LocalDate appointmentDate, LocalTime StartTime, LocalTime EndTime) throws IOException{
        this.appointmentId = makeNewId();
        this.PatientId = PatientId;
        this.DoctorId = DoctorId;
        this.CheckTime(StartTime, EndTime);
        this.appointmentDate = appointmentDate;
        this.appointmentStartTime = StartTime;
        this.appointmentEndTime = EndTime;
        this.status = AppointmentStatus.BOOKED;
        
    }
    
    public Appointment(String appointmentId, String PatientId,String DoctorId,LocalDate appointmentDateTime, LocalTime StartTime, LocalTime EndTime) throws IOException{
        this.appointmentId = appointmentId;
        this.PatientId = PatientId;
        this.DoctorId = DoctorId;
        this.appointmentDate = appointmentDateTime;
        this.CheckTime(StartTime, EndTime);
        this.appointmentStartTime = StartTime;
        this.appointmentEndTime = EndTime;
        this.status = AppointmentStatus.BOOKED;
    }
    
    public void schedule() throws IOException{
        FileWriter fw = new FileWriter("AppointmentSchedule.txt",true);
        BufferedWriter bw = new BufferedWriter(fw);
        
        String ScheduleDetails = 
                appointmentId+","+
                PatientId+","+
                DoctorId+","+
                appointmentDate.toString()+","+
                appointmentStartTime.toString()+","+
                appointmentEndTime.toString()+","+
                status;
        bw.write(ScheduleDetails +"\n");
        bw.close();
        fw.close();
    }
    
    public void reschedule(LocalDate newDate,LocalTime StartTime, LocalTime EndTime){
        this.appointmentDate = newDate;
        this.appointmentStartTime = StartTime;
        this.appointmentEndTime = EndTime;
        try{
        String check = appointmentId;
            FileReader fr = new FileReader("AppointmentSchedule.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            String allData = "";
            while((line = br.readLine()) !=null){
                String[] data= line.split(",");

                if(data[0].equals(check)){
                    data[3] = appointmentDate.toString();
                    data[4] = appointmentStartTime.toString();
                    data[5] = appointmentEndTime.toString();
                    line = String.join(",", data);
                }

                allData += line +"\n";
            }

            br.close();
            fr.close();

            FileWriter fw = new FileWriter("AppointmentSchedule.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(allData);
            bw.close();
            fw.close();}
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void cancel(){
        this.status = AppointmentStatus.CANCELLED;
        saveToFile();
    }
    
    public void markCompleted(){
        this.status = AppointmentStatus.COMPLETED;
        saveToFile();
    }
    
    public void saveToFile(){
        String check = appointmentId;
        try{
            FileReader fr = new FileReader("AppointmentSchedule.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            String allData = "";
            while((line = br.readLine()) !=null){
                String[] data= line.split(",");

                if(data[0].equals(check)){
                    data[6] = status.toString();
                    line = String.join(",", data);
                }

                allData += line +"\n";
            }

            br.close();
            fr.close();

            FileWriter fw = new FileWriter("AppointmentSchedule.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(allData);
            bw.close();
            fw.close();
    }catch(Exception e){
        System.out.println(e);
    }
    }
    
    private String makeNewId() throws IOException {
        File file = new File("AppointmentSchedule.txt");
        if (!file.exists()) 
            return "1";
        int highest = 0;
        
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        
        while ((line = br.readLine()) != null) {
                String[] department = line.split(",", -1);
                String number = department[0];
                int id = Integer.parseInt(number.substring(3));
                if (id > highest){
                    highest = id;
                }
        }
        br.close();
        fr.close();
        return String.format("APT%03d",highest + 1);
    }
    
    private void CheckTime(LocalTime StartTime, LocalTime EndTime){
            if(StartTime.isAfter(EndTime)||StartTime.equals(EndTime)){
            throw new IllegalArgumentException("Start time cannot be more than or equal to end time");
        }    
    }
    
}