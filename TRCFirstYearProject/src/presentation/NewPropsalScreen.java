package presentation;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import logic.Salesman;
import styles.ButtonWithStyle;
import styles.ComboBoxWithStyle;
import styles.GridPaneCenter;
import styles.LabelWithStyle;
import styles.RadioButtonWithStyle;
import styles.StyleClass;
import styles.TextAreaWithStyle;
import styles.TextFieldWithStyle;
import styles.VBoxWithStyle;

public class NewPropsalScreen {

	private StyleClass style = new StyleClass();

	private boolean rbState = false;
	private boolean recreate = true;

	private ComboBoxWithStyle modelcb;
	private ComboBoxWithStyle yearcb;
	private ComboBoxWithStyle equipmentcb;
	private TextFieldWithStyle paymenttf;
	private TextFieldWithStyle durationtf;
	private Scene scene;

	public void newProposalUI() {
		HBox hbox = new HBox(inputBox(), textReader());
		hbox.setAlignment(Pos.CENTER);

		VBoxWithStyle vbox = new VBoxWithStyle(title(), hbox, buttons());
		vbox.setAlignment(Pos.CENTER);

		scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	private GridPane inputBox() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(30));

		VBox vbox = new VBox(indentInput(), apiValues());
		vbox.setBorder(new Border(
				new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3))));
		grid.getChildren().add(vbox);

		return grid;
	}

	private GridPane indentInput() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(0));
		if (recreate == true) {
			grid.setPadding(new Insets(10, 10, 10, 10));
			recreate = false;
		}
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.setVgap(10);

		Salesman sm1 = new Salesman(12345678, "Johnny Sins", "sins@brazzers.com",
				"SENIOR VICE LEADING EXECUTIVE SUPREME SALESMAN", 1000000000);
		Salesman sm2 = new Salesman(98765432, "Riley Reid", "reid@mylips.com", "SLAVE", 5);
		Salesman sm3 = new Salesman(21586895, "Dean", "dean@eamv.dk", "CEO", 100);
		ArrayList<Salesman> userList = new ArrayList<Salesman>(Arrays.asList(sm1, sm2, sm3));

		//////////////////////////////
		// ADVANCED FILTER
		//////////////////////////////

		RadioButtonWithStyle rb = new RadioButtonWithStyle("Advanced Filter", grid, 0, 0);
		rbState = !rbState;
		rb.setSelected(rbState);
		rb.setMinWidth(300);
		GridPane.setColumnSpan(rb, 2);

		rb.setOnAction(e -> {
			grid.getChildren().clear();
			grid.getChildren().add(indentInput());
		});

		//////////////////////////////
		// MODEL
		//////////////////////////////

		LabelWithStyle model = new LabelWithStyle("Model: ", grid, 0, 1);
		GridPane.setColumnSpan(model, 2);
		modelcb = new ComboBoxWithStyle(FXCollections.observableArrayList(userList), grid, 3, 1);
		modelcb.setMinWidth(600);
		GridPane.setColumnSpan(modelcb, 2);

		if (rbState) {

			//////////////////////////////
			// YEAR
			//////////////////////////////

			LabelWithStyle year = new LabelWithStyle("År: ", grid, 1, 2);
			GridPane.setColumnSpan(year, 1);
			yearcb = new ComboBoxWithStyle(FXCollections.observableArrayList(userList), grid, 3, 2);
			yearcb.setMinWidth(600);
			GridPane.setColumnSpan(yearcb, 2);

			//////////////////////////////
			// EQUIPMENT
			//////////////////////////////

			LabelWithStyle equipment = new LabelWithStyle("Udstyr: ", grid, 1, 3);
			GridPane.setColumnSpan(equipment, 1);
			equipmentcb = new ComboBoxWithStyle(FXCollections.observableArrayList(userList), grid, 3, 3);
			equipmentcb.setMinWidth(600);
			GridPane.setColumnSpan(equipmentcb, 2);
		}

		//////////////////////////////
		// LOAN DURATION
		//////////////////////////////

		LabelWithStyle duration = new LabelWithStyle("Afbetalingsperiode: ", grid, 0, 4);
		GridPane.setColumnSpan(duration, 2);
		durationtf = new TextFieldWithStyle("ex. 32", grid, 3, 4);
		new LabelWithStyle(" Måned(er)", grid, 4, 4);

		//////////////////////////////
		// DOWN PAYMENT
		//////////////////////////////

		LabelWithStyle payment = new LabelWithStyle("Udbetaling: ", grid, 0, 5);
		GridPane.setColumnSpan(payment, 2);
		paymenttf = new TextFieldWithStyle("ex. 1234567", grid, 3, 5);
		new LabelWithStyle(" DKK", grid, 4, 5);

		//////////////////////////////
		// INDENTS
		//////////////////////////////

		new LabelWithStyle("   ", grid, 0, 0);
		new LabelWithStyle("  ", grid, 1, 0);
		new LabelWithStyle("  ", grid, 2, 0);
		new LabelWithStyle("  ", grid, 3, 0);
		new LabelWithStyle("  ", grid, 4, 0);

		new LabelWithStyle(" ", grid, 0, 6);
		new LabelWithStyle(" ", grid, 0, 7);

		return grid;
	}

	private GridPane apiValues() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setBackground(new Background(
				new BackgroundFill(Color.web(style.defaultHoverColor()), new CornerRadii(0), Insets.EMPTY)));

		new LabelWithStyle("Bank Rente:					", grid, 0, 0);
		new LabelWithStyle("Kredit Score: ", grid, 1, 0);

		return grid;
	}

	private GridPane textReader() {
		GridPaneCenter grid = new GridPaneCenter();

		TextAreaWithStyle ta = new TextAreaWithStyle(grid, 0, 0);
		ta.setText(textAreaString());
		textAreaEvents(ta);

		return grid;
	}

	private void textAreaEvents(TextAreaWithStyle ta) {

		//////////////////////////////
		// MODEL
		//////////////////////////////

		modelcb.setOnHiding(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				ta.setText(textAreaString());
			}
		});

		//////////////////////////////
		// YEAR
		//////////////////////////////

		yearcb.setOnHiding(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				ta.setText(textAreaString());
			}
		});

		//////////////////////////////
		// EQUIPMENT
		//////////////////////////////

		equipmentcb.setOnHiding(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				ta.setText(textAreaString());
			}
		});

		//////////////////////////////
		// LOAN DURATION
		//////////////////////////////

		durationtf.setOnKeyTyped(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				ta.setText(textAreaString());
			}
		});

		//////////////////////////////
		// DOWN PAYMENT
		//////////////////////////////

		paymenttf.setOnKeyTyped(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				ta.setText(textAreaString());
			}
		});
	}

	private String textAreaString() {
		String model = "";
		String year = "";
		String equipment = "";
		String duration = "";
		String payment = "";

		if (modelcb.getValue() != null) {
			model = modelcb.getValue().toString();
		}

		if (yearcb.getValue() != null) {
			year = yearcb.getValue().toString();
		}

		if (equipmentcb.getValue() != null) {
			equipment = equipmentcb.getValue().toString();
		}

		if (durationtf.getText() != null) {
			duration = durationtf.getText();
		}

		if (paymenttf.getText() != null) {
			payment = paymenttf.getText();
		}

		String string = "		       Oversigt" + "\n\nModel: 			" + model + "\nÅr: 				" + year
				+ "\nUdstyr: 			" + equipment + "\nAfbetalingsperiode:	" + duration + "\nUdbetaling: 		"
				+ payment;

		return string;
	}

	//////////////////////////////
	// Buttons
	//////////////////////////////

	private Pane icon() {
		Image image = new Image(
				"https://png2.cleanpng.com/sh/bd9a65de8a2b04505409571d2c2f04bb/L0KzQYm3V8E2N5Nsi5H0aYP2gLBuTgNkfZVqittqLXbogsPokvkua5J3RdNAdHAwccfwj71kd6R5iudFaXBxeX6BUcUubppmRd54Z3Awdrb5kvFzcV46eqZrYna6dYW6hsg0Pl87SqsBNkC3Q4K8U8E3QWE9T6o7N0i3PsH1h5==/kisspng-scuderia-ferrari-car-auto-avio-costruzioni-815-fia-logo-ferrari-5b4bbf7e43f836.6296604315316908782784.png");
		ImageView imageview = new ImageView(image);
		imageview.setFitHeight(150);
		imageview.setFitWidth(150);
		imageview.setX(100);
		imageview.setY(-30);

		Pane pane = new Pane(imageview);
		pane.setPadding(new Insets(0, 200, 0, 0));  

		return pane;
	}

	private HBox buttons() {
		HBox hbox = new HBox(icon(), completeButton(), completeButton(), completeButton(), completeButton());
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setBorder(new Border(new BorderStroke(Color.web(style.defaultHoverColor()), BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, new BorderWidths(7, 0, 0, 0))));

		return hbox; 
	}

	private GridPane completeButton() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setAlignment(Pos.CENTER_LEFT);

		ButtonWithStyle button = new ButtonWithStyle("Complete", grid, 0, 1);
		button.setOnAction(e -> {
			System.out.println(scene.getHeight() + " " + scene.getWidth());
		});

		return grid;
	}

	//////////////////////////////
	// Label Title
	//////////////////////////////

	private Label title() {
		Label label = new Label("New Proposal");
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
