/**
 *
 * @author harith
 */

package com.mycompany.assignment.classes;
import java.io.*;
import java.util.ArrayList;

public class Department {
    
    private String departmentId;
    private String departmentName;
    private String specialty;
    private Doctor[] doctors;
        
    public Department(String departmentName, String specialty, Doctor[] doctors) throws IOException {
        this.departmentId = makeNewId();
        this.departmentName = departmentName;
        this.specialty = specialty;
        this.doctors = validateDoctors(doctors);
        saveToFile();
    }
    public Department(String departmentId) throws IOException {
        File file = new File("Department.txt");
        if (!file.exists()) {
            throw new IOException("department.txt does not exist or file not found");
        }
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 3 && data[0].equals(departmentId)) {
                    this.departmentId = data[0];
                    this.departmentName = data[1];
                    this.specialty = data[2];
                    this.doctors = loadDoctors(data);
                    br.close();
                    fr.close();
                    return;
                }
        }
        br.close();
        fr.close();
        throw new IllegalArgumentException("id not found" + departmentId);
    }

    public String getDepartmentId() { 
        return departmentId; 
    }
    public String getDepartmentName() { 
        return departmentName; 
    }
    public String getSpecialty() { 
        return specialty; 
    }
    public Doctor[] getDoctors() { 
        return doctors; 
    }

    public static ArrayList<Department> getAllDepartments() throws IOException {
        ArrayList<Department> departments = new ArrayList<>();
        FileReader fr = new FileReader("Department.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            String[] department = line.split(",");
            Department currentDepartment = new Department(department[0]);
            departments.add(currentDepartment);
        }
        br.close();
        fr.close();
        return departments;
    }

    public void updateDetails(String departmentName, String specialty) throws IOException {
        this.departmentName = departmentName;
        this.specialty = specialty;
        updateDepartment();
    }

    public void updateDoctors(Doctor[] doctors) throws IOException {
        if (doctors.length > 5) {
            System.out.println("max 5 doctor.");
            return;
        }
        this.doctors = doctors;
        updateDepartment();
    }

    public void addDoctor(Doctor doctor) throws IOException {
        if (doctors.length >= 5) {
            System.out.println("max 5 doctor.");
            return;
        }
        Doctor[] updatedDoctors = new Doctor[doctors.length + 1];
        for (int i = 0; i < doctors.length; i++) {
            updatedDoctors[i] = doctors[i];
        }
        updatedDoctors[doctors.length] = doctor;
        this.doctors = updatedDoctors;
        updateDepartment();
    }

    public boolean removeDoctor(String doctorId) throws IOException {
        ArrayList<Doctor> remaining = new ArrayList<>();
        boolean removed = false;
        for (Doctor doctor : doctors) {
            if (doctor.getUserId().equals(doctorId)){
                removed = true;
            }
            else {
                remaining.add(doctor);
            }
        }
        if (removed) {
            updateDoctors(remaining.toArray(new Doctor[0]));
        }
        
        return removed;
    }

    public void saveToFile() throws IOException {
        FileWriter fw = new FileWriter("Department.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write(toRecord());
        bw.newLine();
        bw.close();
        fw.close();
    }

    private void updateDepartment() throws IOException {
        File file = new File("Department.txt");
        ArrayList<String> records = new ArrayList<>();
        boolean found = false;
        if (file.exists()) {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                    String[] data = line.split(",", -1);
                    if (data.length > 0 && data[0].equals(departmentId)) {
                        records.add(toRecord());
                        found = true;
                    } 
                    else {
                        records.add(line);
                    }
            }
            br.close();
            fr.close();
        }
        if (!found) {
            records.add(toRecord());
        }
        
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String record : records) {
            bw.write(record);
            bw.newLine();
        }
        bw.close();
        fw.close();
    }

    private String toRecord() {
        String record = departmentId + "," + departmentName + "," + specialty;
        for (Doctor doctor : doctors) {
            record = record + "," + doctor.getUserId();
        }
        return record;
    }

    private Doctor[] loadDoctors(String[] data) throws IOException {
        ArrayList<Doctor> loadedDoctors = new ArrayList<>();
        for (int index = 3; index < data.length && loadedDoctors.size() < 5; index++) {
            if (data[index].isBlank()) {
                continue;
            }
            Doctor doctor = findDoctor(data[index]);
            if (doctor != null) {
                loadedDoctors.add(doctor);
            }
        }
        return loadedDoctors.toArray(new Doctor[0]);
    }

    private Doctor findDoctor(String doctorId) throws IOException {
        File accounts = new File("accountlist.txt");
        if (!accounts.exists()) {
            return null;
        }
        FileReader fr = new FileReader(accounts);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",", -1);
            if (data.length >= 7 && data[0].equals(doctorId) && "DOCTOR".equals(data[5])) {
                Doctor doctor = new Doctor(data[0], data[1], data[2], data[3], data[4], Boolean.parseBoolean(data[6]),data[7]);
                br.close();
                fr.close();
                return doctor;
                }
        }
        br.close();
        fr.close();
        return null;
    }

    private Doctor[] validateDoctors(Doctor[] doctors) {
        if (doctors == null) {
            return new Doctor[0];
        }
        if (doctors.length > 5) {
            throw new IllegalArgumentException("A department can have at most 5 doctors.");
        }
        return doctors;
    }

    private String makeNewId() throws IOException {
        File file = new File("Department.txt");
        if (!file.exists()) 
            return "1";
        int highest = 0;
        
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        
        while ((line = br.readLine()) != null) {
                String[] department = line.split(",", -1);
                int id = Integer.parseInt(department[0]);
                if (id > highest){
                    highest = id;
                }
        }
        br.close();
        fr.close();
        return String.valueOf(highest + 1);
    }
   

}
