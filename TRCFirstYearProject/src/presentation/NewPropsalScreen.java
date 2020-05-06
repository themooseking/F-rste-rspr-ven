package presentation;

import java.util.ArrayList;

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
import logic.DB_Controller;
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

	private ArrayList<String> yearList = new ArrayList<String>();
	private String yearString;

	private RadioButtonWithStyle rbAdvanced;
	private GridPaneCenter trgrid;
	private ComboBoxWithStyle modelcb;
	private ComboBoxWithStyle yearcb;
	private TextFieldWithStyle paymenttf;
	private TextFieldWithStyle durationtf;
	private DB_Controller controller = new DB_Controller();

	public void newProposalUI() {
		HBox hbox = new HBox(inputBox(), textReader());
		hbox.setAlignment(Pos.CENTER);

		VBoxWithStyle vbox = new VBoxWithStyle(title(), hbox, buttons());
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	private VBox inputBox() {
		VBox vbox = new VBox(indentInput(), apiValues());
		vbox.setBorder(new Border(
				new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3))));

		return vbox;
	}

	//////////////////////////////
	// INDENT
	//////////////////////////////

	private GridPane indentInput() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(0));
		if (recreate == true) {
			grid.setPadding(new Insets(10, 10, 10, 10));
		}
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.setVgap(10);

		//////////////////////////////
		// ADVANCED FILTER
		//////////////////////////////

		rbAdvanced = new RadioButtonWithStyle("Advanced Filter", grid, 0, 0);
		rbState = !rbState;
		rbAdvanced.setSelected(rbState);
		rbAdvanced.setMinWidth(300);
		GridPane.setColumnSpan(rbAdvanced, 2);

		rbAdvanced.setOnAction(e -> {
			grid.getChildren().clear();
			grid.getChildren().add(indentInput());
			trgrid.getChildren().clear();
			trgrid.getChildren().add(textReader());
		});

		if (rbState) {
			
			LabelWithStyle model = new LabelWithStyle("Model: ", grid, 0, 1);
			GridPane.setColumnSpan(model, 2);
			modelcb = new ComboBoxWithStyle(FXCollections.observableArrayList(controller.getNewCarModels()), grid, 3, 1);
			modelcb.setMinWidth(600);
			GridPane.setColumnSpan(modelcb, 2);

			LabelWithStyle year = new LabelWithStyle("År: ", grid, 1, 2);
			GridPane.setColumnSpan(year, 1);
			yearcb = new ComboBoxWithStyle(
					FXCollections.observableArrayList(controller.getNewCarYears(yearList.toString())), grid, 3, 2);
			yearcb.setMinWidth(600);
			GridPane.setColumnSpan(yearcb, 2);
		} else {
			
			LabelWithStyle model = new LabelWithStyle("Model: ", grid, 0, 1);
			GridPane.setColumnSpan(model, 2);
			modelcb = new ComboBoxWithStyle(FXCollections.observableArrayList(controller.getNewCars()), grid, 3, 1);
			modelcb.setMinWidth(600);
			GridPane.setColumnSpan(modelcb, 2);
			
			LabelWithStyle yearSpace = new LabelWithStyle("  ", grid, 0, 2);
			yearSpace.setMinHeight(yearcb.getPrefHeight());
		}

		LabelWithStyle duration = new LabelWithStyle("Afbetalingsperiode: ", grid, 0, 3);
		GridPane.setColumnSpan(duration, 2);
		durationtf = new TextFieldWithStyle("ex. 32", grid, 3, 3);
		new LabelWithStyle(" Måned(er)", grid, 4, 3);

		LabelWithStyle payment = new LabelWithStyle("Udbetaling: ", grid, 0, 4);
		GridPane.setColumnSpan(payment, 2);
		paymenttf = new TextFieldWithStyle("ex. 1234567", grid, 3, 4);
		new LabelWithStyle(" DKK", grid, 4, 4);

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

	//////////////////////////////
	// BANK RATE AND CREDIT SCORE
	//////////////////////////////

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
		trgrid = new GridPaneCenter();
		trgrid.setPadding(new Insets(0));
		if (recreate == true) {
			trgrid.setPadding(new Insets(10, 10, 10, 10));
			recreate = false;
		}

		TextAreaWithStyle ta = new TextAreaWithStyle(trgrid, 0, 0);
		ta.setText(textAreaString());
		textAreaEvents(ta);

		return trgrid;
	}

	//////////////////////////////
	// EVENTS
	//////////////////////////////

	private void textAreaEvents(TextAreaWithStyle ta) {
		
//		rbAdvanced.setOn

		modelcb.setOnHiding(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				ta.setText(textAreaString());
				if (modelcb.getValue() != null) {
					yearList = controller.getNewCarYears(modelcb.getValue().toString());
					yearcb.getItems().clear();
					yearcb.getItems().addAll(yearList);
				}
			}
		});

		yearcb.setOnHiding(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				ta.setText(textAreaString());
			}
		});

		durationtf.setOnKeyTyped(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				ta.setText(textAreaString());
			}
		});

		paymenttf.setOnKeyTyped(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				ta.setText(textAreaString());
			}
		});
	}

	//////////////////////////////
	// TEXT AREA TEXT
	//////////////////////////////

	private String textAreaString() {
		String model = "";
		yearString = "";
		String duration = "";
		String payment = "";

		if (modelcb.getValue() != null) {
			model = modelcb.getValue().toString();
		}

		if (yearcb.getValue() != null) {
			yearString = yearcb.getValue().toString();
		}

		if (durationtf.getText() != null) {
			duration = durationtf.getText();
		}

		if (paymenttf.getText() != null) {
			payment = paymenttf.getText();
		}

		String string = "		       Oversigt" + "\n\nModel: 			" + model + "\nÅr: 				" + yearString
				+ "\nAfbetalingsperiode:	" + duration + "\nUdbetaling: 		" + payment;

		return string;
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
