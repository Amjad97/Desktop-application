/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanegmentsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Amjad
 */
public class SignUpController implements Initializable {

    @FXML
    private JFXButton Back;
    @FXML
    private JFXPasswordField Password;
    @FXML
    private JFXButton Create_Account;
    @FXML
    private JFXTextField Email;
    @FXML
    private JFXTextField user_name;
    @FXML
    private JFXTextField Full_name;
    @FXML
    private JFXCheckBox Show;
    @FXML
    private JFXTextField textField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        textField.setManaged(false);
        textField.setVisible(false);

        Password.managedProperty().bind(Show.selectedProperty().not());
        Password.visibleProperty().bind(Show.selectedProperty().not());

        textField.managedProperty().bind(Show.selectedProperty());
        textField.visibleProperty().bind(Show.selectedProperty());

        textField.textProperty().bindBidirectional(Password.textProperty());
        
        Create_Account.setOnAction((ActionEvent event) -> {

            if (Full_name.getText().equals("") || Email.getText().equals("") || Password.getText().equals("") || user_name.getText().equals("")) {

                Image img = new Image(getClass().getResourceAsStream("false.png"));
                Notifications notification = Notifications.create()
                        .title("Creation Information")
                        .text("Failed creating account -_- ")
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

            } else {
                Users.data.add(new Users(Users.data.size()+1, Full_name.getText(), Email.getText(), Password.getText(), user_name.getText()));

                try {
                    Users.Save();
                } catch (Exception e) {
                }

                Image img = new Image(getClass().getResourceAsStream("True.png"));
                Notifications notification = Notifications.create()
                        .title("Information of Creating")
                        .text("\n Creat Successfully")
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
        });

        Back.setOnAction((ActionEvent event) -> {
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
