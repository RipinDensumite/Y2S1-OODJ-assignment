/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import com.mycompany.assignment.enums.ShiftStatus;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author HP
 */
public class DoctorShift {
    private String shiftId;
    private LocalDate shiftDate;
    private LocalTime startTime;
    private LocalTime endTime;
    // need access to doctor class, so change later dont forget gang
    private String doctor;
    private ShiftStatus status;

    public DoctorShift(LocalDate shiftDate, LocalTime startTime,LocalTime endTime, String doctor) throws IOException {
        this.shiftId = makenewid();
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
        this.status = ShiftStatus.SCHEDULED;
        savetofile();
    }
    
    public DoctorShift(String shiftId) {
        this.shiftId = shiftId;

        try {
            FileReader fr = new FileReader("doctorShift.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {String[] shift = line.split(",");

                if (shift[0].equals(shiftId)) {
                    this.shiftDate = LocalDate.parse(shift[1]);
                    this.startTime = LocalTime.parse(shift[2]);
                    this.endTime = LocalTime.parse(shift[3]);
                    this.doctor = shift[4];
                    this.status = ShiftStatus.valueOf(shift[5]);
                    found = true;
                    break;
                }
            }
            br.close();
            fr.close();
            if (!found) {
                System.out.println("shift id not found.");
            }
        } catch (Exception e) {
            System.out.println("error occured");
        }
    }

    public void updateShift(LocalDate shiftDate, LocalTime startTime, LocalTime endTime) {
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
        saveChanges();
    }

    public void savetofile() throws IOException {
        FileWriter fw = new FileWriter("doctorShift.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        String shiftdetaul = shiftId + "," + shiftDate + "," + startTime + "," + endTime + "," + doctor + "," + status;
        bw.write(shiftdetaul + "\n");
        bw.close();
        fw.close();
    }

    private String makenewid() {
        int largestId = 0;

        try {
            FileReader fr = new FileReader("doctorShift.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0) {
                    int number = Integer.parseInt(data[0].substring(2));
                    if (number > largestId) {
                        largestId = number;
                    }
                }
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            //error if i remove this, idk why
        }

        return String.format("DS%02d", largestId + 1);
    }

    private void saveChanges() {
        try {
            FileReader fr = new FileReader("doctorShift.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            String allData = "";

            while ((line = br.readLine()) != null) {
                String[] shift = line.split(",");

                if (shift[0].equals(shiftId)) {
                    line = shiftId + "," + shiftDate + "," + startTime + ","
                            + endTime + "," + doctor + "," + status;
                }
                allData += line + "\n";
            }
            br.close();
            fr.close();

            FileWriter fw = new FileWriter("doctorShift.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(allData);
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean containsTimeSlot(LocalDateTime dateTime, int durationMinutes) {
        if (durationMinutes < 0) {
            return false;
        }
        LocalDateTime shiftStart = LocalDateTime.of(shiftDate, startTime);
        LocalDateTime shiftEnd = LocalDateTime.of(shiftDate, endTime);
        LocalDateTime slotEnd = dateTime.plusMinutes(durationMinutes);

        return !dateTime.isBefore(shiftStart) && !slotEnd.isAfter(shiftEnd);
    }

    public void cancelShift() {
        this.status = ShiftStatus.CANCELLED;
        saveChanges();
    }

    public void markCompleted() {
        this.status = ShiftStatus.COMPLETED;
        saveChanges();
    }
    
}