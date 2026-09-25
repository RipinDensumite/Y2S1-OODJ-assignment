/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import com.mycompany.assignment.enums.ShiftStatus;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author harith
 */
public class doctorShift {
    private String shiftId;
    private LocalDate shiftDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Doctor doctor;
    private ShiftStatus status;

    public doctorShift(LocalDate shiftDate, LocalTime startTime,LocalTime endTime, Doctor doctor) throws IOException {
        this.shiftId = makenewid();
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
        this.status = ShiftStatus.SCHEDULED;
        savetofile();
    }
    
    public doctorShift(String shiftId) {
        this.shiftId = shiftId;

        try {
            FileReader fr = new FileReader("doctorShift.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] shift = line.split(",");

                if (shift[0].equals(shiftId)) {
                    this.shiftDate = LocalDate.parse(shift[1]);
                    this.startTime = LocalTime.parse(shift[2]);
                    this.endTime = LocalTime.parse(shift[3]);
                    this.doctor = getDoctor(shift[4]);
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
            System.out.println("error ocured");
        }
    }

    
    public static ArrayList<doctorShift> getAllShift() throws IOException {
        ArrayList<doctorShift> doctorShifts = new ArrayList<>();
        FileReader fr = new FileReader("doctorShift.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            String[] doctorShift = line.split(",");
            doctorShift currentshift = new doctorShift(doctorShift[0]);
            doctorShifts.add(currentshift);
        }
        br.close();
        fr.close();
        return doctorShifts;
    }

    public String getshiftId() { 
        return shiftId; 
    }
    public LocalDate getdate() { 
        return shiftDate; 
    }
    public LocalDate getShiftDate(){
        return shiftDate;
    }
    public LocalTime getStarttime() { 
        return startTime; 
    }
    public LocalTime getendtime() { 
        return endTime; 
    }
    public LocalTime getStartTime(){
        return startTime;
    }
    
    public LocalTime getEndTime(){
        return endTime;
    }

    public Doctor getDoctor() { 
        return doctor; 
    }
    public ShiftStatus getstatus() { 
        return status; 
    }

    public void updateShift(LocalDate shiftDate, LocalTime startTime, LocalTime endTime) {
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
        saveChanges();
    }

    public void updateShift(LocalDate shiftDate, LocalTime startTime, LocalTime endTime, Doctor doctor) {
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
        saveChanges();
    }

    public void savetofile() throws IOException {
        FileWriter fw = new FileWriter("doctorShift.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        String shiftdetaul = shiftId + "," + shiftDate + "," + startTime + "," + endTime + "," + doctor.getUserId() + "," + status;
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
                    int number = Integer.parseInt(data[0]);
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

        return String.valueOf(largestId + 1);
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
                            + endTime + "," + doctor.getUserId() + "," + status;
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


    private Doctor getDoctor(String doctorId) {
        try {
            FileReader fr = new FileReader("accountlist.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(doctorId) && data[5].equals("DOCTOR")) {
                    Doctor doctor = new Doctor(data[0], data[1], data[2], data[3], data[4], Boolean.parseBoolean(data[6]),data[7]);
                    br.close();
                    fr.close();
                    return doctor;
                }
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("doctor not found");
        }
        return null;
    }
    
}
