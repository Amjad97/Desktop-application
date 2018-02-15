/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanegmentsystem;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Amjad
 */
public class AdminController implements Initializable {

    @FXML
    private TableView<Borrow> Table_Members;
    @FXML
    private Pane Pane;
    @FXML
    private JFXHamburger Hamburger;
    @FXML
    private JFXDrawer Drawer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Borrow.Load();
        } catch (Exception e) {
        }
        
        Pane pane;
        try {
            pane = FXMLLoader.load(getClass().getResource("AdminOptions.fxml"));
            Drawer.setSidePane(pane);
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        HamburgerBackArrowBasicTransition task = new HamburgerBackArrowBasicTransition(Hamburger);
        task.setRate(-1);
        Hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            task.setRate(task.getRate() * -1);
            task.play();

            if (Drawer.isShown()) {
                Drawer.close();
            } else {
                Drawer.open();
            }
        });

        TableColumn<Borrow, Integer> id = new TableColumn<>("ID");
        id.setMinWidth(20);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setStyle("-fx-alignment: CENTER;");

        TableColumn<Borrow, Integer> user_Id = new TableColumn<>("User ID");
        user_Id.setMinWidth(50);
        user_Id.setCellValueFactory(new PropertyValueFactory<>("userId"));
        user_Id.setStyle("-fx-alignment: CENTER;");

        TableColumn<Borrow, Integer> user_name = new TableColumn<>("User Name");
        user_name.setMinWidth(80);
        user_name.setCellValueFactory(new PropertyValueFactory<>("username"));
        user_name.setStyle("-fx-alignment: CENTER;");

        TableColumn<Borrow, Integer> Book_name = new TableColumn<>("Book Name");
        Book_name.setMinWidth(75);
        Book_name.setCellValueFactory(new PropertyValueFactory<>("bookname"));
        Book_name.setStyle("-fx-alignment: CENTER;");

        TableColumn<Borrow, Date> Borrow_Date = new TableColumn<>("Borrow Date");
        Borrow_Date.setMinWidth(153);
        Borrow_Date.setCellValueFactory(new PropertyValueFactory<>("BorrowDate"));
        Borrow_Date.setStyle("-fx-alignment: CENTER;");

        TableColumn<Borrow, Date> Due_Date = new TableColumn<>("Due Date");
        Due_Date.setMinWidth(150);
        Due_Date.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        Due_Date.setStyle("-fx-alignment: CENTER;");

        Table_Members.getColumns().addAll(id, user_Id, user_name, Book_name, Borrow_Date, Due_Date);
        Table_Members.setItems(Borrow.data);

    }

}
