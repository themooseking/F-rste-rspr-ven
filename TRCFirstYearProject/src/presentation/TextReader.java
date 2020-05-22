package presentation;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.Car;
import logic.Customer;
import logic.Proposal;
import styles.ComboBoxWithStyle;
import styles.GridPaneWithStyle;
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
	private TextWithStyle proposalMonthlyPayment;
	private TextWithStyle sumCarPrice;
	private TextWithStyle sumIntereset;
	private TextWithStyle proposalTotalSum;

	public TextReader(Customer customer, Proposal proposal) {
		this.customer = customer;
		this.proposal = proposal;
	}

	public VBox textReader() {
		VBox vbox = new VBox(customerTitle(), customerInfo(), carTitle(), carInfo(), carPriceTitle(), carPriceInfo(),
				proposalInfo(), priceSum(), totalPrice());
		vbox.setPadding(new Insets(30, style.textReaderInsets(), 40, style.textReaderInsets()));
		vbox.setBorder(style.elementBorder());
		vbox.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 20, 0, 5, 5));
		vbox.setStyle("-fx-background-color: \"" + style.white() + "\";"
				+ "-fx-background-image: url(\"file:resources/background/BackgroundTextReader.jpg\"); "
				+ "-fx-background-repeat: no-repeat;" + "-fx-background-size: 500;"
				+ "-fx-background-position: center;");
		checkDone();

		return vbox;
	}

	//////////////////////////////
	// INFO
	//////////////////////////////

	private GridPane customerInfo() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(10, 0, 20, 0));
		grid.setBorder(style.underLine());
		new TextWithStyle(" ", grid, 0, 0, 5, 1);

		TextWithStyle name = new TextWithStyle("Navn: ", grid, 1, 0, 110, 1);
		name.setStyle(style.bold());
		new TextWithStyle(customer.getCustomerName(), grid, 2, 0, 200, 1);

		TextWithStyle adress = new TextWithStyle("Adresse: ", grid, 1, 1, 110, 1);
		adress.setStyle(style.bold());
		new TextWithStyle(customer.getCustomerAddress() + ", " + customer.getPostalCode(), grid, 2, 1, 200, 1);

		TextWithStyle phone = new TextWithStyle("Telefon nr.: ", grid, 1, 2, 110, 1);
		phone.setStyle(style.bold());
		new TextWithStyle(Integer.toString(customer.getPhone()), grid, 2, 2, 300, 1);

		TextWithStyle mail = new TextWithStyle("Email: ", grid, 1, 3, 110, 1);
		mail.setStyle(style.bold());
		new TextWithStyle(customer.getEmail(), grid, 2, 3, 200, 1);

		return grid;
	}

	private GridPane carInfo() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(10, 0, 10, 0));
		grid.setBorder(style.underLine());
		new TextWithStyle(" ", grid, 0, 0, 5, 1);

		TextWithStyle model = new TextWithStyle("Model: ", grid, 1, 0, 90, 1);
		model.setStyle(style.bold());
		carModel = new TextWithStyle("", grid, 2, 0, 290, 1);

		TextWithStyle km = new TextWithStyle("Kilometer: ", grid, 1, 1, 110, 1);
		km.setStyle(style.bold());
		carMilage = new TextWithStyle("", grid, 2, 1, 50, 1);

		TextWithStyle year = new TextWithStyle("År: ", grid, 1, 2, 60, 1);
		year.setStyle(style.bold());
		carYear = new TextWithStyle("", grid, 2, 2, 200, 1);

		return grid;
	}

	private GridPane carPriceInfo() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(10, 0, 20, 0));
		grid.setBorder(style.dottedUnderLine());
		grid.setHgap(5);
		new TextWithStyle(" ", grid, 0, 0, 5, 1);

		carPriceModel = new TextWithStyle("", grid, 1, 0, style.textReaderTextWidth(), 1);
		carPriceModelPrice = new TextWithStyle("", grid, 2, 0, 200, 2);
		new TextWithStyle("DKK", grid, 3, 0, style.textUnitWidth(), 1);

		new TextWithStyle("+ Moms (25%) ", grid, 1, 1, 200, 1);
		carPriceVat = new TextWithStyle("", grid, 2, 1, 200, 2);
		new TextWithStyle("DKK", grid, 3, 1, style.textUnitWidth(), 1);

		new TextWithStyle("-  Udbetaling: ", grid, 1, 2, 200, 1);
		carPriceDownPayment = new TextWithStyle("", grid, 2, 2, 200, 2);
		new TextWithStyle("DKK", grid, 3, 2, style.textUnitWidth(), 1);

		TextWithStyle carprice = new TextWithStyle("Total Bilpris: ", grid, 1, 3, 200, 1);
		carprice.setStyle(style.bold());
		carPriceTotal = new TextWithStyle("", grid, 2, 3, 200, 2);
		carPriceTotal.setStyle(style.bold());
		TextWithStyle dkk = new TextWithStyle("DKK", grid, 3, 3, style.textUnitWidth(), 1);
		dkk.setStyle(style.bold());

		return grid;
	}

	private GridPane proposalInfo() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(10, 0, 3, 0));
		grid.setBorder(style.dottedUnderLine());
		grid.setHgap(5);
		new TextWithStyle(" ", grid, 0, 0, 5, 1);

		new TextWithStyle("Bank rente: ", grid, 1, 0, style.textReaderTextWidth(), 1);
		proposalInterest = new TextWithStyle("", grid, 2, 0, 200, 2);
		checkInterest();
		new TextWithStyle("%", grid, 3, 0, style.textUnitWidth(), 1);

		new TextWithStyle("Kreditværdighed: ", grid, 1, 1, 200, 1);
		proposalCreditScore = new TextWithStyle("", grid, 2, 1, 200, 2);
		checkCreditScore();

		new TextWithStyle("Udbetaling: ", grid, 1, 2, 200, 1);
		proposalDownPayment = new TextWithStyle("", grid, 2, 2, 200, 2);
		new TextWithStyle("DKK", grid, 3, 2, style.textUnitWidth(), 1);

		new TextWithStyle("Afbetalingsperiode: ", grid, 1, 3, 200, 1);
		proposalDuration = new TextWithStyle("", grid, 2, 3, 200, 2);
		new TextWithStyle("Måned(er)", grid, 3, 3, style.textUnitWidth(), 1);

		TextWithStyle interest = new TextWithStyle("Total rente: ", grid, 1, 4, 200, 1);
		interest.setStyle(style.bold());
		proposalTotalInterest = new TextWithStyle("", grid, 2, 4, 100, 2);
		proposalTotalInterest.setStyle(style.bold());
		TextWithStyle perc = new TextWithStyle("%", grid, 3, 4, style.textUnitWidth(), 1);
		perc.setStyle(style.bold());

		TextWithStyle monthly = new TextWithStyle("Månedlig ydelse: ", grid, 1, 5, 200, 1);
		monthly.setStyle(style.bold());
		proposalMonthlyPayment = new TextWithStyle("", grid, 2, 5, 200, 2);
		proposalMonthlyPayment.setStyle(style.bold());
		TextWithStyle dkk = new TextWithStyle("DKK", grid, 3, 5, style.textUnitWidth(), 1);
		dkk.setStyle(style.bold());

		return grid;
	}

	private GridPane priceSum() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(10, 0, 3, 0));
		grid.setBorder(style.underLine());
		grid.setHgap(5);
		new TextWithStyle(" ", grid, 0, 0, 5, 1);

		TextWithStyle carprice = new TextWithStyle("Total bilpris: ", grid, 1, 0, style.textReaderTextWidth(), 1);
		carprice.setStyle(style.bold());
		sumCarPrice = new TextWithStyle("", grid, 2, 0, 200, 2);
		sumCarPrice.setStyle(style.bold());
		TextWithStyle dkk1 = new TextWithStyle("DKK", grid, 3, 0, style.textUnitWidth(), 1);
		dkk1.setStyle(style.bold());

		TextWithStyle totalinterest = new TextWithStyle("Total Renteomkostninger: ", grid, 1, 1, 210, 1);
		totalinterest.setStyle(style.bold());
		sumIntereset = new TextWithStyle("", grid, 2, 1, 200, 2);
		sumIntereset.setStyle(style.bold());
		TextWithStyle dkk2 = new TextWithStyle("DKK", grid, 3, 1, style.textUnitWidth(), 1);
		dkk2.setStyle(style.bold());

		return grid;
	}

	private GridPane totalPrice() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.BASELINE_RIGHT);
		grid.setPadding(new Insets(15, 0, 5, 0));
		grid.setBorder(style.sumLine());
		grid.setHgap(5);

		TextWithStyle total = new TextWithStyle("Samlet Tilbagebetaling: ", grid, 0, 0, 150, 1);
		total.setStyle(style.bold());
		proposalTotalSum = new TextWithStyle("", grid, 1, 0, 130, 2);
		proposalTotalSum.setStyle(style.bold());
		TextWithStyle dkk = new TextWithStyle("DKK", grid, 2, 0, style.textUnitWidth(), 1);
		dkk.setStyle(style.bold());

		return grid;
	}

	//////////////////////////////
	// TITLES
	//////////////////////////////

	private GridPane customerTitle() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(0, 0, 0, 0));

		LabelWithStyle label = new LabelWithStyle("Kunde", grid, 0, 0);
		label.setFont(Font.font(style.textFont(), FontWeight.BOLD, 25));
		label.setTextFill(Color.web(style.grey()));

		return grid;
	}

	private GridPane carTitle() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(10, 0, 0, 0));

		LabelWithStyle label = new LabelWithStyle("Bil", grid, 0, 0);
		label.setFont(Font.font(style.textFont(), FontWeight.BOLD, 25));
		label.setTextFill(Color.web(style.grey()));

		return grid;
	}

	private GridPane carPriceTitle() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(10, 0, 0, 0));

		LabelWithStyle label = new LabelWithStyle("Bilpris", grid, 0, 0);
		label.setFont(Font.font(style.textFont(), FontWeight.BOLD, 25));
		label.setTextFill(Color.web(style.grey()));

		return grid;
	}

	//////////////////////////////
	// CHECKS
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
		if (proposal.getCar() != null && proposal.getCustomer() != null && proposal.getProposalDate() != null
				&& proposal.getInterest() != 0 && proposal.getLoanDuration() != 0) {

			carModel.setText(proposal.getCar().getModel());
			carMilage.setText(Integer.toString(proposal.getCar().getMilage()) + " km");
			carYear.setText(Integer.toString(proposal.getCar().getFactory()));
			
			carPriceModel.setText(proposal.getCar().toString());
			carPriceModelPrice.setText(autoDotBD(decimal(proposal.getCar().getPrice())));
			carPriceVat.setText(autoDotBD(decimal(proposal.getCar().getVat())));
			carPriceDownPayment.setText(autoDotBD(decimal(proposal.getDownPayment())));
			carPriceTotal.setText(autoDotBD(decimal(proposal.totalCarPrice())));			

			proposalCreditScore.setText(proposal.getCreditScore().toString());
			proposalDownPayment.setText(autoDotBD(decimal(proposal.getDownPayment())));
			proposalDuration.setText(Integer.toString(proposal.getLoanDuration()));
			proposalTotalInterest.setText(decimal(proposal.calcInterest()));
			proposalMonthlyPayment.setText(autoDotBD(decimal(proposal.monthlyPayment())));

			sumCarPrice.setText(autoDotBD(decimal(proposal.totalCarPrice())));
			sumIntereset.setText(autoDotBD(decimal(proposal.totalInterestSum())));

			proposalTotalSum.setText(autoDotBD(decimal(proposal.getProposalTotalSum())));
		}
	}

	//////////////////////////////
	// CLEAR
	//////////////////////////////

	public void clearTR() {
		proposal.setCar(null);
		proposal.setDownPayment(new BigDecimal(0));
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
	// UPDATES
	//////////////////////////////

	public void interestUpdate(String interestFormat) {
		proposalInterest.setText(interestFormat);
	}

	public void creditScoreUpdate(String creditScore) {
		proposalCreditScore.setText(creditScore);
	}
	
	/*****************************************************
	 * Updates textreader with inputs when called
	 *****************************************************/

	public void update(boolean state, ComboBoxWithStyle model, ComboBoxWithStyle year, ComboBoxWithStyle regnr,
			TextFieldWithStyle duration, TextFieldWithStyle payment) {
		if (state) {
			if (!duration.getText().isEmpty() && !payment.getText().isEmpty() && model.getValue() != null) {
				proposalTotalInterest.setText(decimal(proposal.calcInterest()));
				proposalMonthlyPayment.setText(autoDotBD(decimal(proposal.monthlyPayment())));
				proposalTotalSum.setText(autoDotBD(decimal(proposal.getProposalTotalSum())));
				sumIntereset.setText(autoDotBD(decimal(proposal.totalInterestSum())));
			}

			if (model.getValue() != null) {
				carModel.setText(proposal.getCar().getModel());
				carMilage.setText(Integer.toString(proposal.getCar().getMilage()) + " km");
				carYear.setText(Integer.toString(proposal.getCar().getFactory()));

				carPriceModel.setText(proposal.getCar().toString());
				carPriceModelPrice.setText(autoDotBD(decimal(proposal.getCar().getPrice())));
				carPriceVat.setText(autoDotBD(decimal(proposal.getCar().getVat())));
			}

			if (!payment.getText().isEmpty() && model.getValue() != null) {
				carPriceTotal.setText(autoDotBD(decimal(proposal.totalCarPrice())));
				sumCarPrice.setText(autoDotBD(decimal(proposal.totalCarPrice())));
			}

		} else {
			if (!duration.getText().isEmpty() && !payment.getText().isEmpty() && regnr.getValue() != null) {
				proposalTotalInterest.setText(decimal(proposal.calcInterest()));
				proposalMonthlyPayment.setText(autoDotBD(decimal(proposal.monthlyPayment())));
				proposalTotalSum.setText(autoDotBD(decimal(proposal.getProposalTotalSum())));
				sumIntereset.setText(autoDotBD(decimal(proposal.totalInterestSum())));
			}

			if (regnr.getValue() != null) {
				carModel.setText(proposal.getCar().getModel());
				carMilage.setText(Integer.toString(proposal.getCar().getMilage()) + " km");
				carYear.setText(Integer.toString(proposal.getCar().getFactory()));

				carPriceModel.setText(proposal.getCar().toString());
				carPriceModelPrice.setText(autoDotBD(decimal(proposal.getCar().getPrice())));
				carPriceVat.setText(autoDotBD(decimal(proposal.getCar().getVat())));
			} else {
				car = null;
				proposal.setCar(car);

				carModel.setText("");
				carMilage.setText("");
				carYear.setText("");
			}

			if (!payment.getText().isEmpty() && regnr.getValue() != null) {
				carPriceTotal.setText(autoDotBD(decimal(proposal.totalCarPrice())));
				sumCarPrice.setText(autoDotBD(decimal(proposal.totalCarPrice())));
			}
		}

		if (!payment.getText().isEmpty()) {
			carPriceDownPayment.setText(autoDotBD(decimal(proposal.getDownPayment())));
			proposalDownPayment.setText(autoDotBD(decimal(proposal.getDownPayment())));
		} else if (payment.getText().isEmpty()) {
			carPriceDownPayment.setText("");
			proposalDownPayment.setText("");
		}

		if (!duration.getText().isEmpty()) {
			proposalDuration.setText(Integer.toString(proposal.getLoanDuration()));
		} else if (duration.getText().isEmpty()) {
			proposalDuration.setText("");
		}
	}

	private String decimal(double number) {
		String format = new DecimalFormat("0.00").format(number);
		return format;
	}

	private String decimal(BigDecimal number) {
		String format = new DecimalFormat("0.00").format(number);
		return format;
	}

	private String autoDotBD(String string) {	
		String str = string.replaceAll(",", ".");
		DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");

		return decimalFormat.format(Double.parseDouble(str));
	}
}
