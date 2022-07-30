module Blood_Bank_Assistant {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
	opens donorreg to javafx.graphics, javafx.fxml;
	opens bloodcollection to javafx.graphics, javafx.fxml;
	opens bloodavailable to javafx.graphics, javafx.fxml;
	opens controlpanel to javafx.graphics, javafx.fxml;
	opens home to javafx.graphics, javafx.fxml;
	opens loginpage to javafx.graphics, javafx.fxml;
	opens bloodissue to javafx.graphics, javafx.fxml;
	opens donorhistory to javafx.graphics, javafx.fxml,javafx.base;
}
