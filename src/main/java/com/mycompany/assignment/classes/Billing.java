
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
public class Billing {
    private String billId;
    private LocalDate billingDate;
    private double consultationFee;
    private double serviceFee;
    private double insuranceDeduction;
    private double totalAmount;
    private BillingStatus Status;
    
    public Billing(String billId, double consultationFee, double serviceFee, double insuranceDeduction){
        this.billId = billId;
        this.billingDate = LocalDate.now();
        this.consultationFee = consultationFee;
        this.serviceFee = serviceFee;
        this.insuranceDeduction = insuranceDeduction;
        calculateTotal();
        this.Status = BillingStatus.PENDING;
    }
    
    public Billing(){
        this.billId = "01";
        this.billingDate = LocalDate.now();
        this.consultationFee = 1;
        this.serviceFee = 1;
        this.insuranceDeduction = 1;
        calculateTotal();
        this.Status = BillingStatus.PENDING;
    }
    
    public void InsertFile() throws IOException{
        FileWriter fw = new FileWriter("Billing.txt",true);
        BufferedWriter bw = new BufferedWriter(fw);
        
        String BillingDetails = billId+","+String.valueOf(billingDate)+","+String.valueOf(consultationFee)+","+String.valueOf(serviceFee)+","+String.valueOf(insuranceDeduction)+","+String.valueOf(totalAmount)+","+Status;
        bw.write(BillingDetails +"\n");
        bw.close();
        fw.close();
    }
    
    public void calculateTotal(){
        totalAmount = consultationFee+serviceFee-insuranceDeduction;
    }
    
    public void markPaid(){
        try{
        String check = billId;
        Status = BillingStatus.PAID;
        FileReader fr = new FileReader("Billing.txt");
        BufferedReader br = new BufferedReader(fr);
        
        String line;
        String allData = "";
        while((line = br.readLine()) !=null){
            String[] data= line.split(",");

            if(data[0].equals(check)){
                data[6] = Status.toString();
                line = String.join(",", data);
            }

            allData += line +"\n";
        }
        
        br.close();
        fr.close();
        
        FileWriter fw = new FileWriter("Billing.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write(allData);
        bw.close();
        fw.close();
        
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
}

enum BillingStatus{
    PENDING,
    PAID
}
