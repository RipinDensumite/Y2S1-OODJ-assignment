/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignmentv1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

 
public class generatetextfileforlistofaccountonlyrunonce {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args )throws IOException {
        
        FileWriter fw =new FileWriter("accountlist.txt");
        BufferedWriter bw =new BufferedWriter(fw);
        
        String defaultaccount=("001,ABU,ABU@GMAIL.COM,ABUPASSWORD,0123456789,ADMIN_STAFF,true\n002,ALI,ALI@GMAIL.COM,ALIPASSWORD,0132456789,MEDICAL_MANAGER,true;");
       
        bw.write(defaultaccount+"\n");

        bw.close();
        fw.close();

        
    }
    
}
