/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentv1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author HP
 */
public class Authentication {
    private String email;
    private String password;
    
    public Authentication(String email, String pass){
        this.email=email;
        this.password=pass;
    }
    
    public String login() throws FileNotFoundException, IOException{
        Scanner sc = new Scanner(System.in);
        FileReader fr =new FileReader("accountlist.txt");
        BufferedReader br =new BufferedReader(fr);
        
        ArrayList<String[]> accounts=new ArrayList<>();
        String line;
        while ((line=br.readLine())!=null){
            accounts.add(line.split(","));
        }
        int i=0;
        while (i<accounts.size()){
            if ((accounts.get(i)[2]).equals(email)){
                if ((accounts.get(i)[3]).equals(password))
                    if ((accounts.get(i)[6].equals("true"))){
                        return accounts.get(i)[5];

                    }
                    else{
                        i++;
                    }
                else{
                    i++;
                }
            }
            else{
                i++;
            }
        }
        return "FAIL";
    }
}
