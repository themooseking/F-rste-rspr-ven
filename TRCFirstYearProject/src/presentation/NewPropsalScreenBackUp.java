package presentation;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import logic.Customer;
import logic.DB_Controller;
import logic.Proposal;
import styles.ButtonWithStyle;
import styles.ComboBoxWithStyle;
import styles.GridPaneCenter;
import styles.LabelWithStyle;
import styles.RadioButtonWithStyle;
import styles.StyleClass;
import styles.TextAreaWithStyle;
import styles.TextFieldWithStyle;
import styles.VBoxWithStyle;

public class NewPropsalScreenBackUp {

	private StyleClass style = new StyleClass();

	private boolean rbState = false;
	private boolean recreate = true;

	private ArrayList<String> yearList = new ArrayList<String>();
	private String yearString;

	private RadioButtonWithStyle rbOld;
	private GridPaneCenter trgrid;
	private ComboBoxWithStyle modelcb;
	private ComboBoxWithStyle yearcb;
	private TextFieldWithStyle paymenttf;
	private TextFieldWithStyle regnrtf;
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
		// OLD FILTER
		//////////////////////////////

		rbOld = new RadioButtonWithStyle("Gamle Biler", grid, 0, 0);
		rbState = !rbState;
		rbOld.setSelected(!rbState);
		rbOld.setMinWidth(300);
		GridPane.setColumnSpan(rbOld, 2);

		rbOld.setOnAction(e -> {
			grid.getChildren().clear();
			grid.getChildren().add(indentInput());
			trgrid.getChildren().clear();
			trgrid.getChildren().add(textReader());
		});

		if (rbState) {

			LabelWithStyle model = new LabelWithStyle("Model: ", grid, 0, 1);
			GridPane.setColumnSpan(model, 2);
			modelcb = new ComboBoxWithStyle(FXCollections.observableArrayList(controller.getNewCarModels()), grid, 3,
					1);
			modelcb.setMinWidth(600);
			GridPane.setColumnSpan(modelcb, 2);

			yearcb = new ComboBoxWithStyle(FXCollections.observableArrayList(""), grid, 3, 2);
			yearcb.setVisible(false);

			regnrtf = new TextFieldWithStyle("ex. 465411", grid, 3, 3);
			regnrtf.setVisible(false);

		} else {

			LabelWithStyle model = new LabelWithStyle("Model: ", grid, 0, 1);
			GridPane.setColumnSpan(model, 2);
			modelcb = new ComboBoxWithStyle(FXCollections.observableArrayList(controller.getNewCars()), grid, 3, 1);
			modelcb.setMinWidth(600);
			GridPane.setColumnSpan(modelcb, 2);

			LabelWithStyle year = new LabelWithStyle("År: ", grid, 1, 2);
			GridPane.setColumnSpan(year, 1);
			yearcb = new ComboBoxWithStyle(
					FXCollections.observableArrayList(controller.getNewCarYears(yearList.toString())), grid, 3, 2);
			yearcb.setMinWidth(600);
			yearcb.setDisable(true);
			GridPane.setColumnSpan(yearcb, 2);

			LabelWithStyle regnr = new LabelWithStyle("Reg. Nr.: ", grid, 0, 3);
			GridPane.setColumnSpan(regnr, 2);
			regnrtf = new TextFieldWithStyle("ex. 465411", grid, 3, 3);
			regnrtf.setMinWidth(600);
			regnrtf.setDisable(true);
			GridPane.setColumnSpan(regnrtf, 2);

		}

		LabelWithStyle duration = new LabelWithStyle("Afbetalingsperiode: ", grid, 0, 4);
		GridPane.setColumnSpan(duration, 2);
		durationtf = new TextFieldWithStyle("ex. 32", grid, 3, 4);
		new LabelWithStyle(" Måned(er)", grid, 4, 4);

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

	//////////////////////////////
	// BANK RATE AND CREDIT SCORE
	//////////////////////////////

	private GridPane apiValues() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setBackground(new Background(
				new BackgroundFill(Color.web(style.defaultHoverColor()), new CornerRadii(0), Insets.EMPTY)));

		Customer customer = new Customer(88888888, "John Brick", "3213909874", "johnshitsbricks@gmail.dk",
				"Brick st. 11", 7400);
		Proposal proposal = new Proposal(customer, LoggedInST.getUser());

		new LabelWithStyle("Bank Rente:	", grid, 0, 0);
		TextFieldWithStyle banktf = new TextFieldWithStyle("", grid, 1, 0);
		banktf.setMaxWidth(100);
		banktf.setDisable(true);
		banktf.setOpacity(100);
		proposal.doubleProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double interest = proposal.getInterest();
				String format = new DecimalFormat("0.00").format(interest);
				banktf.setText(format);
			}
		});

		new LabelWithStyle("			", grid, 2, 0);

		new LabelWithStyle("Kredit Score: ", grid, 3, 0);
		TextFieldWithStyle credittf = new TextFieldWithStyle("", grid, 4, 0);
		credittf.setMaxWidth(100);
		credittf.setDisable(true);
		credittf.setOpacity(100);
		customer.stringProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				credittf.setText(customer.getCreditScore().toString());
			}
		});

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

		modelcb.setOnHiding(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				ta.setText(textAreaString());
				if (modelcb.getValue() != null) {
					yearList = controller.getNewCarYears(modelcb.getValue().toString());
					yearcb.getItems().clear();
					yearcb.getItems().addAll(yearList);
					yearcb.setDisable(false);
					regnrtf.setDisable(false);
				}
			}
		});

		yearcb.setOnHiding(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				ta.setText(textAreaString());
			}
		});

		regnrtf.setOnKeyTyped(new EventHandler<Event>() {
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
		String regnr = "";
		String duration = "";
		String payment = "";

		if (modelcb.getValue() != null) {
			model = modelcb.getValue().toString();
		}

		if (yearcb.getValue() != null) {
			yearString = yearcb.getValue().toString();
		}

		if (regnrtf.getText() != null) {
			regnr = regnrtf.getText();
		}

		if (durationtf.getText() != null) {
			duration = durationtf.getText();
		}

		if (paymenttf.getText() != null) {
			payment = paymenttf.getText();
		}

		String string;
		if (rbState) {
			string = "				       Oversigt" + "\n\nModel:	 			" + model
					+ "\nAfbetalingsperiode:		" + duration + "\nUdbetaling: 			" + payment;
		} else {
			string = "				       Oversigt" + "\n\nModel: 				" + model + "\nÅr: 					"
					+ yearString + "\nReg. Nr.: 				" + regnr + "\nAfbetalingsperiode:		" + duration
					+ "\nUdbetaling: 			" + payment;
		}

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
