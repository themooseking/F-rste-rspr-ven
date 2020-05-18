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
		vbox.setAlignment(Pos.TOP_CENTER);

		return vbox;
	}

	private VBox inputBox() {
		VBox vbox = new VBox(indentInput(), apiValues());
		vbox.setBorder(new Border(
				new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3))));
		vbox.setBackground(
				new Background(new BackgroundFill(Color.web(style.white()), new CornerRadii(0), Insets.EMPTY)));

		return vbox;
	}

	private GridPane indentInput() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);
		grid.setPadding(new Insets(0));
		grid.setVgap(5);

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
		rbOld.setMinWidth(300);
		GridPane.setColumnSpan(rbOld, 2);

		rbOld.setOnAction(e -> {
			grid.getChildren().clear();
			grid.getChildren().add(indentInput());
			tr.clearTR();
			nextButton.setDisable(true);
		});

		if (rbState) {

			LabelWithStyle modelLabel = new LabelWithStyle("Model: ", grid, 0, 1);
			GridPane.setColumnSpan(modelLabel, 2);
			modelcb = new ComboBoxWithStyle(FXCollections.observableArrayList(controller.getNewCars()), grid, 3, 1);
			modelcb.setMinWidth(400);
			GridPane.setColumnSpan(modelcb, 2);

			modelcb.setOnHiding(e -> {
				proposal.setCar((Car) modelcb.getValue());
				tr.update(rbState, modelcb, yearcb, regnrcb, durationtf, paymenttf);

				nextButtonDisable();
			});

			yearcb = new ComboBoxWithStyle(FXCollections.observableArrayList(""), grid, 3, 2);
			yearcb.setVisible(false);
			GridPane.setColumnSpan(yearcb, 2);

			regnrcb = new ComboBoxWithStyle(FXCollections.observableArrayList(""), grid, 3, 3);
			regnrcb.setVisible(false);
			GridPane.setColumnSpan(regnrcb, 2);

		} else {

			LabelWithStyle modelLabel = new LabelWithStyle("Model: ", grid, 0, 1);
			GridPane.setColumnSpan(modelLabel, 2);
			modelcb = new ComboBoxWithStyle(FXCollections.observableArrayList(controller.getCarModels()), grid, 3, 1);
			modelcb.setMinWidth(400);
			GridPane.setColumnSpan(modelcb, 2);
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

			new LabelWithStyle("År: ", grid, 1, 2);
			yearcb = new ComboBoxWithStyle(FXCollections.observableArrayList(""), grid, 3, 2);
			yearcb.setMinWidth(400);
			yearcb.setDisable(true);
			GridPane.setColumnSpan(yearcb, 2);
			yearcb.setOnHiding(e -> {
				if (yearcb.getValue() != null) {
					regnrcb.setItems(FXCollections.observableArrayList(
							controller.getUsedCars(modelcb.getValue().toString(), yearcb.getValue().toString())));
				}
				tr.update(rbState, modelcb, yearcb, regnrcb, durationtf, paymenttf);
			});

			LabelWithStyle regnr = new LabelWithStyle("Reg. Nr.: ", grid, 0, 3);
			GridPane.setColumnSpan(regnr, 2);
			regnrcb = new ComboBoxWithStyle(FXCollections.observableArrayList(controller.getUsedCars()), grid, 3, 3);
			regnrcb.setMinWidth(400);
			GridPane.setColumnSpan(regnrcb, 2);
			regnrcb.setOnHiding(e -> {
				proposal.setCar((Car) regnrcb.getValue());
				tr.update(rbState, modelcb, yearcb, regnrcb, durationtf, paymenttf);

				nextButtonDisable();
			});
		}

		LabelWithStyle durationLabel = new LabelWithStyle("Afbetalingsperiode: ", grid, 0, 4);
		GridPane.setColumnSpan(durationLabel, 2);
		durationtf = new TextFieldWithStyle("ex. 32", grid, 3, 4);
		durationtf.setMinWidth(300);
		durationtf.setMaxWidth(300);
		new LabelWithStyle(" M�ned(er)", grid, 4, 4);
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
		GridPane.setColumnSpan(payment, 2);
		paymenttf = new TextFieldWithStyle("ex. 1234567", grid, 3, 5);
		paymenttf.setMinWidth(300);
		paymenttf.setMaxWidth(300);
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

	//////////////////////////////
	// BANK RATE AND CREDIT SCORE
	//////////////////////////////

	private GridPane apiValues() {
		GridPaneCenter grid = new GridPaneCenter(Pos.BOTTOM_LEFT);
		grid.setPadding(new Insets(0, 10, 0, 10));
		grid.setBackground(new Background(
				new BackgroundFill(Color.web(style.grey()), new CornerRadii(0), Insets.EMPTY)));

		apiLabel("Bank rente:", grid, 0, 0, 150);
		TextFieldWithStyle apiInteresttf = new TextFieldWithStyle("", grid, 1, 0);
		apiInteresttf.setBackground(null);
		apiInteresttf.setBorder(null);
		apiInteresttf.setStyle(
				"-fx-text-fill: " + style.white() + "; -fx-effect: innershadow( gaussian , rgba(0,0,0,0) , 0,0,0,0 );");
		apiInteresttf.setMinWidth(100);
		apiInteresttf.setPrefWidth(100);
		apiInteresttf.setMaxWidth(100);
		apiInteresttf.setMinHeight(50);
		apiInteresttf.setPrefHeight(50);
		apiInteresttf.setMaxHeight(50);
		apiInteresttf.setDisable(true);
		apiInteresttf.setOpacity(100);

		if (proposal.getInterest() != 0.0) {
			apiInteresttf.setText(decimal(proposal.getInterest()));
		}

		proposal.doubleProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String interestFormat = new DecimalFormat("0.00").format(proposal.getInterest());
				apiInteresttf.setText(interestFormat);
				tr.interestUpdate(interestFormat);
			}
		});

		new LabelWithStyle("		", grid, 2, 0);

		LabelWithStyle creditScore = new LabelWithStyle("Kredit Score:", grid, 3, 0);
		creditScore.setTextFill(Color.web(style.white()));
		creditScore.setMinWidth(230);
		creditScore.setPrefWidth(230);
		creditScore.setMaxWidth(230);
		TextFieldWithStyle apiCredittf = new TextFieldWithStyle("", grid, 4, 0);
		apiCredittf.setBackground(null);
		apiCredittf.setBorder(null);
		apiCredittf.setStyle(
				"-fx-text-fill: " + style.white() + "; -fx-effect: innershadow( gaussian , rgba(0,0,0,0) , 0,0,0,0 );");
		apiCredittf.setMinWidth(100);
		apiCredittf.setPrefWidth(100);
		apiCredittf.setMaxWidth(100);
		apiCredittf.setDisable(true);
		apiCredittf.setOpacity(100);
		apiCredittf.setMinHeight(50);
		apiCredittf.setPrefHeight(50);
		apiCredittf.setMaxHeight(50);

		ProgressIndicatorWithStyle progressIndicator = new ProgressIndicatorWithStyle(grid, 4, 0);
		progressIndicator.setMaxSize(20, 20);

		if (customer.getCreditScore() != null) {
			progressIndicator.setVisible(false);
			apiCredittf.setText(customer.getCreditScore().toString());
		}

		customer.stringProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String creditScore = customer.getCreditScore().toString();
				apiCredittf.setText(creditScore);
				tr.creditScoreUpdate(creditScore);
				paymenttf.setDisable(false);
				progressIndicator.setVisible(false);
			}
		});

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

		nextButton = new ButtonWithStyle("N�ste", grid, 0, 0);
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
		Label label = new Label("Nyt Låneforslag");
		label.setFont(Font.loadFont("file:resources/fonts/FerroRosso.ttf", 120));
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
