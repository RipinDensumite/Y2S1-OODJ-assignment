/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class HospitalReport {
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalappointment;
    private double totalrevenue;
    private double averageRating;
    
    public HospitalReport(LocalDate startDate,LocalDate endDate) throws IOException {
	this.startDate = startDate;
	this.endDate = endDate;
        this.totalappointment= calculateAppointments();
        this.totalrevenue=calculateRevenue();
        this.averageRating=calculateAverageRating();
	}
    
    public int calculateAppointments() throws FileNotFoundException, IOException{
        FileReader fr =new FileReader("AppointmentSchedule.txt");
        BufferedReader br =new BufferedReader(fr);
        
        int count = 0;
        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");

        // AP01,2026-09-10T14:19:46.175833100,PENDING
            LocalDate appointmentDate = LocalDateTime.parse(data[1]).toLocalDate();

            if ((!appointmentDate.isBefore(startDate)) && (!appointmentDate.isAfter(endDate))) {
                count++;
            }
        }

        br.close();
        return count;
   }   
   
   public double calculateRevenue() throws FileNotFoundException, IOException{
        FileReader fr =new FileReader("Billing.txt");
        BufferedReader br =new BufferedReader(fr);

        double totalRevenue = 0.0;
        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");

        //01,2026-09-08,1.0,1.0,1.0,1.0,PAID
            LocalDate billingDate = LocalDate.parse(data[1]);

            if ((!billingDate.isBefore(startDate)) &&(!billingDate.isAfter(endDate))) {
                totalRevenue += Double.parseDouble(data[5]);
        }
    }

        br.close();
        return totalRevenue;
   }
 
   
   public double calculateAverageRating() throws FileNotFoundException, IOException{
        FileReader fr =new FileReader("Feedback.txt");
        BufferedReader br =new BufferedReader(fr);
        double totalRating = 0.0;
        int count = 0;
        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");

            // 66,3,I think its good,2026-09-10
            LocalDate feedbackDate = LocalDate.parse(data[3]);

            if ((!feedbackDate.isBefore(startDate)) &&(!feedbackDate.isAfter(endDate))) {
                totalRating += Double.parseDouble(data[1]);
                count++;
            }
        }
        br.close();
        if (count == 0) {
            return 0.0;
        } else {
            return totalRating / count;
        }
   }
}
   
