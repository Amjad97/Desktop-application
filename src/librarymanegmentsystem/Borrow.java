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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Amjad
 */
public class Borrow implements Serializable {

    protected static ObservableList<Borrow> data = FXCollections.observableArrayList();

    private int id;
    private int userId;
    private String username;
    private String bookname;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private String dueDate;
    private String BorrowDate; 

    public Borrow(int id, int userId, String username, String bookname) {
       
        Date temp = new Date();
        temp.setTime(new Date().getTime() + 864000000);
        this.dueDate = dateFormat.format(temp);
        this.BorrowDate = dateFormat.format(new Date());
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.bookname = bookname;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBorrowDate() {
        return BorrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.BorrowDate = borrowDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public static void Save() throws IOException {

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Borrow.dat"));
        try {
            for (Borrow data1 : data) {
                out.writeObject(data1);
            }
            out.close();
            System.out.println("Borrowing is saving");

        } catch (Exception e) {
            System.out.println("Error in saving Borrowing");
        }
    }

    public static void Load() throws FileNotFoundException, IOException {
        data.clear();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Borrow.dat"));
        try {
            while (true) {
                data.add((Borrow) in.readObject());
            }
        } catch (Exception e) {

        }
    }

}
