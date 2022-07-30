package bloodavailable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BloodAvailableViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtabnev;

    @FXML
    private TextField txtabp;

    @FXML
    private TextField txtanev;

    @FXML
    private TextField txtap;

    @FXML
    private TextField txtbnev;

    @FXML
    private TextField txtbp;

    @FXML
    private TextField txtonev;

    @FXML
    private TextField txtop;
    
    Connection con;
    PreparedStatement pst;
    
    void setValues()
    {
    	try 
    	{
			pst=con.prepareStatement("select * from blood_group");
			ResultSet table=pst.executeQuery();
        	while(table.next())
        	{
        		txtop.setText(String.valueOf(table.getInt("op")));
        		txtonev.setText(String.valueOf(table.getInt("onev")));
        		txtap.setText(String.valueOf(table.getInt("ap")));
        		txtanev.setText(String.valueOf(table.getInt("anev")));
        		txtbp.setText(String.valueOf(table.getInt("bp")));
        		txtbnev.setText(String.valueOf(table.getInt("bnev")));
        		txtabp.setText(String.valueOf(table.getInt("abp")));
        		txtabnev.setText(String.valueOf(table.getInt("abnev")));
        	}
    	} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}    	
    }
    
    @FXML
    void initialize() 
    {
    	con=MySqlConnectivity.doConnect();
    	setValues();    	
    }

}
