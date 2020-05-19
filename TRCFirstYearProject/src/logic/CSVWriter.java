package logic;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVWriter {
	public void csvWriter(Proposal proposal) throws FileNotFoundException, IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(
				"C:\\Users\\Moose\\Documents\\FirstYearProjectCSV\\proposal" + proposal.getProposalId() + ".txt"))) {
			writer.write("Låneoversigt;");
			writer.newLine();
			writer.write("Total rente:;" + proposal.getTotalInterest());
			writer.newLine();
			writer.write("Afbetalingsperiode:;" + proposal.getLoanDuration());
			writer.newLine();
			writer.write("Tilbagebetalingsplan");
			writer.newLine();
			writer.write("Termin nr.;Afdrag;Renter;Ydelse;Restgæld;");
			writer.newLine();

			for (int i = 1; i <= proposal.getLoanDuration(); i++) {
				writer.write(csvFormat(i));
				// writer.write();
				// writer.write(str);
				writer.write(LocalDate.now().minusMonths(i).toString() + ";" + proposal.monthlyPayment().toString()
						+ ";" + "ikke uregnet" + ";" + "ydelse");
				writer.newLine();
			}
		}
	}

	private String csvFormat(Object obj) {
		return obj + ";";
	}

	private String autoDotBD(String string) {
		String str = string.replaceAll(",", ".");
		DecimalFormat decimalFormat = new DecimalFormat("###,###.00");

		return decimalFormat.format(str);
	}
}
