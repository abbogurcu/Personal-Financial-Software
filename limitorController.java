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
import javafx.scene.control.*;
import javafx.stage.Stage;

public class limitorController implements Initializable{
    @FXML
    private Button geriButton;

    @FXML
    private TextField satimLimit;

    @FXML
    private TextField alimLimit;

    @FXML
    private ComboBox<String> comBoBoxLimit;


    @FXML
    void limitKaldir(ActionEvent event) throws SQLException {
		String text = comBoBoxLimit.getValue();
		Connection myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
		Statement myStat = (Statement) myConn.createStatement();
		myStat.executeUpdate("delete from usermoneylimit where username='"+DBConnection.cekUsername()+"' and currencyName='"+text+"'");
		JOptionPane.showMessageDialog(null,"Limit kaldirildi!","Title",1);
    }
    
    @FXML
    void limitKoy(ActionEvent event) throws SQLException {
		String a = null,b = null;
    	
		String text = comBoBoxLimit.getValue();
		String text2 = alimLimit.getText();
		String text3 = satimLimit.getText();

		Connection myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
		
		Statement myStat = (Statement) myConn.createStatement();
		ResultSet myRs=myStat.executeQuery("select * from usermoneylimit where username='"+DBConnection.cekUsername()+"' and currencyName='"+text+"'");
		while(myRs.next()) {
                 a=myRs.getString("toTLbuyingLimit");
			     b=myRs.getString("toTLsellingLimit");
		}

		if(a==null||b==null) {		

		     	Statement myStat1 = (Statement) myConn.createStatement();
			    myStat1.executeUpdate("insert into usermoneylimit (username,currencyName,toTLbuyingLimit,toTLsellingLimit) values ('"+DBConnection.cekUsername()+"','"+text+"','"+text2+"','"+text3+"')");
			    JOptionPane.showMessageDialog(null,"Limit konuldu!","Title",1);
			}
		
		else {
			JOptionPane.showMessageDialog(null,"Limitlerinden biri önceden konulmuþ veya iki limiti de girmediniz. Lütfen öncelikle limitleri kaldirin!","Title",1);
		}
	}

    @FXML
    void geri(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
    	Parent root=(Parent) fxmlLoader.load();
    	Stage stage=new Stage();
    	stage.setScene(new Scene(root));
    	stage.show();
    	
    	  Stage stage2 = (Stage) geriButton.getScene().getWindow();
    	  stage2.hide();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    ObservableList<String> currenciesList = null;
		try {
			currenciesList = kurlariCek();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		comBoBoxLimit.setValue(currenciesList.get(0));
		comBoBoxLimit.setItems(currenciesList);
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
}
