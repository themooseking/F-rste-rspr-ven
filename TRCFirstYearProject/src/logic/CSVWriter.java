package logic;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CSVWriter {
	public void csvWriter(Proposal proposal) throws FileNotFoundException, IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(
				"C:\\Users\\Moose\\Documents\\FirstYearProjectCSV\\proposal" + proposal.getProposalId() + ".txt", true))) {
			
			writer.write("Låneoversigt;");
			writer.newLine();
			writer.write("Total rente:;" + proposal.getTotalInterest());
			writer.newLine();
			writer.write("Afbetalingsperiode:;" + proposal.getLoanDuration());
			writer.newLine();
			writer.write("Tilbagebetalingsplan");
			writer.newLine();
			writer.write("Termin nr.;Dato;Ydelse;Renter;Afdrag;Restgæld;");
			writer.newLine();

			BigDecimal loanAmount = proposal.totalCarPrice();

			for (int i = 1; i <= proposal.getLoanDuration(); i++) {
				BigDecimal monthlyRateAmount = proposal.monthlyRateAmount(loanAmount);
				BigDecimal repayment = proposal.repayment(monthlyRateAmount);

				writer.write(csvFormat(i));
				writer.write(csvFormat(proposal.getDate().plusMonths(i)));
				writer.write(csvFormat(proposal.monthlyPayment()));
				writer.write(csvFormat(monthlyRateAmount));
				writer.write(csvFormat(repayment));
				writer.write(csvFormat(loanAmount));
				writer.newLine();
				
				loanAmount = proposal.remainingLoanAmount(loanAmount, repayment);
			}
		}
	}

	private String csvFormat(Object obj) {
		if (obj instanceof BigDecimal) {
			obj = autoDotBD(obj.toString());
		}
		return obj + ";";
	}

	private String autoDotBD(String string) {
		String str = string.replaceAll(",", ".");
		DecimalFormat decimalFormat = new DecimalFormat("###,###.00");

		return decimalFormat.format(Double.parseDouble(str));
	}
}
