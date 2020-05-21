package styles;

import java.util.LinkedHashMap;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Proposal;

public class TableColumnWithStyle extends TableColumn<Proposal, Object>{
	
	private StyleClass style = new StyleClass();

	public TableColumnWithStyle(String columnName, String objectGetter, LinkedHashMap<String, String> map) {
		super(columnName);
		super.setCellValueFactory(new PropertyValueFactory<Proposal, Object>(objectGetter));
		super.setResizable(false);
		super.setMinWidth((style.sceneX() - 200) / (map.size() + 1));
	}
}
