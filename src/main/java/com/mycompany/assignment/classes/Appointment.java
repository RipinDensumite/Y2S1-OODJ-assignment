/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
public class Appointment {
    private String appointmentId;
    private LocalDateTime appointmentDateTime;
    private AppointmentStatus status;//?
    
    public Appointment(String appointmentId, LocalDateTime appointmentDateTime){
        this.appointmentId = appointmentId;
        this.appointmentDateTime = appointmentDateTime;
        this.status = AppointmentStatus.PENDING;
    }
    
    public void schedule() throws IOException{
        FileWriter fw = new FileWriter("AppointmentSchedule.txt",true);
        BufferedWriter bw = new BufferedWriter(fw);
        
        String ScheduleDetails = appointmentId+","+appointmentDateTime.toString()+","+status;
        bw.write(ScheduleDetails +"\n");
        bw.close();
        fw.close();
    }
    
    public void reschedule(LocalDateTime newDateTime){
        this.appointmentDateTime = newDateTime;
        try{
        String check = appointmentId;
            FileReader fr = new FileReader("AppointmentSchedule.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            String allData = "";
            while((line = br.readLine()) !=null){
                String[] data= line.split(",");

                if(data[0].equals(check)){
                    data[1] = appointmentDateTime.toString();
                    line = String.join(",", data);
                }

                allData += line +"\n";
            }

            br.close();
            fr.close();

            FileWriter fw = new FileWriter("Feedback.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(allData);
            bw.close();
            fw.close();}
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void cancel(){
        this.status = AppointmentStatus.CANCEL;
        saveToFile();
    }
    
    public void markCompleted(){
        this.status = AppointmentStatus.COMPLETE;
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
                    data[2] = status.toString();
                    line = String.join(",", data);
                }

                allData += line +"\n";
            }

            br.close();
            fr.close();

            FileWriter fw = new FileWriter("Feedback.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(allData);
            bw.close();
            fw.close();
    }catch(Exception e){
        System.out.println(e);
    }
    }
    
    
}
enum AppointmentStatus{
        COMPLETE,
        PENDING,
        CANCEL
    }