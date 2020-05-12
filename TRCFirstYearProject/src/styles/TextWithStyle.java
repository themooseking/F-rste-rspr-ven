package styles;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class TextWithStyle extends TextField {
	
	StyleClass style = new StyleClass();

	public TextWithStyle(String text, GridPaneCenter grid, int row, int col, int width, int underline, int alignPos) {
		super.setText(text);
		super.getStylesheets().add("/styles/Text.css");
		super.setDisable(true);
		super.setOpacity(100);

		super.setFont(Font.font(style.textFont(), 16));
		super.setPrefSize(width, 10);
		
		underLine(underline);
		position(alignPos);		
		
		GridPane.setConstraints(this, row, col);
		grid.getChildren().add(this);
	}
	
	private void underLine(int underline) {
		switch (underline) {
		case 0:
			super.setBorder(null);
			break;
					
		case 1:
			super.setBorder(style.underLine());
			break;
			
		case 2:
			super.setBorder(style.dottedUnderLine());
			break;
		}
	}
	
	private void position(int alignPos) {
		switch (alignPos) {
		case 0:
			super.setAlignment(Pos.CENTER);
			break;
					
		case 1:
			super.setAlignment(Pos.CENTER_LEFT);
			break;
			
		case 2:
			super.setAlignment(Pos.CENTER_RIGHT);
			break;
		}
	}
}
