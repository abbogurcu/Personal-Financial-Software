package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class yeniparagirisiController implements Initializable{
	
	static Connection myConn;
	static Statement myStat;

	
    @FXML
    private ComboBox<String> paraCinsiComboBox;
    
    @FXML
    private ComboBox<String> paraCinsiComboBox2;
    
    @FXML
    private ComboBox<String> paraCinsiComboBox3;
    
    @FXML
    private ComboBox<String> paraCinsiComboBox4;
    
    @FXML
    private ComboBox<String> paraCinsiComboBox5;
    
    @FXML
    private TextField paraMiktari;
    @FXML
    private TextField paraMiktari2;
    @FXML
    private TextField paraMiktari3;
    
    @FXML
    private Button geriProfileButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    ObservableList<String> currenciesList = null;
		try {
			currenciesList = kurlariCek();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		paraCinsiComboBox.setValue(currenciesList.get(0));
		paraCinsiComboBox.setItems(currenciesList);
		
		paraCinsiComboBox2.setValue(currenciesList.get(0));
		paraCinsiComboBox2.setItems(currenciesList);
		
		paraCinsiComboBox3.setValue(currenciesList.get(0));
		paraCinsiComboBox3.setItems(currenciesList);
		
		paraCinsiComboBox4.setValue(currenciesList.get(0));
		paraCinsiComboBox4.setItems(currenciesList);
		
		paraCinsiComboBox5.setValue(currenciesList.get(0));
		paraCinsiComboBox5.setItems(currenciesList);
		
		
	}
    
	
	public ObservableList<String> kurlariCek() throws SQLException {
		ArrayList<String> currencies = new ArrayList();
		ResultSet myRs=null;
		myConn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
		myStat=(Statement) myConn.createStatement();
		myRs=myStat.executeQuery("select currencyName from currency");
		while(myRs.next()) {
			currencies.add(myRs.getString("currencyName"));
		}
		
		//-----------------------------------
		ObservableList<String> currency=FXCollections.observableArrayList(currencies);
		return currency;
	}
	
    @FXML
    void geriProfile(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
    	Parent root=(Parent) fxmlLoader.load();
    	Stage stage=new Stage();
    	stage.setScene(new Scene(root));
    	stage.show();
    	
    	  Stage stage2 = (Stage) geriProfileButton.getScene().getWindow();
    	  stage2.hide();
    }
	
    @FXML
    void paraYatir(ActionEvent event) throws SQLException {
		String text = paraCinsiComboBox.getValue();
		Double value = Double.parseDouble(paraMiktari.getText());
		DBConnection.paraYatir(text, value);
    }

    @FXML
    void paraCek(ActionEvent event) throws SQLException {
		String text = paraCinsiComboBox2.getValue();
		String text2 = paraCinsiComboBox3.getValue();
		Double value = Double.parseDouble(paraMiktari2.getText());
		DBConnection.paraCek(text,text2,value);
    }

    @FXML
    void tlCEK(ActionEvent event) throws SQLException {
		String text = paraCinsiComboBox2.getValue();
		Double value = Double.parseDouble(paraMiktari2.getText());
		DBConnection.tlCek(text,value);
    }

    @FXML
    void paraDonustur(ActionEvent event) throws SQLException {
		String text = paraCinsiComboBox4.getValue();
		Double value = Double.parseDouble(paraMiktari3.getText());
		String text2 = paraCinsiComboBox5.getValue();
		DBConnection.kurDonustur(text,value,text2);
    }
}
