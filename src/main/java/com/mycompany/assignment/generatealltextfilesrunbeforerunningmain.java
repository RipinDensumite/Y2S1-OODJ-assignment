import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class generatealltextfilesrunbeforerunningmain {

    public static void createFiles() throws IOException {

        // AppointmentSchedule.txt
        FileWriter fw1 = new FileWriter("AppointmentSchedule.txt");
        BufferedWriter bw1 = new BufferedWriter(fw1);
        bw1.write("APT001,4,3,2026-10-15,14:30,15:00,CANCEL\n");
        bw1.write("APT002,4,3,2026-10-16,09:00,09:30,COMPLETE\n");
        bw1.write("APT003,4,3,2026-10-20,15:30,16:30,BOOKED\n");
        bw1.close();
        fw1.close();

        // accountlist.txt
        FileWriter fw2 = new FileWriter("accountlist.txt");
        BufferedWriter bw2 = new BufferedWriter(fw2);
        bw2.write("1,ABU,ABU@GMAIL.COM,0123456789,ABUPASSWORD,ADMIN,true\n");
        bw2.write("2,ALI,ALI@GMAIL.COM,0132456789,ALIPASSWORD,MEDICAL_MANAGER,true\n");
        bw2.write("3,DR AINA,AINA@GMAIL.COM,0145678901,AINAPASSWORD,DOCTOR,true,General\n");
        bw2.write("4,SITI,SITI@GMAIL.COM,0156789012,SITIPASSWORD,PATIENT,true,2004-05-18,Female\n");
        bw2.close();
        fw2.close();

        // CheckUp.txt
        FileWriter fw3 = new FileWriter("CheckUp.txt");
        BufferedWriter bw3 = new BufferedWriter(fw3);
        bw3.write("001,General Checkup,Routine health consultation,50.0,30,true\n");
        bw3.write("002,Dental Checkup,Teeth and oral examination,80.0,45,true\n");
        bw3.close();
        fw3.close();

        // Billing.txt
        FileWriter fw4 = new FileWriter("Billing.txt");
        BufferedWriter bw4 = new BufferedWriter(fw4);
        bw4.write("B001,2026-10-15,50.0,20.0,10.0,60.0,PENDING\n");
        bw4.write("B002,2026-10-16,80.0,30.0,20.0,90.0,PAID\n");
        bw4.close();
        fw4.close();

        // Consultation1.txt
        FileWriter fw5 = new FileWriter("Consultation1.txt");
        BufferedWriter bw5 = new BufferedWriter(fw5);
        bw5.write("ConsultationID: C001\n");
        bw5.write("ConsultationDateTime: 2026-10-15 14:30:00\n");
        bw5.write("ConsultationNotes: Patient reports mild fever and headache.\n");
        bw5.write("Consultation: false\n\n");
        bw5.write("ConsultationID: C002\n");
        bw5.write("ConsultationDateTime: 2026-10-16 09:15:00\n");
        bw5.write("ConsultationNotes: Follow-up consultation completed successfully.\n");
        bw5.write("Consultation: true\n\n");
        bw5.close();
        fw5.close();

        // Department.txt
        FileWriter fw6 = new FileWriter("Department.txt");
        BufferedWriter bw6 = new BufferedWriter(fw6);
        bw6.write("1,General Medicine,General,3\n");
        bw6.write("2,Dentistry,Dental,5,6\n");
        bw6.close();
        fw6.close();

        // DoctorAssignment.txt
        FileWriter fw7 = new FileWriter("DoctorAssignment.txt");
        BufferedWriter bw7 = new BufferedWriter(fw7);
        bw7.write("3,2\n");
        bw7.write("5,7\n");
        bw7.close();
        fw7.close();

        // doctorShift.txt
        FileWriter fw8 = new FileWriter("doctorShift.txt");
        BufferedWriter bw8 = new BufferedWriter(fw8);
        bw8.write("1,2026-10-15,08:00,16:00,3,SCHEDULED\n");
        bw8.write("2,2026-10-16,09:00,17:00,5,COMPLETED\n");
        bw8.close();
        fw8.close();

        // Feedback.txt
        FileWriter fw9 = new FileWriter("Feedback.txt");
        BufferedWriter bw9 = new BufferedWriter(fw9);
        bw9.write("F001,5,Excellent service and friendly staff,Wed Oct 15 14:30:00 MYT 2026\n");
        bw9.write("F002,3,Waiting time was longer than expected,Thu Oct 16 09:15:00 MYT 2026\n");
        bw9.close();
        fw9.close();

        // HospitalAsset.txt
        FileWriter fw10 = new FileWriter("HospitalAsset.txt");
        BufferedWriter bw10 = new BufferedWriter(fw10);
        bw10.write("001,ECG Machine,MEDICAL_EQUIPMENT,AVAILABLE,1\n");
        bw10.write("002,Wheelchair,FURNITURE,IN_USE,\n");
        bw10.close();
        fw10.close();

        // InsuranceNetwork.txt
        FileWriter fw11 = new FileWriter("InsuranceNetwork.txt");
        BufferedWriter bw11 = new BufferedWriter(fw11);
        bw11.write("001,AIA Malaysia,80.0,true\n");
        bw11.write("002,Great Eastern,60.0,true\n");
        bw11.close();
        fw11.close();

        // MedicalAssessment.txt
        FileWriter fw12 = new FileWriter("MedicalAssessment.txt");
        BufferedWriter bw12 = new BufferedWriter(fw12);
        bw12.write("AssessmentID: MA001\n");
        bw12.write("AssessmentDate: 2026-10-15\n");
        bw12.write("Temperature: 36.8\n");
        bw12.write("BloodPressure: 120/80\n");
        bw12.write("HeartRate: 72\n");
        bw12.write("Weight: 65.5\n");
        bw12.write("HealthGrade: NORMAL\n");
        bw12.write("Remarks: Patient is healthy.\n\n");
        bw12.write("AssessmentID: MA002\n");
        bw12.write("AssessmentDate: 2026-10-16\n");
        bw12.write("Temperature: 38.6\n");
        bw12.write("BloodPressure: 145/95\n");
        bw12.write("HeartRate: 108\n");
        bw12.write("Weight: 72.0\n");
        bw12.write("HealthGrade: MODERATE\n");
        bw12.write("Remarks: Mild fever and elevated heart rate.\n\n");
        bw12.close();
        fw12.close();

        // MedicalServiceRequest.txt
        FileWriter fw13 = new FileWriter("MedicalServiceRequest.txt");
        BufferedWriter bw13 = new BufferedWriter(fw13);
        bw13.write("SR001,LAB_TEST,2026-10-15,3,C001,Blood test,PENDING,,,,0.0\n");
        bw13.write("SR002,XRAY,2026-10-16,5,C002,Chest X-ray,COMPLETED,001,No abnormalities detected,2026-10-16,120.0\n");
        bw13.close();
        fw13.close();

        // Prescription.txt
        FileWriter fw14 = new FileWriter("Prescription.txt");
        BufferedWriter bw14 = new BufferedWriter(fw14);
        bw14.write("PrescriptionID: P001\n");
        bw14.write("MedicatioName: Paracetamol\n");
        bw14.write("Dosage: 500mg\n");
        bw14.write("Instruction: Take 1 tablet every 6 hours after meals.\n");
        bw14.write("IssuedDate: Wed Oct 15 14:30:00 MYT 2026\n\n");
        bw14.write("PrescriptionID: P002\n");
        bw14.write("MedicatioName: Amoxicillin\n");
        bw14.write("Dosage: 250mg\n");
        bw14.write("Instruction: Take 1 capsule three times daily for 5 days.\n");
        bw14.write("IssuedDate: Thu Oct 16 09:15:00 MYT 2026\n");
        bw14.close();
        fw14.close();

        // Consultation.txt (used by MedicalRecord.java)
        FileWriter fw15 = new FileWriter("Consultation.txt");
        BufferedWriter bw15 = new BufferedWriter(fw15);
        bw15.write("ConsultationID: C001\n");
        bw15.write("ConsultationDateTime: 2026-10-15 14:30:00\n");
        bw15.write("ConsultationNotes: Patient reports mild fever and headache.\n");
        bw15.write("Consultation: false\n\n");
        bw15.write("ConsultationID: C002\n");
        bw15.write("ConsultationDateTime: 2026-10-16 09:15:00\n");
        bw15.write("ConsultationNotes: Follow-up consultation completed successfully.\n");
        bw15.write("Consultation: true\n\n");
        bw15.close();
        fw15.close();

        // Prescriptions.txt (used by MedicalRecord.java)
        FileWriter fw16 = new FileWriter("Prescriptions.txt");
        BufferedWriter bw16 = new BufferedWriter(fw16);
        bw16.write("PrescriptionID: P001\n");
        bw16.write("MedicatioName: Paracetamol\n");
        bw16.write("Dosage: 500mg\n");
        bw16.write("Instruction: Take 1 tablet every 6 hours after meals.\n");
        bw16.write("IssuedDate: Wed Oct 15 14:30:00 MYT 2026\n\n");
        bw16.write("PrescriptionID: P002\n");
        bw16.write("MedicatioName: Amoxicillin\n");
        bw16.write("Dosage: 250mg\n");
        bw16.write("Instruction: Take 1 capsule three times daily for 5 days.\n");
        bw16.write("IssuedDate: Thu Oct 16 09:15:00 MYT 2026\n");
        bw16.close();
        fw16.close();

        // Service.txt (used by MedicalRecord.java)
        FileWriter fw17 = new FileWriter("Service.txt");
        BufferedWriter bw17 = new BufferedWriter(fw17);
        bw17.write("SR001,LAB_TEST,2026-10-15,3,C001,Blood test,PENDING,,,,0.0\n");
        bw17.write("SR002,XRAY,2026-10-16,5,C002,Chest X-ray,COMPLETED,001,No abnormalities detected,2026-10-16,120.0\n");
        bw17.close();
        fw17.close();
    }

public static void main(String[] args) throws IOException {
    createFiles();
    }
}