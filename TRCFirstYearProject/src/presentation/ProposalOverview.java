package presentation;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Customer;
import logic.DB_Controller;
import logic.Proposal;
import logic.Status;
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

		VBoxWithStyle vbox = new VBoxWithStyle(title(customer.toString(), i), proposalTableView(i), buttons(i));
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	public void salesmanUI() {
		int i = 1;
		VBoxWithStyle vbox = new VBoxWithStyle(title(LoggedInST.getUser().toString(), i), proposalTableView(i),
				buttons(i));
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	public void cosUI() {
		int i = 2;
		VBoxWithStyle vbox = new VBoxWithStyle(title(LoggedInST.getUser().toString(), i), proposalTableView(i),
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
		map.put("Dato", "proposalDate");
		map.put("Bil", "car");
		if (i == 1) {
			map.put("Kunde", "customer");
		}
		map.put("Rente (%)", "totalInterest");
		map.put("Sum (DKK)", "proposalTotalSum");
		if (i != 1) {
			map.put("S�lger Navn", "salesman");
		}
		if (i == 2) {
			map.put("Salgs titel", "salesmanTitel");
		}
		//map.put("Status", "proposalStatus");

		TableViewWithStyle table = new TableViewWithStyle(grid, 0, 0);
		table.setItems(eventList);
		for (String key : map.keySet()) {
			table.getColumns().add(createColumn(key, map));
		}
		TableColumn<Proposal, Status> statusCol = new TableColumn<Proposal, Status>("Status");
		statusCol.setCellValueFactory(new PropertyValueFactory<Proposal, Status>("proposalStatus"));
		table.getColumns().add(statusCol);

		customiseFactory(statusCol);
		
		//table.getColumns().get(4).setStyle("-fx-alignment: CENTER;");
		//table.getColumns().get(4).getStyleClass().add("numbers");
		//table.getColumns().get(5).setStyle("-fx-alignment: CENTER;");

		accessProposal(table, i);

		return grid;
	}

	private void customiseFactory(TableColumn<Proposal, Status> statusCol) {
		statusCol.setCellFactory(column -> {
			return new TableCell<Proposal, Status>() {
				@Override
				protected void updateItem(Status item, boolean empty) {
					super.updateItem(item, empty);

					setText(empty ? "" : getItem().toString());
					//setGraphic(null);

					TableRow<Proposal> currentRow = getTableRow();
					
					if (!isEmpty() && currentRow != null) {
						if (item == Status.IGANG || item == Status.GODKENDT) {
							currentRow.setStyle("-fx-background-color:#F9F69C;");
						} else if (item == Status.AFVENTER) {
							currentRow.setStyle("-fx-background-color:dedb7a;");
						} else if (item == Status.ANNULLERET) {
							currentRow.setStyle("-fx-background-color:#FF7E7E;");
						} else if (item == Status.AFSLUTTET) {
							currentRow.setStyle("-fx-background-color:#A5F99C;");
						} 
					}
				}
			};
		});
	}

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

	private Label title(String person, int i) {
		Label label = null;
		if (i == 0) {
			label = new Label("L�netilbud for " + person);
		} else if (i == 1) {
			label = new Label(LoggedInST.getUser() + "s l�netilbud");
		} else if (i == 2) {
			label = new Label("Godkend tilbud");
		}

		label.setFont(Font.loadFont(style.titleFont(), 100));
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
