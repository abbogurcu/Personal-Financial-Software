package application;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import javax.swing.JOptionPane;

public class getDatafromWebsite{
    static ResultSet myRs;
	
    public static void main(String[] args) throws SQLException {
    	if(compareToHour(19)==true) {
    		verileriCek();
    	}
    }
    
    public static void verileriCek() throws SQLException {
        try {
            URL url = new URL("http://www.tcmb.gov.tr/kurlar/today.xml");
            URLConnection conn = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            
            //---------------------------------------

            String currencyName = null;
            int num=0;
            boolean dollar=true;
            double crossRate = 0,forexbuying = 0,forexselling = 0;
            while ((inputLine = br.readLine()) != null) {
            	if((inputLine.contains("<CurrencyName>")==true)
            			||(inputLine.contains("<ForexBuying>")==true)
            			||(inputLine.contains("<ForexSelling>")==true)
            			||(inputLine.contains("<CrossRateUSD>")==true)||
            			(inputLine.contains("<CrossRateOther>")==true)) {
                if(inputLine.contains("<CurrencyName>")==true) {
                	currencyName=(inputLine.replaceAll("			<CurrencyName>", "").replaceAll("                       </CurrencyName>", "").replaceAll("</CurrencyName>", "").replaceAll("		<CurrencyName>", ""));
                }
                
                if(inputLine.contains("<ForexBuying>")==true) {
                	forexbuying=Double.parseDouble(inputLine.replaceAll("			<ForexBuying>", "").replaceAll("</ForexBuying>", "").replaceAll("		<ForexBuying>", ""));
                }
                if(inputLine.contains("<ForexSelling>")==true) {
                	forexselling=Double.parseDouble(inputLine.replaceAll("			<ForexSelling>", "").replaceAll("</ForexSelling>", ""));
                }
                boolean a=false;
                if(inputLine.contains("<CrossRateUSD>")==true) {
                	crossRate=Double.parseDouble(inputLine.replaceAll("				<CrossRateUSD>", "").replaceAll("</CrossRateUSD>", ""));
                   a=true;
                }
                if(inputLine.contains("<CrossRateOther>")==true&&a==false) {
                	DecimalFormat df = new DecimalFormat("#.####");
                	crossRate=(Double.parseDouble(df.format(1/Double.parseDouble(inputLine.replaceAll("				<CrossRateOther>", "").replaceAll("		<CrossRateOther>", "").replaceAll("</CrossRateOther>", "").replaceAll("				<CrossRateOther>", ""))).replaceAll(",",".")));
                }
                if(forexbuying!=0&&forexselling!=0){
                     if(dollar==true) {
                    	 DBConnection.kurGuncelle(currencyName,forexbuying,forexselling,crossRate);
                    	 dollar=false;
                    	 }
                     num++;
                     
                         if(num==5) {
                               DBConnection.kurGuncelle(currencyName,forexbuying,forexselling,crossRate);
                    	       num=1;
                         }
                         
                         if(num==4&&currencyName.contains("SDR")) {
                             DBConnection.kurGuncelle(currencyName,forexbuying,forexselling,crossRate);
                  	       num=1;
                       }
                }
              }
            }
            	
           
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		JOptionPane.showMessageDialog(null,"Veriler manuel olarak çekildi!","Title",1);
        String text;
        Double a,b,c,d;
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
			     }}
		}
		
    
    
    
	public static boolean compareToHour(int arg)
	{
	    Calendar temp = Calendar.getInstance();
	    int tempHour = temp.get(Calendar.HOUR_OF_DAY);
	    return arg==tempHour;

	}
}
