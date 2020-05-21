package presentation;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
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
import styles.AlertWithStyle;
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

	public void show() {
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

	//////////////////////////////
	// INDENT
	//////////////////////////////

	private GridPane indentInput() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);
		grid.setPadding(new Insets(0));
		grid.setVgap(15);

		rbState = !rbState;

		RadioButtonWithStyle rbOldCars = new RadioButtonWithStyle("Gamle Biler", grid, 0, 0);
		radioButtonEvent(rbOldCars, grid);

		new LabelWithStyle("Model: ", grid, 0, 1);
		modelcb = new ComboBoxWithStyle(FXCollections.observableArrayList(""), grid, 3, 1);
		indentComboBox(modelcb, 400);
		modelcbEvent();

		yearcb = new ComboBoxWithStyle(FXCollections.observableArrayList(""), grid, 3, 2);
		indentComboBox(yearcb, 400);

		regnrcb = new ComboBoxWithStyle(FXCollections.observableArrayList(""), grid, 3, 3);
		indentComboBox(regnrcb, 400);

		if (rbState) {

			modelcb.setItems(FXCollections.observableArrayList(controller.getNewCars()));
			yearcb.setVisible(false);
			regnrcb.setVisible(false);

		} else {

			modelcb.setItems(FXCollections.observableArrayList(controller.getCarModels()));

			new LabelWithStyle("År: ", grid, 0, 2);
			yearcb.setDisable(true);
			yearcbEvent();

			new LabelWithStyle("Reg. Nr.: ", grid, 0, 3);
			regnrcb.setItems(FXCollections.observableArrayList(controller.getUsedCars()));
			regnrEvent();

		}

		new LabelWithStyle("Afbetalingsperiode: ", grid, 0, 4);
		durationtf = new TextFieldWithStyle("", grid, 3, 4);
		durationtf.setAlignment(Pos.CENTER_RIGHT);
		indentTextField(durationtf);
		durationEvent(durationtf);
		new LabelWithStyle(" Måned(er)", grid, 4, 4);

		new LabelWithStyle("Udbetaling: ", grid, 0, 5);
		paymenttf = new TextFieldWithStyle("", grid, 3, 5);
		paymenttf.setAlignment(Pos.CENTER_RIGHT);
		indentTextField(paymenttf);
		downPaymentEvent(paymenttf);
		new LabelWithStyle(" DKK", grid, 4, 5);

		return grid;
	}

	//////////////////////////////
	// COMBOBOX EVENTS
	//////////////////////////////

	private void radioButtonEvent(RadioButtonWithStyle rb, GridPaneCenter grid) {
		rb.setSelected(!rbState);

		rb.setOnAction(e -> {
			grid.getChildren().clear();
			grid.getChildren().add(indentInput());
			tr.clearTR();
			nextButton.setDisable(true);
		});
	}

	private void modelcbEvent() {
		modelcb.setOnAction(e -> {
			if (rbState) {

				proposal.setCar((Car) modelcb.getValue());
				nextButtonDisable();

			} else {

				if (modelcb.getValue() != null) {
					yearcb.getItems().clear();
					yearcb.setDisable(false);

					if (yearcb.getValue() == null) {
						regnrcb.setItems(FXCollections
								.observableArrayList(controller.getUsedCars(modelcb.getValue().toString())));
						yearcb.setItems(FXCollections
								.observableArrayList(controller.getCarFactoryYears(modelcb.getValue().toString())));
					}
				}
			}

			tr.update(rbState, modelcb, yearcb, regnrcb, durationtf, paymenttf);
		});
	}

	private void yearcbEvent() {
		
		yearcb.setOnAction(e -> {
			if (yearcb.getValue() != null) {
				regnrcb.setItems(FXCollections.observableArrayList(
						controller.getUsedCars(modelcb.getValue().toString(), yearcb.getValue().toString())));
			}
			tr.update(rbState, modelcb, yearcb, regnrcb, durationtf, paymenttf);
		});
	}

	private void regnrEvent() {
		regnrcb.setOnAction(e -> {
			proposal.setCar((Car) regnrcb.getValue());
			nextButtonDisable();
			tr.update(rbState, modelcb, yearcb, regnrcb, durationtf, paymenttf);
		});
	}

	//////////////////////////////
	// TEXTFIELD / COMBOBOX STYLES
	//////////////////////////////

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
	// TEXTFIELD EVENTS
	//////////////////////////////

	private void durationEvent(TextFieldWithStyle tf) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue,
					final String newValue) {
				if (!newValue.matches("\\d*")) {
					tf.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if (!tf.getText().isEmpty()) {
					int tfInt = Integer.parseInt(tf.getText());

					if (tfInt == 0) {
						String s = tf.getText().substring(0, 0);
						tf.setText(s);
					} else if (tfInt > 1000) {
						String s = tf.getText().substring(0, 3);
						tf.setText(s);
					} else if (tfInt > 290) {
						String s = tf.getText().substring(0, 2);
						tf.setText(s);
					}
				}
			}
		});

		tf.setOnKeyReleased(e -> {
			if (!tf.getText().isEmpty()) {
				proposal.setLoanDuration(Integer.parseInt(tf.getText()));
			} else {
				proposal.setLoanDuration(0);
			}
			nextButtonDisable();
			tr.update(rbState, modelcb, yearcb, regnrcb, durationtf, paymenttf);
		});
	}

	private void downPaymentEvent(TextFieldWithStyle tf) {
		if (customer.getCreditScore() == null) {
			tf.setDisable(true);
		}

		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue,
					final String newValue) {
				if (!newValue.matches("\\d*")) {
					tf.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if (tf.getText().length() > 8) {
					String s = tf.getText().substring(0, 8);
					tf.setText(s);
				}
			}
		});

		tf.setOnKeyReleased(e -> {
			if (!tf.getText().isEmpty()) {
				proposal.setDownPayment(new BigDecimal(tf.getText()));
			} else {
				proposal.setDownPayment(new BigDecimal(0));
			}
			nextButtonDisable();
			tr.update(rbState, modelcb, yearcb, regnrcb, durationtf, paymenttf);
		});
	}

	//////////////////////////////
	// API VALUES
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

	//////////////////////////////
	// API STYLES
	//////////////////////////////

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

	//////////////////////////////
	// API EVENTS
	//////////////////////////////

	private void interestEvent(TextFieldWithStyle tf) {
		if (proposal.getInterest() != 0.0) {
			tf.setText(new DecimalFormat("0.00").format(proposal.getInterest()));
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
	// Alert boxes
	//////////////////////////////

	private void alertSaveContinue() {
		ButtonType bSave = new ButtonType("Gem");
		ButtonType bContinue = new ButtonType("Underskriv");
		ButtonType bCancel = new ButtonType("Fortryd", ButtonData.CANCEL_CLOSE);

		ArrayList<ButtonType> arrayList = new ArrayList<ButtonType>();
		arrayList.add(bSave);
		arrayList.add(bContinue);
		arrayList.add(bCancel);

		String text = "Ville du gemme lånet til en senere underskrivelse eller ville du under skrive det nu?";
		AlertWithStyle alert = new AlertWithStyle(AlertType.CONFIRMATION, text, "Gem eller underskriv nu", arrayList);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == bSave) {
			carHandler();
			proposal.checkLimit();
			new ProposalOverview().customerUI(customer.getCpr());
		} else if (result.get() == bContinue) {
			carHandler();
			proposal.checkLimit();
			new SignProposalScreen(proposal).defaultUI();
		} else {
			alert.close();
		}
	}
	
	private void carHandler() {
		if(proposal.getCar().getCarStatus().compareTo("NEW") == 0) {
			controller.createCar(proposal.getCar());
		} else {
			proposal.getCar().setCarStatus("NOT_AVAILABLE");
			controller.updateCarStatus(proposal.getCar());
		}
	}

	private void alertBack() {
		ButtonType bBack = new ButtonType("Tilbage");
		ButtonType bCancel = new ButtonType("Fortryd", ButtonData.CANCEL_CLOSE);

		ArrayList<ButtonType> arrayList = new ArrayList<ButtonType>();
		arrayList.add(bBack);
		arrayList.add(bCancel);

		String text = "Er du sikker bare at du ville gå tilbage, hvis du gøre ville alt data blive slettet";
		AlertWithStyle alert = new AlertWithStyle(AlertType.CONFIRMATION, text, "Tilbage", arrayList);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == bBack) {
			new ProposalOverview().customerUI(customer.getCpr()); 
		} else {
			alert.close();
		}
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
			alertSaveContinue();
		});

		return grid;
	}

	private GridPane backButton() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);

		ButtonWithStyle button = new ButtonWithStyle("Tilbage", grid, 0, 0);
		button.setOnAction(e -> {
			alertBack();
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
		label.setFont(Font.loadFont(style.titleFont(), 60));
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
