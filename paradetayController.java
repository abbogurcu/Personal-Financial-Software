package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class paradetayController implements Initializable{
	
    @FXML
    private Button geriButton;
	
    @FXML
    private TableView<paraDetayTable> paraDetayTable;
    
    @FXML
    private TableColumn<paraDetayTable,String> currencyName;
    
    @FXML
    private TableColumn<paraDetayTable, Double> currencyAmount;
    
    @FXML
    private TableColumn<paraDetayTable, Double> toBuyingTL;

    @FXML
    private TableColumn<paraDetayTable, Double> toSellingTL;

    @FXML
    private TableColumn<paraDetayTable, Double> crossrate;
    
    @FXML
    private PieChart pieChart;

    ObservableList<paraDetayTable> oblist=FXCollections.observableArrayList();
    
    ObservableList<PieChart.Data> oblist2=FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String a = DBConnection.cekUsername();
		Connection myConn = null;
		try {
			myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
			ResultSet myRs=myConn.createStatement().executeQuery("select * from usermoneyinfo where username='"+a+"'");
			while(myRs.next()) {
				if(myRs.getString("currencyName")!=null) {
					String currencyName=myRs.getString("currencyName");
					Double currencyAmount=myRs.getDouble("currencyAmount");
					LocalDate date = java.time.LocalDate.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
					String formattedString = date.format(formatter);
					ResultSet myRs2=myConn.createStatement().executeQuery("select * from currencyrates where currencyName='"+myRs.getString("currencyName")+"' and date='"+formattedString+"'");
					while(myRs2.next()) {
						myRs2.getDouble("toTLbuying");
						myRs2.getDouble("toTLselling");
						myRs2.getDouble("crossrate");
						oblist.add(new paraDetayTable(currencyName,currencyAmount,myRs2.getDouble("toTLbuying"),myRs2.getDouble("toTLselling"),myRs2.getDouble("crossrate")));
						oblist2.add(new PieChart.Data(currencyAmount+" "+currencyName,currencyAmount*myRs2.getDouble("toTLbuying")));
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		currencyName.setCellValueFactory(new PropertyValueFactory<>("currencyName"));
		currencyAmount.setCellValueFactory(new PropertyValueFactory<>("currencyAmount"));
		toBuyingTL.setCellValueFactory(new PropertyValueFactory<>("toBuyingTL"));
		toSellingTL.setCellValueFactory(new PropertyValueFactory<>("toSellingTL"));
		crossrate.setCellValueFactory(new PropertyValueFactory<>("crossrate"));
		
		paraDetayTable.setItems(oblist);
		pieChart.setData(oblist2);
		
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
}
