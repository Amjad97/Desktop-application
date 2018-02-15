/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanegmentsystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Amjad
 */
public class Users implements Serializable{

    private int id;
    private String fullName;
    private String email;
    private String pasword;
    private String userName;
    protected static ObservableList<Users> data = FXCollections.observableArrayList();

    public Users(int id, String fullName, String email, String pasword, String userName) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.pasword = pasword;
        this.userName = userName;
   }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static void Save() throws IOException {
        
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Users.dat"));
        try {
            for (Users data1 : data) {
                out.writeObject(data1);
            }
            out.close();
            System.out.println("User is saving");

        } catch (Exception e) {
            System.out.println("Error in saving User");
        }
    }

    public static void Load() throws FileNotFoundException, IOException {
        data.clear();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Users.dat"));
        try {
            while (true) {
                data.add((Users) in.readObject()); 
            }
        } catch (Exception e) {
        
        }
    }
    
}
