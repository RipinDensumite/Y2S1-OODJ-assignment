/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;
import java.time.LocalDateTime;


/**
 *
 * @author kohty
 */
public class Consultation {
    
   private String consultationId;
   private LocalDateTime consultationDateTime;
   private String consultationNotes;
   private boolean completed;
   
   public Consultation(String consultationId,LocalDateTime consultationDateTime, String consultationNotes ){
       this.consultationId=consultationId;
       this.consultationDateTime=consultationDateTime;
       this.consultationNotes=consultationNotes;
       this.completed=false;
   }
   
   public void addNotes(String notes){
       if(this.consultationNotes==null | this.consultationNotes.isEmpty()){
           this.consultationNotes=notes;
       }else{
           this.consultationNotes+="\n Addition: "+notes;
       }
   }
   
   public void completeConsultation(){
       this.completed=true;
   }
   
   public String getconsultationId(){
       return consultationId;
   }
   
   public LocalDateTime getconsultationDateTime(){
       return consultationDateTime;
   }
}
