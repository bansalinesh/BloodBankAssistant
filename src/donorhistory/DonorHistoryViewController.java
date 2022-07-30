package donorhistory;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

public class DonorHistoryViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<DonorBean> tblGrid;

    @FXML
    private TextField txtBlood;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;
    
    Connection con;
    PreparedStatement pst;
    
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
    
    ResultSet table;
    
    ObservableList<DonorBean> getAllObjects() 
	{
		ObservableList<DonorBean> ary=FXCollections.observableArrayList();
		
		PreparedStatement pst;
		try 
		{
			pst=con.prepareStatement("select * from blood_collection where phone=?");
			pst.setString(1, txtPhone.getText());
			table=pst.executeQuery();
			while(table.next())
			{
				Date sdate=table.getDate("dondate");
				LocalDate date=sdate.toLocalDate();
				String blood=bloodgroup(table.getString("bgroup"));
				DonorBean obj=new DonorBean(date,blood);
				ary.add(obj);
			}
		}
		catch(Exception exp)
		{ 	
			System.out.println(exp);
		}
		System.out.println(ary.size());
		return ary;
	}

    @FXML
    void doFetch(ActionEvent event) 
    {
    	getValues();
    	
    	TableColumn<DonorBean, Integer> date=new TableColumn<DonorBean, Integer>("Date of Donation");
    	date.setCellValueFactory(new PropertyValueFactory<>("date"));//same as bean property
    	date.setMinWidth(150);
    	
    	TableColumn<DonorBean, String> blood=new TableColumn<DonorBean, String>("Blood Group");
    	blood.setCellValueFactory(new PropertyValueFactory<>("blood"));//same as bean property
    	blood.setMinWidth(100);    
    	
    	tblGrid.getColumns().clear();
    	
    	tblGrid.getColumns().addAll(date,blood);
    	   
    	tblGrid.setItems(null);
    	
    	ObservableList<DonorBean>allRecords=getAllObjects();	
    	
    	tblGrid.setItems(allRecords);
    }

    void getValues()
    {
    	txtName.setText("");
    	txtBlood.setText("");
    	try 
		{
			pst=con.prepareStatement("select name,bgroup from donor where phone=?");
			pst.setString(1, txtPhone.getText());
			ResultSet tab=pst.executeQuery();
			if(tab.next())
			{
				tab.previous();
	        	while(tab.next())
	        	{
	        		txtName.setText(tab.getString("name"));
	        		txtBlood.setText(bloodgroup(tab.getString("bgroup")));
	        	}
			}
			else
				showNOKMsg("No previous record");
		}
		catch(Exception exp)
		{ 	
			System.out.println(exp);
		}
    }
    
    @FXML
    void initialize() 
    {
    	con=MySqlConnectivity.doConnect();
    }
}
