package application.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import application.Main;
import application.database.DataBase;
import application.database.ItemData;


public class adminController implements Initializable  {
	
	public adminController() {}
	
    @FXML
    private Button cart_nav_button;
    @FXML
    private Button close_window;
    @FXML
    private Button delete_button;
    @FXML
    private TableView<ItemData> item_table;
    @FXML
    private ImageView image_preview;
    @FXML
    private Button menu_nav_button;
    @FXML
    private TextField name_input;
    @FXML
    private TextField price_input;
    @FXML
    private ComboBox<?> category_input;
    @FXML
    private Button import_button;
    private Image image;
    @FXML
    private Button add_button;
    @FXML
    private BorderPane window_pane;
    @FXML
    private TableColumn<ItemData, String> table_col_category;
    @FXML
    private TableColumn<ItemData, Integer> table_col_id;
    @FXML
    private TableColumn<ItemData, String> table_col_image;
    @FXML
    private TableColumn<ItemData, String> table_col_name;
    @FXML
    private TableColumn<ItemData, String> table_col_price;
    
    private Connection connect;
    private PreparedStatement prepare;
    private	ResultSet result;
    private Alert alert;

// DISPLAYING TABLE DATA --------------------------------------------------------------------
    public ObservableList<ItemData> tableDataList(){
    	ObservableList<ItemData> listData = FXCollections.observableArrayList();
    	String query = "SELECT * FROM item";
    	
    	connect = DataBase.connectDB();
    	
    	try {
    		prepare = connect.prepareStatement(query);
    		result = prepare.executeQuery();
    		
        	ItemData item ;

    		while(result.next()) {
    			item = new ItemData(result.getInt("id"),
    					result.getString("name"),result.getString("price"),
    					result.getString("category"),result.getString("image"));
    			listData.add(item);
    		}
    	}catch(Exception e) {e.printStackTrace();}
    	return listData;
    }
    private ObservableList<ItemData> tableListData;
    public void tableShowData() {
    	tableListData = tableDataList();
    	
    	table_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    	table_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	table_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    	table_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
    	table_col_image.setCellValueFactory(new PropertyValueFactory<>("image"));
    	
    	item_table.setItems(tableListData);
    }
    
    
// ADDING NEW ITEM TO DATABASE -------------------------------------------------------------
    @FXML
    void onAdd(ActionEvent event) {
    	if(name_input.getText().isEmpty()
    			|| price_input.getText().isEmpty()
    			|| category_input.getSelectionModel().getSelectedItem() == null
    			|| DataBase.path == null) {
    		alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error message");
    		alert.setHeaderText(null);
    		alert.setContentText("Please fill all blank fields");
    		alert.showAndWait();
    	}else {
    		connect = DataBase.connectDB();
    		try {
        		String query = "INSERT INTO item"
        				+ "(`name`, `price`, `category`, `image`)"
        				+ "VALUES (?,?,?,?)";
    			prepare = connect.prepareStatement(query);

    			prepare.setString(1, name_input.getText());
    			prepare.setString(2, price_input.getText() + " DA");
    			prepare.setString(3, (String)category_input.getSelectionModel().getSelectedItem());

    			String path = DataBase.path;
    			path = path.replace("\\", "\\\\");
    			
    			prepare.setString(4, path);
    			
    			prepare.executeUpdate();
    			
    			alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Added Successfully");
    			alert.setHeaderText(null);
    			alert.setContentText("Successfully Added");
    			alert.showAndWait();

    			tableShowData();

    		}catch(Exception e) {e.printStackTrace();}
    	}
    		
    		
    }
// IMPORTING IMAGE --------------------------------------------------------------------------
    @FXML
    void onImport(ActionEvent event) {
    	FileChooser openFile = new FileChooser();
    	openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File","*png","*jpg"));
    	File file = openFile.showOpenDialog(window_pane.getScene().getWindow());
    	
    	if(file != null) {
    		DataBase.path = file.getAbsolutePath();
    		image  = new Image(file.toURI().toString(), 135,146,false,true);
    		image_preview.setImage(image);
    	}
    }
    @FXML
    void onDelete(ActionEvent event) {

    }

    @FXML
    public void closeWindow(ActionEvent event) throws IOException {
        Stage stage  = (Stage) window_pane.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void navToMenu(ActionEvent event) throws IOException {
    	Main main = new Main();
    	main.changeScene("screens/menu_screen.fxml");
    }
    @FXML
    public void navToCart(ActionEvent event) throws IOException {
    	Main main = new Main();
    	main.changeScene("screens/cart_screen.fxml");
    }

	private String[] categoryList = {"Hamburgers","Pizzas","Pasta & Noodles","Sandwiches","Desserts","Drinks"};
	public void renderCategoryList() {
		List<String> categoryL = new ArrayList<>();
		for(String data:categoryList) {
			categoryL.add(data);
		}
		ObservableList listData = FXCollections.observableArrayList(categoryL);
		category_input.setItems(listData);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		renderCategoryList();
		tableShowData();
		
	}

}