package application.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import application.database.DataBase;
import application.database.ItemData;



public class menuController implements Initializable{
	
	public menuController() {}
	
	@FXML
	private BorderPane window_pane;
	@FXML
	private Button close_window;
	@FXML
	private Button cart_nav_button;
	@FXML
	private Button admin_nav_button;
    @FXML
    private VBox item_list;
    @FXML
    private GridPane menu_grid;
    
    private Connection connect;
    private PreparedStatement prepare;
    private	ResultSet result;
    private Alert alert;
	private ObservableList<ItemData> cardListData = FXCollections.observableArrayList();

 // GETTING MENU FOOD ITEMS FROM DATABASE ----------------------------------------------------------------
 	public ObservableList<ItemData> getMenuData(){
 		String query = "SELECT * FROM item";
 		
 		ObservableList<ItemData> listData = FXCollections.observableArrayList();
 		connect = DataBase.connectDB();
 		try {
 			prepare = connect.prepareStatement(query);
 			result = prepare.executeQuery();
 			
 			ItemData item;
 			while(result.next()) {
 				item  = new ItemData(result.getInt("id"),result.getString("name"),
 						result.getString("price"),result.getString("category"),
 						result.getString("image"));
 				String price = result.getString("price");
 				price = price.substring(0, price.indexOf(" "));
 				listData.add(item);
 			}
 			
 		}catch(Exception e) {e.printStackTrace();}
 		return listData;
 	}
 // DISPLAY FOOD ITEMS --------------------------------------------------------------------------------
 	public void renderMenuItems() {
 		cardListData.clear();
 		cardListData.addAll(getMenuData());
 		
 		int row = 0;
 		int col = 0;
 		
 		menu_grid.getRowConstraints().clear();
 		menu_grid.getColumnConstraints().clear();

 		for(int i = 0;i <cardListData.size();i++) {
 			try {
 				FXMLLoader load = new FXMLLoader();
 				load.setLocation(getClass().getResource("../screens/item_card.fxml"));
 				AnchorPane pane = load.load();
 				cardController cardController = load.getController();
 				cardController.setData(cardListData.get(i));
 				
 				if(col == 2) {
 					col = 0;
 					row += 1;
 				}
 				menu_grid.add(pane,col++,row);
 			}catch(Exception e) {e.printStackTrace();}
 		}
 	}


    public void closeWindow(ActionEvent event) throws IOException {
        Stage stage  = (Stage) window_pane.getScene().getWindow();
        stage.close();
    }
    public void navToCart(ActionEvent event) throws IOException {
    	Main main = new Main();
    	main.changeScene("screens/cart_screen.fxml");
    }
    public void navToAdmin(ActionEvent event) throws IOException {
    	Main main = new Main();
    	main.changeScene("screens/admin_screen.fxml");
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		renderMenuItems();
	}
    
    

}
