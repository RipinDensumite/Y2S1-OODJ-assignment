/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import java.time.LocalDate;

/**
 *
 * @author kohty
 */
public class MedicalServiceRequest {
    private String requestId;
    private ServiceType requestType;
    private LocalDate requestDate;
    private String reason;
    private RequestStatus status;
    private String resultDetails;
    private LocalDate resultDate;
    private double serviceFee;
    
    
    public MedicalServiceRequest(String requestId,ServiceType requestType, String reason, double serviceFee ){
        this.requestId=requestId;
        this.requestType=requestType;
        this.reason=reason;
        this.serviceFee=serviceFee;
        this.requestDate=LocalDate.now();
        this.status=RequestStatus.PENDING;
    }
    
    public void approve(){
        if(this.status==RequestStatus.PENDING){
            this.status=RequestStatus.APPROVED;
            System.out.println(requestId+"approved");
        }else{
            System.out.println("Cannot approve. Status:"+this.status);
        }
    }
    
    public void reject(){
        if(this.status==RequestStatus.PENDING){
            this.status=RequestStatus.REJECTED;
            System.out.println(requestId+"rejected");
        }else{
            System.out.println("Cannot approve. Status:"+this.status);
        }
    }
    
    public void recordResult(String result){
        this.resultDetails=result;
        this.resultDate=LocalDate.now();
        System.out.println("Recorded");
    }
    
    public void markCompleted(){
        if(this.status==RequestStatus.APPROVED){
            this.status=RequestStatus.COMPLETED;
            System.out.println("Completed");
        }else if(this.status==RequestStatus.COMPLETED){
            System.out.println("Already completed");
        }else{
            System.out.println("Status: "+this.status);
        }
    }
}
