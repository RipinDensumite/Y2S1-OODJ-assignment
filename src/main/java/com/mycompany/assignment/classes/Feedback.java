/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author xuheng
 */

package com.mycompany.assignment.classes;
import java.io.*;
import java.util.Date;
public class Feedback {
    private String feedbackId;
    private int rating;
    private String comment;
    private Date feedbackDate;
    private String patientId;
    private String doctorId;
    
    public Feedback(String patientID,int rating, String comment, String doctorId) throws IOException{
        feedbackId = makeNewId();
        this.patientId = patientID;
        this.rating = rating;
        this.comment = comment;
        feedbackDate = new Date();
        this.doctorId = doctorId;
    }
    
    
    
    public void submit() throws IOException{
          try (FileWriter fw = new FileWriter("Feedback.txt",true) //to avoid overwrite and append, put true
          ) {
              BufferedWriter bw = new BufferedWriter(fw);
              
              String FeedbackDetails = feedbackId+";"+patientId+";"+doctorId+";"+String.valueOf(rating)+";"+comment+";"+feedbackDate;
              bw.write(FeedbackDetails +"\n");
              bw.close();
          }catch(Exception e){
              System.out.println("Invalid input");
          }
    }
    
    public static void editComment(String FeedbackId, String Comment){
        try{
            String check = FeedbackId;
            FileReader fr = new FileReader("Feedback.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            String allData = "";
            while((line = br.readLine()) !=null){
                String[] data= line.split(",");

                if(data[0].equals(check)){
                    data[4] = Comment;
                    line = String.join(";", data);
                }

                allData += line +"\n";
            }

            br.close();
            fr.close();

            FileWriter fw = new FileWriter("Feedback.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(allData);
            bw.close();
            fw.close();

        }catch(Exception e){
            System.out.println("Invalid input.");
        }
    }
    private String makeNewId() throws IOException {
        File file = new File("Feedback.txt");
        if (!file.exists()) 
            return "1";
        int highest = 0;
        
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        
        while ((line = br.readLine()) != null) {
                String[] department = line.split(";", -1);
                String number = department[0];
                int id = Integer.parseInt(number.substring(1));
                if (id > highest){
                    highest = id;
                }
        }
        br.close();
        fr.close();
        return String.format("F%03d",highest + 1);
    }
}

