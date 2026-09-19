/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MedicalRecord {
    private String recordId;
    private Date createdDate;
    public MedicalRecord(String PatientID, String recordId, Date createdDate){
        super();
        this.recordId = recordId;
        this.createdDate = createdDate;
    }
    
    public void viewConsultationHistory() throws IOException{
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
