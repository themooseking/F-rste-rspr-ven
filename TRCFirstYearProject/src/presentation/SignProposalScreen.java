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
import styles.GridPaneCenter;
import styles.LabelWithStyle;
import styles.PasswordFieldWithStyle;
import styles.StyleClass;
import styles.TextFieldWithStyle;
import styles.VBoxWithStyle;

public class SignProposalScreen {

	private StyleClass style = new StyleClass();
	private Proposal proposal;
	private TextReader tr;
	private DB_Controller controller = new DB_Controller();

	private PasswordFieldWithStyle password;
	private LabelWithStyle wrong;

	public SignProposalScreen(Proposal proposal) {
		this.proposal = proposal;
		this.tr = new TextReader(proposal.getCustomer(), proposal);
	}

	public void defaultUI() {
		int i = 0;
		HBox hbox;
		Status status = proposal.getProposalStatus();

		if (status == Status.IGANG || status == Status.GODKENDT) {
			hbox = new HBox(tr.textReader(), fitter(i));
		} else {
			hbox = new HBox(tr.textReader());
		}
		uiSetup(i, hbox);
	}

	public void salesmanUI() {
		int i = 1;
		HBox hbox;
		Status status = proposal.getProposalStatus();

		if (status == Status.IGANG || status == Status.GODKENDT) {
			hbox = new HBox(tr.textReader(), fitter(i));
		} else {
			hbox = new HBox(tr.textReader());
		}
		uiSetup(i, hbox);
	}

	public void cosUI() {
		int i = 2;
		HBox hbox;
		Status status = proposal.getProposalStatus();

		if (status != Status.GODKENDT) {
			hbox = new HBox(tr.textReader(), fitter(i));
		} else {
			hbox = new HBox(tr.textReader());
		}
		uiSetup(i, hbox);
	}

	//////////////////////////////
	// Sign Inputs
	//////////////////////////////

	private VBox fitter(int i) {
		VBox vbox = new VBox(signInput(i));
		vbox.setAlignment(Pos.CENTER);

		return vbox;
	}

	private VBox signInput(int i) {
		VBox vbox = new VBox(textFields(), signButtons(i));
		vbox.setBackground(
				new Background(new BackgroundFill(Color.web(style.white()), new CornerRadii(0), Insets.EMPTY)));
		vbox.setBorder(style.elementBorder());
		vbox.setPadding(new Insets(0, 0, 0, 50));

		return vbox;
	}

	private GridPaneCenter textFields() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);
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

	private GridPaneCenter signButtons(int i) {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);
		grid.setHgap(20);

		ButtonWithStyle deleteCancel = new ButtonWithStyle("Slet", grid, 0, 0);
		ButtonWithStyle signConfirm = new ButtonWithStyle("Underskriv", grid, 1, 0);

		if (i == 2) {
			cancelEvent(deleteCancel, i);
			confirmEvent(signConfirm);
		} else {
			deleteEvent(deleteCancel, i);
			signEvent(signConfirm, i);
		}

		return grid;
	}

	//////////////////////////////
	// Sign Button Events
	//////////////////////////////

	private void deleteEvent(ButtonWithStyle deleteCancel, int i) {
		deleteCancel.setOnAction(e -> {
			Salesman salesman = controller.getSalesman(LoggedInST.getUser().getSalesmanId(), password.getText());
			if (salesman != null) {
				controller.deleteProposal(proposal);
				if (i == 0) {
					new ProposalOverview().customerUI(proposal.getCustomer().getCpr());
				} else if (i == 1) {
					new ProposalOverview().salesmanUI();
				} else if (i == 2) {
					new ProposalOverview().cosUI();
				}
			} else {
				wrong.setText("Forkert adgangskode");
			}
		});
	}

	private void cancelEvent(ButtonWithStyle deleteCancel, int i) {
		deleteCancel.setText("Annuller");
		deleteCancel.setOnAction(e -> {
			Salesman salesman = controller.getSalesman(LoggedInST.getUser().getSalesmanId(), password.getText());
			if (salesman != null) {
				proposal.setProposalStatus(Status.ANNULLERET);
				controller.updateProposalStatus(proposal);
				if (i == 0) {
					new ProposalOverview().customerUI(proposal.getCustomer().getCpr());
				} else if (i == 1) {
					new ProposalOverview().salesmanUI();
				} else if (i == 2) {
					new ProposalOverview().cosUI();
				}
			} else {
				wrong.setText("Forkert adgangskode");
			}
		});
	}

	private void signEvent(ButtonWithStyle signConfirm, int i) {
		signConfirm.setOnAction(e -> {
			Salesman salesman = controller.getSalesman(LoggedInST.getUser().getSalesmanId(), password.getText());
			if (salesman != null) {
				proposal.setProposalStatus(Status.AFSLUTTET);
				if (i == 0) {
					new ProposalOverview().customerUI(proposal.getCustomer().getCpr());
				} else if (i == 1) {
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
	// Bottom Buttons
	//////////////////////////////

	private HBox buttons(int i) {
		HBox hbox = new HBox(backButton(i), csvButton());
		hbox.setPadding(new Insets(45, 50, 0, 0));
		hbox.setAlignment(Pos.BOTTOM_RIGHT);

		return hbox;
	}

	private GridPane csvButton() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);

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

	private GridPane backButton(int i) {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);

		ButtonWithStyle button = new ButtonWithStyle("Tilbage", grid, 0, 0);
		button.setOnAction(e -> {
			if (i == 0) {
				new ProposalOverview().customerUI(proposal.getCustomer().getCpr());
			} else if (i == 1) {
				new ProposalOverview().salesmanUI();
			} else if (i == 2) {
				new ProposalOverview().cosUI();
			}
		});

		return grid;
	}

	//////////////////////////////
	// Label Title
	//////////////////////////////

	private Label title(int i) {
		Label label = new Label();
		if (i == 0 || i == 1) {
			label.setText("Underskrift for " + proposal.getCar());
		} else if (i == 2) {
			label.setText("Godkend lånetilbud " + proposal.getProposalId());
		}
		label.setFont(Font.loadFont(style.titleFont(), 80));
		label.setTextFill(Color.web(style.black()));
		return label;
	}

	//////////////////////////////
	// Scene stuff
	//////////////////////////////

	private void uiSetup(int i, HBox hbox) {
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(50);

		VBoxWithStyle vbox = new VBoxWithStyle(title(i), hbox, buttons(i));
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	private void sceneSetup(Scene scene) {
		PrimaryStageST.getStage().setTitle("Ferrari LÃ¥nesystem");
		PrimaryStageST.getStage().setScene(scene);
		PrimaryStageST.getStage().show();
	}

}
