/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import com.mycompany.assignment.enums.AssetStatus;
import com.mycompany.assignment.enums.AssetType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author arifi
 */
public class HospitalAsset {

    private String assetId;
    private String assetName;
    private AssetType assetType;
    private AssetStatus status;
    private String departmentId;

    public HospitalAsset(
            String assetId,
            String assetName,
            AssetType assetType,
            AssetStatus status,
            String departmentId
    ) {
        this.assetId = assetId;
        this.assetName = assetName;
        this.assetType = assetType;
        this.status = status;
        this.departmentId = departmentId;
    }

    public HospitalAsset(
            String assetName,
            AssetType assetType,
            AssetStatus status,
            String departmentId
    ) throws IOException {
        this.assetId = generateHospitalAssetId();
        this.assetName = assetName;
        this.assetType = assetType;
        this.status = status;
        this.departmentId = departmentId;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void addHospitalAsset() throws IOException {
        FileWriter fw = new FileWriter("HospitalAsset.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(
                assetId + ","
                + assetName + ","
                + assetType + ","
                + status + ","
                + (departmentId == null ? "" : departmentId)
        );

        bw.newLine();

        bw.close();
        fw.close();
    }

    public void updateDetails(String assetName, AssetStatus status) throws IOException {
        File originalFile = new File("HospitalAsset.txt");
        File tempFile = new File("HospitalAsset_temp.txt");

        FileReader fr = new FileReader(originalFile);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",", -1);
            if (data[0].equals(assetId)) {
                bw.write(
                        assetId + ","
                        + assetName + ","
                        + assetType + ","
                        + status + ","
                        + (departmentId == null
                                ? ""
                                : departmentId)
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

        this.assetName = assetName;
        this.status = status;
    }

    public void allocateTo(Department department) throws IOException {
        if (department == null) {
            throw new IllegalArgumentException(
                    "Department is required."
            );
        }

        String newDepartmentId = null;

        File originalFile = new File("HospitalAsset.txt");
        File tempFile = new File("HospitalAsset_temp.txt");

        FileReader fr = new FileReader(originalFile);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",", -1);
            if (data[0].equals(assetId)) {
                bw.write(
                        assetId + ","
                        + assetName + ","
                        + assetType + ","
                        + status + ","
                        + newDepartmentId
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

        this.departmentId = newDepartmentId;
    }

    public void removeAllocation() throws IOException {
        File originalFile = new File("HospitalAsset.txt");
        File tempFile = new File("HospitalAsset_temp.txt");

        FileReader fr = new FileReader(originalFile);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",", -1);
            if (data[0].equals(assetId)) {
                bw.write(
                        assetId + ","
                        + assetName + ","
                        + assetType + ","
                        + status + ","
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

        this.departmentId = null;
    }

    public void deleteHospitalAsset() throws IOException {
        File originalFile = new File("HospitalAsset.txt");
        File tempFile = new File("HospitalAsset_temp.txt");

        FileReader fr = new FileReader(originalFile);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",", -1);
            if (!data[0].equals(assetId)) {
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

    private String generateHospitalAssetId() throws IOException {
        File file = new File("HospitalAsset.txt");

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

            String[] data = line.split(",", -1);

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

    public static ArrayList<HospitalAsset> getAllHospitalAssets() throws IOException {
        ArrayList<HospitalAsset> assetList = new ArrayList<>();

        File file = new File("HospitalAsset.txt");

        if (!file.exists()) {
            return assetList;
        }

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",", -1);

            String assetId = data[0];
            String assetName = data[1];

            AssetType assetType = AssetType.valueOf(data[2]);

            AssetStatus status = AssetStatus.valueOf(data[3]);

            String departmentId = data[4];

            if (departmentId.isEmpty()) {
                departmentId = null;
            }

            HospitalAsset asset = new HospitalAsset(
                    assetId,
                    assetName,
                    assetType,
                    status,
                    departmentId
            );

            assetList.add(asset);
        }

        br.close();
        fr.close();

        return assetList;
    }
}
