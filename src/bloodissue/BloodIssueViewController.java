package bloodissue;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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

public class BloodIssueViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboBlood;

    @FXML
    private TextArea txtAddress;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtHospital;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtPurpose;

    @FXML
    private TextField txtUnits;
    
    Connection con;
    PreparedStatement pst;
    
    LocalDate date=LocalDate.now();
    
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

    @FXML
    void doDate(ActionEvent event) 
    {
    	if(date!=LocalDate.now())
    		date=txtDate.getValue();
    }

    @FXML
    void doUpdate(ActionEvent event) 
    {
    	try 
    	{
			pst=con.prepareStatement("insert into blood_issue values (?,?,?,?,?,?,?,?)");
			pst.setString(1, txtName.getText());
			pst.setString(2, txtPhone.getText());
			Date sqldate = Date.valueOf(date);
			pst.setString(3, sqldate.toString());
			pst.setString(4, bloodgroupsql(comboBlood.getSelectionModel().getSelectedItem()));
			pst.setString(5, txtUnits.getText());			
			pst.setString(6, txtHospital.getText());
			pst.setString(7, txtPurpose.getText());
			pst.setString(8, txtAddress.getText());
			pst.executeUpdate();
			String bg=new String(bloodgroupsql(comboBlood.getSelectionModel().getSelectedItem()));
			int units=Integer.parseInt(txtUnits.getText());
			pst=con.prepareStatement("update blood_group set "+bg+"="+bg+"-?");
			pst.setInt(1, units);
			pst.executeUpdate();
			showOKMsg("Updated Successfully");
    	} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
			showNOKMsg("Updation Unsuccessful");
		}
    }
    
    @FXML
    void doNew(ActionEvent event) 
    {
    	txtPhone.setText("");
    	txtName.setText("");
    	comboBlood.setValue(null);
    	txtPurpose.setText("");
    	txtHospital.setText("");
    	txtAddress.setText("");
    	txtUnits.setText("");
    	date=LocalDate.now();
    	txtDate.setValue(date);
    }

    @FXML
    void initialize() 
    {
    	con=MySqlConnectivity.doConnect();
    	ArrayList<String> bloods=new ArrayList<String>(Arrays.asList("O+ve","O-ve","A+ve","A-ve","B+ve","B-ve","AB+ve","AB-ve"));
    	comboBlood.getItems().setAll(bloods);
    	txtDate.setValue(date);
    }

}
