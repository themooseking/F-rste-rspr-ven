package presentation;

import java.text.DecimalFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import logic.Car;
import logic.Customer;
import logic.DB_Controller;
import logic.Proposal;
import styles.ButtonWithStyle;
import styles.ComboBoxWithStyle;
import styles.GridPaneCenter;
import styles.LabelWithStyle;
import styles.ProgressIndicatorWithStyle;
import styles.RadioButtonWithStyle;
import styles.StyleClass;
import styles.TextFieldWithStyle;
import styles.VBoxWithStyle;

public class NewPropsalScreen {

	private Customer customer;
	private Proposal proposal;

	private StyleClass style = new StyleClass();
	private DB_Controller controller = new DB_Controller();

	private boolean rbState = false;
	private boolean recreate = true;
	private TextReader tr;

	private ComboBoxWithStyle modelcb;
	private ComboBoxWithStyle yearcb;
	private ComboBoxWithStyle regnrcb;
	private TextFieldWithStyle paymenttf;
	private TextFieldWithStyle durationtf;
	private ButtonWithStyle nextButton;

	public NewPropsalScreen(Customer customer) {
		this.customer = customer;
		this.proposal = new Proposal(customer, LoggedInST.getUser());
		this.tr = new TextReader(customer, proposal);
	}

	public void newProposalUI() {
		HBox hbox = new HBox(50, fitter(), tr.textReader());
		hbox.setAlignment(Pos.CENTER);

		VBoxWithStyle vbox = new VBoxWithStyle(title(), hbox, buttons());
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	//////////////////////////////
	// Input Fields
	//////////////////////////////

	private VBox fitter() {
		VBox vbox = new VBox(inputBox());
		vbox.setAlignment(Pos.CENTER);

		return vbox;
	}

	private VBox inputBox() {
		VBox vbox = new VBox(20, indentInput(), apiValues());
		vbox.setPadding(new Insets(10));
		vbox.setBorder(new Border(
				new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3))));
		vbox.setBackground(
				new Background(new BackgroundFill(Color.web(style.white()), new CornerRadii(0), Insets.EMPTY)));

		return vbox;
	}

	private GridPane indentInput() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER); 
		grid.setPadding(new Insets(0));
		grid.setVgap(15);

		if (recreate == true) {
			grid.setPadding(new Insets(0, 0, 10, 0));
			recreate = false;
		}

		//////////////////////////////
		// Toggle
		//////////////////////////////

		RadioButtonWithStyle rbOld = new RadioButtonWithStyle("Gamle Biler", grid, 0, 0);
		rbState = !rbState;
		rbOld.setSelected(!rbState);

		rbOld.setOnAction(e -> {
			grid.getChildren().clear();
			grid.getChildren().add(indentInput());
			tr.clearTR();
			nextButton.setDisable(true);
		}); 

		if (rbState) {

			LabelWithStyle modelLabel = new LabelWithStyle("Model: ", grid, 0, 1);
			modelcb = new ComboBoxWithStyle(FXCollections.observableArrayList(controller.getNewCars()), grid, 3, 1);
			indentComboBox(modelcb, 400);
			modelcb.setOnHiding(e -> {
				proposal.setCar((Car) modelcb.getValue());
				tr.update(rbState, modelcb, yearcb, regnrcb, durationtf, paymenttf);
				nextButtonDisable();
			});

			yearcb = new ComboBoxWithStyle(FXCollections.observableArrayList(""), grid, 3, 2);
			yearcb.setVisible(false);
			indentComboBox(yearcb, 400);
			GridPane.setColumnSpan(yearcb, 2);

			regnrcb = new ComboBoxWithStyle(FXCollections.observableArrayList(""), grid, 3, 3);
			regnrcb.setVisible(false);
			indentComboBox(regnrcb, 400);
			GridPane.setColumnSpan(regnrcb, 2);

		} else {

			LabelWithStyle modelLabel = new LabelWithStyle("Model: ", grid, 0, 1);
			modelcb = new ComboBoxWithStyle(FXCollections.observableArrayList(controller.getCarModels()), grid, 3, 1);
			indentComboBox(modelcb, 400);
			modelcb.setOnAction(e -> {
				if (modelcb.getValue() != null) {
					yearcb.getItems().clear();
					yearcb.setItems(FXCollections
							.observableArrayList(controller.getCarFactoryYears(modelcb.getValue().toString())));
					yearcb.setDisable(false);
				}

				if (modelcb.getValue() != null && yearcb.getValue() == null) {
					regnrcb.setItems(
							FXCollections.observableArrayList(controller.getUsedCars(modelcb.getValue().toString())));
				}
				tr.update(rbState, modelcb, yearcb, regnrcb, durationtf, paymenttf);
			});

			new LabelWithStyle("År: ", grid, 0, 2);
			yearcb = new ComboBoxWithStyle(FXCollections.observableArrayList(""), grid, 3, 2);
			indentComboBox(yearcb, 400);
			yearcb.setDisable(true);
			yearcb.setOnHiding(e -> {
				if (yearcb.getValue() != null) {
					regnrcb.setItems(FXCollections.observableArrayList(
							controller.getUsedCars(modelcb.getValue().toString(), yearcb.getValue().toString())));
				}
				tr.update(rbState, modelcb, yearcb, regnrcb, durationtf, paymenttf);
			});

			LabelWithStyle regnr = new LabelWithStyle("Reg. Nr.: ", grid, 0, 3);
			regnrcb = new ComboBoxWithStyle(FXCollections.observableArrayList(controller.getUsedCars()), grid, 3, 3);
			indentComboBox(regnrcb, 400);
			regnrcb.setOnHiding(e -> {
				proposal.setCar((Car) regnrcb.getValue());
				nextButtonDisable();
				tr.update(rbState, modelcb, yearcb, regnrcb, durationtf, paymenttf);
			});
		}

		LabelWithStyle durationLabel = new LabelWithStyle("Afbetalingsperiode: ", grid, 0, 4);
		durationtf = new TextFieldWithStyle("ex. 32", grid, 3, 4);
		indentTextField(durationtf);
		new LabelWithStyle(" Måned(er)", grid, 4, 4);
		durationtf.setOnKeyReleased(e -> {
			if (!durationtf.getText().isEmpty()) {
				proposal.setLoanDuration(Integer.parseInt(durationtf.getText()));
			} else {
				proposal.setLoanDuration(0);
			}
			nextButtonDisable();
			tr.update(rbState, modelcb, yearcb, regnrcb, durationtf, paymenttf);
		});

		LabelWithStyle payment = new LabelWithStyle("Udbetaling: ", grid, 0, 5);
		paymenttf = new TextFieldWithStyle("ex. 1234567", grid, 3, 5);
		indentTextField(paymenttf);
		if (customer.getCreditScore() == null) {
			paymenttf.setDisable(true);
		}
		new LabelWithStyle(" DKK", grid, 4, 5);
		paymenttf.setOnKeyReleased(e -> {
			if (!paymenttf.getText().isEmpty()) {
				proposal.setDownPayment(Integer.parseInt(paymenttf.getText()));
			} else {
				proposal.setDownPayment(0);
			}
			nextButtonDisable();
			tr.update(rbState, modelcb, yearcb, regnrcb, durationtf, paymenttf);
		});

		return grid;
	}

	private void indentComboBox(ComboBoxWithStyle cb, int width) {
		cb.setMinWidth(width);
		cb.setMinHeight(50);
		cb.setPrefHeight(50);
		cb.setMaxHeight(50);
		GridPane.setColumnSpan(cb, 2);
	}

	private void indentTextField(TextFieldWithStyle tf) {
		tf.setMinHeight(50);
		tf.setPrefHeight(50);
		tf.setMaxHeight(50);
		tf.setMinWidth(300);
		tf.setMaxWidth(300);
	}

	//////////////////////////////
	// BANK RATE AND CREDIT SCORE
	//////////////////////////////

	private GridPane apiValues() {
		GridPaneCenter grid = new GridPaneCenter(Pos.BOTTOM_LEFT);
		grid.setPadding(new Insets(0, 10, 0, 10));
		grid.setBackground(
				new Background(new BackgroundFill(Color.web(style.grey()), new CornerRadii(0), Insets.EMPTY)));

		apiLabel("Bank rente:", grid, 0, 0, 120);

		TextFieldWithStyle apiInteresttf = new TextFieldWithStyle("", grid, 1, 0);
		apiTextField(apiInteresttf);
		interestEvent(apiInteresttf);

		ProgressIndicatorWithStyle progressIndicator = new ProgressIndicatorWithStyle(grid, 4, 0);

		apiLabel("		Kreditværdighed:", grid, 3, 0, 280);

		TextFieldWithStyle apiCredittf = new TextFieldWithStyle("", grid, 4, 0);
		apiTextField(apiCredittf);
		creditScoreEvent(apiCredittf, progressIndicator);

		return grid;
	}

	private String decimal(double number) {
		String format = new DecimalFormat("0.00").format(number);
		return format;
	}

	private LabelWithStyle apiLabel(String text, GridPaneCenter grid, int x, int y, int width) {
		LabelWithStyle label = new LabelWithStyle(text, grid, x, y);
		label.setTextFill(Color.web(style.white()));
		label.setMinWidth(width);
		label.setPrefWidth(width);
		label.setMaxWidth(width);

		return label;
	}

	private void apiTextField(TextFieldWithStyle tf) {
		tf.setBackground(null);
		tf.setBorder(null);
		tf.setStyle(
				"-fx-text-fill: " + style.white() + "; -fx-effect: innershadow( gaussian , rgba(0,0,0,0) , 0,0,0,0 );");
		tf.setMinWidth(100);
		tf.setPrefWidth(100);
		tf.setMaxWidth(100);
		tf.setMinHeight(50);
		tf.setPrefHeight(50);
		tf.setMaxHeight(50);
		tf.setDisable(true);
		tf.setOpacity(100);
	}

	private void interestEvent(TextFieldWithStyle tf) {
		if (proposal.getInterest() != 0.0) {
			tf.setText(decimal(proposal.getInterest()));
		}

		proposal.doubleProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String interestFormat = new DecimalFormat("0.00").format(proposal.getInterest());
				tf.setText(interestFormat);
				tr.interestUpdate(interestFormat);
			}
		});
	}

	private void creditScoreEvent(TextFieldWithStyle tf, ProgressIndicatorWithStyle pi) {
		if (customer.getCreditScore() != null) {
			pi.setVisible(false);
			tf.setText(customer.getCreditScore().toString());
		}

		customer.stringProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String creditScore = customer.getCreditScore().toString();
				tf.setText(creditScore);
				tr.creditScoreUpdate(creditScore);
				paymenttf.setDisable(false);
				pi.setVisible(false);
			}
		});
	}

	//////////////////////////////
	// Buttons
	//////////////////////////////

	private HBox buttons() {
		HBox hbox = new HBox(backButton(), nextButton());
		hbox.setAlignment(Pos.CENTER_RIGHT);
		hbox.setPadding(new Insets(8, 50, 0, 0));

		return hbox;
	}

	private GridPane nextButton() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);

		nextButton = new ButtonWithStyle("Nï¿½ste", grid, 0, 0);
		nextButton.setDisable(true);
		nextButton.setOnAction(e -> {
			proposal.checkLimit();
			controller.createProposal(proposal);
			new SignProposalScreen(proposal).signProposalUI();
		});

		return grid;
	}

	private GridPane backButton() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);

		ButtonWithStyle button = new ButtonWithStyle("Tilbage", grid, 0, 0);
		button.setOnAction(e -> {
			new ProposalOverview().customerUI(customer.getCpr());
		});

		return grid;
	}

	private void nextButtonDisable() {
		if (!rbState && regnrcb.getValue() != null && !durationtf.getText().isEmpty()
				&& !paymenttf.getText().isEmpty()) {
			nextButton.setDisable(false);
		} else if (rbState && modelcb.getValue() != null && !durationtf.getText().isEmpty()
				&& !paymenttf.getText().isEmpty()) {
			nextButton.setDisable(false);
		} else {
			nextButton.setDisable(true);
		}
	}

	//////////////////////////////
	// Label Title
	//////////////////////////////

	private Label title() {
		Label label = new Label("Nyt LÃ¥neforslag");
		label.setFont(Font.loadFont("file:resources/fonts/FerroRosso.ttf", 60));
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
