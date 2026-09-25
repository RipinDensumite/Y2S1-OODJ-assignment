/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment.classes;
import com.mycompany.assignment.enums.ServiceType;
import com.mycompany.assignment.enums.UserRole;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author ABEL
 */
public class Doctor extends User {

    private String specialisation;
    private List<Consultation> consultations;
    private List<MedicalServiceRequest> serviceRequests;
    

    public Doctor(String userId, String fullName, String email, String phoneNumber, String password, boolean active, String specialisation) {
        super(userId, fullName, email, phoneNumber, password, active);
        this.specialisation=specialisation;
    }
    public void recordAssessment(MedicalAssessment assessment) throws IOException{
        if(assessment ==null){
            System.out.println("Not Recorded");
            return;
        }assessment.updateAssessment();
        System.out.println("Assessment is recorded");
    }
    
    public void writeConsultationNotes(String notes) throws IOException{
        if(notes==null||notes.isEmpty()){
            System.out.println("No notes");
            return;
        }
        if(!consultations.isEmpty()){
            Consultation latest=consultations.get(consultations.size()-1);
            latest.addNotes(notes);
        }else{
            Consultation newConsultation= new Consultation("C"+(consultations.size()+1),LocalDateTime.now(),notes);
            consultations.add(newConsultation);
        }
    }       
     
     public void issuePrescription(Prescription prescription){
         try{
             prescription.updatePrescription();
         }catch(Exception e){
             System.out.println("Failed"+e.getMessage());
         }
     }
    
     public void createServiceRequest(MedicalServiceRequest request){
         serviceRequests.add(request);
     }
     
     public void viewSchedule(){
        if(consultations.isEmpty()){
            System.out.println("No schedule");
            return;
        }for (Consultation c:consultations){
            System.out.println("ID: "+c.getconsultationId()+c.getconsultationDateTime());
        }
     }
     
     public String getSpecialisation(){
         return specialisation;
     }
     
     public List<Consultation> getConsultation(){
         return consultations;
     }
     
     public List<MedicalServiceRequest> getServiceRequest(){
         return serviceRequests;
     }
    @Override
    public UserRole getRole() {
        return UserRole.DOCTOR;
    }     
    public static Doctor getDoctorById(String doctorId) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("accountlist.txt"));
    String line;

    while ((line = br.readLine()) != null) {
        String[] data = line.split(",", -1);

        if (data.length >= 8 &&
            data[0].equals(doctorId) &&
            data[5].equals("DOCTOR")) {

            br.close();

            return new Doctor(
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    data[4],
                    Boolean.parseBoolean(data[6]),
                    data[7]
            );
        }
    }

    br.close();
    return null;
}
}
    
