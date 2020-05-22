package styles;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class TextWithStyle extends TextField {
	
	StyleClass style = new StyleClass();

	public TextWithStyle(String text, GridPaneWithStyle grid, int row, int col, int width, int alignPos) {
		super.setText(text);
		super.getStylesheets().add("/styles/Text.css");
		super.setDisable(true);
		super.setOpacity(100);

		super.setPrefWidth(width);
		
		int i = 16;
		super.setMinHeight(i);
		super.setMaxHeight(i);
		super.setPrefHeight(i);
		
		position(alignPos);		
		
		GridPane.setConstraints(this, row, col);
		grid.getChildren().add(this);
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
