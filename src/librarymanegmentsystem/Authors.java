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
public class Authors implements Serializable {

    private int id;
    private String name;
    protected static ObservableList<Authors> data = FXCollections.observableArrayList();
    protected static ObservableList<String> Author_Name = FXCollections.observableArrayList();
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Authors(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void Save() throws IOException {

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Authors.dat"));
        try {
            for (Authors data1 : data) {
                out.writeObject(data1);
            }
            out.close();
            System.out.println("Author is saving");
        } catch (Exception e) {
            System.out.println("Error in saving Author");
        }
    }

    public static void Load() throws FileNotFoundException, IOException {
        data.clear();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Authors.dat"));
        try {
            while (true) {
                data.add((Authors) in.readObject());
            }
        } catch (Exception e) {
        }
    }

    public static void Save_Author_Name() throws IOException {

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("AuthorName.dat"));
        try {
            for (Authors data1 : data) {
                out.writeUTF(data1.getName());
            }
            out.close();
            System.out.println("Saving Author name");
        } catch (Exception e) {
            System.out.println("Error in saving");
        }
    }

    public static void Load_Author_Name() throws FileNotFoundException, IOException {
        Author_Name.clear();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("AuthorName.dat"));
        try {
            while (true) {
                Author_Name.add(in.readUTF());
            }
        } catch (Exception e) {
        }
    }

}
