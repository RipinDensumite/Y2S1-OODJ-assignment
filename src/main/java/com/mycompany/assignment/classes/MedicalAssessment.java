package com.mycompany.assignment.classes;


import com.mycompany.assignment.enums.HealthGrade;
import java.time.LocalDate;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kohty
 */
public class MedicalAssessment {
    
    private String assessmentId;
    private LocalDate assessmentDate;
    private double temperature;
    private String bloodPressure;
    private int heartRate;
    private double weight;
    private HealthGrade healthGrade;
    private String remarks;
    
    public MedicalAssessment(String assessmentId,LocalDate assessmentDate, double temperature, String bloodPressure, int heartRate,double weight, String remarks ){
        this.assessmentId=assessmentId;
        this.assessmentDate=LocalDate.now();
        this.bloodPressure=bloodPressure;
        this.temperature=temperature;
        this.heartRate=heartRate;
        this.weight=weight;
        this.healthGrade=HealthGrade.NORMAL;
        this.remarks=remarks;
        
    }
        
    public HealthGrade calculateHealthGrade(){
        if (this.temperature > 39.0 || this.heartRate > 120 || this.heartRate < 50){
            this.healthGrade=HealthGrade.HIGH_RISK;
        } else if (this.temperature > 38.0 || this.heartRate > 100) {
            this.healthGrade=HealthGrade.MODERATE;
        }else{
            this.healthGrade=HealthGrade.NORMAL;
        }
        System.out.println(assessmentId+":"+this.healthGrade);
        return this.healthGrade;
    }    
   
    public void updateAssessment(){
        calculateHealthGrade();
        System.out.println(assessmentId+"changed");
        this.remarks="Update";
    }
}
