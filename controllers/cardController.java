package application.controllers;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.database.DataBase;
import application.database.ItemData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class cardController implements Initializable{
	
    @FXML
    private Button add_button;

    @FXML
    private Text card_category;

    @FXML
    private ImageView card_image;

    @FXML
    private Label card_name;

    @FXML
    private Label card_price;

    @FXML
    private AnchorPane item_card;

    private ItemData cardData;
    private Image image;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statment;
    private	ResultSet result;
    private Alert alert;
    
    public void setData(ItemData itemData) {
    	this.cardData = itemData;
    	
    	card_name.setText(itemData.getName());
    	card_price.setText(itemData.getPrice());
    	card_category.setText(itemData.getCategory());
    	String path = "File:"+itemData.getImage();
    	image = new Image(path,141,204,false,true);
    	card_image.setImage(image);
    }
    @FXML
    public void onAddToCart(){
    	connect = DataBase.connectDB();
		try {
    		String query = "INSERT INTO cart"
    				+ "(`name`, `price`, `category`, `image`)"
    				+ "VALUES (?,?,?,?)";
			prepare = connect.prepareStatement(query);
			
			prepare.setString(1, cardData.getName());
			prepare.setString(2, cardData.getPrice());
			prepare.setString(3, cardData.getCategory());
			prepare.setString(4, cardData.getImage());
			
			
			prepare.executeUpdate();
			
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("added to cart");
			alert.setHeaderText(null);
			alert.setContentText("Successfully Added");
			alert.show();

		}catch(Exception e) {e.printStackTrace();}
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
