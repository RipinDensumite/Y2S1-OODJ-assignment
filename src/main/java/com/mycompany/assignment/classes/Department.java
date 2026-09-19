package com.mycompany.assignment.classes;
import java.io.*;
import java.util.ArrayList;

public class Department {
    private String departmentId;
    private String departmentName;
    private String specialty;

    public Department(String departmentName, String specialty) throws IOException {
        this.departmentId = makenewid();
        this.departmentName = departmentName;
        this.specialty = specialty;
        savetofile();
    }

    public Department(String departmentId) throws IOException {
        this.departmentId = departmentId;
        File file = new File("Department.txt");

        if (file.exists()) {
            FileReader fr = new FileReader("Department.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data[0].equals(departmentId)) {
                    this.departmentName = data[1];
                    this.specialty = data[2];
                    found = true;
                    break;
                }
            }

            br.close();
            fr.close();

            if (!found) {
                System.out.println("department id not found.");
            }
        } else {
            System.out.println("error");
        }
    }

    public void updatedetails(String departmentName, String specialty) throws IOException {
        this.departmentName = departmentName;
        this.specialty = specialty;
        updateDepartment();
    }

    // Append new department to Department.txt
    public void savetofile() throws IOException {
        FileWriter fw =new FileWriter("Department.txt");
        BufferedWriter bw =new BufferedWriter(fw);

        bw.write(departmentId + "," + departmentName + "," + specialty);
        bw.newLine();
        bw.close();
    }

    private void updateDepartment() throws IOException {
        FileReader fr =new FileReader("Department.txt");
        BufferedReader br =new BufferedReader(fr);
        ArrayList<String> records = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");

            if (data[0].equals(departmentId)) {
                records.add(departmentId + "," + departmentName + "," + specialty);
            } else {
                records.add(line);
            }
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter("Department.txt"));

        for (String record : records) {
            bw.write(record);
            bw.newLine();
        }

        bw.close();
    }

    private String makenewid() throws IOException {
        File file = new File("Department.txt");
        if (!file.exists()) {
            return "D01";
        }

        FileReader fr =new FileReader("Department.txt");
        BufferedReader br =new BufferedReader(fr);
        String line;
        int highest = 0;

        while ((line = br.readLine()) != null) {
            String[] departmentlist = line.split(",");

            int id = Integer.parseInt(departmentlist[0].substring(1));
            if (id > highest) {
                highest = id;
            }
        }

        br.close();

        return String.format("D%02d", highest + 1);
    }
}
