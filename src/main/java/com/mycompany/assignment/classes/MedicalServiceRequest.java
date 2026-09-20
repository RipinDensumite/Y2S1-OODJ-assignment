/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import com.mycompany.assignment.enums.AssetStatus;
import com.mycompany.assignment.enums.AssetType;
import com.mycompany.assignment.enums.RequestStatus;
import com.mycompany.assignment.enums.ServiceType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author kohty
 */
public class MedicalServiceRequest {

    private final String requestId;
    private ServiceType requestType;
    private LocalDate requestDate;
    private String doctorId;
    private String consultationId;
    private String reason;
    private RequestStatus status;
    private String assetId;
    private String resultDetails;
    private LocalDate resultDate;
    private double serviceFee;

    public MedicalServiceRequest(String doctorId, String consultationId, ServiceType requestType, String reason) throws IOException {
        this.requestId = generateRequestId();
        this.doctorId = doctorId;
        this.consultationId = consultationId;
        this.requestType = requestType;
        this.reason = reason;
        this.requestDate = LocalDate.now();
        this.status = RequestStatus.PENDING;
        this.assetId = null;
        this.resultDetails = null;
        this.resultDate = null;
        this.serviceFee = 0.0;
    }

    public MedicalServiceRequest(String requestId, ServiceType requestType, LocalDate requestDate, String doctorId, String consultationId, String reason, RequestStatus status, String assetId, String resultDetails, LocalDate resultDate, double serviceFee) {
        this.requestId = requestId;
        this.requestType = requestType;
        this.requestDate = requestDate;
        this.doctorId = doctorId;
        this.consultationId = consultationId;
        this.reason = reason;
        this.status = status;
        this.assetId = assetId;
        this.resultDetails = resultDetails;
        this.resultDate = resultDate;
        this.serviceFee = serviceFee;
    }

    public String getRequestId() {
        return requestId;
    }

    public ServiceType getRequestType() {
        return requestType;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getConsultationId() {
        return consultationId;
    }

    public String getReason() {
        return reason;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getResultDetails() {
        return resultDetails;
    }

    public LocalDate getResultDate() {
        return resultDate;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    // Save new request
    public void addMedicalServiceRequest() throws IOException {
        FileWriter fw = new FileWriter("MedicalServiceRequest.txt", true);

        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(toFileString());
        bw.newLine();

        bw.close();
        fw.close();
    }

    public void assignAsset(HospitalAsset asset) throws IOException {
        if (asset == null) {
            throw new IllegalArgumentException("Hospital asset is required.");
        }

        if (asset.getStatus() != AssetStatus.AVAILABLE) {
            throw new IllegalArgumentException("Hospital asset is not available.");
        }

        // Validate correct asset type
        switch (requestType) {
            case LAB_TEST:
                if (asset.getAssetType() != AssetType.LAB) {
                    throw new IllegalArgumentException("Lab test requires a LAB asset.");
                }
                break;

            case XRAY:
                if (asset.getAssetType() != AssetType.XRAY_ROOM) {
                    throw new IllegalArgumentException("X-Ray request requires an XRAY_ROOM.");
                }
                break;

            case IMAGING:
                if (asset.getAssetType() != AssetType.IMAGING_ROOM) {
                    throw new IllegalArgumentException("Imaging request requires an IMAGING_ROOM.");
                }
                break;
        }

        this.assetId = asset.getAssetId();
        updateFile();
    }

    public void approve() throws IOException {
        if (status != RequestStatus.PENDING) {
            throw new IllegalArgumentException("Only pending requests can be approved.");
        }

        if (assetId == null) {
            throw new IllegalArgumentException("Assign an asset before approving.");
        }

        this.status = RequestStatus.APPROVED;

        updateFile();
    }

    public void reject() throws IOException {
        if (status != RequestStatus.PENDING) {
            throw new IllegalArgumentException("Only pending requests can be rejected.");
        }

        this.status = RequestStatus.REJECTED;
        updateFile();
    }

    public void recordResult(String result, double serviceFee) throws IOException {
        if (status != RequestStatus.APPROVED) {
            throw new IllegalArgumentException("Only approved requests can record results.");
        }

        if (result == null || result.trim().isEmpty()) {
            throw new IllegalArgumentException("Result is required.");
        }

        if (serviceFee < 0) {
            throw new IllegalArgumentException("Service fee cannot be negative.");
        }

        this.resultDetails = result.trim();
        this.resultDate = LocalDate.now();
        this.serviceFee = serviceFee;

        // Result means service is finished
        this.status = RequestStatus.COMPLETED;

        updateFile();
    }

    private void updateFile() throws IOException {
        File originalFile = new File("MedicalServiceRequest.txt");
        File tempFile = new File("MedicalServiceRequest_temp.txt");

        FileReader fr = new FileReader(originalFile);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",", -1);

            if (data[0].equals(requestId)) {
                bw.write(toFileString());
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
    }

    private String toFileString() {
        return requestId + ","
                + requestType + ","
                + requestDate + ","
                + doctorId + ","
                + consultationId + ","
                + cleanText(reason) + ","
                + status + ","
                + (assetId == null ? "" : assetId) + ","
                + cleanText(resultDetails) + ","
                + (resultDate == null ? "" : resultDate.toString()) + ","
                + serviceFee;
    }

    private String cleanText(String text) {
        if (text == null) {
            return "";
        }

        // Your project uses comma separated TXT files
        return text.replace(",", " ").trim();
    }

    public static ArrayList<MedicalServiceRequest> getAllMedicalServiceRequests() throws IOException {
        ArrayList<MedicalServiceRequest> requestList = new ArrayList<>();

        File file = new File("MedicalServiceRequest.txt");

        if (!file.exists()) {
            return requestList;
        }

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",", -1);
            String requestId = data[0];
            ServiceType requestType = ServiceType.valueOf(data[1]);
            LocalDate requestDate = LocalDate.parse(data[2]);
            String doctorId = data[3];
            String consultationId = data[4];
            String reason = data[5];
            RequestStatus status = RequestStatus.valueOf(data[6]);

            String assetId = data[7];

            if (assetId.isEmpty()) {
                assetId = null;
            }

            String resultDetails = data[8];

            if (resultDetails.isEmpty()) {
                resultDetails = null;
            }

            LocalDate resultDate = null;

            if (!data[9].isEmpty()) {
                resultDate = LocalDate.parse(data[9]);
            }

            double serviceFee = Double.parseDouble(data[10]);

            MedicalServiceRequest request = new MedicalServiceRequest(
                    requestId,
                    requestType,
                    requestDate,
                    doctorId,
                    consultationId,
                    reason,
                    status,
                    assetId,
                    resultDetails,
                    resultDate,
                    serviceFee
            );

            requestList.add(request);
        }

        br.close();
        fr.close();

        return requestList;
    }

    public static MedicalServiceRequest getMedicalServiceRequest(String requestId) throws IOException {
        ArrayList<MedicalServiceRequest> requests = getAllMedicalServiceRequests();

        for (MedicalServiceRequest request : requests) {
            if (request.getRequestId().equals(requestId)) {
                return request;
            }
        }

        return null;
    }

    private String generateRequestId() throws IOException {
        File file = new File("MedicalServiceRequest.txt");

        if (!file.exists()) {
            return "SR001";
        }

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line;
        int highestId = 0;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",", -1);

            try {
                int id = Integer.parseInt(data[0].substring(2));

                if (id > highestId) {
                    highestId = id;
                }
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                // Ignore invalid request IDs
            }
        }

        br.close();
        fr.close();

        return String.format("SR%03d", highestId + 1);
    }
}
