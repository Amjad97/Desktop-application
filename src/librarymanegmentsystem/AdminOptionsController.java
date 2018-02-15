/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanegmentsystem;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amjad
 */
public class AdminOptionsController implements Initializable {
    
    @FXML
    private JFXButton btn_managemembers;
    @FXML
    private JFXButton btn_manageAuthors;
    @FXML
    private JFXButton btn_managebooks;
    @FXML
    private JFXButton Log_out;
    @FXML
    private AnchorPane Pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_managebooks.setOnAction((ActionEvent event) -> {
           Parent p;
            try {
                p = FXMLLoader.load(getClass().getResource("ManageBooks1.fxml"));
                Scene s = new Scene(p);
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.hide();
                stage.setScene(s);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btn_manageAuthors.setOnAction((ActionEvent event) -> {
           
            Parent p;
            try {
                p = FXMLLoader.load(getClass().getResource("ManageAuthors1.fxml"));
                Scene s = new Scene(p);
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.hide();
                stage.setScene(s);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btn_managemembers.setOnAction((ActionEvent event) -> {
            Parent p;
            try {
                p = FXMLLoader.load(getClass().getResource("ManageMembers1.fxml"));
                Scene s = new Scene(p);
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
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
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.hide();
                stage.setScene(s);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
}
