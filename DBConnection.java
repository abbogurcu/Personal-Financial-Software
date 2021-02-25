package application;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.beans.Observable;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class DBConnection{



	
    @FXML
    private TextField textField;
    
    @FXML
    private TextField textField_1;
    
    @FXML
    private Label label;
    
    
    @FXML
    void ee1515(ActionEvent event) {

    }
    
    @FXML
    private TextField textField_3;

    @FXML
    private TextField textField_2;

    @FXML
    private TextField textField_5;

    @FXML
    private TextField textField_4;

    @FXML
    private Button button;
    
    @FXML
    private Button button2;
    
    @FXML
    private Button button3;
    
    @FXML
    private Button button4;
    
    


    @FXML
    private Button kurCeviriciButton;

    @FXML
    private Button dataCekButton;

    @FXML
    private Button yeniParaGirisiButton;

    @FXML
    private Button limitorButton;

    @FXML
    private Button kurGecmisiBilgisiButton;

    @FXML
    private Button paraDetayButton;
   


    
    static Connection myConn;
	static Statement myStat;
	static PreparedStatement myPreStat;
	static DatabaseMetaData dbm;
	public static String usernameGLOBAL;
	static String username;
	static String password;
	

    @FXML
    void dataCek(ActionEvent event) throws SQLException {
    	getDatafromWebsite.verileriCek();
    }

    @FXML
    void yeniParaGirisi(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("yeniparagirisi.fxml"));
    	Parent root=(Parent) fxmlLoader.load();
    	Stage stage=new Stage();
    	stage.setScene(new Scene(root));
    	stage.show();
    	
    	  Stage stage2 = (Stage) yeniParaGirisiButton.getScene().getWindow();
    	  stage2.hide();
    }

    @FXML
    void paraDetay(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paradetay.fxml"));
    	Parent root=(Parent) fxmlLoader.load();
    	Stage stage=new Stage();
    	stage.setScene(new Scene(root));
    	stage.show();
    	
    	  Stage stage2 = (Stage) paraDetayButton.getScene().getWindow();
    	  stage2.hide();
    }

    @FXML
    void kurCevirici(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("kurcevirici.fxml"));
    	Parent root=(Parent) fxmlLoader.load();
    	Stage stage=new Stage();
    	stage.setScene(new Scene(root));
    	stage.show();
    	
    	  Stage stage2 = (Stage) kurCeviriciButton.getScene().getWindow();
    	  stage2.hide();
    }

    @FXML
    void kurGecmisiBilgisi(ActionEvent event) {

    }

    @FXML
    void limitor(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("limitor.fxml"));
    	Parent root=(Parent) fxmlLoader.load();
    	Stage stage=new Stage();
    	stage.setScene(new Scene(root));
    	stage.show();
    	
    	  Stage stage2 = (Stage) limitorButton.getScene().getWindow();
    	  stage2.hide();
    }

    @FXML
    void login(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
    	Parent root=(Parent) fxmlLoader.load();
    	Stage stage=new Stage();
    	stage.setScene(new Scene(root));
    	stage.show();
    	
    	  Stage stage2 = (Stage) button.getScene().getWindow();
    	  stage2.hide();
    }

    @FXML
    void register(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
    	Parent root=(Parent) fxmlLoader.load();
    	Stage stage=new Stage();
    	stage.setScene(new Scene(root));
    	stage.show();
    	
  	  Stage stage2 = (Stage) button2.getScene().getWindow();
  	  stage2.hide();
    }
    
    @FXML
    void kayitOl(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String username=textField_2.getText();
        String encryptedPassword=CryptWithMD5.cryptWithMD5(textField_3.getText());
		String address=textField_4.getText();
		int year=Integer.parseInt(textField_5.getText());
        register(username,encryptedPassword,address,year);
    }

    @FXML
    void geri(ActionEvent event) throws IOException {
    	  Stage stage = (Stage) button4.getScene().getWindow();
      	  stage.hide();
      	  
      	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
      	Parent root=(Parent) fxmlLoader.load();
      	Stage stage2=new Stage();
      	stage2.setScene(new Scene(root));
      	stage2.show();
    }
    
	
	public String getUsername() {return usernameGLOBAL;}
	public void setUsername(String usernameGLOBAL) {DBConnection.usernameGLOBAL=usernameGLOBAL;}
	
    @FXML
    void logincheck(ActionEvent event) throws IOException, SQLException {
		String username=textField.getText();
        String encryptedPassword=CryptWithMD5.cryptWithMD5(textField_1.getText());
        
		if(loginchecked(username,encryptedPassword)==true) {
		  	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
		  	Parent root=(Parent) fxmlLoader.load();
		  	Stage stage=new Stage();
		  	stage.setScene(new Scene(root));
		  	stage.show();
	    	
	    	 Stage stage2 = (Stage) button3.getScene().getWindow();
	      	 stage2.hide();
	      	 checkLimit();
		}
		else {
			label.setOpacity(1);
		}
    }

	public static boolean loginchecked(String username,String password) {
		ResultSet myRs=null;
		boolean a=false;
		usernameGLOBAL=username;
		try {
			myConn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
			myStat=(Statement) myConn.createStatement();
			myRs=myStat.executeQuery("select * from user where username='"+username+"' and password='"+password+"'");
			while(myRs.next())
				if(!myRs.isBeforeFirst()) {
					a=true;
					JOptionPane.showMessageDialog(null,"Giriþ Baþarýlý!","Title",1);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
	
	public static String cekUsername() {
		return usernameGLOBAL;
	}
	
	public static void register(String username,String password,String address,int year) throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			myConn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			boolean a=false;
			ResultSet myRs=null;
			myConn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
			myStat=(Statement) myConn.createStatement();
			myRs=myStat.executeQuery("select * from user where username='"+username+"'");
			while(myRs.next()) {
				if(!myRs.isBeforeFirst()) {
					JOptionPane.showMessageDialog(null,"Böyle bir kullanici adi mevcut!","Title",1);
					a=true;
				}
			}
			if(a==false) {
				myPreStat=(PreparedStatement) myConn.prepareStatement("insert into user (username,password,address,year) values (?,?,?,?)");
				myPreStat.setString(1,username);
				myPreStat.setString(2,password);
				myPreStat.setString(3,address);
				myPreStat.setInt(4,year);
				myPreStat.executeUpdate();
				JOptionPane.showMessageDialog(null,"Kayýt Baþarýlý!","Title",1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void paraYatir(String currencyName,Double value) throws SQLException {
		ResultSet myRs=null;
		myConn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
		myStat=(Statement) myConn.createStatement();
		myRs=myStat.executeQuery("select currencyAmount from usermoneyinfo where username='"+usernameGLOBAL+"' and currencyName='"+currencyName+"'");
		Double value2=0.0;
		boolean a=false;
		//----------------------------------
		while(myRs.next()) {
			value2=myRs.getDouble("currencyAmount");
			double sonuc=value2+value;
		
		if(!myRs.isBeforeFirst()) {
		myPreStat=(PreparedStatement) myConn.prepareStatement("UPDATE usermoneyinfo SET currencyAmount=? WHERE username=? and currencyName=?");
		myPreStat.setDouble(1,sonuc);
		myPreStat.setString(2,usernameGLOBAL);
		myPreStat.setString(3,currencyName);
		myPreStat.execute();
		JOptionPane.showMessageDialog(null,"Para Yatýrýldý!\nToplam Miktar:"+(sonuc)+" "+(currencyName),"Title",1);
		a=true;
		}
		
	    }
        if(a==false) {
        	if(value==0) {			
        		JOptionPane.showMessageDialog(null,(value)+" "+(currencyName)+ " Yatýrýlýmaz!","Title",1);
        	}else{
			myPreStat=(PreparedStatement) myConn.prepareStatement("insert into usermoneyinfo (username,currencyName,currencyAmount) values (?,?,?)");
			myPreStat.setString(1,usernameGLOBAL);
			myPreStat.setString(2,currencyName);
			myPreStat.setDouble(3,value);
			myPreStat.execute();
			JOptionPane.showMessageDialog(null,"Para Yatýrýldý!\nMiktar:"+(value)+" "+(currencyName),"Title",1);
			}
		}
	}
	

	
	public static void paraCek(String currencyName,String currencyName2,Double value) throws SQLException {
		
		double toTLbuying=0.0,toTLselling=0.0,crossrate=0.0,sonuc=0.0,sonuc2=0.0,sonuc3=0.0,value2=0.0;
		ResultSet myRs=null,myRs2=null;
		boolean a=false;
		
		
		myConn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
		myStat=(Statement) myConn.createStatement();
		myRs=myStat.executeQuery("select currencyAmount from usermoneyinfo where username='"+usernameGLOBAL+"' and currencyName='"+currencyName+"'");
		while(myRs.next()) {
			value2=myRs.getDouble("currencyAmount");
			sonuc2=value2-value;
		}
		
		//SINIR AÞILIRSA
		if(value2==0.0) {			
			JOptionPane.showMessageDialog(null,(currencyName)+" kuruna ait paraniz yok!","Title",1);
		    a=true;
		}

		if(value>value2&&a==false) {
			JOptionPane.showMessageDialog(null,"Çekebileceðiniz Miktari Aþtýnýz!\nSýnýr:"+value2+" "+(currencyName),"Title",1);
		}
		else if(value==value2&&a==false){
	           if(currencyName==currencyName2){
			   //AYNI KUR PARA ÇEKÝLECEKSE
	   			       JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar:"+(value)+" "+(currencyName),"Title",1);
	    	    //------------------------------  	
		        }else {
		        //FARKLI KUR PARA ÇEKÝLECEKSE
		        	
		        	if(currencyName.contains("US DOLLAR")==true) {
			    		myStat=(Statement) myConn.createStatement();
			    		myRs=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName2+"'");
			    		while(myRs.next()) {
			    			crossrate=myRs.getDouble("crossrate");
			    			sonuc=crossrate*value;
			    			}
			   			 JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar: "+(sonuc)+" "+(currencyName2),"Title",1);
		        	}
		        	
		        	
		        	else if(currencyName2.contains("US DOLLAR")==true) {
			    		myStat=(Statement) myConn.createStatement();
			    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName+"'");
			    		while(myRs2.next()) {
			    			crossrate=myRs2.getDouble("crossrate");
			    			sonuc=(1/crossrate)*value;
			    			}
			   			 JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar: "+(sonuc)+" "+(currencyName),"Title",1);
		        	}
		        	
		        	
		        	else {
		    		myStat=(Statement) myConn.createStatement();
		    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName+"'");
		    		while(myRs2.next()) {
		    			toTLbuying=myRs2.getDouble("toTLbuying");
		    			sonuc=toTLbuying*value;
		    		}
		    		
		    		myStat=(Statement) myConn.createStatement();
		    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName2+"'");
		    		while(myRs2.next()) {
		    			toTLselling=myRs2.getDouble("toTLselling");
		    			sonuc3=sonuc/toTLselling;
			        }
	   			    JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar:"+(sonuc3)+" "+(currencyName2),"Title",1);
	   			    }
			    //SINIR AÞILMAZSA DEÐERLER EÞÝTSE
		        }
		    	myStat=(Statement) myConn.createStatement();
		    	myStat.executeUpdate("delete from usermoneyinfo where username='"+usernameGLOBAL+"' and currencyName='"+currencyName+"'");
		    	JOptionPane.showMessageDialog(null,"Paranýzý Sýfýrladýnýz!","Title",1);

	           }
		else {
			if(a==false) {
	           if(currencyName==currencyName2){
			   //AYNI KUR PARA ÇEKÝLECEKSE
	   			       JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar:"+(value)+" "+(currencyName),"Title",1);
	    	    //------------------------------  	
		        }else {
		        //FARKLI KUR PARA ÇEKÝLECEKSE
		        	
		        	if(currencyName.contains("US DOLLAR")==true) {
			    		myStat=(Statement) myConn.createStatement();
			    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName2+"'");
			    		while(myRs2.next()) {
			    			crossrate=myRs2.getDouble("crossrate");
			    			sonuc=crossrate*value;
			    			}
			   			 JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar:"+(sonuc)+" "+(currencyName2),"Title",1);
		        	}
		        	
		        	
		        	else if(currencyName2.contains("US DOLLAR")==true) {
			    		myStat=(Statement) myConn.createStatement();
			    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName+"'");
			    		while(myRs2.next()) {
			    			crossrate=myRs2.getDouble("crossrate");
			    			sonuc=(1/crossrate)*value;
			    			}
			   			 JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar:"+(sonuc)+" "+(currencyName),"Title",1);
		        	}
		        	
		        	
		        	else {
		    		myStat=(Statement) myConn.createStatement();
		    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName+"'");
		    		while(myRs2.next()) {
		    			toTLbuying=myRs2.getDouble("toTLbuying");
		    		}
	    			sonuc=toTLbuying*value;
		    		
		    		myStat=(Statement) myConn.createStatement();
		    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName2+"'");
		    		while(myRs2.next()) {
		    			toTLselling=myRs2.getDouble("toTLselling");
			        }
	    			sonuc3=sonuc/toTLselling;
	   			    JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar:"+(sonuc3)+" "+(currencyName2),"Title",1);
	   			    }
		        } 
		    	myStat=(Statement) myConn.createStatement();
		    	myStat.executeUpdate("UPDATE usermoneyinfo SET currencyAmount='"+sonuc2+"' WHERE username='"+usernameGLOBAL+"' and currencyName='"+currencyName+"'");
		    	JOptionPane.showMessageDialog(null,"Kalan Miktar:"+(sonuc2)+" "+(currencyName),"Title",1);
		    }
		}
	}
	
	public static void tlCek(String currencyName,Double value) throws SQLException {
		double toTLbuying=0.0,toTLselling=0.0,sonuc=0.0,sonuc2=0.0,value2=0.0;
		ResultSet myRs=null,myRs2=null;
		boolean a=false;
		
		
		myConn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
		myStat=(Statement) myConn.createStatement();
		myRs=myStat.executeQuery("select currencyAmount from usermoneyinfo where username='"+usernameGLOBAL+"' and currencyName='"+currencyName+"'");
		while(myRs.next()) {
			value2=myRs.getDouble("currencyAmount");
			sonuc2=value2-value;
		}
		
		//SINIR AÞILIRSA
		if(value2==0.0) {			
			JOptionPane.showMessageDialog(null,(currencyName)+" kuruna ait paraniz yok!","Title",1);
		    a=true;
		}

		if(value>value2&&a==false) {
			JOptionPane.showMessageDialog(null,"Çekebileceðiniz Miktari Aþtýnýz!\nSýnýr:"+value2+" "+(currencyName),"Title",1);
		}
		else if(value==value2&&a==false){		    		myStat=(Statement) myConn.createStatement();
			    		myRs=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName+"'");
			    		while(myRs.next()) {
			    			toTLbuying=myRs.getDouble("toTLbuying");
			    			sonuc=toTLbuying*value;
			    			}
			   			 JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar: "+(sonuc)+" Turkish Liras","Title",1);
    	myStat=(Statement) myConn.createStatement();
    	myStat.executeUpdate("delete from usermoneyinfo where username='"+usernameGLOBAL+"' and currencyName='"+currencyName+"'");
    	JOptionPane.showMessageDialog(null,"Paranýzý Sýfýrladýnýz!","Title",1);        
    	}
    	else {
			if(a==false) {
		    		myStat=(Statement) myConn.createStatement();
		    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName+"'");
		    		while(myRs2.next()) {
		    			toTLbuying=myRs2.getDouble("toTLbuying");
		    		}
	    			sonuc=toTLbuying*value;

	   			    JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar:"+(sonuc)+" Turkish Liras","Title",1);

		    	myStat=(Statement) myConn.createStatement();
		    	myStat.executeUpdate("UPDATE usermoneyinfo SET currencyAmount='"+sonuc2+"' WHERE username='"+usernameGLOBAL+"' and currencyName='"+currencyName+"'");
		    	JOptionPane.showMessageDialog(null,"Kalan Miktar:"+(sonuc2)+" "+(currencyName),"Title",1);	   			    
		    	}
		    	}
		 }
	
	public static void kurDonustur(String currencyName,Double value,String currencyName2) throws SQLException {
		double toTLbuying=0.0,toTLselling=0.0,crossrate=0.0,sonuc=0.0,sonuc2=0.0,sonuc3=0.0,value2=0.0,currencyName2Value=0.0;
		ResultSet myRs=null,myRs2=null;
		boolean a=false,b=false;
		
		
		myConn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
		myStat=(Statement) myConn.createStatement();
		myRs=myStat.executeQuery("select currencyAmount from usermoneyinfo where username='"+usernameGLOBAL+"' and currencyName='"+currencyName+"'");
		while(myRs.next()) {
			value2=myRs.getDouble("currencyAmount");
			sonuc2=value2-value;
		}
		
		myConn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
		myStat=(Statement) myConn.createStatement();
		myRs=myStat.executeQuery("select currencyAmount from usermoneyinfo where username='"+usernameGLOBAL+"' and currencyName='"+currencyName2+"'");
		while(myRs.next()) {
			currencyName2Value=myRs.getDouble("currencyAmount");
		}
		
		//SINIR AÞILIRSA
		if(value2==0.0) {			
			JOptionPane.showMessageDialog(null,(currencyName)+" kuruna ait paraniz yok!","Title",1);
		    a=true;
		}

		if(value>value2&&a==false) {
			JOptionPane.showMessageDialog(null,"Çekebileceðiniz Miktari Aþtýnýz!\nSýnýr:"+value2+" "+(currencyName),"Title",1);
		}
		else if(value==value2&&a==false){
	           if(currencyName==currencyName2){
	        	   b=true;
			   //AYNI KUR PARA ÇEKÝLECEKSE
	   			       JOptionPane.showMessageDialog(null,"Ayný kuru çekmeye çalýþýyorsunuz!","Title",1);
	    	    //------------------------------  	
		        }else {
		        //FARKLI KUR PARA ÇEKÝLECEKSE
		        	
		        	if(currencyName.contains("US DOLLAR")==true) {
			    		myStat=(Statement) myConn.createStatement();
			    		myRs=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName2+"'");
			    		while(myRs.next()) {
			    			crossrate=myRs.getDouble("crossrate");
			    			sonuc=crossrate*value;
			    			}
			   			 JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar: "+(sonuc)+" "+(currencyName2),"Title",1);
					    	if(currencyName2Value>0&&b==false) {
						    	myStat.executeUpdate("UPDATE usermoneyinfo SET currencyAmount='"+(currencyName2Value+sonuc)+"' WHERE username='"+usernameGLOBAL+"' and currencyName='"+currencyName2+"'");
					    	}else {if(b==false) {myStat.executeUpdate("insert into usermoneyinfo (username,currencyName,currencyAmount) values ('"+usernameGLOBAL+"','"+currencyName2+"','"+sonuc+"')");}}
			   			 
		        	}
		        	
		        	
		        	else if(currencyName2.contains("US DOLLAR")==true) {
			    		myStat=(Statement) myConn.createStatement();
			    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName+"'");
			    		while(myRs2.next()) {
			    			crossrate=myRs2.getDouble("crossrate");
			    			sonuc=(1/crossrate)*value;
			    			}
			   			 JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar: "+(sonuc)+" "+(currencyName),"Title",1);
			   			 
					    	if(currencyName2Value>0&&b==false) {
						    	myStat.executeUpdate("UPDATE usermoneyinfo SET currencyAmount='"+(currencyName2Value+sonuc)+"' WHERE username='"+usernameGLOBAL+"' and currencyName='"+currencyName2+"'");
					    	}else {if(b==false) {myStat.executeUpdate("insert into usermoneyinfo (username,currencyName,currencyAmount) values ('"+usernameGLOBAL+"','"+currencyName2+"','"+sonuc+"')");}}
			   			 
			   			 
		        	}
		        	
		        	
		        	
		        	else {
		    		myStat=(Statement) myConn.createStatement();
		    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName+"'");
		    		while(myRs2.next()) {
		    			toTLbuying=myRs2.getDouble("toTLbuying");
		    			sonuc=toTLbuying*value;
		    		}
		    		
		    		myStat=(Statement) myConn.createStatement();
		    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName2+"'");
		    		while(myRs2.next()) {
		    			toTLselling=myRs2.getDouble("toTLselling");
		    			sonuc3=sonuc/toTLselling;
			        }
	   			    JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar:"+(sonuc3)+" "+(currencyName2),"Title",1);
	   			    
			    	if(currencyName2Value>0&&b==false) {
				    	myStat.executeUpdate("UPDATE usermoneyinfo SET currencyAmount='"+(currencyName2Value+sonuc3)+"' WHERE username='"+usernameGLOBAL+"' and currencyName='"+currencyName2+"'");
			    	}else {if(b==false) {myStat.executeUpdate("insert into usermoneyinfo (username,currencyName,currencyAmount) values ('"+usernameGLOBAL+"','"+currencyName2+"','"+sonuc3+"')");}}

		        	}
	   			    
		        }
			    //SINIR AÞILMAZSA DEÐERLER EÞÝTSE
		    	myStat=(Statement) myConn.createStatement();
		    	myStat.executeUpdate("delete from usermoneyinfo where username='"+usernameGLOBAL+"' and currencyName='"+currencyName+"'");
		    	JOptionPane.showMessageDialog(null,"Paranýzý Sýfýrladýnýz!","Title",1);
		        }

		else {
			if(a==false) {
	           if(currencyName==currencyName2){
	        	   b=true;
			   //AYNI KUR PARA ÇEKÝLECEKSE
   			       JOptionPane.showMessageDialog(null,"Ayný kuru çekmeye çalýþýyorsunuz!","Title",1);
	    	    //------------------------------  	
		        }else {
		        //FARKLI KUR PARA ÇEKÝLECEKSE
		        	
		        	if(currencyName.contains("US DOLLAR")==true) {
			    		myStat=(Statement) myConn.createStatement();
			    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName2+"'");
			    		while(myRs2.next()) {
			    			crossrate=myRs2.getDouble("crossrate");
			    			sonuc=crossrate*value;
			    			}
			   			 JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar:"+(sonuc)+" "+(currencyName2),"Title",1);
			   			 
					    	if(currencyName2Value>0&&b==false) {
						    	myStat.executeUpdate("UPDATE usermoneyinfo SET currencyAmount='"+(currencyName2Value+sonuc)+"' WHERE username='"+usernameGLOBAL+"' and currencyName='"+currencyName2+"'");
					    	}else {if(b==false) {myStat.executeUpdate("insert into usermoneyinfo (username,currencyName,currencyAmount) values ('"+usernameGLOBAL+"','"+currencyName2+"','"+sonuc+"')");}}
		        	}
		        	
		        	
		        	else if(currencyName2.contains("US DOLLAR")==true) {
			    		myStat=(Statement) myConn.createStatement();
			    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName+"'");
			    		while(myRs2.next()) {
			    			crossrate=myRs2.getDouble("crossrate");
			    			sonuc=(1/crossrate)*value;
			    			}
			   			 JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar:"+(sonuc)+" "+(currencyName),"Title",1);
			   			 
					    	if(currencyName2Value>0&&b==false) {
						    	myStat.executeUpdate("UPDATE usermoneyinfo SET currencyAmount='"+(currencyName2Value+sonuc)+"' WHERE username='"+usernameGLOBAL+"' and currencyName='"+currencyName2+"'");
					    	}else {if(b==false) {myStat.executeUpdate("insert into usermoneyinfo (username,currencyName,currencyAmount) values ('"+usernameGLOBAL+"','"+currencyName2+"','"+sonuc+"')");}}
		        	}
		        	
		        	
		        	else {
		    		myStat=(Statement) myConn.createStatement();
		    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName+"'");
		    		while(myRs2.next()) {
		    			toTLbuying=myRs2.getDouble("toTLbuying");
		    		}
	    			sonuc=toTLbuying*value;
		    		
		    		myStat=(Statement) myConn.createStatement();
		    		myRs2=myStat.executeQuery("select * from currencyrates where currencyName='"+currencyName2+"'");
		    		while(myRs2.next()) {
		    			toTLselling=myRs2.getDouble("toTLselling");
			        }
	    			sonuc3=sonuc/toTLselling;
	   			    JOptionPane.showMessageDialog(null,"Parayý Çektiniz!\nÇekilen Miktar:"+(sonuc3)+" "+(currencyName2),"Title",1);
	   			    
	   			    
			    	if(currencyName2Value>0&&b==false) {
				    	myStat.executeUpdate("UPDATE usermoneyinfo SET currencyAmount='"+(currencyName2Value+sonuc3)+"' WHERE username='"+usernameGLOBAL+"' and currencyName='"+currencyName2+"'");
			    	}else {if(b==false) {myStat.executeUpdate("insert into usermoneyinfo (username,currencyName,currencyAmount) values ('"+usernameGLOBAL+"','"+currencyName2+"','"+sonuc3+"')");}}
	   			    
		        	
		        	}
		        } 
	           if(b==false) {
		    	myStat=(Statement) myConn.createStatement();
		    	myStat.executeUpdate("UPDATE usermoneyinfo SET currencyAmount='"+sonuc2+"' WHERE username='"+usernameGLOBAL+"' and currencyName='"+currencyName+"'");
		    	JOptionPane.showMessageDialog(null,"Kalan Miktar:"+(sonuc2)+" "+(currencyName),"Title",1);}
		    }
		}
	}
	
	
	public static void kurGuncelle(String currencyName,double forexbuying,double forexselling,double crossrate) throws SQLException {
		myConn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
		myPreStat=(PreparedStatement) myConn.prepareStatement("insert into currencyrates values (?,?,?,?,?)");
		myPreStat.setString(1,currencyName);
		LocalDate date = java.time.LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		String formattedString = date.format(formatter);
		myPreStat.setString(2,formattedString);
		myPreStat.setDouble(3,forexbuying);
		myPreStat.setDouble(4,forexselling);
		myPreStat.setDouble(5,crossrate);
		myPreStat.executeUpdate();
		//-------------------
		boolean a=false;
		ResultSet myRs=null;
		myConn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");
		myStat=(Statement) myConn.createStatement();
		myRs=myStat.executeQuery("select * from currency where currencyName='"+currencyName+"'");
		
		while(myRs.next()) {
			if(!myRs.isBeforeFirst()) {
				a=true;
			}
		}
		if(a==false) {
			myPreStat=(PreparedStatement) myConn.prepareStatement("insert into currency (currencyName) values (?)");
			myPreStat.setString(1,currencyName);
			myPreStat.executeUpdate();
		}
		//--------------------
	}
	
	public void checkLimit() throws SQLException {
        String text;
        Double a=0.0,b=0.0,c,d;
		Connection myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/financialsoftware?useTimezone=true&serverTimezone=UTC","root","password");		
		Statement myStat = (Statement) myConn.createStatement();
		Statement myStat2 = (Statement) myConn.createStatement();
		ResultSet myRs=myStat.executeQuery("select * from usermoneylimit where username='"+DBConnection.cekUsername()+"'");
		while(myRs.next()) {
			     text=myRs.getString("currencyName");
                 a=Double.parseDouble("0"+myRs.getString("toTLbuyingLimit"));
			     b=Double.parseDouble("0"+myRs.getString("toTLsellingLimit"));
			     
					LocalDate date = java.time.LocalDate.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
					String formattedString = date.format(formatter);
				 ResultSet myRs2=myStat2.executeQuery("select * from currencyrates where currencyName='"+text+"' and date='"+formattedString+"'");
			     while(myRs2.next()) {
	                 c=myRs2.getDouble("toTLbuying");
				     d=myRs2.getDouble("toTLselling");
				     if(c<a&&!(a==0)) {
							JOptionPane.showMessageDialog(null,text+" almanin tam zamani.Koyduðunuz limit olan "+a+" 'dan aþaðýya düþtü!","Title",1);
				     }
				     if(b<d&&!(b==0)) {
							JOptionPane.showMessageDialog(null,text+" satmanin tam zamani.Koyduðunuz limit olan "+b+" 'dan yukarýya çýktý!","Title",1);
				     }
			     }
		}
	}
	
}
