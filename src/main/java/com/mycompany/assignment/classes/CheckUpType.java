package com.mycompany.assignment.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author xuheng
 */
public class CheckUpType {

    private final String checkUpTypeId;
    private String name;
    private String description;
    private double baseRate;
    private boolean active;
    private int duration;

    public CheckUpType(String name, String description, double baseRate, int duration) throws IOException {
        this.checkUpTypeId = generateCheckUpTypeId();
        this.name = name;
        this.description = description;
        this.baseRate = baseRate;
        this.duration = duration;
        this.active = true;
    }

    public CheckUpType(String checkUpTypeId, String name, String description, double baseRate, int duration, boolean active) {
        this.checkUpTypeId = checkUpTypeId;
        this.name = name;
        this.description = description;
        this.baseRate = baseRate;
        this.duration = duration;
        this.active = active;
    }

    public String getCheckUpTypeId() {
        return checkUpTypeId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getBaseRate() {
        return baseRate;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isActive() {
        return active;
    }

    public void insertFile() throws IOException {
        FileWriter fw = new FileWriter("CheckUp.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(
                checkUpTypeId + ","
                + name + ","
                + description + ","
                + baseRate + ","
                + duration + ","
                + active
        );

        bw.newLine();

        bw.close();
        fw.close();
    }

    public void updateDetails(String name, String description, double baseRate, int duration, boolean active) throws IOException {
        File originalFile = new File("CheckUp.txt");
        File tempFile = new File("CheckUp_temp.txt");

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
            String currentId = data[0];

            if (currentId.equals(checkUpTypeId)) {
                bw.write(
                        checkUpTypeId + ","
                        + name + ","
                        + description + ","
                        + baseRate + ","
                        + duration + ","
                        + active
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

        this.name = name;
        this.description = description;
        this.baseRate = baseRate;
        this.duration = duration;
        this.active = active;
    }

    public static ArrayList<CheckUpType> getAllCheckUpTypes() throws IOException {
        ArrayList<CheckUpType> checkUpList = new ArrayList<>();

        File file = new File("CheckUp.txt");

        if (!file.exists()) {
            return checkUpList;
        }

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",");

            String checkUpTypeId = data[0];
            String name = data[1];
            String description = data[2];
            double baseRate = Double.parseDouble(data[3]);
            int duration = Integer.parseInt(data[4]);
            boolean active = Boolean.parseBoolean(data[5]);

            CheckUpType checkUpType = new CheckUpType(
                    checkUpTypeId,
                    name,
                    description,
                    baseRate,
                    duration,
                    active
            );

            checkUpList.add(checkUpType);
        }

        br.close();
        fr.close();

        return checkUpList;
    }

    private String generateCheckUpTypeId() throws IOException {
        File file = new File("CheckUp.txt");

        if (!file.exists()) {
            return "001";
        }

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        int highestId = 0;
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",");
            int id = Integer.parseInt(data[0]);

            if (id > highestId) {
                highestId = id;
            }
        }

        br.close();
        fr.close();

        int nextId = highestId + 1;

        return String.format("%03d", nextId);
    }
}
