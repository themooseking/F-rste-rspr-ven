package presentation;

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
import logic.Proposal;
import styles.ButtonWithStyle;
import styles.GridPaneCenter;
import styles.LabelWithStyle;
import styles.StyleClass;
import styles.TextFieldWithStyle;
import styles.VBoxWithStyle;

public class SignProposalScreen {

	private StyleClass style = new StyleClass();
	private Proposal proposal;
	private TextReader tr;

	private TextFieldWithStyle password;

	public SignProposalScreen(Proposal proposal) {
		this.proposal = proposal;
		this.tr = new TextReader(proposal.getCustomer(), proposal);
	}

	public void signProposalUI() {
		int i = 0;
		HBox hbox = new HBox(tr.textReader(), fitter(i));
		hbox.setSpacing(50);
		hbox.setAlignment(Pos.CENTER);

		VBoxWithStyle vbox = new VBoxWithStyle(title(i), hbox, buttons(i));
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	public void salesmanSignProposalUI() {
		int i = 1;
		HBox hbox = new HBox(tr.textReader(), fitter(i));
		hbox.setSpacing(50);
		hbox.setAlignment(Pos.CENTER);

		VBoxWithStyle vbox = new VBoxWithStyle(title(i), hbox, buttons(i));
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	public void cosSignProposalUI() {
		int i = 2;
		HBox hbox = new HBox(tr.textReader(), fitter(i));
		hbox.setSpacing(50);
		hbox.setAlignment(Pos.CENTER);

		VBoxWithStyle vbox = new VBoxWithStyle(title(i), hbox, buttons(i));
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

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

		new LabelWithStyle("S�lger ID ", grid, 0, 0);
		TextFieldWithStyle id = new TextFieldWithStyle("", grid, 1, 0);
		id.setDisable(true);
		id.setOpacity(100);
		id.setText(Integer.toString(LoggedInST.getUser().getSalesmanId()));

		new LabelWithStyle("S�lger Navn ", grid, 0, 1);
		TextFieldWithStyle name = new TextFieldWithStyle("", grid, 1, 1);
		name.setDisable(true);
		name.setOpacity(100);
		name.setText(LoggedInST.getUser().toString());

		new LabelWithStyle("Kodeord ", grid, 0, 2);
		password = new TextFieldWithStyle("", grid, 1, 2);

		return grid;
	}

	private GridPaneCenter signButtons(int i) {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);
		grid.setHgap(20);

		ButtonWithStyle delete = new ButtonWithStyle("Slet", grid, 0, 0);
		delete.setOnAction(e -> {

		});

		ButtonWithStyle sign = new ButtonWithStyle("Underskriv", grid, 1, 0);
		if (i == 2) {
			sign.setText("Godkend");
			sign.setOnAction(e -> {

			});
		} else {
			sign.setOnAction(e -> {

			});
		}


		return grid;
	}

	//////////////////////////////
	// Buttons
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
			label.setText("Godekend lånetilbud " + proposal.getId());
		}
		label.setFont(Font.loadFont(style.titleFont(), 80));
		label.setTextFill(Color.web(style.black()));
		return label;
	}

	//////////////////////////////
	// Scene stuff
	//////////////////////////////

	private void sceneSetup(Scene scene) {
		PrimaryStageST.getStage().setTitle("Ferrari Lånesystem");
		PrimaryStageST.getStage().setScene(scene);
		PrimaryStageST.getStage().show();
	}

}
