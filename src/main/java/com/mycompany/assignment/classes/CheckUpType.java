
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
public class CheckUpType {
    private String checkUpTypeId;
    private String name;
    private String description;
    private double baseRate;
    private boolean active;
    
    public CheckUpType(String checkUpTypeId, String name, String description, double baseRate){
        this.checkUpTypeId = checkUpTypeId;
        this.name = name;
        this.description = description;
        this.baseRate = baseRate;
        this.active = true;
    }
    
    public void InsertFile() throws IOException{
        FileWriter fw = new FileWriter("CheckUp.txt",true);
        BufferedWriter bw = new BufferedWriter(fw);
        
        String CheckUpDetails = checkUpTypeId+","+name+","+description+","+String.valueOf(baseRate)+","+String.valueOf(active);
        bw.write(CheckUpDetails +"\n");
        bw.close();
        fw.close();
    }
    
    public void updateDetails(String description){
        this.description = description;
        try{
            String check = checkUpTypeId;
            FileReader fr = new FileReader("CheckUp.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            String allData = "";
            while((line = br.readLine()) !=null){
                String[] data= line.split(",");

                if(data[0].equals(check)){
                    data[2] = this.description;
                    line = String.join(",", data);
                }

                allData += line +"\n";
            }

            br.close();
            fr.close();

            FileWriter fw = new FileWriter("CheckUp.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(allData);
            bw.close();
            fw.close();

        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void updateBaseRate(double rate){
        this.baseRate= rate;
        try{
            String check = checkUpTypeId;
            FileReader fr = new FileReader("CheckUp.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            String allData = "";
            while((line = br.readLine()) !=null){
                String[] data= line.split(",");

                if(data[0].equals(check)){
                    data[3] = String.valueOf(this.baseRate);
                    line = String.join(",", data);
                }

                allData += line +"\n";
            }

            br.close();
            fr.close();

            FileWriter fw = new FileWriter("CheckUp.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(allData);
            bw.close();
            fw.close();

        }catch(Exception e){
            System.out.println(e);
        }
    }
}
