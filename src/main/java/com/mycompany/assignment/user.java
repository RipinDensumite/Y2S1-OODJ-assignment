
package com.mycompany.assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public abstract class user {
    private String userId;
    private String FullName;
    private String email;
    private String password;
    private String phoneNumber;
    private UserRole role;
    private boolean active;
    
    public user(String userId, String FullName,String email,String password,String phoneNumber,UserRole role,boolean active) {
        this.userId = userId;
	this.FullName = FullName;
	this.email = email;
	this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.active = active;
	}
    public user() {
        this.userId = "test";
	this.FullName = "testname";
	this.email = "testemail";
	this.password = "testpassword";
        this.phoneNumber = "testphonenumber";
        this.role = UserRole.MEDICAL_MANAGER;
        this.active = true;
	}
    
    public boolean updateProfile(String FullName,String email,String phoneNumber){
        this.FullName = FullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        return true;
    }
    public boolean changePassword(String oldPassword, String newPassword){
        if (this.password!=oldPassword){
            return false;
        }
        else{
            this.password=newPassword;
            return true;
        }
    }
    public String[] getProfile(){
        String[]array={userId,FullName,password,phoneNumber,(role.toString()),(String.valueOf(active))};
        return(array);
    }
    public boolean login(String Username, String password) throws FileNotFoundException, IOException{
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
            if ((accounts.get(i)[1]).equals(Username)){
                if ((accounts.get(i)[3]).equals(password))
                    if ((accounts.get(i)[6].equals("true"))){
                        return true;
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
        return false;
        
    }
    public void logout(){
        this.userId = "";
	this.FullName = "";
	this.email = "";
	this.password = "";
        this.phoneNumber = "";
        this.role = UserRole.UNASSIGNED;
        this.active = false;
    }
}
enum UserRole{
    ADMIN_STAFF,
    MEDICAL_MANAGER,
    DOCTOR,
    PATIENT,
    UNASSIGNED
}