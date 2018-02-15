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
public class Books implements Serializable {

    private int id;
    private int authorId;
    private int Number_Books;
    private String title;
    private String status;
    protected static ObservableList<Books> data = FXCollections.observableArrayList();
    protected static ObservableList<String> data_Tital = FXCollections.observableArrayList();
   
    public Books(int id) {
        this.id = id;
    }

    public Books(int id, int authorId, String title,int Number_Books ,String status) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.Number_Books = Number_Books;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumber_Books(int Number_Books) {
        this.Number_Books = Number_Books;
    }

    public int getNumber_Books() {
        return Number_Books;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static void Save() throws IOException {

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Book.dat"));
        try {
            for (Books data1 : data) {
                out.writeObject(data1);
            }
            out.close();
            System.out.println("Book is saving");

        } catch (Exception e) {
            System.out.println("Error in saving Book");
        }
    }

    public static void Load() throws FileNotFoundException, IOException {
        data.clear();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Book.dat"));
        try {
            while (true) {
                data.add((Books) in.readObject());
            }
        } catch (Exception e) {

        }

    }

}
