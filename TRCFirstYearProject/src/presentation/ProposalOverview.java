package presentation;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import logic.Customer;
import logic.DB_Controller;
import logic.Proposal;
import styles.ButtonWithStyle;
import styles.GridPaneCenter;
import styles.StyleClass;
import styles.TableColumnWithStyle;
import styles.TableViewWithStyle;
import styles.VBoxWithStyle;

public class ProposalOverview {
	private StyleClass style = new StyleClass();
	private Customer customer;
	private DB_Controller controller = new DB_Controller();

	public void customerUI(String customerCPR) {
		int i = 0;
		customer = controller.getCustomer(Customer.removeDashFromCpr(customerCPR));

		VBoxWithStyle vbox = new VBoxWithStyle(title(customer.toString()), proposalTableView(i), buttons(i));
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	public void salesmanUI() {
		int i = 1;
		VBoxWithStyle vbox = new VBoxWithStyle(title(LoggedInST.getUser().toString()), proposalTableView(i),
				buttons(i));
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	public void cosUI() {
		int i = 2;
		VBoxWithStyle vbox = new VBoxWithStyle(title(LoggedInST.getUser().toString()), proposalTableView(i),
				buttons(i));
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	//////////////////////////////
	// TableView
	//////////////////////////////

	private GridPane proposalTableView(int i) {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);

		ArrayList<Proposal> arrayList = new ArrayList<Proposal>();
		if (i == 0) {
			arrayList = controller.getProposalByCustomer(customer);
		} else if (i == 1) {
			arrayList = controller.getProposalBySalesman(LoggedInST.getUser());
		} else if (i == 2) {
			arrayList = controller.getAwaitingProposals();
		}

		ObservableList<Proposal> eventList = FXCollections.observableArrayList();
		eventList.addAll(arrayList);

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

		map.put("Låne nr.", "proposalId");
		map.put("Bil", "car");
		if (i == 1) {
			map.put("Kunde", "customer");
		}
		map.put("Rente (%)", "totalInterest");
		map.put("ÅOP (%)", "apr");
		map.put("Sum (DKK)", "proposalTotalSum");
		if (i == 2) {
			map.put("Salgs titel", "salesmanTitel");
		}
		map.put("Status", "proposalStatus");

		TableViewWithStyle table = new TableViewWithStyle(grid, 0, 0);
		
		table.setRowFactory(tv -> new TableRow<Proposal>() {
		    @Override
		    public void updateItem(Proposal item, boolean empty) {
		    	System.out.println("hej");
		        super.updateItem(item, empty) ;
		        if (item == null) {
		            setStyle("");
		        } else if (item.getProposalStatus().equals("AWAITING")) {
		            setStyle("-fx-background-color: yellow;");
		        } else {
		            setStyle("");
		        }
		    }
		});
		
		table.setItems(eventList);
		for (String key : map.keySet()) {
			table.getColumns().add(createColumn(key, map));
		}
		


 
		
//		table.getColumns().add(makeStringColumn("Status", "proposalStatus"));
//
//		int j = 0;
//		for (Node n : table.lookupAll("TableRow")) {
//			System.out.println("hej4");
//			if (n instanceof TableRow) {
//				TableRow row = (TableRow) n;
//				System.out.println("hej3");
//				if (table.getItems().get(j).getProposalStatus() == "AWAITING") {
//					System.out.println("hej");
//					row.getStyleClass().add("-fx-background-color: yellow;");
//					row.setDisable(false);
//				} else {
//					System.out.println("hej2");
//					row.getStyleClass().add("-fx-background-color: green;");
//					row.setDisable(true);
//				}
//				j++;
//				if (j == table.getItems().size())
//					break;
//			}
//		}

		accessProposal(table, i);
		System.out.println(table.lookup("Status")); 
		return grid;
	}

//	private TableColumn<Proposal, String> makeStringColumn(String columnName, String propertyName) {
//		TableColumn<Proposal, String> column = new TableColumn<>(columnName);
//		column.setCellValueFactory(new PropertyValueFactory<Proposal, String>(propertyName));
//		column.setCellFactory(new Callback<TableColumn<Proposal, String>, TableCell<Proposal, String>>() {
//			@Override
//			public TableCell<Proposal, String> call(TableColumn<Proposal, String> soCalledFriendStringTableColumn) {
//				return new TableCell<Proposal, String>() {
//					@Override
//					public void updateItem(String item, boolean empty) {
//						super.updateItem(item, empty);
//						if (item != null) {
//							setText(item);
//						}
//					}
//				};
//			}
//		});
//		column.setSortable(false);
//		return column;
//	}

	private TableColumnWithStyle createColumn(String key, LinkedHashMap<String, String> map) {
		TableColumnWithStyle column = new TableColumnWithStyle(key, map.get(key), map);

		return column;
	}

	private TableRow<Proposal> accessProposal(TableView<Proposal> table, int i) {
		table.setRowFactory(e -> {
			TableRow<Proposal> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Proposal rowData = row.getItem();
					if (i == 0) {
						new SignProposalScreen(rowData).signProposalUI();
					} else if (i == 1) {
						new SignProposalScreen(rowData).salesmanSignProposalUI();
					} else if (i == 2) {
						new SignProposalScreen(rowData).cosSignProposalUI();
						;
					}
				}
			});

			return row;
		});

		return null;
	}

	//////////////////////////////
	// Buttons
	//////////////////////////////

	private HBox buttons(int i) {
		HBox hbox = new HBox(backButton());
		hbox.setAlignment(Pos.BASELINE_RIGHT);
		hbox.setPadding(new Insets(105, 50, 0, 0));

		if (i == 0) {
			hbox.getChildren().add(newProposalButton());
		}

		return hbox;
	}

	private GridPane backButton() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);

		ButtonWithStyle button = new ButtonWithStyle("Tilbage", grid, 0, 1);
		button.setOnAction(e -> {
			new CPRScreen().cprUI();
		});

		return grid;
	}

	private GridPane newProposalButton() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);

		ButtonWithStyle button = new ButtonWithStyle("Ny", grid, 0, 1);
		button.setOnAction(e -> {
			new NewPropsalScreen(customer).newProposalUI();
		});

		return grid;
	}

	//////////////////////////////
	// Label Title
	//////////////////////////////

	private Label title(String person) {
		Label label = new Label("L�netilbud for " + person);
		label.setFont(Font.loadFont("file:resources/fonts/FerroRosso.ttf", 120));
		label.setTextFill(Color.web(style.grey()));
		return label;
	}

	//////////////////////////////
	// Scene stuff
	//////////////////////////////

	private void sceneSetup(Scene scene) {
		PrimaryStageST.getStage().setTitle("The Red Car");
		PrimaryStageST.getStage().setScene(scene);
		PrimaryStageST.getStage().show();
	}
}
