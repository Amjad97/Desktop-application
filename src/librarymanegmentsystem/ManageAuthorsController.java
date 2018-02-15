/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanegmentsystem;

import com.jfoenix.controls.JFXButton;
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
public class ManageAuthorsController implements Initializable {

    @FXML
    private StackPane stackpane;
    @FXML
    private JFXButton btn_Delete;
    @FXML
    private JFXButton btn_Update;
    @FXML
    private TableView<Authors> Authors_Table;
    @FXML
    private JFXTextField Text_search;
    @FXML
    private JFXButton btn_Add;
    @FXML
    private Button Back;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Authors.Load();
        } catch (Exception e) {
        }

        TableColumn<Authors, String> id = new TableColumn<>("ID");
        id.setMinWidth(200);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setStyle("-fx-alignment: CENTER;");

        TableColumn<Authors, String> name = new TableColumn<>("Name");
        name.setMinWidth(368);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setStyle("-fx-alignment: CENTER;");

        Authors_Table.setItems(Authors.data);
        Authors_Table.getColumns().addAll(id, name);

        Authors_Table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Authors>() {

            @Override
            public void changed(ObservableValue<? extends Authors> observable, Authors oldValue, Authors newValue) {

                btn_Update.setOnAction((ActionEvent event) -> {

                    VBox pane = new VBox();
                    pane.setPadding(new Insets(15, 5, 5, 5));

                    HBox ID = new HBox(15);
                    ID.setPadding(new Insets(15, 15, 15, 15));
                    ID.setSpacing(10);

                    HBox Author_Name = new HBox(15);
                    Author_Name.setPadding(new Insets(15, 15, 15, 15));
                    Author_Name.setSpacing(10);

                    Label Author_ID = new Label("ID");
                    JFXTextField Author_id = new JFXTextField();

                    Label Authors_name = new Label("Name");
                    JFXTextField Author_name = new JFXTextField();

                    ID.getChildren().add(0, Author_ID);
                    ID.getChildren().add(1, Author_id);

                    Author_Name.getChildren().add(0, Authors_name);
                    Author_Name.getChildren().add(1, Author_name);

                    pane.getChildren().add(0, ID);
                    pane.getChildren().add(1, Author_Name);

                    Author_id.setText(String.valueOf(Authors_Table.getSelectionModel().getSelectedItem().getId()));
                    Author_name.setText(String.valueOf(Authors_Table.getSelectionModel().getSelectedItem().getName()));

                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Update Author"));
                    content.setBody(pane);
                    
                    JFXDialog dialog_Update = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.LEFT);

                    JFXButton done = new JFXButton("Done");

                    done.setOnAction((ActionEvent event1) -> {

                        dialog_Update.close();
                        Authors_Table.getSelectionModel().getSelectedItem().setId(Integer.valueOf(Author_id.getText()));
                        Authors_Table.getSelectionModel().getSelectedItem().setName(Author_name.getText());
                        Authors_Table.refresh();
                    
                        Authors_Table.refresh();
                        try {
                            Authors.Save();
                        } catch (Exception e) {
                        }
                    });
                    content.setActions(done);
                    dialog_Update.show();
                });
            }
        });

        Authors_Table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Authors>() {

            @Override
            public void changed(ObservableValue<? extends Authors> observable, Authors oldValue, Authors newValue) {

                btn_Delete.setOnAction((ActionEvent event) -> {
                    Authors.data.remove(Authors_Table.getSelectionModel().getSelectedItem());
                    Authors_Table.refresh();
                    try {
                        Authors.Save();
                    } catch (Exception e) {
                    }
                    try {
                        Authors.Save_Author_Name();
                    } catch (IOException ex) {
                        Logger.getLogger(ManageAuthorsController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
            }
        });

        FilteredList<Authors> filteredData = new FilteredList<>(Authors.data, p -> true);
        Text_search.setOnKeyReleased(e -> {
            Text_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

                filteredData.setPredicate((Predicate<? super Authors>) Author -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (Author.getName().toLowerCase().contains(newValue)) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<Authors> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(Authors_Table.comparatorProperty());
            Authors_Table.setItems(sortedData);

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

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Add Author"));

        JFXDialog dialog_Add = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.RIGHT);

        HBox pane = new HBox();
        pane.setSpacing(5);
        pane.setPadding(new Insets(15, 15, 15, 15));
        Label Author_name = new Label("Author name");
        JFXTextField name = new JFXTextField();
        pane.getChildren().addAll(Author_name, name);

        Authors_Table.refresh();
        content.setBody(pane);

        JFXButton done = new JFXButton("Done");
        done.setOnAction((ActionEvent event1) -> {
            
                Authors.data.add(new Authors(Authors.data.size()+1, name.getText()));            
            try {
                Authors.Save();
            } catch (IOException ex) {
                Logger.getLogger(ManageAuthorsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Authors.Save_Author_Name();
            } catch (IOException ex) {
                Logger.getLogger(ManageAuthorsController.class.getName()).log(Level.SEVERE, null, ex);
            }

            dialog_Add.close();
            Authors_Table.refresh();
        });
        content.setActions(done);

        dialog_Add.show();
    }

}
