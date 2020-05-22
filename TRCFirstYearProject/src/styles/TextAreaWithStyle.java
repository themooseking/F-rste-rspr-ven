package styles;

import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class TextAreaWithStyle extends TextArea{
	
	private StyleClass style = new StyleClass();
	
	public TextAreaWithStyle(GridPaneWithStyle grid, int row, int col) {
		super.getStylesheets().add("/styles/TextArea.css");
		super.setFont(Font.font(style.textFont(), 20));
		super.setPrefSize(600, 560);
		super.setWrapText(true);
		super.setEditable(false);
		
		super.setStyle("-fx-control-inner-background: " + style.red() + ";");
		
		GridPane.setConstraints(this, row, col);
		grid.getChildren().add(this);
	}
}
