package presentation;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Car;
import logic.Customer;
import logic.DB_Controller;
import logic.Proposal;
import logic.Salesman;
import styles.ButtonWithStyle;
import styles.ComboBoxWithStyle;
import styles.GridPaneCenter;
import styles.LabelWithStyle;
import styles.RadioButtonWithStyle;
import styles.StyleClass;
import styles.TableViewWithStyle;
import styles.TextAreaWithStyle;
import styles.TextFieldWithStyle;
import styles.VBoxWithStyle;

public class ProposalOverview {
	private StyleClass style = new StyleClass();

	private GridPaneCenter trgrid;

	private DB_Controller controller = new DB_Controller();

	public void proposalOverviewUI(String customerCPR) {
		Customer customer = new Customer(88888888, "John Brick", "3213909874", "johnshitsbricks@gmail.dk", "Brick st. 11", 7400);
		
		HBox hbox = new HBox(proposalTableView(customer));
		hbox.setAlignment(Pos.CENTER);

		VBoxWithStyle vbox = new VBoxWithStyle(title(customer), hbox, buttons());
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	//////////////////////////////
	// Scroll Pane
	//////////////////////////////

	private GridPane proposalTableView(Customer customer) {
		GridPaneCenter grid = new GridPaneCenter();
		
		////////////////*****************************************************************************************************
		Car carTest = new Car(456, "F8 Tributo", 2349000, 5, 2020, "NEW");
		Car carTest2 = new Car(132, "Ferrari Roma", 1859000, 3000, 2018, "USED");


		Proposal propTest = new Proposal(02103, customer, 6.9, 45000, 12, LocalDate.now(), "ONGOING", LoggedInST.getUser(), carTest);
		
		ArrayList<Proposal> propList = new ArrayList<Proposal>();
		propList.add(propTest);
		////////////**********************************************************************************************************************
		
		ObservableList<Proposal> eventList = FXCollections.observableArrayList();
		eventList.addAll(propList);

		TableColumn<Proposal, Integer> proposalIdCol = new TableColumn<Proposal, Integer>(
				"Låne nr.");
		proposalIdCol.setCellValueFactory(new PropertyValueFactory<Proposal, Integer>("proposalId"));

		TableColumn<Proposal, Car> carCol = new TableColumn<Proposal, Car>(
				"Bil");
		carCol.setCellValueFactory(new PropertyValueFactory<Proposal, Car>("car"));

		TableColumn<Proposal, Double> interestCol = new TableColumn<Proposal, Double>(
				"Rente (%)");
		interestCol.setCellValueFactory(new PropertyValueFactory<Proposal, Double>("interest"));
		
		TableColumn<Proposal, Customer> aprCol = new TableColumn<Proposal, Customer>(
				"ÅOP (%)");
		aprCol.setCellValueFactory(new PropertyValueFactory<Proposal, Customer>("customer"));
		
		TableColumn<Proposal, Double> totalSum = new TableColumn<Proposal, Double>(
				"Sum (DKK)");
		totalSum.setCellValueFactory(new PropertyValueFactory<Proposal, Double>("proposalToalSum"));
		
		TableColumn<Proposal, String> statusCol = new TableColumn<Proposal, String>(
				"Status");
		statusCol.setCellValueFactory(new PropertyValueFactory<Proposal, String>("proposalStatus"));

		TableView<Proposal> table = new TableView<Proposal>();
		//table.setPlaceholder(new Label("Ingen lånetilbud for denne kunde endnu."));

		table.setPrefWidth(800);
		table.setPrefHeight(600);
		grid.setColumnSpan(table, 3);
		grid.setRowSpan(table, 14);

		table.setItems(eventList);
		table.getColumns().addAll(proposalIdCol, carCol, interestCol, aprCol, totalSum, statusCol);
		grid.getChildren().add(table);
		
		return grid;
	}

	private GridPane customersProposalSetup() {
		GridPaneCenter grid = new GridPaneCenter();
		ArrayList<ButtonWithStyle> proposalButtons = new ArrayList<ButtonWithStyle>();

		for (int i = 0; i < 5; i++) {
			ButtonWithStyle propButt = new ButtonWithStyle("SUPER PISSE LANG LÅNETILBUD DER. EMIL HAR KAGE MED", grid, 0, i);
			propButt.setOnAction(e -> {
				//new UNDSKRIVSCREE().UNDERSKRIVSCREENUI(æbler);
			});
			propButt.setPrefSize(1100, 100);
			proposalButtons.add(propButt);
		}

		return grid;
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
