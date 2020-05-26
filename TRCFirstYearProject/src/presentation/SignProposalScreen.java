package presentation;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.CSVWriter;
import logic.DB_Controller;
import logic.Proposal;
import logic.Salesman;
import logic.Status;
import styles.ButtonWithStyle;
import styles.GridPaneWithStyle;
import styles.LabelWithStyle;
import styles.PasswordFieldWithStyle;
import styles.StyleClass;
import styles.TextFieldWithStyle;
import styles.VBoxWithStyle;

public class SignProposalScreen {

	private StyleClass style = new StyleClass();
	private DB_Controller controller = new DB_Controller();
	private Proposal proposal;
	private TextReader tr;
	private PasswordFieldWithStyle password;
	private LabelWithStyle wrong;

	public SignProposalScreen(Proposal proposal) {
		this.proposal = proposal;
		this.tr = new TextReader(proposal.getCustomer(), proposal);
	}

	public void defaultUI() {
		View view = View.Default;
		HBox hbox;
		Status status = proposal.getProposalStatus();

		if (status == Status.IGANG || status == Status.GODKENDT) {
			hbox = new HBox(tr.textReader(), fitter(view));
		} else {
			hbox = new HBox(tr.textReader());
		}
		uiSetup(view, hbox);
	}

	public void salesmanUI() {
		View view = View.Salesman;
		HBox hbox;
		Status status = proposal.getProposalStatus();

		if (status == Status.IGANG || status == Status.GODKENDT) {
			hbox = new HBox(tr.textReader(), fitter(view));
		} else {
			hbox = new HBox(tr.textReader());
		}
		uiSetup(view, hbox);
	}

	public void cosUI() {
		View view = View.ChiefOfSales;
		HBox hbox;
		Status status = proposal.getProposalStatus();

		if (status != Status.GODKENDT) {
			hbox = new HBox(tr.textReader(), fitter(view));
		} else {
			hbox = new HBox(tr.textReader());
		}
		uiSetup(view, hbox);
	}

	//////////////////////////////
	// SIGN INPUTS
	//////////////////////////////

	private VBox fitter(View view) {
		VBox vbox = new VBox(signInput(view));
		vbox.setAlignment(Pos.CENTER);

		return vbox;
	}

	private VBox signInput(View view) {
		VBox vbox = new VBox(textFields(), signButtons(view));
		vbox.setBackground(
				new Background(new BackgroundFill(Color.web(style.white()), new CornerRadii(0), Insets.EMPTY)));
		vbox.setBorder(style.elementBorder());
		vbox.setPadding(new Insets(0, 0, 0, 50));

		return vbox;
	}

	private GridPaneWithStyle textFields() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER);
		grid.setVgap(10);

		new LabelWithStyle("Sælger ID ", grid, 0, 0);
		TextFieldWithStyle id = new TextFieldWithStyle("", grid, 1, 0);
		id.setDisable(true);
		id.setText(Integer.toString(LoggedInST.getUser().getSalesmanId()));

		new LabelWithStyle("Sælger Navn ", grid, 0, 1);
		TextFieldWithStyle name = new TextFieldWithStyle("", grid, 1, 1);
		name.setDisable(true);
		name.setText(LoggedInST.getUser().toString());

		new LabelWithStyle("Kodeord ", grid, 0, 2);
		password = new PasswordFieldWithStyle("kodeord", grid, 1, 2);

		wrong = new LabelWithStyle("", grid, 1, 3);
		wrong.setTextFill(Color.web(style.red()));

		return grid;
	}

	private GridPaneWithStyle signButtons(View view) {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER);
		grid.setHgap(20);

		ButtonWithStyle deleteCancel = new ButtonWithStyle("Slet", grid, 0, 0);
		ButtonWithStyle signConfirm = new ButtonWithStyle("Underskriv", grid, 1, 0);

		if (view == View.ChiefOfSales) {
			cancelEvent(deleteCancel, view);
			confirmEvent(signConfirm);
		} else {
			deleteEvent(deleteCancel, view);
			signEvent(signConfirm, view);
		}

		return grid;
	}

	//////////////////////////////
	// SIGN BUTTONS EVENTS
	//////////////////////////////

	private void deleteEvent(ButtonWithStyle deleteCancel, View view) {
		deleteCancel.setOnAction(e -> {
			Salesman salesman = controller.getSalesman(LoggedInST.getUser().getSalesmanId(), password.getText());
			if (salesman != null) {
				proposal.getCar().setCarStatus("AVAILABLE");
				controller.updateCarStatus(proposal.getCar());
				controller.deleteProposal(proposal);
				if (view == View.Default) {
					new ProposalOverview().defaultUI(proposal.getCustomer().getCpr());
				} else if (view == View.Salesman) {
					new ProposalOverview().salesmanUI();
				} else if (view == View.ChiefOfSales) {
					new ProposalOverview().cosUI();
				}
			} else {
				wrong.setText("Forkert adgangskode");
			}
		});
	}

	private void cancelEvent(ButtonWithStyle deleteCancel, View view) {
		deleteCancel.setText("Annuller");
		deleteCancel.setOnAction(e -> {
			Salesman salesman = controller.getSalesman(LoggedInST.getUser().getSalesmanId(), password.getText());
			if (salesman != null) {
				proposal.getCar().setCarStatus("AVAILABLE");
				controller.updateCarStatus(proposal.getCar());
				proposal.setProposalStatus(Status.ANNULLERET);
				controller.updateProposalStatus(proposal);
				if (view == View.Default) {
					new ProposalOverview().defaultUI(proposal.getCustomer().getCpr());
				} else if (view == View.Salesman) {
					new ProposalOverview().salesmanUI();
				} else if (view == View.ChiefOfSales) {
					new ProposalOverview().cosUI();
				}
			} else {
				wrong.setText("Forkert adgangskode");
			}
		});
	}

	private void signEvent(ButtonWithStyle signConfirm, View view) {
		signConfirm.setOnAction(e -> {
			Salesman salesman = controller.getSalesman(LoggedInST.getUser().getSalesmanId(), password.getText());
			if (salesman != null) {
				proposal.setProposalStatus(Status.AFSLUTTET);
				if (view == View.Default) {
					new ProposalOverview().defaultUI(proposal.getCustomer().getCpr());
				} else if (view == View.Salesman) {
					new ProposalOverview().salesmanUI();
				}
			} else {
				wrong.setText("Forkert adgangskode");
			}
		});
	}

	private void confirmEvent(ButtonWithStyle signConfirm) {
		signConfirm.setText("Godkend");
		signConfirm.setOnAction(e -> {
			Salesman salesman = controller.getSalesman(LoggedInST.getUser().getSalesmanId(), password.getText());
			if (salesman != null) {
				proposal.setProposalStatus(Status.GODKENDT);
				new ProposalOverview().cosUI();
			} else {
				wrong.setText("Forkert adgangskode");
			}
		});
	}

	//////////////////////////////
	// BOTTOM BUTTONS
	//////////////////////////////

	private HBox buttons(View view) {
		HBox hbox = new HBox(backButton(view), csvButton());
		hbox.setPadding(new Insets(80, 50, 0, 0));
		hbox.setAlignment(Pos.BOTTOM_RIGHT);

		return hbox;
	}

	private GridPane csvButton() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER);

		ButtonWithStyle button = new ButtonWithStyle("CSV", grid, 0, 0);
		button.setOnAction(e -> {
			try {
				new CSVWriter().csvWriter(proposal);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		return grid;
	}

	private GridPane backButton(View view) {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER);

		ButtonWithStyle button = new ButtonWithStyle("Tilbage", grid, 0, 0);
		button.setOnAction(e -> {
			if (view == View.Default) {
				new ProposalOverview().defaultUI(proposal.getCustomer().getCpr());
			} else if (view == View.Salesman) {
				new ProposalOverview().salesmanUI();
			} else if (view == View.ChiefOfSales) {
				new ProposalOverview().cosUI(); 
			}
		});

		return grid;
	}

	//////////////////////////////
	// LABEL TITLE
	//////////////////////////////

	private Label title(View view) {
		Label label = new Label();
		if (view == View.Default || view == View.Salesman) {
			label.setText("Underskrift for " + proposal.getCar());
		} else if (view == View.Salesman) {
			label.setText("Godkend lånetilbud " + proposal.getProposalId());
		}
		label.setFont(Font.loadFont(style.titleFont(), 80));
		label.setTextFill(Color.web(style.black()));
		return label;
	}

	//////////////////////////////
	// SCENE STUFF
	//////////////////////////////

	private void uiSetup(View view, HBox hbox) {
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(50);

		VBoxWithStyle vbox = new VBoxWithStyle(title(view), hbox, buttons(view));
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	private void sceneSetup(Scene scene) {
		PrimaryStageST.getStage().setTitle("Ferrari Lånesystem");
		PrimaryStageST.getStage().setScene(scene);
		PrimaryStageST.getStage().show();
	}
}
