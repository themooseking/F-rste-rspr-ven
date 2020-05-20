package styles;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertWithStyle extends Alert {
	
	StyleClass style = new StyleClass();

	public AlertWithStyle(AlertType alert, String text, String title, ArrayList<ButtonType> arrayList) {
		super(alert);
		super.getDialogPane().setMinSize(350, 100);
		super.getDialogPane().setPrefSize(350, 100);
		super.getDialogPane().setMaxSize(350, 100);
		super.getDialogPane().getStylesheets().add("/styles/Alert.css");		

		super.setHeaderText(null);
		super.setTitle(title);
		super.setContentText(text);
		
		super.getButtonTypes().setAll(arrayList);
	}
}
