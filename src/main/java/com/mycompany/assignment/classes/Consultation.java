/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;


/**
 *
 * @author abel
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
   
   public void addNotes(String notes)throws IOException{
       
         this.consultationNotes=notes;
      
        FileWriter fw = new FileWriter("Consultation.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
    
        bw.write("ConsultationID: " + consultationId + "\n");
        bw.write("ConsultationDateTime: " + consultationDateTime.format(
        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
        bw.write("ConsultationNotes: " + consultationNotes + "\n");
        bw.write("Completed: "+completed);
        bw.newLine();
        bw.newLine();
    
        bw.close();
   }
   
   public void completeConsultation() throws FileNotFoundException, IOException{
              File file=new File("Consultation.txt");
        
        
            BufferedReader br=new BufferedReader(new FileReader(file));
            StringBuilder content=new StringBuilder();
            
            String line;
            boolean found=false;

            
            while((line=br.readLine())!=null){
                if(line.equals("ConsultationID: "+consultationId)){
                    found=true;
                }
                if(found && line.equals("Completed: false")){
                line="Completed: true";
                found=false;
            }
                content.append(line);
                content.append(System.lineSeparator());
            }
            br.close();
            
    FileWriter fw = new FileWriter(file);
    BufferedWriter bw = new BufferedWriter(fw);

    bw.write(content.toString());
    bw.close();
        
   }
   
   public String getconsultationId(){
       return consultationId;
   }
   
   public LocalDateTime getconsultationDateTime(){
       return consultationDateTime;
   }
}
