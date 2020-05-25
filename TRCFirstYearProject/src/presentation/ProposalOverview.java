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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Customer;
import logic.DB_Controller;
import logic.Proposal;
import logic.Status;
import styles.ButtonWithStyle;
import styles.GridPaneWithStyle;
import styles.StyleClass;
import styles.TableColumnWithStyle;
import styles.TableViewWithStyle;
import styles.VBoxWithStyle;

public class ProposalOverview {

	private StyleClass style = new StyleClass();
	private DB_Controller controller = new DB_Controller();
	private Customer customer;

	public void defaultUI(String customerCPR) {
		View view = View.Default;
		customer = controller.getCustomer(Customer.removeDashFromCpr(customerCPR));

		VBoxWithStyle vbox = new VBoxWithStyle(title(customer.toString(), view), proposalTableView(view),
				buttons(view));
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	public void salesmanUI() {
		View view = View.Salesman;
		VBoxWithStyle vbox = new VBoxWithStyle(title(LoggedInST.getUser().toString(), view), proposalTableView(view),
				buttons(view));
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	public void cosUI() {
		View view = View.ChiefOfSales;
		VBoxWithStyle vbox = new VBoxWithStyle(title("", view), proposalTableView(view), buttons(view));
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	//////////////////////////////
	// TABLEVIEW
	//////////////////////////////

	private GridPane proposalTableView(View view) {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER);

		ArrayList<Proposal> arrayList = new ArrayList<Proposal>();
		if (view == View.Default) {
			arrayList = controller.getProposalByCustomer(customer);
		} else if (view == View.Salesman) {
			arrayList = controller.getProposalBySalesman(LoggedInST.getUser());
		} else if (view == View.ChiefOfSales) {
			arrayList = controller.getAwaitingProposals();
		}

		ObservableList<Proposal> eventList = FXCollections.observableArrayList();
		eventList.addAll(arrayList);

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

		map.put("Låne nr.", "proposalId");
		map.put("Dato", "proposalDate");
		map.put("Bil", "car");
		if (view == View.Salesman) {
			map.put("Kunde", "customer");
		}
		map.put("Rente (%)", "totalInterest");
		map.put("Sum (DKK)", "proposalTotalSum");
		if (view != View.Salesman) {
			map.put("Sælger Navn", "salesman");
		}
		if (view == View.ChiefOfSales) {
			map.put("Salgs titel", "salesmanTitel");
		}

		TableViewWithStyle table = new TableViewWithStyle(grid, 0, 0);
		table.setItems(eventList);
		for (String key : map.keySet()) {
			table.getColumns().add(createColumn(key, map));
		}
		TableColumn<Proposal, Status> statusCol = new TableColumn<Proposal, Status>("Status");
		statusCol.setCellValueFactory(new PropertyValueFactory<Proposal, Status>("proposalStatus"));
		statusCol.setMinWidth((style.sceneX() - 200) / (map.size() + 1));
		table.getColumns().add(statusCol);

		customiseFactory(statusCol);
		rowEvent(table, view);

		return grid;
	}

	private void customiseFactory(TableColumn<Proposal, Status> statusCol) {
		statusCol.setCellFactory(column -> {
			return new TableCell<Proposal, Status>() {
				@Override
				protected void updateItem(Status item, boolean empty) {
					super.updateItem(item, empty);

					setText(empty ? "" : getItem().toString());

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

	private TableRow<Proposal> rowEvent(TableView<Proposal> table, View view) {
		table.setRowFactory(e -> {
			TableRow<Proposal> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Proposal rowData = row.getItem();
					if (view == View.Default) {
						new SignProposalScreen(rowData).defaultUI();
					} else if (view == View.Salesman) {
						new SignProposalScreen(rowData).salesmanUI();
					} else if (view == View.ChiefOfSales) {
						new SignProposalScreen(rowData).cosUI();
						;
					}
				}
			});

			return row;
		});

		return null;
	}

	//////////////////////////////
	// BUTTONS
	//////////////////////////////

	private HBox buttons(View view) {
		HBox hbox = new HBox(backButton());
		hbox.setAlignment(Pos.BASELINE_RIGHT);
		hbox.setPadding(new Insets(105, 50, 0, 0));

		if (view == View.Default) {
			hbox.getChildren().add(newProposalButton());
		}

		return hbox;
	}

	private GridPane backButton() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER_LEFT);

		ButtonWithStyle button = new ButtonWithStyle("Tilbage", grid, 0, 0);
		button.setOnAction(e -> {
			new CPRScreen().show();
		});

		return grid;
	}

	private GridPane newProposalButton() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER_LEFT);

		ButtonWithStyle button = new ButtonWithStyle("Ny", grid, 0, 0);
		button.setOnAction(e -> {
			new NewProposalScreen(customer).show();
		});

		return grid;
	}

	//////////////////////////////
	// LABEL TITLE
	//////////////////////////////

	private Label title(String person, View view) {
		Label label = null;
		if (view == View.Default) {
			label = new Label("Lånetilbud for " + person);
		} else if (view == View.Salesman) {
			label = new Label(person + "s lånetilbud");
		} else if (view == View.ChiefOfSales) {
			label = new Label("Godkend tilbud");
		}

		label.setFont(Font.loadFont(style.titleFont(), 100));
		label.setTextFill(Color.web(style.grey()));
		return label;
	}

	//////////////////////////////
	// SCENE STUFF
	//////////////////////////////

	private void sceneSetup(Scene scene) {
		PrimaryStageST.getStage().setTitle("The Red Car");
		PrimaryStageST.getStage().setScene(scene);
		PrimaryStageST.getStage().show();
	}
}
