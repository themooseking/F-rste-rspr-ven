package styles;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Car;
import logic.Proposal;

public class TableColumnWithStyle extends TableColumn<Proposal, Object>{

	public TableColumnWithStyle(String columnName, String objectGetter) {
		super(columnName);
		super.setCellValueFactory(new PropertyValueFactory<Proposal, Object>(objectGetter));
		super.setResizable(false);
		super.setMinWidth(1600/6);
	}
}
