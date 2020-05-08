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

public class TableViewWithStyle extends TableView{
	
	StyleClass style = new StyleClass();

	public TableViewWithStyle(GridPaneCenter grid, int row, int col) {
		super.getStylesheets().add("/styles/TableView.css");

		//super.setFont(Font.font(style.textFont(), 24));
		super.setPrefSize(900, 900);

		//defaultEffect(this);

		//super.onMouseEnteredProperty().set(e -> enterEffect(this));
		//super.onMouseExitedProperty().set(e -> defaultEffect(this));
		
		GridPane.setColumnSpan(this, 3);
		GridPane.setRowSpan(this, 14);
		
		GridPane.setConstraints(this, row, col);
		grid.getChildren().add(this);
	}
	
	private void enterEffect(TextField obj) {
		BackgroundFill background_fill = new BackgroundFill(Color.web(style.enterHoverColor()), new CornerRadii(0), Insets.EMPTY);
		Background background = new Background(background_fill);

		obj.setBackground(background);
		obj.setCursor(Cursor.HAND);
	}
	
	private void defaultEffect(TextField obj) {
		BackgroundFill background_fill = new BackgroundFill(Color.web(style.defaultHoverColor()), new CornerRadii(0), Insets.EMPTY);
		Background background = new Background(background_fill);
		
		obj.setBackground(background);
		obj.setCursor(Cursor.DEFAULT);
	}
}
