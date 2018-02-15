/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanegmentsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amjad
 */
public class ManageBooks1Controller implements Initializable {

    @FXML
    private JFXButton btn_Add;
    @FXML
    private JFXTextField Text_search;

    @FXML
    private JFXButton btn_Update;
    @FXML
    private JFXButton btn_Delete;
    @FXML
    private TableView<Books> Book_Table;
    @FXML
    private StackPane stackpane;
    @FXML
    private JFXButton Back;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Books.Load();
        } catch (IOException ex) {
        }

        TableColumn<Books, Integer> id = new TableColumn<>("ID");
        id.setMinWidth(75);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setStyle("-fx-alignment: CENTER;");

        TableColumn<Books, Integer> Author_id = new TableColumn<>("Author ID");
        Author_id.setMinWidth(100);
        Author_id.setCellValueFactory(new PropertyValueFactory<>("authorId"));
        Author_id.setStyle("-fx-alignment: CENTER;");

        TableColumn<Books, String> tital = new TableColumn<>("Tital");
        tital.setMinWidth(150);
        tital.setCellValueFactory(new PropertyValueFactory<>("title"));
        tital.setStyle("-fx-alignment: CENTER;");

        TableColumn<Books, String> status = new TableColumn<>("Status");
        status.setMinWidth(139);
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        status.setStyle("-fx-alignment: CENTER;");

        TableColumn<Books, String> Number_Books = new TableColumn<>("Number Of Books");
        Number_Books.setMinWidth(132);
        Number_Books.setCellValueFactory(new PropertyValueFactory<>("Number_Books"));
        Number_Books.setStyle("-fx-alignment: CENTER;");

        Book_Table.setItems(Books.data);
        Book_Table.getColumns().addAll(id, Author_id, tital, Number_Books, status);

        Book_Table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Books>() {

            @Override
            public void changed(ObservableValue<? extends Books> observable, Books oldValue, Books newValue) {

                btn_Update.setOnAction((ActionEvent event) -> {

                    VBox pane = new VBox();
                    pane.setPadding(new Insets(15, 5, 5, 5));

                    HBox ID = new HBox(15);
                    ID.setPadding(new Insets(15, 15, 15, 15));
                    ID.setSpacing(10);

                    HBox Author_ID = new HBox(15);
                    Author_ID.setPadding(new Insets(15, 15, 15, 15));
                    Author_ID.setSpacing(10);

                    HBox Book_Tital = new HBox(15);
                    Book_Tital.setPadding(new Insets(15, 15, 15, 15));
                    Book_Tital.setSpacing(10);

                    HBox Number_Book = new HBox(15);
                    Number_Book.setPadding(new Insets(15, 15, 15, 15));
                    Number_Book.setSpacing(10);

                    Label Book_ID = new Label("ID");
                    JFXTextField Book_id = new JFXTextField();

                    Label Authors_ID = new Label("Author ID");
                    JFXTextField Author_id = new JFXTextField();

                    Label Books_Tital = new Label("Tital");
                    JFXTextField Book_tital = new JFXTextField();

                    Label Number_Books = new Label("Number Of Book");
                    JFXTextField Numbers_Book = new JFXTextField();

                    ID.getChildren().add(0, Book_ID);
                    ID.getChildren().add(1, Book_id);

                    Author_ID.getChildren().add(0, Authors_ID);
                    Author_ID.getChildren().add(1, Author_id);

                    Book_Tital.getChildren().add(0, Books_Tital);
                    Book_Tital.getChildren().add(1, Book_tital);

                    Number_Book.getChildren().add(0, Number_Books);
                    Number_Book.getChildren().add(1, Numbers_Book);

                    pane.getChildren().add(0, ID);
                    pane.getChildren().add(1, Author_ID);
                    pane.getChildren().add(2, Book_Tital);
                    pane.getChildren().add(3, Number_Book);

                    Book_id.setText(String.valueOf(Book_Table.getSelectionModel().getSelectedItem().getId()));
                    Author_id.setText(String.valueOf(Book_Table.getSelectionModel().getSelectedItem().getAuthorId()));
                    Book_tital.setText(String.valueOf(Book_Table.getSelectionModel().getSelectedItem().getTitle()));
                    Numbers_Book.setText(String.valueOf(Book_Table.getSelectionModel().getSelectedItem().getNumber_Books()));

                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Update Book"));
                    content.setBody(pane);

                    JFXDialog dialog_Update = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.LEFT);

                    JFXButton done = new JFXButton("Done");

                    done.setOnAction((ActionEvent event1) -> {

                        dialog_Update.close();
                        Book_Table.getSelectionModel().getSelectedItem().setId(Integer.valueOf(Book_id.getText()));
                        Book_Table.getSelectionModel().getSelectedItem().setAuthorId(Integer.valueOf(Author_id.getText()));
                        Book_Table.getSelectionModel().getSelectedItem().setTitle(Book_tital.getText());
                        Book_Table.getSelectionModel().getSelectedItem().setNumber_Books(Integer.valueOf(Numbers_Book.getText()));
                        if(Integer.valueOf(Numbers_Book.getText()) > 0 )
                        Book_Table.getSelectionModel().getSelectedItem().setStatus("Available");
                        
                        Book_Table.refresh();

                        try {
                            Books.Save();
                        } catch (Exception e) {
                        }
                    });

                    content.setActions(done);
                    dialog_Update.show();

                });
            }
        });

        Book_Table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Books>() {

            @Override
            public void changed(ObservableValue<? extends Books> observable, Books oldValue, Books newValue) {

                btn_Delete.setOnAction((ActionEvent event) -> {
                    Books.data.remove(Book_Table.getSelectionModel().getSelectedItem());
                    Book_Table.refresh();
                    try {
                        Books.Save();
                    } catch (Exception e) {
                    }
                });
            }
        });

        FilteredList<Books> filteredData = new FilteredList<>(Books.data, p -> true);
        Text_search.setOnKeyReleased(e -> {
            Text_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

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

        Back.setOnAction((ActionEvent event) -> {
            Parent p;
            try {
                p = FXMLLoader.load(getClass().getResource("Admin11.fxml"));
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

    @FXML
    private void Load_Dialog(ActionEvent event) {

        try {
            Authors.Load_Author_Name();
        } catch (IOException ex) {
            Logger.getLogger(ManageBooks1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        VBox pane = new VBox();
        pane.setPadding(new Insets(15, 5, 5, 5));

        HBox Author = new HBox(15);
        Author.setPadding(new Insets(15, 15, 15, 15));
        Author.setSpacing(10);

        HBox Book = new HBox(15);
        Book.setPadding(new Insets(15, 15, 15, 15));
        Book.setSpacing(10);

        HBox Number_Book = new HBox(15);
        Number_Book.setPadding(new Insets(15, 15, 15, 15));
        Number_Book.setSpacing(10);

        Label Author_name = new Label("Author name");
        JFXComboBox Authors_name = new JFXComboBox();
        Authors_name.setItems(Authors.Author_Name);

        Label Books_name = new Label("Book name");
        JFXTextField Book_name = new JFXTextField();

        Label Number_Books = new Label("Number Of Book");
        JFXTextField Numbers_Book = new JFXTextField();

        Author.getChildren().add(0, Author_name);
        Author.getChildren().add(1, Authors_name);

        Book.getChildren().add(0, Books_name);
        Book.getChildren().add(1, Book_name);

        Number_Book.getChildren().add(0, Number_Books);
        Number_Book.getChildren().add(1, Numbers_Book);

        pane.getChildren().add(0, Book);
        pane.getChildren().add(1, Author);
        pane.getChildren().add(2, Number_Book);

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Add Book"));
        content.setBody(pane);

        JFXDialog dialog_Add = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.RIGHT);

        JFXButton done = new JFXButton("Done");
        done.setOnAction((ActionEvent event1) -> {
            dialog_Add.close();
            if(Integer.valueOf(Numbers_Book.getText()) > 0){
            Books.data.add(new Books(Books.data.size()+1, Authors_name.getSelectionModel().getSelectedIndex() + 1, Book_name.getText(), Integer.valueOf(Numbers_Book.getText()), "Available"));
            }else{
            Books.data.add(new Books(Books.data.size()+1, Authors_name.getSelectionModel().getSelectedIndex() + 1, Book_name.getText(), 0, "Unavailable"));                
            }
            try {
                Books.Save();
            } catch (IOException ex) {
                Logger.getLogger(ManageBooks1Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            Book_Table.refresh();
        });

        content.setActions(done);
        dialog_Add.show();

    }

}
