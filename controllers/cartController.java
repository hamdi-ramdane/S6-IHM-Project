package application.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.Main;
import application.database.DataBase;
import application.database.ItemData;


public class cartController implements Initializable {
	
	public cartController() {}
	
	@FXML
	private BorderPane window_pane;
	@FXML
	private Button close_window;
	@FXML
	private Button menu_nav_button;
	@FXML
	private Button admin_nav_button;
    @FXML
    private GridPane cart_grid;
    @FXML
    private Button checkout_button;
    @FXML
    private Button clear_button;
    @FXML
    private Label total_price;
	
    private Connection connect;
    private PreparedStatement prepare;
    private	ResultSet result;
    private Alert alert;
	private ObservableList<ItemData> cardListData = FXCollections.observableArrayList();
    private int totalP= 0;

// GETTING CART FOOD ITEMS FROM DATABASE ----------------------------------------------------------------
	public ObservableList<ItemData> getCartData(){
		String query = "SELECT * FROM cart";
		
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
				totalP += Integer.parseInt(price);
				listData.add(item);
			}
			
		}catch(Exception e) {e.printStackTrace();}
		return listData;
	}
// DISPLAY FOOD ITEMS IN CARD PANE ------------------------------------------------------------------------
	public void renderCartItems() {
		cardListData.clear();
		cardListData.addAll(getCartData());
		
		int row = 0;
		int col = 0;
		
		cart_grid.getRowConstraints().clear();
		cart_grid.getColumnConstraints().clear();

		for(int i = 0;i <cardListData.size();i++) {
			try {
				FXMLLoader load = new FXMLLoader();
				load.setLocation(getClass().getResource("../screens/cart_card.fxml"));
				AnchorPane pane = load.load();
				cartCardController cardController = load.getController();
				cardController.setData(cardListData.get(i));
				
				if(col == 1) {
					col = 0;
					row += 1;
				}
				cart_grid.add(pane,col++,row);
			}catch(Exception e) {e.printStackTrace();}
			total_price.setText("Total: " + String.valueOf(totalP) + " DA");
		}
	}
    @FXML
    void onCheckout(ActionEvent event) {
		alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Checked out!");
		alert.setHeaderText(null);
		alert.setContentText("Thank you for Visiting :)");
		alert.showAndWait();
		
    	clearCart();
    }

    @FXML
    void onClear(ActionEvent event) {
    	clearCart();
    	
    }
    public void closeWindow(ActionEvent event) throws IOException {
        Stage stage  = (Stage) window_pane.getScene().getWindow();
        stage.close();

    }
    public void navToMenu(ActionEvent event) throws IOException {
    	Main main = new Main();
    	main.changeScene("screens/menu_screen.fxml");
    }
    public void navToAdmin(ActionEvent event) throws IOException {
    	Main main = new Main();
    	main.changeScene("screens/admin_screen.fxml");
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		renderCartItems();			
	}
	public void clearCart() {
		total_price.setText("Total: 0.00 DA");
		cart_grid.getChildren().clear();
		
		String query = "DELETE FROM cart";
    	
    	connect = DataBase.connectDB();
    	
    	try {
    		prepare = connect.prepareStatement(query);
    		prepare.executeUpdate();
    	}catch(Exception e) {e.printStackTrace();}
	}
}