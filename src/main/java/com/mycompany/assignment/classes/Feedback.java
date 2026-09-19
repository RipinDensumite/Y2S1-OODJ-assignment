/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
public class Feedback {
    private String feedbackId;
    private int rating;
    private String comment;
    private Date feedbackDate;
    
    public void submit() throws IOException{
        FileWriter fw = new FileWriter("Feedback.txt",true); //to avoid overwrite and append, put true
        BufferedWriter bw = new BufferedWriter(fw);
        
        String FeedbackDetails = feedbackId+","+String.valueOf(rating)+","+comment+","+feedbackDate;
        bw.write(FeedbackDetails +"\n");
        bw.close();
        fw.close();
    }
    
    public void editComment(String Comment){
        this.comment = Comment;
        try{
            String check = feedbackId;
            FileReader fr = new FileReader("Feedback.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            String allData = "";
            while((line = br.readLine()) !=null){
                String[] data= line.split(",");

                if(data[0].equals(check)){
                    data[2] = this.comment;
                    line = String.join(",", data);
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
            System.out.println(e);
        }
    }
}
