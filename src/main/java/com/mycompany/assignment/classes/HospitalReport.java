/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author harith
 */
public class HospitalReport {

    private LocalDate startDate;
    private LocalDate endDate;
    private int totalappointment;
    private double totalrevenue;
    private double averageRating;

    public HospitalReport(LocalDate startDate, LocalDate endDate) throws IOException {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalappointment = calculateAppointments();
        this.totalrevenue = calculateRevenue();
        this.averageRating = calculateAverageRating();
    }

    public int calculateAppointments() throws IOException {
        FileReader fr = new FileReader("AppointmentSchedule.txt");
        BufferedReader br = new BufferedReader(fr);

        int count = 0;
        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");

            LocalDate appointmentDate = LocalDate.parse(data[3]);

            if (!appointmentDate.isBefore(startDate)
                    && !appointmentDate.isAfter(endDate)) {
                count++;
            }
        }

        br.close();
        return count;
    }

    public double calculateRevenue() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader("Billing.txt");
        BufferedReader br = new BufferedReader(fr);

        double totalRevenue = 0.0;
        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");

            //01,2026-09-08,1.0,1.0,1.0,1.0,PAID assume
            LocalDate billingDate = LocalDate.parse(data[1]);

            if ((!billingDate.isBefore(startDate)) && (!billingDate.isAfter(endDate))) {
                totalRevenue += Double.parseDouble(data[5]);

            }
        }

        br.close();
        return totalRevenue;
    }

    public double calculateAverageRating() throws FileNotFoundException, IOException {

        FileReader fr = new FileReader("Feedback.txt");
        BufferedReader br = new BufferedReader(fr);

        double totalRating = 0.0;
        int count = 0;
        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(";", 6);

            String dateString = data[5];   // Wed Oct 15 14:30:00 GMT+08:00 2026 assume

            String withoutDay = dateString.substring(4); // 

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    "MMM dd HH:mm:ss zzz yyyy",
                    Locale.ENGLISH
            );

            LocalDate feedbackDate = ZonedDateTime
                    .parse(withoutDay, formatter)
                    .toLocalDate();

            if (!feedbackDate.isBefore(startDate) && !feedbackDate.isAfter(endDate)) {
                totalRating += Integer.parseInt(data[3]);
                count++;
            }
        }
        return totalRating / count;
    }
}
