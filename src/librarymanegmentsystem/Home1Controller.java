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
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import static librarymanegmentsystem.Users.data;
import org.controlsfx.control.Notifications;

/**
 *
 * @author kareem
 */
public class Home1Controller implements Initializable {

    @FXML
    private JFXButton btn_login;
    @FXML
    private JFXTextField Text_username;
    @FXML
    private JFXPasswordField Text_Password;
    @FXML
    private JFXButton sign_up;
    @FXML
    private Pane Pane;

    public static String[] name = new String[1];
    @FXML
    private JFXCheckBox Show;
    @FXML
    private JFXTextField textField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (!LibraryManegmentSystem.isloaded) {
            load_Welcome();
        }

        try {
            Users.Load();
        } catch (IOException ex) {
            Logger.getLogger(Home1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        textField.setManaged(false);
        textField.setVisible(false);

        Text_Password.managedProperty().bind(Show.selectedProperty().not());
        Text_Password.visibleProperty().bind(Show.selectedProperty().not());

        textField.managedProperty().bind(Show.selectedProperty());
        textField.visibleProperty().bind(Show.selectedProperty());

        textField.textProperty().bindBidirectional(Text_Password.textProperty());

        btn_login.setOnAction((ActionEvent event) -> {

            name[0] = Text_username.getText();

            if (Text_username.getText().equals("") || Text_Password.getText().equals("")) {
                Image img = new Image(getClass().getResourceAsStream("false.png"));
                Notifications notification = Notifications.create()
                        .title("Login Information")
                        .text("Failed Login or its not exist -_- ")
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

            if (Text_username.getText().toLowerCase().equals("admin") && Text_Password.getText().equals("123")) {
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
            } else if (Text_username.getText().toLowerCase().equals("admin") && !Text_Password.getText().equals("123")) {
                Image img = new Image(getClass().getResourceAsStream("false.png"));
                Notifications notification = Notifications.create()
                        .title("Login Information")
                        .text("Failed Login to Admin -_- ")
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

            for (Users col : data) {
                if (Text_username.getText().toLowerCase().equals(col.getUserName().toLowerCase()) && Text_Password.getText().equals(col.getPasword())) {
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
                } else if (Text_username.getText().toLowerCase().equals(col.getUserName().toLowerCase()) && !Text_Password.getText().equals(col.getPasword())) {
                    Image img = new Image(getClass().getResourceAsStream("false.png"));
                    Notifications notification = Notifications.create()
                            .title("Login Information")
                            .text("Failed Login or its not exist -_- ")
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
        });

        sign_up.setOnAction((ActionEvent event) -> {
            Parent p;
            try {
                p = FXMLLoader.load(getClass().getResource("SignUp1.fxml"));
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

    private void load_Welcome() {

        try {

            LibraryManegmentSystem.isloaded = true;

            StackPane stack = FXMLLoader.load(getClass().getResource("Welcome1.fxml"));
            Pane.getChildren().setAll(stack);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), stack);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeUp = new FadeTransition(Duration.seconds(2), stack);
            fadeUp.setFromValue(1);
            fadeUp.setToValue(0);
            fadeUp.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished(e -> {
                fadeUp.play();
            });

            fadeUp.setOnFinished((ActionEvent e) -> {
                Pane stack1;
                try {
                    stack1 = FXMLLoader.load(getClass().getResource("Home.fxml"));
                    Pane.getChildren().setAll(stack1);
                } catch (IOException ex) {
                    Logger.getLogger(Home1Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(Home1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
