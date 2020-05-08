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
import logic.Car;
import logic.Customer;
import logic.DB_Controller;
import logic.Proposal;
import styles.ButtonWithStyle;
import styles.ComboBoxWithStyle;
import styles.GridPaneCenter;
import styles.LabelWithStyle;
import styles.RadioButtonWithStyle;
import styles.StyleClass;
import styles.TextFieldWithStyle;
import styles.TextWithStyle;
import styles.VBoxWithStyle;

public class NewPropsalScreen {

	private StyleClass style = new StyleClass();

	private Customer customer = new Customer(88888888, "John Brick", "3213909874", "johnshitsbricks@gmail.dk",
			"Brick st. 11", 7400);
	private Proposal proposal = new Proposal(customer, LoggedInST.getUser());

	private boolean rbState = false;
	private boolean recreate = true;

//	private ArrayList<String> yearList = new ArrayList<String>();
//	private String yearString;
	private String cCreditScore = "";
	private String interestFormat = "";

	private VBox trvbox;
	private ArrayList<Car> carList = new ArrayList<Car>();

	private TextWithStyle carModeltr;
	private TextWithStyle carMilagetr;
	private TextWithStyle carYeartr;

	private TextWithStyle carPriceModeltr;
	private TextWithStyle carPriceModelPricetr;
	private TextWithStyle carPriceVattr;
	private TextWithStyle carPriceDownPaymenttr;
	private TextWithStyle carPriceTotaltr;

	private TextWithStyle proposalInteresttr;
	private TextWithStyle proposalCreditScoretr;
	private TextWithStyle proposalDownPaymenttr;
	private TextWithStyle proposalDurationtr;
	private TextWithStyle proposalTotalInteresttr;
	private TextWithStyle proposalAprtr;
	private TextWithStyle proposalMonthlyPaymenttr;

	private TextWithStyle proposalTotalSumtr;
	private TextWithStyle sumInteresettr;
	private TextWithStyle sumCarPricetr;

	private RadioButtonWithStyle rbOld;
	private ComboBoxWithStyle modelcb;
	private ComboBoxWithStyle yearcb;
	private TextFieldWithStyle paymenttf;
	private ComboBoxWithStyle regnrcb;
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
			recreate = false;
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
			trvbox.getChildren().clear();
			trvbox.getChildren().add(textReader());
		});

		if (rbState) {

			LabelWithStyle model = new LabelWithStyle("Model: ", grid, 0, 1);
			GridPane.setColumnSpan(model, 2);
			modelcb = new ComboBoxWithStyle(FXCollections.observableArrayList(controller.getNewCars()), grid, 3, 1);
			modelcb.setMinWidth(600);
			GridPane.setColumnSpan(modelcb, 2);
			modelcb.setOnAction(e -> {
				carList.clear();
				carList.add((Car) modelcb.getValue());
				proposal.setCarsList(carList);
				useTotalInterest();
			});

			yearcb = new ComboBoxWithStyle(FXCollections.observableArrayList(""), grid, 3, 2);
			yearcb.setVisible(false);

			regnrcb = new ComboBoxWithStyle(FXCollections.observableArrayList(""), grid, 3, 3);
			regnrcb.setVisible(false);

		} else {

			LabelWithStyle model = new LabelWithStyle("Model: ", grid, 0, 1);
			GridPane.setColumnSpan(model, 2);
			modelcb = new ComboBoxWithStyle(FXCollections.observableArrayList(controller.getCarModels()), grid, 3, 1);
			modelcb.setMinWidth(600);
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
			});

			LabelWithStyle year = new LabelWithStyle("År: ", grid, 1, 2);
			GridPane.setColumnSpan(year, 1);
			yearcb = new ComboBoxWithStyle(FXCollections.observableArrayList(""), grid, 3, 2);
			yearcb.setMinWidth(600);
			yearcb.setDisable(true);
			GridPane.setColumnSpan(yearcb, 2);
			yearcb.setOnAction(e -> {
				if (yearcb.getValue() != null) {
					regnrcb.setItems(FXCollections.observableArrayList(
							controller.getUsedCars(modelcb.getValue().toString(), yearcb.getValue().toString())));
				}
			});

			LabelWithStyle regnr = new LabelWithStyle("Reg. Nr.: ", grid, 0, 3);
			GridPane.setColumnSpan(regnr, 2);
			regnrcb = new ComboBoxWithStyle(FXCollections.observableArrayList(controller.getUsedCars()), grid, 3, 3);
			regnrcb.setMinWidth(600);
			GridPane.setColumnSpan(regnrcb, 2);
			regnrcb.setOnAction(e -> {
				carList.clear();
				carList.add((Car) regnrcb.getValue());
				proposal.setCarsList(carList);
			});

		}

		LabelWithStyle duration = new LabelWithStyle("Afbetalingsperiode: ", grid, 0, 4);
		GridPane.setColumnSpan(duration, 2);
		durationtf = new TextFieldWithStyle("ex. 32", grid, 3, 4);
		new LabelWithStyle(" Måned(er)", grid, 4, 4);

		LabelWithStyle payment = new LabelWithStyle("Udbetaling: ", grid, 0, 5);
		GridPane.setColumnSpan(payment, 2);
		paymenttf = new TextFieldWithStyle("ex. 1234567", grid, 3, 5);
		paymenttf.setDisable(true);
		new LabelWithStyle(" DKK", grid, 4, 5);
		paymenttf.setOnAction(e -> {
			proposal.setDownPayment(Integer.parseInt(paymenttf.getText()));
		});

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

		new LabelWithStyle("Bank Rente:	", grid, 0, 0);
		TextFieldWithStyle interesttf = new TextFieldWithStyle("", grid, 1, 0);
		interesttf.setMaxWidth(100);
		interesttf.setDisable(true);
		interesttf.setOpacity(100);
		proposal.doubleProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				interestFormat = new DecimalFormat("0.00").format(proposal.getInterest());
				interesttf.setText(interestFormat);
				proposalInteresttr.setText(interestFormat);
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
				cCreditScore = customer.getCreditScore().toString();
				credittf.setText(cCreditScore);
				proposalCreditScoretr.setText(cCreditScore);
				paymenttf.setDisable(false);
			}
		});

		return grid;
	}

	//////////////////////////////
	// NEW SHIT
	//////////////////////////////

	private VBox textReader() {
		trvbox = new VBox(customerTitle(), customerInfo(), carTitle(), carInfo(), carPriceTitle(), carPriceInfo(),
				proposalInfo(), priceSum(), totalPrice());
		trvbox.setBorder(new Border(
				new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3))));

		onHiding();
		keyTypedDownPayment();
		keyTypedDuration();

		return trvbox;
	}

	//////////////////////////////
	// Info
	//////////////////////////////

	private GridPane customerInfo() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));
		grid.setAlignment(Pos.CENTER_LEFT);

		new TextWithStyle("Navn: ", grid, 0, 0, 110);
		new TextWithStyle(customer.getCustomerName(), grid, 1, 0, 200);

		new TextWithStyle("Adresse: ", grid, 2, 0, 110);
		new TextWithStyle(customer.getCustomerAddress(), grid, 3, 0, 200);

		TextWithStyle phone = new TextWithStyle("Telefon nr.: ", grid, 0, 1, 110);
		phone.setBorder(style.underLine());
		TextWithStyle cphone = new TextWithStyle(Integer.toString(customer.getPhone()), grid, 1, 1, 300);
		cphone.setBorder(style.underLine());

		TextWithStyle email = new TextWithStyle("Email: ", grid, 2, 1, 110);
		email.setBorder(style.underLine());
		TextWithStyle cemail = new TextWithStyle(customer.getEmail(), grid, 3, 1, 300);
		cemail.setBorder(style.underLine());

		return grid;
	}

	private GridPane carInfo() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));
		grid.setAlignment(Pos.CENTER_LEFT);

		TextWithStyle model = new TextWithStyle("Model: ", grid, 0, 0, 90);
		model.setBorder(style.underLine());
		carModeltr = new TextWithStyle("", grid, 1, 0, 230);
		carModeltr.setBorder(style.underLine());

		TextWithStyle distance = new TextWithStyle("Kilometer: ", grid, 3, 0, 120);
		distance.setBorder(style.underLine());
		carMilagetr = new TextWithStyle("", grid, 4, 0, 100);
		carMilagetr.setAlignment(Pos.CENTER_RIGHT);
		carMilagetr.setBorder(style.underLine());
		TextWithStyle km = new TextWithStyle("km", grid, 5, 0, style.textUnitWidth());
		km.setBorder(style.underLine());

		TextWithStyle year = new TextWithStyle("År: ", grid, 7, 0, 60);
		year.setBorder(style.underLine());
		carYeartr = new TextWithStyle("", grid, 8, 0, 100);
		carYeartr.setBorder(style.underLine());

		return grid;
	}

	private GridPane carPriceInfo() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));
		grid.setAlignment(Pos.CENTER_LEFT);

		carPriceModeltr = new TextWithStyle("", grid, 0, 0, 500);
		carPriceModelPricetr = new TextWithStyle("", grid, 1, 0, 200);
		carPriceModelPricetr.setAlignment(Pos.CENTER_RIGHT);
		new TextWithStyle("DKK", grid, 2, 0, style.textUnitWidth());

		new TextWithStyle("- Moms (25%) ", grid, 0, 1, 200);
		carPriceVattr = new TextWithStyle("PLACEHOLDER", grid, 1, 1, 200);
		carPriceVattr.setAlignment(Pos.CENTER_RIGHT);
		new TextWithStyle("DKK", grid, 2, 1, style.textUnitWidth());

		new TextWithStyle("- Udbetaling: ", grid, 0, 2, 200);
		carPriceDownPaymenttr = new TextWithStyle("", grid, 1, 2, 200);
		carPriceDownPaymenttr.setAlignment(Pos.CENTER_RIGHT);
		new TextWithStyle("DKK", grid, 2, 2, style.textUnitWidth());

		TextWithStyle totalCarPrice = new TextWithStyle("Total Bilpris: ", grid, 0, 3, 200);
		totalCarPrice.setBorder(style.dottedUnderLine());
		carPriceTotaltr = new TextWithStyle("", grid, 1, 3, 200);
		carPriceTotaltr.setAlignment(Pos.CENTER_RIGHT);
		carPriceTotaltr.setBorder(style.dottedUnderLine());
		TextWithStyle dkk = new TextWithStyle("DKK", grid, 2, 3, style.textUnitWidth());
		dkk.setBorder(style.dottedUnderLine());

		return grid;
	}

	private GridPane proposalInfo() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));
		grid.setAlignment(Pos.CENTER_LEFT);

		new TextWithStyle("Bank rente: ", grid, 0, 0, 500);
		proposalInteresttr = new TextWithStyle("", grid, 1, 0, 200);
		proposalInteresttr.setAlignment(Pos.CENTER_RIGHT);
		if (interestFormat != "") {
			proposalInteresttr.setText(interestFormat);
		}
		new TextWithStyle("%", grid, 2, 0, style.textUnitWidth());

		new TextWithStyle("Kreditværdighed: ", grid, 0, 1, 200);
		proposalCreditScoretr = new TextWithStyle("", grid, 1, 1, 200);
		proposalCreditScoretr.setAlignment(Pos.CENTER_RIGHT);
		if (cCreditScore != "") {
			proposalCreditScoretr.setText(cCreditScore);
		}

		new TextWithStyle("Udbetaling: ", grid, 0, 2, 200);
		proposalDownPaymenttr = new TextWithStyle("", grid, 1, 2, 200);
		proposalDownPaymenttr.setAlignment(Pos.CENTER_RIGHT);
		new TextWithStyle("DKK", grid, 2, 2, style.textUnitWidth());

		new TextWithStyle("Afbetalingsperiode: ", grid, 0, 3, 200);
		proposalDurationtr = new TextWithStyle("", grid, 1, 3, 200);
		proposalDurationtr.setAlignment(Pos.CENTER_RIGHT);
		new TextWithStyle("Måned(er)", grid, 2, 3, style.textUnitWidth());

		new TextWithStyle("Total rente: ", grid, 0, 4, 200);
		proposalTotalInteresttr = new TextWithStyle("", grid, 1, 4, 100);
		proposalTotalInteresttr.setAlignment(Pos.CENTER_RIGHT);
		new TextWithStyle("%", grid, 2, 4, style.textUnitWidth());

		new TextWithStyle("ÅOP: ", grid, 0, 5, 200);
		proposalAprtr = new TextWithStyle("PLACEHOLDER", grid, 1, 5, 200);
		proposalAprtr.setAlignment(Pos.CENTER_RIGHT);
		new TextWithStyle("%", grid, 2, 5, style.textUnitWidth());

		TextWithStyle monthlyPayment = new TextWithStyle("Månedlig ydelse: ", grid, 0, 6, 200);
		monthlyPayment.setBorder(style.dottedUnderLine());
		proposalMonthlyPaymenttr = new TextWithStyle("PLACEHOLDER", grid, 1, 6, 200);
		proposalMonthlyPaymenttr.setAlignment(Pos.CENTER_RIGHT);
		proposalMonthlyPaymenttr.setBorder(style.dottedUnderLine());
		TextWithStyle dkk = new TextWithStyle("DKK", grid, 2, 6, style.textUnitWidth());
		dkk.setBorder(style.dottedUnderLine());

		return grid;
	}

	private GridPane priceSum() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));
		grid.setAlignment(Pos.CENTER_LEFT);

		new TextWithStyle("Total bilpris: ", grid, 0, 0, 500);
		sumCarPricetr = new TextWithStyle("PLACEHOLDER", grid, 1, 0, 200);
		sumCarPricetr.setAlignment(Pos.CENTER_RIGHT);
		new TextWithStyle("DKK", grid, 2, 0, style.textUnitWidth());

		TextWithStyle totalInterestSum = new TextWithStyle("Total rentebeløb: ", grid, 0, 1, 200);
		totalInterestSum.setBorder(style.underLine());
		sumInteresettr = new TextWithStyle("PLACEHOLDER", grid, 1, 1, 200);
		sumInteresettr.setAlignment(Pos.CENTER_RIGHT);
		sumInteresettr.setBorder(style.underLine());
		TextWithStyle dkk = new TextWithStyle("DKK", grid, 2, 1, style.textUnitWidth());
		dkk.setBorder(style.underLine());

		return grid;
	}

	private GridPane totalPrice() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(0, style.textReaderInsets() + 43, 0, style.textReaderInsets()));
		grid.setAlignment(Pos.CENTER_RIGHT);

		TextWithStyle total = new TextWithStyle("Total: ", grid, 0, 0, 65);
		total.setAlignment(Pos.CENTER_RIGHT);
		total.setBorder(style.underLine());
		proposalTotalSumtr = new TextWithStyle("PLACEHOLDER", grid, 1, 0, 150);
		proposalTotalSumtr.setAlignment(Pos.CENTER_RIGHT);
		proposalTotalSumtr.setBorder(style.underLine());
		TextWithStyle dkk = new TextWithStyle("DKK", grid, 2, 0, 65);
		dkk.setBorder(style.underLine());

		return grid;
	}

	//////////////////////////////
	// Titles
	//////////////////////////////

	private GridPane customerTitle() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(30, style.textReaderInsets(), 0, style.textReaderInsets()));
		grid.setAlignment(Pos.CENTER_LEFT);

		LabelWithStyle label = new LabelWithStyle("Kunde", grid, 0, 0);
		label.setFont(Font.font(style.textFont(), 25));

		return grid;
	}

	private GridPane carTitle() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));
		grid.setAlignment(Pos.CENTER_LEFT);

		LabelWithStyle label = new LabelWithStyle("Bil", grid, 0, 0);
		label.setFont(Font.font(style.textFont(), 25));

		return grid;
	}

	private GridPane carPriceTitle() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));
		grid.setAlignment(Pos.CENTER_LEFT);

		LabelWithStyle label = new LabelWithStyle("Bilpris", grid, 0, 0);
		label.setFont(Font.font(style.textFont(), 25));

		return grid;
	}

	//////////////////////////////
	// EVENTS
	//////////////////////////////

	private void onHiding() {
		modelcb.setOnHiding(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				carModeltr.setText(modelcb.getValue().toString());
				carMilagetr.setText(Integer.toString(((Car) (modelcb.getValue())).getMilage()));
				carYeartr.setText(Integer.toString(((Car) (modelcb.getValue())).getFactory()));

				carPriceModeltr.setText(modelcb.getValue().toString());
				carPriceModelPricetr.setText(Integer.toString(((Car) modelcb.getValue()).getPrice()));
				carPriceTotaltr.setText(
						Integer.toString(((Car) modelcb.getValue()).getPrice()) + " PLACEHOLDER NEED MORE CARS");
			}
		});
	}

	private void keyTypedDuration() {
		durationtf.setOnKeyTyped(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				proposalDurationtr.setText(durationtf.getText());

				if (!durationtf.getText().isEmpty()) {
					proposal.setLoanDuration(Integer.parseInt(durationtf.getText()));
				} else {
					proposal.setLoanDuration(0);
				}

				useTotalInterest();
			}
		});
	}

	private void keyTypedDownPayment() {
		paymenttf.setOnKeyTyped(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				carPriceDownPaymenttr.setText(paymenttf.getText());
				proposalDownPaymenttr.setText(paymenttf.getText());

				if (!paymenttf.getText().isEmpty()) {
					proposal.setDownPayment(Integer.parseInt(paymenttf.getText()));
				} else {
					proposal.setDownPayment(0);
				}

				useTotalInterest();
			}
		});
	}

	private void useTotalInterest() {
		Double totalInterest;
		if (!durationtf.getText().isEmpty() && !paymenttf.getText().isEmpty() && modelcb.getValue() != null) {
			totalInterest = proposal.calcInterest();
			String format = new DecimalFormat("0.00").format(totalInterest);
			proposalTotalInteresttr.setText(format);
		} else {
			proposalTotalInteresttr.setText("");
		}
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
