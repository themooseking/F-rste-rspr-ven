package presentation;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Car;
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

	public void proposalOverviewUI(String customerCPR) {
		Customer customer = new Customer(88888888, "John Brick", "3213909874", "johnshitsbricks@gmail.dk",
				"Brick st. 11", 7400);

		HBox hbox = new HBox(proposalTableView(customer));
		hbox.setAlignment(Pos.CENTER);

		VBoxWithStyle vbox = new VBoxWithStyle(title(customer), hbox, buttons());
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	//////////////////////////////
	// TableView
	//////////////////////////////

	private GridPane proposalTableView(Customer customer) {
		GridPaneCenter grid = new GridPaneCenter();

		//////////////// *****************************************************************************************************
		Car carTest = new Car(456, "F8 Tributo", 2349000, 5, 2020, "NEW");
		Car carTest2 = new Car(132, "Ferrari Roma", 1859000, 3000, 2018, "USED");

		Proposal propTest = new Proposal(2103, customer, 6.9, 45000, 12, LocalDate.now(), "ONGOING",
				LoggedInST.getUser(), carTest);
		Proposal propTest2 = new Proposal(2117, customer, 8.5, 200000, 60, LocalDate.now(), "COMPLETE",
				LoggedInST.getUser(), carTest2);

		ArrayList<Proposal> propList = new ArrayList<Proposal>();
		propList.add(propTest);
		propList.add(propTest2);
		//////////// **********************************************************************************************************************

		ObservableList<Proposal> eventList = FXCollections.observableArrayList();
		eventList.addAll(propList);
		
		TableViewWithStyle table = new TableViewWithStyle(grid, 0, 0);
		//table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TableColumnWithStyle proposalIdCol = new TableColumnWithStyle("Låne nr.", "proposalId");
		//proposalIdCol.setMinWidth(130);
		TableColumnWithStyle carCol = new TableColumnWithStyle("Bil", "car");
		//carCol.setMinWidth((1600-130)/6);
		TableColumnWithStyle interestCol = new TableColumnWithStyle("Rente (%)", "interest");
		//interestCol.setMinWidth((1600-130)/6);
		TableColumnWithStyle aprCol = new TableColumnWithStyle("ÅOP (%)", "customer");
		//aprCol.setMinWidth((1600-130)/6);
		TableColumnWithStyle totalSum = new TableColumnWithStyle("Sum (DKK)", "proposalTotalSum");
		//totalSum.setMinWidth((1600-130)/6);
		TableColumnWithStyle statusCol = new TableColumnWithStyle("Status", "proposalStatus");
		//statusCol.setMinWidth((1600-130)/6);
		
		table.setItems(eventList);
		table.getColumns().addAll(proposalIdCol, carCol, interestCol, aprCol, totalSum, statusCol);
		accessProposal(table);
		
		return grid;
	}

	private TableRow<Proposal> accessProposal(TableView<Proposal> table) {
		table.setRowFactory(e -> {
			TableRow<Proposal> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Proposal rowData = row.getItem();
					new SignProposalScreen().signProposalUI(rowData);
				}
			});
			return row;
		});
		return null;
	}

	//////////////////////////////
	// Buttons
	//////////////////////////////

	private Pane icon() {
		Image image = new Image(
				"https://upload.wikimedia.org/wikipedia/sco/thumb/d/d1/Ferrari-Logo.svg/1200px-Ferrari-Logo.svg.png");
		ImageView imageview = new ImageView(image);
		imageview.setFitHeight(150);
		imageview.setFitWidth(100);
		imageview.setX(100);
		imageview.setY(-30);

		Pane pane = new Pane(imageview);
		pane.setPadding(new Insets(0, 200, 0, 0));

		return pane;
	}

	private HBox buttons() {
		HBox hbox = new HBox(icon(), backButton(), newProposalButton());
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setBorder(new Border(new BorderStroke(Color.web(style.defaultHoverColor()), BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, new BorderWidths(7, 0, 0, 0))));

		return hbox;
	}

	private GridPane backButton() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setAlignment(Pos.CENTER_LEFT);

		ButtonWithStyle button = new ButtonWithStyle("Tilbage", grid, 0, 1);
		button.setOnAction(e -> {
			new CPRScreen().cprUI();

		});

		return grid;
	}

	private GridPane newProposalButton() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setAlignment(Pos.CENTER_LEFT);

		ButtonWithStyle button = new ButtonWithStyle("Ny", grid, 0, 1);
		button.setOnAction(e -> {
			new NewPropsalScreen().newProposalUI();

		});

		return grid;
	}

	//////////////////////////////
	// Label Title
	//////////////////////////////

	private Label title(Customer customer) {
		Label label = new Label("Lånetilbud for " + customer.getCustomerName());
		label.setFont(Font.loadFont("file:resources/fonts/FerroRosso.ttf", 120));
		label.setTextFill(Color.web(style.defaultTextColor()));
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
