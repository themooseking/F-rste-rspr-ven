package styles;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LabelWithStyle extends Label {
	
	StyleClass style = new StyleClass();

	public LabelWithStyle(String text, GridPaneWithStyle grid, int row, int col) {
		super.setText(text);
		super.setFont(Font.font(style.textFont(), style.textSize()));
		super.setTextFill(Color.web(style.grey()));
		
		GridPane.setConstraints(this, row, col);
		grid.getChildren().add(this); 
	}
}
