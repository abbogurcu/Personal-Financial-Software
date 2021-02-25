package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class kurceviriciController implements Initializable{
    @FXML
    private Button kurCeviriciButton;

    @FXML
    private TextField kurCeviriciMiktari;

    @FXML
    private ComboBox<String> kurCeviriciComboBox;

    @FXML
    private Label kurCeviriciLabel;

    @FXML
    private Button geriButton;
    
    @FXML
    void kurCevirici(ActionEvent event) throws SQLException, IOException {
    	kurCeviriciCek(kurCeviriciComboBox.getValue(),kurCeviriciMiktari.getText());
    }

    @FXML
    void geri(ActionEvent event) throws IOException {
  	  Stage stage = (Stage) geriButton.getScene().getWindow();
  	  stage.hide();
  	  
  	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
  	Parent root=(Parent) fxmlLoader.load();
  	Stage stage2=new Stage();
  	stage2.setScene(new Scene(root));
  	stage2.show();
    }
  
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	    ObservableList<String> currenciesList = null;
	    try {
			currenciesList = kurlariCek();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	    kurCeviriciComboBox.setValue(currenciesList.get(0));
	    kurCeviriciComboBox.setItems(currenciesList);	
	    kurCeviriciButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent event) {
	        	try {
	        		kurCeviriciLabel.setText(kurCeviriciCek(kurCeviriciComboBox.getValue(),kurCeviriciMiktari.getText()).toString());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }     
	    }); 
	    	
	    }

	
	public ObservableList<String> kurlariCek() throws SQLException {
		ArrayList<String> currencies = new ArrayList();
		ResultSet myRs=null;
		Connection myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
		Statement myStat = (Statement) myConn.createStatement();
		myRs=myStat.executeQuery("select currencyName from usermoneyinfo where username='"+DBConnection.cekUsername()+"'");
		while(myRs.next()) {
			currencies.add(myRs.getString("currencyName"));
		}
		
		//-----------------------------------
		ObservableList<String> currency=FXCollections.observableArrayList(currencies);
		return currency;
	}
	
	public String kurCeviriciCek(String text,String text2) throws SQLException{
	   	ArrayList<String> list=new ArrayList();
    	Double a;
		Connection myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
		StringBuilder sb=new StringBuilder();
		boolean usdollar=false;
		boolean text2check=false;
    	DecimalFormat df = new DecimalFormat("#.####");
		Statement myStat = (Statement) myConn.createStatement();
		Double value2 = null;
		ResultSet myRs5=myStat.executeQuery("select currencyAmount from usermoneyinfo where username='"+DBConnection.cekUsername()+"' and currencyName='"+text+"'");
		while(myRs5.next()) {
			value2=myRs5.getDouble("currencyAmount");}
		if(Double.parseDouble(text2)>value2) {
			JOptionPane.showMessageDialog(null,"Çekebileceðiniz Miktari Aþtýnýz!\nSýnýr:"+value2+" "+(text),"Title",1);
			text2check=true;
		}
		if(text.contains("US DOLLAR")==true&&text2check==false) {
			Statement myStat1 = (Statement) myConn.createStatement();
			ResultSet myRs = myStat1.executeQuery("select * from currencyrates");
		        while(myRs.next()) {
		        	if(usdollar=false) {
		        		;
						sb.append(df.format(Double.parseDouble(text2)).toString()+" "+text+"\n");
						usdollar=true;
		        	}
		        if(!(myRs.getDouble("crossrate")==0.0)) {
				a=Double.parseDouble(text2)*myRs.getDouble("crossrate");
				sb.append(df.format(a).toString()+" "+myRs.getString("currencyName")+"\n");}
			}
		}
		
		else {
			if(text2check==false) {
			Statement myStat2 = (Statement) myConn.createStatement();
			ResultSet myRs2 = myStat2.executeQuery("select * from currencyrates where currencyName='"+text+"'");
			while(myRs2.next()) {
				Double b;
				a=Double.parseDouble(text2)*myRs2.getDouble("toTLbuying");
				
				Statement myStat3 = (Statement) myConn.createStatement();
				ResultSet myRs3 = myStat3.executeQuery("select * from currencyrates");
				while(myRs3.next()) {

				
					if(myRs3.getString("currencyName").contains("US DOLLAR")==true) {
						b=Double.parseDouble(text2)*1/myRs2.getDouble("crossrate");
						sb.append(df.format(b).toString()+" "+myRs3.getString("currencyName")+"\n");
					}
					if(!(myRs3.getString("currencyName").contains(text)==true)&&!(myRs3.getString("currencyName").contains("US DOLLAR")==true)) {
						b=a/myRs3.getDouble("toTLselling");
					sb.append(df.format(b).toString()+" "+myRs3.getString("currencyName")+"\n");}
				}
			}
		    }
		}
		return sb.toString();
	}
}

	
