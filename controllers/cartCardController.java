package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.database.ItemData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class cartCardController implements Initializable{
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
    
    public void setData(ItemData itemData) {
    	this.cardData = itemData;
    	
    	card_name.setText(itemData.getName());
    	card_price.setText(itemData.getPrice());
    	card_category.setText(itemData.getCategory());
    	String path = "File:"+itemData.getImage();
    	image = new Image(path,141,204,false,true);
    	card_image.setImage(image);
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
}
