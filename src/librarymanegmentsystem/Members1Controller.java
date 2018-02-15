/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanegmentsystem;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import static librarymanegmentsystem.Users.data;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Amjad
 */
public class Members1Controller implements Initializable {

    private int i = 1;
    @FXML
    private JFXButton Search;
    @FXML
    private TableView<Borrow> Table_Borrowed;
    @FXML
    private JFXButton Log_out;
    @FXML
    private Pane Pane;
    @FXML
    private Label Label_name;
    @FXML
    private JFXButton Return;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (Users col : data) {
            if (Home1Controller.name[0].toLowerCase().equals(col.getUserName().toLowerCase())) {
                Label_name.setText(col.getFullName());
            }
        }

        try {
            Borrow.Load();
        } catch (IOException ex) {
            Logger.getLogger(Members1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        ObservableList<Borrow> user = FXCollections.observableArrayList();

        for (Borrow data1 : Borrow.data) {
            if (data1.getUsername().toLowerCase().equals(Home1Controller.name[0].toLowerCase())) {
                user.add(data1);
            }
        }
        for (int j = 0; j < user.size(); j++) {
            user.get(j).setId(i);
            i++;
        }

        TableColumn<Borrow, Integer> id = new TableColumn<>("ID");
        id.setMinWidth(50);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setStyle("-fx-alignment: CENTER;");

        TableColumn<Borrow, Integer> Book_name = new TableColumn<>("Book Name");
        Book_name.setMinWidth(105);
        Book_name.setCellValueFactory(new PropertyValueFactory<>("bookname"));
        Book_name.setStyle("-fx-alignment: CENTER;");

        TableColumn<Borrow, Date> Borrow_Date = new TableColumn<>("Borrow Date");
        Borrow_Date.setMinWidth(183);
        Borrow_Date.setCellValueFactory(new PropertyValueFactory<>("BorrowDate"));
        Borrow_Date.setStyle("-fx-alignment: CENTER;");

        TableColumn<Borrow, Date> Due_Date = new TableColumn<>("Due Date");
        Due_Date.setMinWidth(184);
        Due_Date.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        Due_Date.setStyle("-fx-alignment: CENTER;");

        Table_Borrowed.setItems(user);
        Table_Borrowed.getColumns().addAll(id, Book_name, Borrow_Date, Due_Date);

        Table_Borrowed.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Borrow>() {

            @Override
            public void changed(ObservableValue<? extends Borrow> observable, Borrow oldValue, Borrow newValue) {

                Return.setOnAction((ActionEvent event) -> {

                    for (Borrow data1 : Borrow.data) {
                        ObservableList<Borrow> data = FXCollections.observableArrayList(Borrow.data);
                        if (data1.getBorrowDate().equals(Table_Borrowed.getSelectionModel().getSelectedItem().getBorrowDate())) {
                            data.remove(data1);
                            Borrow.data = data;
                        }
                    }
                    int j = 1;
                    for (Borrow data1 : Borrow.data) {
                        data1.setId(j);
                        j++;
                    }
                    
                    Table_Borrowed.refresh();
                    for (Books data1 : Books.data) {
                        if (data1.getTitle().equals(Table_Borrowed.getSelectionModel().getSelectedItem().getBookname())) {
                            data1.setNumber_Books(data1.getNumber_Books() + 1);
                            data1.setStatus("Available");
                        }
                    }

                    Image img = new Image(getClass().getResourceAsStream("True.png"));
                    Notifications notification = Notifications.create()
                            .title("Back up Information")
                            .text("Back up Successfully")
                            .graphic(new ImageView(img))
                            .hideAfter(Duration.seconds(4))
                            .position(Pos.TOP_CENTER)
                            .onAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                }
                            });
                    notification.darkStyle();
                    notification.show();

                    try {
                        Borrow.Save();
                    } catch (IOException ex) {
                    }
                    try {
                        Books.Save();
                    } catch (Exception e) {
                    }
                });
            }
        });

        Search.setOnAction((ActionEvent event) -> {
            Parent p;
            try {
                p = FXMLLoader.load(getClass().getResource("Search1.fxml"));
                Scene s = new Scene(p);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.hide();
                stage.setScene(s);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Log_out.setOnAction((ActionEvent event) -> {
            Parent p;
            try {
                p = FXMLLoader.load(getClass().getResource("Home.fxml"));
                Scene s = new Scene(p);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.hide();
                stage.setScene(s);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
