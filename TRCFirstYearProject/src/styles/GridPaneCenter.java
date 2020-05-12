package styles;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class GridPaneCenter extends GridPane {

	public GridPaneCenter(Pos pos) {
		super.setAlignment(pos);
		super.setPadding(new Insets(10, 10, 10, 10));
	}
}
