package styles;

import javafx.scene.Cursor;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RadioButtonWithStyle extends RadioButton {
	
	private StyleClass style = new StyleClass();

	public RadioButtonWithStyle(String radioButtonText, GridPane grid, int col, int row) {
		super.setText(radioButtonText);
		super.setFont(Font.font(style.textFont(), FontWeight.BOLD, style.textSize()));
		super.getStylesheets().add("/styles/RadioButton.css");
		super.setPrefSize(200, 60);

		defaultEffect(this);

		super.onMouseEnteredProperty().set(e -> enterEffect(this));
		super.onMouseExitedProperty().set(e -> defaultEffect(this));
		
		GridPane.setConstraints(this, col, row);
		grid.getChildren().add(this);
	}
	
	private void enterEffect(RadioButton obj) {
		obj.setCursor(Cursor.HAND);		
		obj.setTextFill(Color.web(style.red()));
	}

	private void defaultEffect(RadioButton obj) {
		obj.setCursor(Cursor.DEFAULT);
		obj.setTextFill(Color.web(style.grey()));
	}
}
