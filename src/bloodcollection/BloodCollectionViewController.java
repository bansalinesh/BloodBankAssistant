package bloodcollection;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import donorreg.MySqlConnectivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BloodCollectionViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView showImg;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtBlood;

    @FXML
    private TextField txtCity;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtDisease;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;
	
    Connection con;
    PreparedStatement pst;
    
    LocalDate date=LocalDate.now();
    
    Stage stage=null;
	FileChooser fc=new FileChooser();
	File file=null;
	
	void showOKMsg(String msg)
    {
    	Alert alert=new Alert(AlertType.INFORMATION);
    	alert.setTitle("Title");
    	alert.setContentText(msg);
    	alert.show();
    }
    void showNOKMsg(String msg)
    {
    	Alert alert=new Alert(AlertType.WARNING);
    	alert.setTitle("Title");
    	alert.setContentText(msg);
    	alert.show();
    }
    
    String bloodgroupsql(String blood)
    {
    	String ch=null;
    	
    	switch(blood)
    	{
    		case("O+ve"): ch="op";
    		case("O-ve"): ch="onev";
    		case("A+ve"): ch="ap";
    		case("A-ve"): ch="anev";
    		case("B+ve"): ch="bp";
    		case("B-ve"): ch="bnev";
    		case("AB+ve"): ch="abp";
    		case("AB-ve"): ch="abnev";
    	}
    	return ch;
    }
    String bloodgroup(String blood)
    {
    	String ch=null;
    	
    	switch(blood)
    	{
    		case("op"): ch="O+ve";
    		case("onev"): ch="O-ve";
    		case("ap"): ch="A+ve";
    		case("anev"): ch="A-ve";
    		case("bp"): ch="B+ve";
    		case("bnev"): ch="B-ve";
    		case("abp"): ch="AB+ve";
    		case("abnev"): ch="AB-ve";
    	}
    	return ch;
    }

    @FXML
    void doDate(ActionEvent event) 
    {
    	if(date!=LocalDate.now())
    		date=txtDate.getValue();
    }

    @FXML
    void doNew(ActionEvent event) 
    {
    	txtPhone.setText("");
    	txtName.setText("");
    	txtAge.setText("");
    	txtGender.setText("");
    	txtBlood.setText("");
    	txtDisease.setText("");
    	txtCity.setText("");
    	showImg.setImage(null);
    	date=LocalDate.now();
    	txtDate.setValue(date);
    }

    @FXML
    void doSearch(ActionEvent event) 
    {
    	try 
    	{
			pst=con.prepareStatement("select * from donor where phone=?");
			pst.setString(1, txtPhone.getText());
			ResultSet table=pst.executeQuery();
			if(table.next())
			{
				table.previous();
				showOKMsg("Record found");
	        	while(table.next())
	        	{
	        		txtName.setText(table.getString("name"));
	        		txtAge.setText(table.getString("age"));
	        		txtGender.setText(table.getString("gender"));
	        		txtBlood.setText(bloodgroup(table.getString("bgroup")));
	        		txtDisease.setText(table.getString("disease"));
	        		txtCity.setText(table.getString("city"));
	        		Image img=new Image(table.getString("imgpath"));
	        		showImg.setImage(img);
	        	}
			}
			else
				showNOKMsg("No Record Found");
				
    	} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}    	
    }

    @FXML
    void doUpload(ActionEvent event) 
    {
    	try 
    	{
			pst=con.prepareStatement("insert into blood_collection values (?,?,?)");
			pst.setString(1, txtPhone.getText());
			pst.setString(2, bloodgroupsql(txtBlood.getText()));
			Date sqldate = Date.valueOf(date);
			pst.setString(3, sqldate.toString());
			pst.executeUpdate();
			String bg=new String(bloodgroupsql(txtBlood.getText()));
			pst=con.prepareStatement("update blood_group set "+bg+"="+bg+"+?");
			pst.setInt(1, 1);
			pst.executeUpdate();
			showOKMsg("Uploaded Successfully");
    	} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
			showNOKMsg("Upload Failed");
		}
    }

    @FXML
    void initialize() 
    {
    	con=MySqlConnectivity.doConnect();
    	txtDate.setValue(date);
    }

}
