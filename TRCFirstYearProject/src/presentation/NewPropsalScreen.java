package presentation;

import java.text.DecimalFormat;

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

	private String cCreditScore = "";
	private String interestFormat = "";

	private VBox trvbox;
	private Car car;

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
				car = ((Car) modelcb.getValue());
				proposal.setCar(car);
				updateTextReader();
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
				updateTextReader();
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
				updateTextReader(); 
			});

			LabelWithStyle regnr = new LabelWithStyle("Reg. Nr.: ", grid, 0, 3);
			GridPane.setColumnSpan(regnr, 2);
			regnrcb = new ComboBoxWithStyle(FXCollections.observableArrayList(controller.getUsedCars()), grid, 3, 3);
			regnrcb.setMinWidth(600);
			GridPane.setColumnSpan(regnrcb, 2);
			regnrcb.setOnAction(e -> {
				car = ((Car) regnrcb.getValue());
				proposal.setCar(car);
				updateTextReader();
			});
		}

		LabelWithStyle duration = new LabelWithStyle("Afbetalingsperiode: ", grid, 0, 4);
		GridPane.setColumnSpan(duration, 2);
		durationtf = new TextFieldWithStyle("ex. 32", grid, 3, 4);
		new LabelWithStyle(" Måned(er)", grid, 4, 4);

		LabelWithStyle payment = new LabelWithStyle("Udbetaling: ", grid, 0, 5);
		GridPane.setColumnSpan(payment, 2);
		paymenttf = new TextFieldWithStyle("ex. 1234567", grid, 3, 5);

		if (customer.getCreditScore() == null) {
			paymenttf.setDisable(true);
		}

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
		if (proposal.getInterest() != 0) {
			interesttf.setText(decimal(proposal.getInterest()));
			interestFormat = new DecimalFormat("0.00").format(proposal.getInterest());

		}
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

	

	private String decimal(double number) {
		String format = new DecimalFormat("0.00").format(number);
		return format;
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
		HBox hbox = new HBox(icon(), backButton(), nextButton());
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setBorder(new Border(new BorderStroke(Color.web(style.defaultHoverColor()), BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, new BorderWidths(7, 0, 0, 0))));

		return hbox;
	}	

	private GridPane nextButton() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setAlignment(Pos.CENTER_LEFT);

		ButtonWithStyle button = new ButtonWithStyle("Næste", grid, 0, 0);
		button.setOnAction(e -> {
			new SignProposalScreen().signProposalUI(proposal);
		});

		return grid;
	}
	
	private GridPane backButton() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setAlignment(Pos.CENTER_LEFT);

		ButtonWithStyle button = new ButtonWithStyle("Tilbage", grid, 0, 0);
		button.setOnAction(e -> {
			new ProposalOverview().proposalOverviewUI(customer.getCpr());
		});

		return grid;
	}

	//////////////////////////////
	// Label Title
	//////////////////////////////

	private Label title() {
		Label label = new Label("Nyt Låneforslag");
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
