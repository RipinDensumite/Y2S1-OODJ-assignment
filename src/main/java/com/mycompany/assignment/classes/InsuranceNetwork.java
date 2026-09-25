/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author arifin + harith
 */
public class InsuranceNetwork {

    private final String insuranceId;
    private String providerName;
    private double coverageRate;
    private boolean accepted;

    public InsuranceNetwork(String providerName, double coverageRate, boolean accepted) throws IOException {
        insuranceNetworkInputChecker(providerName, coverageRate);
        this.insuranceId = generateInsuranceId();
        this.providerName = providerName.trim();
        this.coverageRate = coverageRate;
        this.accepted = accepted;
    }

    public InsuranceNetwork(String insuranceId, String providerName, double coverageRate, boolean accepted) {
        this.insuranceId = insuranceId;
        this.providerName = providerName;
        this.coverageRate = coverageRate;
        this.accepted = accepted;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public String getProviderName() {
        return providerName;
    }

    public double getCoverageRate() {
        return coverageRate;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void addInsuranceNetwork() throws IOException {
        FileWriter fw = new FileWriter("InsuranceNetwork.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(insuranceId + "," + providerName + "," + coverageRate + "," + accepted + "\n");

        bw.close();
        fw.close();
    }

    public void deleteInsuranceNetwork() throws IOException {
        File originalFile = new File("InsuranceNetwork.txt");
        File tempFile = new File("InsuranceNetwork_temp.txt");

        FileReader fr = new FileReader(originalFile);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            String currentId = data[0];

            if (!currentId.equals(insuranceId)) {
                bw.write(line);
                bw.newLine();
            }
        }

        br.close();
        fr.close();

        bw.close();
        fw.close();

        originalFile.delete();
        tempFile.renameTo(originalFile);
    }

    public void updateDetails(String providerName, double coverageRate, boolean accepted) throws IOException {
        insuranceNetworkInputChecker(providerName, coverageRate);

        File originalFile = new File("InsuranceNetwork.txt");
        File tempFile = new File("InsuranceNetwork_temp.txt");

        FileReader fr = new FileReader(originalFile);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            String currentId = data[0];

            if (currentId.equals(insuranceId)) {
                bw.write(
                        insuranceId + ","
                        + providerName.trim() + ","
                        + coverageRate + ","
                        + accepted
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

        this.providerName = providerName.trim();
        this.coverageRate = coverageRate;
        this.accepted = accepted;
    }

    public static ArrayList<InsuranceNetwork> getAllInsuranceNetworks() throws IOException {
        ArrayList<InsuranceNetwork> insuranceList = new ArrayList<>();

        File file = new File("InsuranceNetwork.txt");

        if (!file.exists()) {
            return insuranceList;
        }

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line;

        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",");

            String insuranceId = data[0];
            String providerName = data[1];
            double coverageRate = Double.parseDouble(data[2]);
            boolean accepted = Boolean.parseBoolean(data[3]);

            InsuranceNetwork insurance = new InsuranceNetwork(
                    insuranceId,
                    providerName,
                    coverageRate,
                    accepted
            );

            insuranceList.add(insurance);
        }

        br.close();
        fr.close();

        return insuranceList;
    }

    public void insuranceNetworkInputChecker(String providerName, double coverageRate) {
        if (providerName.isEmpty()) {
            throw new IllegalArgumentException("Provider name is required.");
        }
        if (coverageRate < 0 || coverageRate > 100) {
            throw new IllegalArgumentException("Coverage must be 0-100%.");
        }
    }

    private String generateInsuranceId() throws IOException {
        File file = new File("InsuranceNetwork.txt");

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
