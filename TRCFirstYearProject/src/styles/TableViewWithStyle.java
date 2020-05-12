package styles;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Proposal;

public class TableViewWithStyle extends TableView<Proposal>{
	
	StyleClass style = new StyleClass();

	public TableViewWithStyle(GridPaneCenter grid, int row, int col) {
		super.getStylesheets().add("/styles/TableView.css");

		super.setPrefSize(1600, 800);

		BackgroundFill background_fill = new BackgroundFill(Color.web(style.defaultHoverColor()), new CornerRadii(0), Insets.EMPTY);
		Background background = new Background(background_fill);
		
		super.setBackground(background);
		
		GridPaneCenter.setConstraints(this, row, col);
		grid.getChildren().add(this);
	}
}
