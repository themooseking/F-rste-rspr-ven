package presentation;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Customer;
import styles.ComboBoxWithStyle;
import styles.GridPaneCenter;
import styles.LabelWithStyle;
import styles.StyleClass;
import styles.TextFieldWithStyle;
import styles.TextWithStyle;

public class TextReader {
	
	private boolean recreate;
	private StyleClass style = new StyleClass();
	private Customer customer;
	private TextWithStyle carModeltr;
	private TextWithStyle carMilagetr;
	private TextWithStyle carYeartr;
	private TextWithStyle carPriceModelPricetr;
	private TextWithStyle carPriceModeltr;
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
	private TextWithStyle sumCarPricetr;
	private TextWithStyle sumInteresettr;
	private TextWithStyle proposalTotalSumtr;
	private ComboBoxWithStyle modelcb;
	private TextFieldWithStyle durationtf;
	private TextFieldWithStyle paymenttf;
	private ComboBoxWithStyle regnrcb;
	
	

	

	public VBox textReader() {
		VBox trvbox = new VBox(customerTitle(), customerInfo(), carTitle(), carInfo(), carPriceTitle(), carPriceInfo(),
				proposalInfo(), priceSum(), totalPrice());
		if (recreate == true) {
			trvbox.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(0),
					new BorderWidths(3))));
			recreate = false;
		}

		onHidingModel();
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

		new TextWithStyle("+ Moms (25%) ", grid, 0, 1, 200);
		carPriceVattr = new TextWithStyle("", grid, 1, 1, 200);
		carPriceVattr.setAlignment(Pos.CENTER_RIGHT);
		new TextWithStyle("DKK", grid, 2, 1, style.textUnitWidth());

		new TextWithStyle("-  Udbetaling: ", grid, 0, 2, 200);
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
		proposalMonthlyPaymenttr = new TextWithStyle("", grid, 1, 6, 200);
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
		sumCarPricetr = new TextWithStyle("", grid, 1, 0, 200);
		sumCarPricetr.setAlignment(Pos.CENTER_RIGHT);
		new TextWithStyle("DKK", grid, 2, 0, style.textUnitWidth());

		TextWithStyle totalInterestSum = new TextWithStyle("Total Renteomkostninger: ", grid, 0, 1, 200);
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

		TextWithStyle total = new TextWithStyle("Samlet Tilbagebetaling: ", grid, 0, 0, 65);
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

	private void onHidingModel() {
		modelcb.setOnHiding(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				updateTextReader();
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

				updateTextReader();
			}
		});
	}

	private void keyTypedDownPayment() {
		paymenttf.setOnKeyTyped(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {

				if (!paymenttf.getText().isEmpty()) {
					proposal.setDownPayment(Integer.parseInt(paymenttf.getText()));
				} else {
					proposal.setDownPayment(0);
				}

				updateTextReader();
			}
		});
	}

	private void updateTextReader() {
		if (rbState) {
			if (!durationtf.getText().isEmpty() && !paymenttf.getText().isEmpty() && modelcb.getValue() != null) {
				proposalTotalInteresttr.setText(decimal(proposal.calcInterest()));
				proposalMonthlyPaymenttr.setText(decimal(proposal.monthlyPayment()));
				sumInteresettr.setText(decimal(proposal.totalInterestSum()));
				proposalTotalSumtr.setText(decimal(proposal.totalProposalPrice()));
			}

			if (modelcb.getValue() != null) {
				carModeltr.setText(proposal.getCar().getModel());
				carMilagetr.setText(Integer.toString(proposal.getCar().getMilage()));
				carYeartr.setText(Integer.toString(proposal.getCar().getFactory()));

				carPriceModeltr.setText(proposal.getCar().toString());
				carPriceModelPricetr.setText(decimal(proposal.getCar().getPrice()));
				carPriceVattr.setText(decimal(proposal.getCar().getVat()));
			}

			if (!paymenttf.getText().isEmpty() && modelcb.getValue() != null) {
				carPriceTotaltr.setText(decimal(proposal.totalCarPrice()));
				sumCarPricetr.setText(decimal(proposal.totalCarPrice()));
			}

		} else {
			if (!durationtf.getText().isEmpty() && !paymenttf.getText().isEmpty() && regnrcb.getValue() != null) {
				proposalTotalInteresttr.setText(decimal(proposal.calcInterest()));
				proposalMonthlyPaymenttr.setText(decimal(proposal.monthlyPayment()));
				sumInteresettr.setText(decimal(proposal.totalInterestSum()));
				proposalTotalSumtr.setText(decimal(proposal.totalProposalPrice()));
			}

			if (regnrcb.getValue() != null) {
				carModeltr.setText(proposal.getCar().getModel());
				carMilagetr.setText(Integer.toString(proposal.getCar().getMilage()));
				carYeartr.setText(Integer.toString(proposal.getCar().getFactory()));

				carPriceModeltr.setText(proposal.getCar().toString());
				carPriceModelPricetr.setText(decimal(proposal.getCar().getPrice()));
				carPriceVattr.setText(decimal(proposal.getCar().getVat()));
			} else {
				car = null;
				proposal.setCar(car);

				carModeltr.setText("");
				carMilagetr.setText("");
				carYeartr.setText("");
			}

			if (!paymenttf.getText().isEmpty() && regnrcb.getValue() != null) {
				carPriceTotaltr.setText(decimal(proposal.totalCarPrice()));
				sumCarPricetr.setText(decimal(proposal.totalCarPrice()));
			}
		}

		if (!paymenttf.getText().isEmpty()) {
			carPriceDownPaymenttr.setText(decimal(proposal.getDownPayment()));
			proposalDownPaymenttr.setText(decimal(proposal.getDownPayment()));
		}
	}
}
