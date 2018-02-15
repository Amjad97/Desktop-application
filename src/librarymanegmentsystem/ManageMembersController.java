/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanegmentsystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Amjad
 */
public class ManageMembersController implements Initializable {

    @FXML
    private Button Back;
    @FXML
    private TableView<Users> Users_Table;
    @FXML
    private JFXButton Delete;
    @FXML
    private JFXTextField Search;
    @FXML
    private StackPane StackPane;
    @FXML
    private JFXButton Send;
    JFXTextArea message = new JFXTextArea();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Users.Load();
        } catch (Exception e) {
        }

        TableColumn<Users, Integer> id = new TableColumn<>("ID");
        id.setMinWidth(50);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setStyle("-fx-alignment: CENTER;");

        TableColumn<Users, Integer> Full_Name = new TableColumn<>("Full name");
        Full_Name.setMinWidth(110);
        Full_Name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        Full_Name.setStyle("-fx-alignment: CENTER;");

        TableColumn<Users, String> Email = new TableColumn<>("Email");
        Email.setMinWidth(164);
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Email.setStyle("-fx-alignment: CENTER;");

        TableColumn<Users, String> password = new TableColumn<>("Password");
        password.setMinWidth(125);
        password.setCellValueFactory(new PropertyValueFactory<>("pasword"));
        password.setStyle("-fx-alignment: CENTER;");

        TableColumn<Users, String> username = new TableColumn<>("User name");
        username.setMinWidth(125);
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        username.setStyle("-fx-alignment: CENTER;");

        Users_Table.setItems(Users.data);
        Users_Table.getColumns().addAll(id, Full_Name, Email, password, username);

        FilteredList<Users> filteredData = new FilteredList<>(Users.data, p -> true);
        Search.setOnKeyReleased(e -> {
            Search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

                filteredData.setPredicate((Predicate<? super Users>) user -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (user.getFullName().toLowerCase().contains(newValue)) {
                        return true;
                    }
                    if (user.getUserName().toLowerCase().contains(newValue)) {
                        return true;
                    }

                    return false;
                });
            });

            SortedList<Users> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(Users_Table.comparatorProperty());
            Users_Table.setItems(sortedData);

        });

        Users_Table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Users>() {

            @Override
            public void changed(ObservableValue<? extends Users> observable, Users oldValue, Users newValue) {
                Delete.setOnAction((ActionEvent event) -> {

                    Users.data.remove(Users_Table.getSelectionModel().getSelectedItem());
                    Users_Table.refresh();
                    try {
                        Users.Save();
                    } catch (Exception e) {
                    }
                });
            }
        });

        Users_Table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Users>() {

            @Override
            public void changed(ObservableValue<? extends Users> observable, Users oldValue, Users newValue) {

                Send.setOnAction((ActionEvent event) -> {

                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Enter a message"));
                    content.setBody(message);

                    JFXDialog dialog_Add = new JFXDialog(StackPane, content, JFXDialog.DialogTransition.CENTER);

                    JFXButton done = new JFXButton("Done");
                    done.setOnAction((ActionEvent event1) -> {
                        try {
                            sendPost();
                        } catch (Exception ex) {
                            Logger.getLogger(ManageMembersController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        dialog_Add.close();
                        message.setText("");
                    });

                    content.setActions(done);
                    dialog_Add.show();

                });
            }
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

    private void sendPost() throws Exception {

        URL url = new URL("http://174.138.52.0:3000/send");
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("toEmail", Users_Table.getSelectionModel().getSelectedItem().getEmail());
        params.put("emailBody", message.getText());

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        for (int c; (c = in.read()) >= 0;) {            
            System.out.print((char)c);      
            
            if ((char)c == 'd') {
                Image img = new Image(getClass().getResourceAsStream("True.png"));
                Notifications notification = Notifications.create()
                        .title("Sending Information")
                        .text("\n Email has been sent")
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
                
            } else if ((char)c == 'r') {
                Image img = new Image(getClass().getResourceAsStream("false.png"));
                Notifications notification = Notifications.create()
                        .title("Sending Information")
                        .text("Error in connection ")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.TOP_CENTER)
                        .onAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                            }
                        });
                notification.darkStyle();
                notification.show();
            }
        }
        }

    }

