package donorreg;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DonorMasterViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboBlood;

    @FXML
    private ComboBox<String> comboGender;

    @FXML
    private ImageView imgPreview;

    @FXML
    private TextArea txtAddress;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtDisease;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;
    
    @FXML
    private DatePicker txtDate;
    
    LocalDate date=LocalDate.now();
    		
    Connection con;
    PreparedStatement pst;
    Stage stage=null;
	FileChooser fc=new FileChooser();
	File file=null;
    
    String txtPath;

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
    
    @FXML
    void doDate(ActionEvent event) 
    {
    	if(date!=LocalDate.now())
    		date=txtDate.getValue();
    }
    
    @FXML
    void doBrowse(ActionEvent event) 
    {
    	file=fc.showOpenDialog(stage);
      	 
        if (file!=null) 
        {
            txtPath=file.getAbsolutePath();
        }
        Image img=new Image(file.getAbsolutePath());
        imgPreview.setImage(img);
    }

    @FXML
    void doDelete(ActionEvent event) 
    {
    	try 
    	{
			pst=con.prepareStatement("delete from donor where phone=?");
			pst.setString(1, txtPhone.getText());
			int count=pst.executeUpdate();
			if(count==0)
				showNOKMsg("Invalid Phone Number");
			else
				showOKMsg("Record Deleted Successfully");
    	}
    	catch (SQLException e) 
    	{
    		e.printStackTrace();
    		showNOKMsg("No Deletion");
		}    	
    }

    @FXML
    void doFind(ActionEvent event) 
    {
    	try 
    	{
			pst=con.prepareStatement("select * from donor where phone=?");
			pst.setString(1, txtPhone.getText());
			ResultSet table=pst.executeQuery();
        	while(table.next())
        	{
        		txtName.setText(table.getString("name"));
        		txtAge.setText(table.getString("age"));
        		comboGender.setValue(table.getString("gender"));
        		comboBlood.setValue(bloodgroup(table.getString("bgroup")));
        		txtDisease.setText(table.getString("disease"));
        		txtCity.setText(table.getString("city"));
        		txtAddress.setText(table.getString("address"));
        		Image img=new Image(table.getString("imgpath"));
        		imgPreview.setImage(img);
        	}
    	} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
			showNOKMsg("No Record Found");
		}    	
    }

    @FXML
    void doNew(ActionEvent event) 
    {
    	txtPhone.setText("");
    	txtName.setText("");
    	txtAge.setText("");
    	comboGender.setValue(null);
    	comboBlood.setValue(null);
    	txtDisease.setText("");
    	txtCity.setText("");
    	txtAddress.setText("");
    	imgPreview.setImage(null);
    	date=LocalDate.now();
    	txtDate.setValue(date);
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
    void doRegister(ActionEvent event) 
    {
    	try 
    	{
			pst=con.prepareStatement("insert into donor values (?,?,?,?,?,?,?,?,?,?)");
			pst.setString(1, txtPhone.getText());
			pst.setString(2, txtName.getText());
			pst.setString(3, txtAge.getText());
			pst.setString(4, comboGender.getSelectionModel().getSelectedItem());
			pst.setString(5, bloodgroupsql(comboBlood.getSelectionModel().getSelectedItem()));
			pst.setString(6, txtDisease.getText());
			pst.setString(7, txtCity.getText());
			pst.setString(8, txtAddress.getText());
			pst.setString(9, txtPath);	
	    	Date sqldate = Date.valueOf(date);
			pst.setString(10, sqldate.toString());
			
			pst.executeUpdate();
			showOKMsg("Registration Successful");
    	} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
			showNOKMsg("Registration Unsuccessful");
		}
    }

    @FXML
    void doUpdate(ActionEvent event) 
    {
    	try 
    	{
			pst=con.prepareStatement("update donor set name=?, age=?, gender=?, bgroup=?, disease=?, city=?, address=?, imgpath=?, regdate=? where phone=?");
			pst.setString(10, txtPhone.getText());
			pst.setString(1, txtName.getText());
			pst.setString(2, txtAge.getText());
			pst.setString(3, comboGender.getSelectionModel().getSelectedItem());
			pst.setString(4, bloodgroupsql(comboBlood.getSelectionModel().getSelectedItem()));
			pst.setString(5, txtDisease.getText());
			pst.setString(6, txtCity.getText());
			pst.setString(7, txtAddress.getText());
			pst.setString(8, txtPath);	
	    	Date sqldate = Date.valueOf(date);
			pst.setString(9, sqldate.toString());
			
			pst.executeUpdate();
			showOKMsg("Record Updation Successful");
    	} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
			showNOKMsg("Record Updation Unsuccessful");
		}
    }

    @FXML
    void initialize() 
    {
    	con=MySqlConnectivity.doConnect();
    	ArrayList<String> gender=new ArrayList<String>(Arrays.asList("M","F","O"));
    	ArrayList<String> bloods=new ArrayList<String>(Arrays.asList("O+ve","O-ve","A+ve","A-ve","B+ve","B-ve","AB+ve","AB-ve"));
    	comboGender.getItems().setAll(gender);
    	comboBlood.getItems().setAll(bloods);
    	txtDate.setValue(date);
    }

}
