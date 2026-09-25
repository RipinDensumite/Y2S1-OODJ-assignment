/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author abel
 */
public class Prescription {
    private String prescriptionID;
    private String medicationName;
    private String dosage;
    private String instruction;
    private Date issuedDate;
    
    public Prescription(String prescriptionID,String medicationName,String dosage,String instruction, Date issuedDate){
        this.prescriptionID=prescriptionID;
        this.medicationName=medicationName;
        this.dosage=dosage;
        this.instruction=instruction;  
        this.issuedDate=issuedDate;
    }
    
    public void updatePrescription()throws IOException{
        FileWriter fw=new FileWriter("Prescription.txt",true);
        BufferedWriter bw=new BufferedWriter(fw);
        bw.write("PrescriptionID: "+prescriptionID+"\nMedicatioName: "+medicationName+"\nDosage: "+dosage+"mg"+"\nInstruction: "+instruction+"\nIssuedDate: "+issuedDate);
        bw.newLine();
        bw.newLine();
        bw.close();
        fw.close();
    }
    
    public String getprescriptionID(){
        return prescriptionID;
    }
    
    public String getmedicationName(){
        return medicationName;
    }
    
    
    
}
