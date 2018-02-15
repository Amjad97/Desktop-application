/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanegmentsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import static librarymanegmentsystem.Users.data;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Amjad
 */
public class SearchController implements Initializable {

    @FXML
    private TableView<Books> Book_Table;
    @FXML
    private JFXButton Back;
    @FXML
    private JFXButton btn_Borrow;
    @FXML
    private JFXTextField Search;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Books.Load();
        } catch (Exception e) {
        }

        TableColumn<Books, Integer> id = new TableColumn<>("ID");
        id.setMinWidth(100);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setStyle("-fx-alignment: CENTER;");

        TableColumn<Books, Integer> Author_id = new TableColumn<>("Author ID");
        Author_id.setMinWidth(100);
        Author_id.setCellValueFactory(new PropertyValueFactory<>("authorId"));
        Author_id.setStyle("-fx-alignment: CENTER;");

        TableColumn<Books, String> tital = new TableColumn<>("Tital");
        tital.setMinWidth(173);
        tital.setCellValueFactory(new PropertyValueFactory<>("title"));
        tital.setStyle("-fx-alignment: CENTER;");

        TableColumn<Books, String> status = new TableColumn<>("Status");
        status.setMinWidth(173);
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        status.setStyle("-fx-alignment: CENTER;");

        Book_Table.setItems(Books.data);
        Book_Table.getColumns().addAll(id, Author_id, tital, status);

        FilteredList<Books> filteredData = new FilteredList<>(Books.data, p -> true);
        Search.setOnKeyReleased(e -> {
            Search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

                filteredData.setPredicate((Predicate<? super Books>) book -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (book.getTitle().toLowerCase().contains(newValue)) {
                        return true;
                    }
                    if (book.getStatus().toLowerCase().contains(newValue)) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<Books> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(Book_Table.comparatorProperty());
            Book_Table.setItems(sortedData);

        });

        Book_Table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Books>() {

            @Override
            public void changed(ObservableValue<? extends Books> observable, Books oldValue, Books newValue) {

                btn_Borrow.setOnAction((ActionEvent event) -> {

                    for (Users col : data) {
                        if (Home1Controller.name[0].toLowerCase().equals(col.getUserName().toLowerCase())) {
                            if (Book_Table.getSelectionModel().getSelectedItem().getStatus().toLowerCase().equals("available")) {
                                Image img = new Image(getClass().getResourceAsStream("True.png"));
                                Notifications notification = Notifications.create()
                                        .title("Borrowing Information")
                                        .text("Borrowing Successfully")
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
                                Borrow.data.add(new Borrow(Borrow.data.size() + 1, col.getId(), Home1Controller.name[0].toUpperCase(), Book_Table.getSelectionModel().getSelectedItem().getTitle()));
                                if (Book_Table.getSelectionModel().getSelectedItem().getNumber_Books() != 0) {
                                    Book_Table.getSelectionModel().getSelectedItem().setNumber_Books(Book_Table.getSelectionModel().getSelectedItem().getNumber_Books() - 1);
                                }
                                try {
                                    Borrow.Save();
                                } catch (IOException ex) {
                                    Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                    if (Book_Table.getSelectionModel().getSelectedItem().getStatus().toLowerCase().equals("unavailable")) {
                        Image img = new Image(getClass().getResourceAsStream("false.png"));
                        Notifications notification = Notifications.create()
                                .title("Borrowing Information")
                                .text("It's not Available")
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
                    }

                    if (Book_Table.getSelectionModel().getSelectedItem().getNumber_Books() == 0) {
                        Book_Table.getSelectionModel().getSelectedItem().setStatus("Unavailable");

                        try {
                            Books.Save();
                        } catch (IOException ex) {
                            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        Book_Table.getSelectionModel().getSelectedItem().setStatus("Available");
                        try {
                            Books.Save();
                        } catch (IOException ex) {
                            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    Book_Table.refresh();
                });
            }
        });

        Back.setOnAction((ActionEvent event) -> {
            Parent p;
            try {
                p = FXMLLoader.load(getClass().getResource("Members.fxml"));
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
