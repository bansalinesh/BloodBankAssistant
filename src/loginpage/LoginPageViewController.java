package loginpage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginPageViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField txtPass;

    @FXML
    private TextField txtUsername;
    
    void showNOKMsg(String msg)
    {
    	Alert alert=new Alert(AlertType.WARNING);
    	alert.setTitle("Title");
    	alert.setContentText(msg);
    	alert.show();
    }

    @FXML
    void doLogin(ActionEvent event) 
    {
    	String username=txtUsername.getText();
    	String pass=txtPass.getText();
    	if(username.equals("admin") && pass.equals("1234"))
    	{
    		try{
	    		Parent root=FXMLLoader.load(getClass().getResource("/home/HomeView.fxml")); 
								//OR
				//Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("marks/card/MarksCard.fxml")); 
				Scene scene = new Scene(root);
				Stage stage=new Stage();
				stage.setScene(scene);
				stage.show();
				//to hide parent window
				Scene scene1=(Scene)txtPass.getScene();
				   scene1.getWindow().hide();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
    	}
    	else
    		showNOKMsg("Incorrect Username or Password");
    }

    @FXML
    void initialize() 
    {
    	
    }

}
