package styles;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class TextWithStyle extends TextField {
	
	StyleClass style = new StyleClass();

	public TextWithStyle(String text, GridPaneCenter grid, int row, int col, int width) {
		super.setText(text);
		super.getStylesheets().add("/styles/Text.css");
		super.setDisable(true);
		super.setOpacity(100);

		super.setFont(Font.font(style.textFont(), 18));
		super.setPrefSize(width, 25);
		
		GridPane.setConstraints(this, row, col);
		grid.getChildren().add(this);
	}
}
