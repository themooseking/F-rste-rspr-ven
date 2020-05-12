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
	private Proposal proposal;
	private Car car;

	public TextReader(Customer customer, Proposal proposal) {
		this.customer = customer;
		this.proposal = proposal;
	}

	public VBox textReader() {
		VBox vbox = new VBox(customerTitle(), customerInfo(), carTitle(), carInfo(), carPriceTitle(), carPriceInfo(),
				proposalInfo(), priceSum(), totalPrice());
			vbox.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(0),
					new BorderWidths(3))));

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
		carModeltr = new TextWithStyle("", grid, 1, 0, 230, 1, 2);

		new TextWithStyle("Kilometer: ", grid, 3, 0, 120, 1, 1);
		carMilagetr = new TextWithStyle("", grid, 4, 0, 100, 1, 2);
		new TextWithStyle("km", grid, 5, 0, style.textUnitWidth(), 1, 1);

		new TextWithStyle("År: ", grid, 7, 0, 60, 1, 1);
		carYeartr = new TextWithStyle("", grid, 8, 0, 100, 1, 2);

		return grid;
	}

	private GridPane carPriceInfo() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));

		carPriceModeltr = new TextWithStyle("", grid, 0, 0, 500, 0, 1);
		carPriceModelPricetr = new TextWithStyle("", grid, 1, 0, 200, 0, 2);
		new TextWithStyle("DKK", grid, 2, 0, style.textUnitWidth(), 0, 1);

		new TextWithStyle("+ Moms (25%) ", grid, 0, 1, 200, 0, 1);
		carPriceVattr = new TextWithStyle("", grid, 1, 1, 200, 0, 2);
		new TextWithStyle("DKK", grid, 2, 1, style.textUnitWidth(), 0, 1);

		new TextWithStyle("-  Udbetaling: ", grid, 0, 2, 200, 0, 1);
		carPriceDownPaymenttr = new TextWithStyle("", grid, 1, 2, 200, 0, 2);
		new TextWithStyle("DKK", grid, 2, 2, style.textUnitWidth(), 0, 1);

		new TextWithStyle("Total Bilpris: ", grid, 0, 3, 200, 2, 1);
		carPriceTotaltr = new TextWithStyle("", grid, 1, 3, 200, 2, 2);
		new TextWithStyle("DKK", grid, 2, 3, style.textUnitWidth(), 2, 1);

		return grid;
	}

	private GridPane proposalInfo() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));

		new TextWithStyle("Bank rente: ", grid, 0, 0, 500, 0, 1);
		proposalInteresttr = new TextWithStyle("", grid, 1, 0, 200, 0, 2);
		checkInterest();
		new TextWithStyle("%", grid, 2, 0, style.textUnitWidth(), 0, 1);

		new TextWithStyle("Kreditværdighed: ", grid, 0, 1, 200, 0, 1);
		proposalCreditScoretr = new TextWithStyle("", grid, 1, 1, 200, 0, 2);
		checkCreditScore();

		new TextWithStyle("Udbetaling: ", grid, 0, 2, 200, 0, 1);
		proposalDownPaymenttr = new TextWithStyle("", grid, 1, 2, 200, 0, 2);
		new TextWithStyle("DKK", grid, 2, 2, style.textUnitWidth(), 0, 1);

		new TextWithStyle("Afbetalingsperiode: ", grid, 0, 3, 200, 0, 1);
		proposalDurationtr = new TextWithStyle("", grid, 1, 3, 200, 0, 2);
		new TextWithStyle("Måned(er)", grid, 2, 3, style.textUnitWidth(), 0, 1);

		new TextWithStyle("Total rente: ", grid, 0, 4, 200, 0, 1);
		proposalTotalInteresttr = new TextWithStyle("", grid, 1, 4, 100, 0, 2);
		new TextWithStyle("%", grid, 2, 4, style.textUnitWidth(), 0, 1);

		new TextWithStyle("ÅOP: ", grid, 0, 5, 200, 0, 1);
		proposalAprtr = new TextWithStyle("PLACEHOLDER", grid, 1, 5, 200, 0, 2);
		new TextWithStyle("%", grid, 2, 5, style.textUnitWidth(), 0, 1);

		new TextWithStyle("Månedlig ydelse: ", grid, 0, 6, 200, 2, 1);
		proposalMonthlyPaymenttr = new TextWithStyle("", grid, 1, 6, 200, 2, 2);
		new TextWithStyle("DKK", grid, 2, 6, style.textUnitWidth(), 2, 1);

		return grid;
	}

	private GridPane priceSum() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(0, style.textReaderInsets(), 0, style.textReaderInsets()));

		new TextWithStyle("Total bilpris: ", grid, 0, 0, 500, 0, 1);
		sumCarPricetr = new TextWithStyle("", grid, 1, 0, 200, 0, 2);
		new TextWithStyle("DKK", grid, 2, 0, style.textUnitWidth(), 0, 1);

		new TextWithStyle("Total Renteomkostninger: ", grid, 0, 1, 200, 1, 1);
		sumInteresettr = new TextWithStyle("", grid, 1, 1, 200, 1, 2);
		new TextWithStyle("DKK", grid, 2, 1, style.textUnitWidth(), 1, 1);

		return grid;
	}

	private GridPane totalPrice() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER_RIGHT);
		grid.setPadding(new Insets(0, style.textReaderInsets() + 43, 0, style.textReaderInsets()));

		new TextWithStyle("Samlet Tilbagebetaling: ", grid, 0, 0, 220, 1, 1);
		proposalTotalSumtr = new TextWithStyle("", grid, 1, 0, 150, 1, 2);
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
			proposalInteresttr.setText(format);
		}
	}
	
	private void checkCreditScore() {
		if (customer.getCreditScore() != null) {
			proposalCreditScoretr.setText(customer.getCreditScore().toString());
		}
	}
	
	//////////////////////////////
	// Clear
	//////////////////////////////
	
	public void clearTR() {
		proposal.setCar(null);
		proposal.setDownPayment(0);
		proposal.setLoanDuration(0);
		
		carModeltr.setText("");
		carMilagetr.setText("");
		carYeartr.setText("");		
		carPriceModeltr.setText("");
		carPriceModelPricetr.setText("");
		carPriceVattr.setText("");		
		carPriceTotaltr.setText("");
		sumCarPricetr.setText("");		
		proposalTotalInteresttr.setText("");
		proposalMonthlyPaymenttr.setText("");
		sumInteresettr.setText("");
		proposalTotalSumtr.setText("");		
		carPriceDownPaymenttr.setText("");
		proposalDownPaymenttr.setText("");
		proposalDurationtr.setText("");
	}
	
	//////////////////////////////
	// Updates
	//////////////////////////////
	
	public void interestUpdate(String interestFormat) {
		proposalInteresttr.setText(interestFormat);
	}
	
	public void creditScoreUpdate(String creditScore) {
		proposalCreditScoretr.setText(creditScore);
	}

	public void update(boolean state, ComboBoxWithStyle model, ComboBoxWithStyle year, ComboBoxWithStyle regnr, TextFieldWithStyle duration, TextFieldWithStyle payment) {
		if (state) {
			if (!duration.getText().isEmpty() && !payment.getText().isEmpty() && model.getValue() != null) {
				proposalTotalInteresttr.setText(decimal(proposal.calcInterest()));
				proposalMonthlyPaymenttr.setText(decimal(proposal.monthlyPayment()));
				sumInteresettr.setText(decimal(proposal.totalInterestSum()));
				proposalTotalSumtr.setText(decimal(proposal.totalProposalPrice()));
			}

			if (model.getValue() != null) {
				carModeltr.setText(proposal.getCar().getModel());
				carMilagetr.setText(Integer.toString(proposal.getCar().getMilage()));
				carYeartr.setText(Integer.toString(proposal.getCar().getFactory()));

				carPriceModeltr.setText(proposal.getCar().toString());
				carPriceModelPricetr.setText(decimal(proposal.getCar().getPrice()));
				carPriceVattr.setText(decimal(proposal.getCar().getVat()));
			}

			if (!payment.getText().isEmpty() && model.getValue() != null) {
				carPriceTotaltr.setText(decimal(proposal.totalCarPrice()));
				sumCarPricetr.setText(decimal(proposal.totalCarPrice()));
			}

		} else {
			if (!duration.getText().isEmpty() && !payment.getText().isEmpty() && regnr.getValue() != null) {
				proposalTotalInteresttr.setText(decimal(proposal.calcInterest()));
				proposalMonthlyPaymenttr.setText(decimal(proposal.monthlyPayment()));
				sumInteresettr.setText(decimal(proposal.totalInterestSum()));
				proposalTotalSumtr.setText(decimal(proposal.totalProposalPrice()));
			}

			if (regnr.getValue() != null) {
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

			if (!payment.getText().isEmpty() && regnr.getValue() != null) {
				carPriceTotaltr.setText(decimal(proposal.totalCarPrice()));
				sumCarPricetr.setText(decimal(proposal.totalCarPrice()));
			}
		}

		if (!payment.getText().isEmpty()) {
			carPriceDownPaymenttr.setText(decimal(proposal.getDownPayment()));
			proposalDownPaymenttr.setText(decimal(proposal.getDownPayment()));
		}
		
		if (!duration.getText().isEmpty()) {
			proposalDurationtr.setText(Integer.toString(proposal.getLoanDuration()));
		}
	}
	
	private String decimal(double number) {
		String format = new DecimalFormat("0.00").format(number);
		return format;
	}
}
