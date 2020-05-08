package presentation;

import java.text.DecimalFormat;
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
import logic.Customer;
import logic.DB_Controller;
import logic.Proposal;
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
	private Customer customer;

	public void proposalOverviewUI(String customerCPR) {
		customer;
		
		HBox hbox = new HBox(proposalScrollPane());
		hbox.setAlignment(Pos.CENTER);

		VBoxWithStyle vbox = new VBoxWithStyle(title(), hbox, buttons());
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	private VBox inputBox() {
		VBox vbox = new VBox();
		vbox.setBorder(new Border(
				new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3))));

		return vbox;
	}

	//////////////////////////////
	// Scroll Pane
	//////////////////////////////

	private GridPane proposalScrollPane() {
		GridPaneCenter grid = new GridPaneCenter();

		Proposal propTest = new Proposal(null, null);
		
		ObservableList<Proposal> eventList = FXCollections.observableArrayList();
		eventList.addAll();

		TableColumn<Proposal, String> proposalIdCol = new TableColumn<Proposal, String>(
				"Låne nr.");
		proposalIdCol.setCellValueFactory(new PropertyValueFactory<Proposal, String>("Home"));

		TableColumn<Proposal, String> carCol = new TableColumn<Proposal, String>(
				"Bil");
		carCol.setCellValueFactory(new PropertyValueFactory<Proposal, String>("Time"));

		TableColumn<Proposal, String> interestCol = new TableColumn<Proposal, String>(
				"Rente (%)");
		interestCol.setCellValueFactory(new PropertyValueFactory<Proposal, String>("Away"));
		
		TableColumn<Proposal, String> aprCol = new TableColumn<Proposal, String>(
				"ÅOP (%)");
		aprCol.setCellValueFactory(new PropertyValueFactory<Proposal, String>("Away"));
		
		TableColumn<Proposal, String> totalSum = new TableColumn<Proposal, String>(
				"Sum (DKK)");
		totalSum.setCellValueFactory(new PropertyValueFactory<Proposal, String>("Away"));
		
		TableColumn<Proposal, String> statusCol = new TableColumn<Proposal, String>(
				"Status");
		statusCol.setCellValueFactory(new PropertyValueFactory<Proposal, String>("Away"));

		TableView<Proposal> table = new TableView<Proposal>();
		table.setPlaceholder(new Label("Ingen lånetilbud for denne kunde endnu."));

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

	private Label title() {
		Label label = new Label("Lånetilbud for **Kunde**");
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
