package styles;

import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;

public class ProgressIndicatorWithStyle extends ProgressIndicator {

	public ProgressIndicatorWithStyle(GridPane grid, int col, int row) {
		super.getStylesheets().add("/styles/ProgressIndicator.css");
		super.setMaxSize(30, 30);
		
		GridPane.setConstraints(this, col, row);
		grid.getChildren().add(this);
	}
}
