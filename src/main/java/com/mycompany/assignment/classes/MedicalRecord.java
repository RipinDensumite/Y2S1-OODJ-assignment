/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author xuheng
 */

package com.mycompany.assignment.classes;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MedicalRecord {
    private String PatientID;
    private String recordId;
    private Date createdDate;
    public MedicalRecord(String PatientID, String recordId, Date createdDate){
        this.PatientID = PatientID;
        this.recordId = recordId;
        this.createdDate = createdDate;
    }
    
    public MedicalRecord(){};
    
    public ArrayList<String[]> viewConsultationHistory(String ID) throws IOException{
        ArrayList<String[]> ConsultationRecord = new ArrayList<>();

        try (FileReader fr = new FileReader("ConsultationHistory.txt")) {
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] value = line.split(",");
                
                if (value[1].equals(ID)) {
                    String[] data = {
                        value[2],
                        value[3],
                        value[4]
                    };
                    
                    ConsultationRecord.add(data);
                }
            }

            br.close();
        }

            return ConsultationRecord;}
    
    public static void viewConsultationHistory() throws IOException{
        try{
            FileReader fr = new FileReader("Consultation.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine())!=null){
            System.out.println(line);
            br.close();
            fr.close();
        }}catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void viewPrescriptions(){
        try{
            FileReader fr = new FileReader("Prescriptions.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine())!=null){
            System.out.println(line);
            br.close();
            fr.close();
        }}catch(Exception e){
            System.out.println(e);
        }
    }
    
        public ArrayList<String[]> viewServiceResults(String ID) throws IOException{
        ArrayList<String[]> ServiceResults = new ArrayList<>();

        try (FileReader fr = new FileReader("ServiceResult.txt")) {
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] value = line.split(",");
                
                if (value[0].equals(ID)) {
                    String[] data = {
                        value[1],
                        value[2]
                    };
                    
                    ServiceResults.add(data);
                }
            }

            br.close();
        }

            return ServiceResults;}
    
    public void viewServiceResults(){
        try{
            FileReader fr = new FileReader("Service.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine())!=null){
            System.out.println(line);
            br.close();
            fr.close();
        }}catch(Exception e){
            System.out.println(e);
        }
    }
}
