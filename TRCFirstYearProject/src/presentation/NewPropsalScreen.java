package presentation;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Salesman;
import styles.ButtonWithStyle;
import styles.ComboBoxWithStyle;
import styles.GridPaneCenter;
import styles.LabelWithStyle;
import styles.StyleClass;
import styles.TextAreaWithStyle;
import styles.TextFieldWithStyle;
import styles.VBoxWithStyle;

public class NewPropsalScreen {

	public void newProposalUI() {
		VBox vbox2 = new VBox(title(), inputBox());
		
		HBox hbox = new HBox(vbox2, textReader());
		
		VBoxWithStyle vbox = new VBoxWithStyle(hbox, completeButton());
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, 1800, 980);
		sceneSetup(scene);
	}

	private GridPane inputBox() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(30));

		VBox vbox = new VBox(modelComboBox(), yearComboBox(), equipmentComboBox(), loanDurationTextfield(),
				downPaymentTextfield(), apiValues());
		vbox.setBorder(new Border(
				new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3))));
		grid.getChildren().add(vbox);

		return grid;
	}

	private GridPane modelComboBox() {
		GridPaneCenter grid = new GridPaneCenter();

		Salesman sm1 = new Salesman(12345678, "Johnny Sins", "sins@brazzers.com",
				"SENIOR VICE LEADING EXECUTIVE SUPREME SALESMAN", 1000000000);
		Salesman sm2 = new Salesman(98765432, "Riley Reid", "reid@mylips.com", "SLAVE", 5);
		Salesman sm3 = new Salesman(21586895, "Dean", "dean@eamv.dk", "CEO", 100);
		ArrayList<Salesman> userList = new ArrayList<Salesman>(Arrays.asList(sm1, sm2, sm3));

		new LabelWithStyle("Model: ", grid, 0, 0);
		new ComboBoxWithStyle(FXCollections.observableArrayList(userList), grid, 1, 0);

		return grid;
	}

	private GridPane yearComboBox() {
		GridPaneCenter grid = new GridPaneCenter();

		Salesman sm1 = new Salesman(12345678, "Johnny Sins", "sins@brazzers.com",
				"SENIOR VICE LEADING EXECUTIVE SUPREME SALESMAN", 1000000000);
		Salesman sm2 = new Salesman(98765432, "Riley Reid", "reid@mylips.com", "SLAVE", 5);
		Salesman sm3 = new Salesman(21586895, "Dean", "dean@eamv.dk", "CEO", 100);
		ArrayList<Salesman> userList = new ArrayList<Salesman>(Arrays.asList(sm1, sm2, sm3));

		new LabelWithStyle("År: ", grid, 0, 0);
		new ComboBoxWithStyle(FXCollections.observableArrayList(userList), grid, 1, 0);

		return grid;
	}

	private GridPane equipmentComboBox() {
		GridPaneCenter grid = new GridPaneCenter();

		Salesman sm1 = new Salesman(12345678, "Johnny Sins", "sins@brazzers.com",
				"SENIOR VICE LEADING EXECUTIVE SUPREME SALESMAN", 1000000000);
		Salesman sm2 = new Salesman(98765432, "Riley Reid", "reid@mylips.com", "SLAVE", 5);
		Salesman sm3 = new Salesman(21586895, "Dean", "dean@eamv.dk", "CEO", 100);
		ArrayList<Salesman> userList = new ArrayList<Salesman>(Arrays.asList(sm1, sm2, sm3));

		new LabelWithStyle("Udstyr: ", grid, 0, 0);
		new ComboBoxWithStyle(FXCollections.observableArrayList(userList), grid, 1, 0);

		return grid;
	}

	private GridPane loanDurationTextfield() {
		GridPaneCenter grid = new GridPaneCenter();

		new LabelWithStyle("Afbetalingsperiode: ", grid, 0, 0);
		new TextFieldWithStyle("ex. 40", grid, 1, 0);
		new LabelWithStyle(" Måneder", grid, 2, 0);

		return grid;
	}

	private GridPane downPaymentTextfield() {
		GridPaneCenter grid = new GridPaneCenter();

		new LabelWithStyle("Udbetaling: ", grid, 0, 0);
		new TextFieldWithStyle("ex. 1234567", grid, 1, 0);
		new LabelWithStyle(" DKK", grid, 2, 0);

		return grid;
	}
	
	private GridPane apiValues() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setBackground(new Background(new BackgroundFill(Color.web(new StyleClass().defaultHoverColor()), new CornerRadii(0), Insets.EMPTY)));

		new LabelWithStyle("Bank Rente: ", grid, 0, 0);
		new LabelWithStyle("Kredit Score: ", grid, 1, 0);

		return grid;
	}
	
	private GridPane textReader() {
		GridPaneCenter grid = new GridPaneCenter();

		TextAreaWithStyle ta = new TextAreaWithStyle(grid, 0, 0);

		return grid;
	}

	private GridPane completeButton() {
		GridPaneCenter grid = new GridPaneCenter();

		ButtonWithStyle button = new ButtonWithStyle("Complete", grid, 0, 1);
		button.setOnAction(e -> {

		});

		return grid;
	}

	//////////////////////////////
	// Label Title
	//////////////////////////////

	private Label title() {
		Label label = new Label("New Proposal");
		label.setFont(Font.loadFont("file:resources/fonts/FerroRosso.ttf", 120));
		label.setTextFill(Color.web(new StyleClass().defaultTextColor()));
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
