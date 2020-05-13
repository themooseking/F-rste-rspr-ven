package presentation;

import java.text.DecimalFormat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Car;
import logic.Customer;
import logic.Proposal;
import styles.ComboBoxWithStyle;
import styles.GridPaneCenter;
import styles.LabelWithStyle;
import styles.StyleClass;
import styles.TextFieldWithStyle;
import styles.TextWithStyle;

public class TextReader {

	private StyleClass style = new StyleClass();
	private Customer customer;
	private Proposal proposal;
	private Car car;
	private TextWithStyle carModel;
	private TextWithStyle carMilage;
	private TextWithStyle carYear;
	private TextWithStyle carPriceModelPrice;
	private TextWithStyle carPriceModel;
	private TextWithStyle carPriceVat;
	private TextWithStyle carPriceDownPayment;
	private TextWithStyle carPriceTotal;
	private TextWithStyle proposalInterest;
	private TextWithStyle proposalCreditScore;
	private TextWithStyle proposalDownPayment;
	private TextWithStyle proposalDuration;
	private TextWithStyle proposalTotalInterest;
	private TextWithStyle proposalApr;
	private TextWithStyle proposalMonthlyPayment;
	private TextWithStyle sumCarPrice;
	private TextWithStyle sumIntereset;
	private TextWithStyle proposalTotalSum;

	public TextReader(Customer customer, Proposal proposal) {
		this.customer = customer;
		this.proposal = proposal;
	}

	public VBox textReader() {
		VBox vbox = new VBox(
				customerTitle(), customerInfo(), carTitle(), carInfo(), carPriceTitle(), carPriceInfo(),
				proposalInfo(), priceSum(), totalPrice()
				);
		vbox.setBorder(style.elementBorder());

		checkDone();

		return vbox;
	}

	//////////////////////////////
	// Info
	//////////////////////////////

	private GridPane customerInfo() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));

		new TextWithStyle("Navn: ", grid, 0, 0, 110, 0, 1);
		new TextWithStyle(customer.getCustomerName(), grid, 1, 0, 200, 0, 1);

		new TextWithStyle("Adresse: ", grid, 2, 0, 110, 0, 1);
		new TextWithStyle(customer.getCustomerAddress(), grid, 3, 0, 200, 0, 1);

		new TextWithStyle("Telefon nr.: ", grid, 0, 1, 110, 1, 1);
		new TextWithStyle(Integer.toString(customer.getPhone()), grid, 1, 1, 300, 1, 1);

		new TextWithStyle("Email: ", grid, 2, 1, 110, 1, 1);
		new TextWithStyle(customer.getEmail(), grid, 3, 1, 300, 1, 1);

		return grid;
	}

	private GridPane carInfo() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));

		new TextWithStyle("Model: ", grid, 0, 0, 90, 1, 1);
		carModel = new TextWithStyle("", grid, 1, 0, 230, 1, 2);

		new TextWithStyle("Kilometer: ", grid, 3, 0, 120, 1, 1);
		carMilage = new TextWithStyle("", grid, 4, 0, 100, 1, 2);
		new TextWithStyle("km", grid, 5, 0, style.textUnitWidth(), 1, 1);

		new TextWithStyle("År: ", grid, 7, 0, 60, 1, 1);
		carYear = new TextWithStyle("", grid, 8, 0, 100, 1, 2);

		return grid;
	}

	private GridPane carPriceInfo() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));

		carPriceModel = new TextWithStyle("", grid, 0, 0, 500, 0, 1);
		carPriceModelPrice = new TextWithStyle("", grid, 1, 0, 200, 0, 2);
		new TextWithStyle("DKK", grid, 2, 0, style.textUnitWidth(), 0, 1);

		new TextWithStyle("+ Moms (25%) ", grid, 0, 1, 200, 0, 1);
		carPriceVat = new TextWithStyle("", grid, 1, 1, 200, 0, 2);
		new TextWithStyle("DKK", grid, 2, 1, style.textUnitWidth(), 0, 1);

		new TextWithStyle("-  Udbetaling: ", grid, 0, 2, 200, 0, 1);
		carPriceDownPayment = new TextWithStyle("", grid, 1, 2, 200, 0, 2);
		new TextWithStyle("DKK", grid, 2, 2, style.textUnitWidth(), 0, 1);

		new TextWithStyle("Total Bilpris: ", grid, 0, 3, 200, 2, 1);
		carPriceTotal = new TextWithStyle("", grid, 1, 3, 200, 2, 2);
		new TextWithStyle("DKK", grid, 2, 3, style.textUnitWidth(), 2, 1);

		return grid;
	}

	private GridPane proposalInfo() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));

		new TextWithStyle("Bank rente: ", grid, 0, 0, 500, 0, 1);
		proposalInterest = new TextWithStyle("", grid, 1, 0, 200, 0, 2);
		checkInterest();
		new TextWithStyle("%", grid, 2, 0, style.textUnitWidth(), 0, 1);

		new TextWithStyle("Kreditværdighed: ", grid, 0, 1, 200, 0, 1);
		proposalCreditScore = new TextWithStyle("", grid, 1, 1, 200, 0, 2);
		checkCreditScore();

		new TextWithStyle("Udbetaling: ", grid, 0, 2, 200, 0, 1);
		proposalDownPayment = new TextWithStyle("", grid, 1, 2, 200, 0, 2);
		new TextWithStyle("DKK", grid, 2, 2, style.textUnitWidth(), 0, 1);

		new TextWithStyle("Afbetalingsperiode: ", grid, 0, 3, 200, 0, 1);
		proposalDuration = new TextWithStyle("", grid, 1, 3, 200, 0, 2);
		new TextWithStyle("Måned(er)", grid, 2, 3, style.textUnitWidth(), 0, 1);

		new TextWithStyle("Total rente: ", grid, 0, 4, 200, 0, 1);
		proposalTotalInterest = new TextWithStyle("", grid, 1, 4, 100, 0, 2);
		new TextWithStyle("%", grid, 2, 4, style.textUnitWidth(), 0, 1);

		new TextWithStyle("ÅOP: ", grid, 0, 5, 200, 0, 1);
		proposalApr = new TextWithStyle("PLACEHOLDER", grid, 1, 5, 200, 0, 2);
		new TextWithStyle("%", grid, 2, 5, style.textUnitWidth(), 0, 1);

		new TextWithStyle("Månedlig ydelse: ", grid, 0, 6, 200, 2, 1);
		proposalMonthlyPayment = new TextWithStyle("", grid, 1, 6, 200, 2, 2);
		new TextWithStyle("DKK", grid, 2, 6, style.textUnitWidth(), 2, 1);

		return grid;
	}

	private GridPane priceSum() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));

		new TextWithStyle("Total bilpris: ", grid, 0, 0, 500, 0, 1);
		sumCarPrice = new TextWithStyle("", grid, 1, 0, 200, 0, 2);
		new TextWithStyle("DKK", grid, 2, 0, style.textUnitWidth(), 0, 1);

		new TextWithStyle("Total Renteomkostninger: ", grid, 0, 1, 200, 1, 1);
		sumIntereset = new TextWithStyle("", grid, 1, 1, 200, 1, 2);
		new TextWithStyle("DKK", grid, 2, 1, style.textUnitWidth(), 1, 1);

		return grid;
	}

	private GridPane totalPrice() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_RIGHT);
		grid.setPadding(new Insets(0, style.textReaderInsets() + 51, 0, style.textReaderInsets()));

		new TextWithStyle("Samlet Tilbagebetaling: ", grid, 0, 0, 220, 1, 1);
		proposalTotalSum = new TextWithStyle("", grid, 1, 0, 150, 1, 2);
		new TextWithStyle("DKK", grid, 2, 0, 65, 1, 1);

		return grid;
	}

	//////////////////////////////
	// Titles
	//////////////////////////////

	private GridPane customerTitle() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(30, style.textReaderInsets(), 0, style.textReaderInsets()));

		LabelWithStyle label = new LabelWithStyle("Kunde", grid, 0, 0);
		label.setFont(Font.font(style.textFont(), 25));

		return grid;
	}

	private GridPane carTitle() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));

		LabelWithStyle label = new LabelWithStyle("Bil", grid, 0, 0);
		label.setFont(Font.font(style.textFont(), 25));

		return grid;
	}

	private GridPane carPriceTitle() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));

		LabelWithStyle label = new LabelWithStyle("Bilpris", grid, 0, 0);
		label.setFont(Font.font(style.textFont(), 25)); 

		return grid;
	}

	//////////////////////////////
	// Checks
	//////////////////////////////

	private void checkInterest() {
		String format = new DecimalFormat("0.00").format(proposal.getInterest());
		if (format != "") {
			proposalInterest.setText(format);
		}
	}

	private void checkCreditScore() {
		if (customer.getCreditScore() != null) {
			proposalCreditScore.setText(customer.getCreditScore().toString());
		}
	}

	private void checkDone() {
		if (proposal.getCar() != null && proposal.getCustomer() != null && proposal.getDate() != null
				&& proposal.getInterest() != 0 && proposal.getLoanDuration() != 0) {

			carModel.setText(proposal.getCar().getModel());
			carMilage.setText(Integer.toString(proposal.getCar().getMilage()));
			carYear.setText(Integer.toString(proposal.getCar().getFactory()));
			carPriceModel.setText(proposal.getCar().toString());
			carPriceModelPrice.setText(decimal(proposal.getCar().getPrice()));
			carPriceVat.setText(decimal(proposal.getCar().getVat()));
			proposalTotalInterest.setText(decimal(proposal.calcInterest()));
			proposalMonthlyPayment.setText(decimal(proposal.monthlyPayment()));
			proposalTotalSum.setText(decimal(proposal.getProposalTotalSum()));
			sumIntereset.setText(decimal(proposal.totalInterestSum()));
			carPriceTotal.setText(decimal(proposal.totalCarPrice()));
			sumCarPrice.setText(decimal(proposal.totalCarPrice()));
			carPriceDownPayment.setText(decimal(proposal.getDownPayment()));
			proposalDownPayment.setText(decimal(proposal.getDownPayment()));
			proposalDuration.setText(Integer.toString(proposal.getLoanDuration()));
		}
	}

	//////////////////////////////
	// Clear
	//////////////////////////////

	public void clearTR() {
		proposal.setCar(null);
		proposal.setDownPayment(0);
		proposal.setLoanDuration(0);

		carModel.setText("");
		carMilage.setText("");
		carYear.setText("");
		carPriceModel.setText("");
		carPriceModelPrice.setText("");
		carPriceVat.setText("");
		carPriceTotal.setText("");
		sumCarPrice.setText("");
		proposalTotalInterest.setText("");
		proposalMonthlyPayment.setText("");
		proposalTotalSum.setText("");
		sumIntereset.setText("");
		carPriceDownPayment.setText("");
		proposalDownPayment.setText("");
		proposalDuration.setText("");
	}

	//////////////////////////////
	// Updates
	//////////////////////////////

	public void interestUpdate(String interestFormat) {
		proposalInterest.setText(interestFormat);
	}

	public void creditScoreUpdate(String creditScore) {
		proposalCreditScore.setText(creditScore);
	}

	public void update(boolean state, ComboBoxWithStyle model, ComboBoxWithStyle year, ComboBoxWithStyle regnr,
			TextFieldWithStyle duration, TextFieldWithStyle payment) {
		if (state) {
			if (!duration.getText().isEmpty() && !payment.getText().isEmpty() && model.getValue() != null) {
				proposalTotalInterest.setText(decimal(proposal.calcInterest()));
				proposalMonthlyPayment.setText(decimal(proposal.monthlyPayment()));
				proposalTotalSum.setText(decimal(proposal.getProposalTotalSum()));
				sumIntereset.setText(decimal(proposal.totalInterestSum()));
			}

			if (model.getValue() != null) {
				carModel.setText(proposal.getCar().getModel());
				carMilage.setText(Integer.toString(proposal.getCar().getMilage()));
				carYear.setText(Integer.toString(proposal.getCar().getFactory()));

				carPriceModel.setText(proposal.getCar().toString());
				carPriceModelPrice.setText(decimal(proposal.getCar().getPrice()));
				carPriceVat.setText(decimal(proposal.getCar().getVat()));
			}

			if (!payment.getText().isEmpty() && model.getValue() != null) {
				carPriceTotal.setText(decimal(proposal.totalCarPrice()));
				sumCarPrice.setText(decimal(proposal.totalCarPrice()));
			}

		} else {
			if (!duration.getText().isEmpty() && !payment.getText().isEmpty() && regnr.getValue() != null) {
				proposalTotalInterest.setText(decimal(proposal.calcInterest()));
				proposalMonthlyPayment.setText(decimal(proposal.monthlyPayment()));
				proposalTotalSum.setText(decimal(proposal.getProposalTotalSum()));
				sumIntereset.setText(decimal(proposal.totalInterestSum()));
			}

			if (regnr.getValue() != null) {
				carModel.setText(proposal.getCar().getModel());
				carMilage.setText(Integer.toString(proposal.getCar().getMilage()));
				carYear.setText(Integer.toString(proposal.getCar().getFactory()));

				carPriceModel.setText(proposal.getCar().toString());
				carPriceModelPrice.setText(decimal(proposal.getCar().getPrice()));
				carPriceVat.setText(decimal(proposal.getCar().getVat()));
			} else {
				car = null;
				proposal.setCar(car);

				carModel.setText("");
				carMilage.setText("");
				carYear.setText("");
			}

			if (!payment.getText().isEmpty() && regnr.getValue() != null) {
				carPriceTotal.setText(decimal(proposal.totalCarPrice()));
				sumCarPrice.setText(decimal(proposal.totalCarPrice()));
			}
		}

		if (!payment.getText().isEmpty()) {
			carPriceDownPayment.setText(decimal(proposal.getDownPayment()));
			proposalDownPayment.setText(decimal(proposal.getDownPayment()));
		}

		if (!duration.getText().isEmpty()) {
			proposalDuration.setText(Integer.toString(proposal.getLoanDuration()));
		}
	}

	private String decimal(double number) {
		String format = new DecimalFormat("0.00").format(number);
		return format;
	}
}
