/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;

import com.mycompany.assignment.enums.ConsultationStatus;
import java.time.LocalDateTime;

/**
 *
 * @author arifi
 */
public class Consultation {

    private String consultationId;
    private LocalDateTime consultationDateTime;
    private String consultationNotes;
    private ConsultationStatus status;

    public Consultation(String consultationId, LocalDateTime consultationDateTime, String consultationNotes, ConsultationStatus status){
        this.consultationId = consultationId;
        this.consultationDateTime = consultationDateTime;
        this.consultationNotes = consultationNotes;
        this.status = status;
    }
    
    public void addNotes(String notes){
        if(notes.isBlank()){
            throw new IllegalArgumentException("Notes cannot be empty");
        }
        consultationNotes = notes;
        // add consultation notes
    }
    
    public void startConsultation(){
        if(status == ConsultationStatus.COMPLETED){
            throw new IllegalArgumentException("Consultation already completed");
        }
        status = ConsultationStatus.IN_PROGRESS;
    }
    
    public void completeConsultation(){
        status = ConsultationStatus.COMPLETED;
    }
}