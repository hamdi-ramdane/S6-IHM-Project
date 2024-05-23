package application.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.ResultSet;

import application.Main;
import application.database.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class loginController {
	
	public loginController() {}
	
	@FXML
	private BorderPane window_pane;
	@FXML
	private Button close_window;
    @FXML
    private TextField email_input;
    @FXML
    private PasswordField password_input;
	@FXML
	private Button login_button;
    @FXML
    private Label login_label;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    
    public void closeWindow(ActionEvent event) throws IOException {
        Stage stage  = (Stage) window_pane.getScene().getWindow();
        stage.close();
    }
    public void checkLogin(ActionEvent event) throws IOException {   
    	String query = "SELECT username , password FROM user WHERE username = ? and password = ?";
    	
    	connect = DataBase.connectDB();	
    	
    	try {
    		prepare = connect.prepareStatement(query);
    		prepare.setString(1, email_input.getText());
    		prepare.setString(2, password_input.getText());

    		result = prepare.executeQuery();
    		
    		if(result.next()){
                Parent root = FXMLLoader.load(getClass().getResource("../screens/menu_screen.fxml"));

        		Scene scene = new Scene(root);

        		Stage stage = new Stage();
        		stage.setScene(scene);
        		stage.initStyle(StageStyle.UNDECORATED);
        		Main.stage = stage;
        		Main.stage.show();
            	
                Stage currentStage  = (Stage) window_pane.getScene().getWindow();
                currentStage.close();
    		}else {
                login_label.setText("Wrong Credentials!!");
    		}
    		
    	}catch(Exception e) {e.printStackTrace();}

    }
}
