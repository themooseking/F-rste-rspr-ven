package styles;

import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import logic.Proposal;

public class TableViewWithStyle extends TableView<Proposal>{
	
	StyleClass style = new StyleClass();

	public TableViewWithStyle(GridPaneWithStyle grid, int row, int col) {
		super.getStylesheets().add("/styles/TableView.css");
		super.setMinSize(1700, 570);

		BackgroundFill background_fill = new BackgroundFill(Color.web(style.red()), new CornerRadii(0), Insets.EMPTY);
		Background background = new Background(background_fill);		
		super.setBackground(background);
		
		GridPaneWithStyle.setConstraints(this, row, col);
		grid.getChildren().add(this);
	}
}
