package styles;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

public class AlertWithStyle extends Alert {

	StyleClass style = new StyleClass();

	public AlertWithStyle(AlertType alert, String text, String title, ArrayList<ButtonType> arrayList) {
		super(alert);
		super.getDialogPane().setMinSize(800, 200);
		super.getDialogPane().setPrefSize(800, 200);
		super.getDialogPane().setMaxSize(800, 200);
		super.getDialogPane().getStylesheets().add("/styles/Alert.css");

		super.initStyle(StageStyle.TRANSPARENT);
		super.getDialogPane().setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

		super.setHeaderText(null);
		super.setTitle(title);
		super.setContentText(text);

		super.getButtonTypes().setAll(arrayList);
	}
}
