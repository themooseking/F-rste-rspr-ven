package styles;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TextFieldWithStyle extends TextField {
	
	StyleClass style = new StyleClass();

	public TextFieldWithStyle(String prompt, GridPaneWithStyle grid, int row, int col) {
		super.setPromptText(prompt);
		super.getStylesheets().add("/styles/TextField.css");
		super.setOpacity(100);
		super.setFont(Font.font(style.textFont(), 24));
		super.setMinSize(400, 80);		

		defaultEffect(this);

		super.onMouseEnteredProperty().set(e -> enterEffect(this));
		super.onMouseExitedProperty().set(e -> defaultEffect(this));
		
		GridPane.setConstraints(this, row, col);
		grid.getChildren().add(this);
	}
	
	private void enterEffect(TextField obj) {
		BackgroundFill background_fill = new BackgroundFill(Color.web(style.white()), new CornerRadii(0), Insets.EMPTY);
		Background background = new Background(background_fill);

		obj.setBackground(background);
		obj.setCursor(Cursor.HAND);
	}
	
	private void defaultEffect(TextField obj) {
		BackgroundFill background_fill = new BackgroundFill(Color.web(style.white()), new CornerRadii(0), Insets.EMPTY);
		Background background = new Background(background_fill);
		
		obj.setBorder(style.elementBorder());
		
		obj.setBackground(background);
		obj.setCursor(Cursor.DEFAULT);
	}
}
