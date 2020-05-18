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
		super.setMinWidth((style.sceneX() - 200) / map.size());
		
//		Callback<TableColumn<Proposal, Object>, TableCell<Proposal, Object>> cellFactory =
//				new Callback<TableColumn<Proposal, Object>, TableCell<Proposal, Object>>() {
//					@Override
//					public TableCell<Proposal, Object> call(final TableColumn<Proposal, Object> param) {
//						final TableCell<Proposal, Object> cell = new TableCell<Proposal, Object>() {
//
//							@Override
//							public void updateItem(String item, boolean empty) {
//								super.updateItem(item, empty);
//								if (empty) {
//									System.out.println("fuck");
//									setGraphic(null);
//									setText(null);
//								} else {
//									setText(item.toString());
//									TableRow<Proposal> row = getTableRow();
//									if (row.getItem().getProposalStatus() == "AWAITING") {
//										System.out.println("hej");
//									}
//								}
//							}
//						};
//						return cell;
//					}
//				};
//		this.setCellFactory(cellFactory);
	}
}
