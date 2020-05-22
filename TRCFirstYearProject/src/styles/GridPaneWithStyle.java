package styles;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class GridPaneWithStyle extends GridPane {

	public GridPaneWithStyle(Pos pos) {
		super.setAlignment(pos);
		super.setPadding(new Insets(10, 10, 10, 10));
	}
}
