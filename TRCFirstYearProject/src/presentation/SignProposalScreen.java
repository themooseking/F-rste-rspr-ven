package presentation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
		HBox hbox = new HBox(tr.textReader(), signInput());
		hbox.setSpacing(50);
		hbox.setAlignment(Pos.CENTER);

		VBoxWithStyle vbox = new VBoxWithStyle(title(proposal), hbox);
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	private VBox signInput() {
		VBox vbox = new VBox(textFields(), signButtons());
		vbox.setBorder(style.elementBorder());
		vbox.setPadding(new Insets(0, 0, 0, 50));

		VBox vbox2 = new VBox(vbox, buttons());
		vbox2.setPadding(new Insets(100, 0, 0, 0));
		vbox2.setAlignment(Pos.CENTER);

		return vbox2;
	}

	private GridPaneCenter textFields() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);
		grid.setVgap(10);

		new LabelWithStyle("Sælger ID ", grid, 0, 0);
		TextFieldWithStyle id = new TextFieldWithStyle("", grid, 1, 0);
		id.setDisable(true);
		id.setOpacity(100);
		id.setText(Integer.toString(proposal.getSalesman().getSalesmanId()));

		new LabelWithStyle("Sælger Navn ", grid, 0, 1);
		TextFieldWithStyle name = new TextFieldWithStyle("", grid, 1, 1);
		name.setDisable(true);
		name.setOpacity(100);
		name.setText(proposal.getSalesman().toString());

		new LabelWithStyle("Kodeord ", grid, 0, 2);
		password = new TextFieldWithStyle("", grid, 1, 2);

		return grid;
	}

	private GridPaneCenter signButtons() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);
		grid.setHgap(20);

		ButtonWithStyle delete = new ButtonWithStyle("Slet", grid, 0, 0);
		delete.setOnAction(e -> {

		});

		ButtonWithStyle sign = new ButtonWithStyle("Underskriv", grid, 1, 0);
		sign.setOnAction(e -> {

		});

		return grid;
	}

	//////////////////////////////
	// Buttons
	//////////////////////////////

	private HBox buttons() {
		HBox hbox = new HBox(backButton(), csvButton());
		hbox.setPadding(new Insets(100, 0, 0, 0));
		hbox.setAlignment(Pos.BOTTOM_CENTER);

		return hbox;
	}

	private GridPane csvButton() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);

		ButtonWithStyle button = new ButtonWithStyle("CSV", grid, 0, 0);
		button.setOnAction(e -> {

		});

		return grid;
	}

	private GridPane backButton() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);

		ButtonWithStyle button = new ButtonWithStyle("Tilbage", grid, 0, 0);
		button.setOnAction(e -> {
			new ProposalOverview().proposalOverviewUI(proposal.getCustomer().getCpr());
		});

		return grid;
	}

	//////////////////////////////
	// Label Title
	//////////////////////////////

	private Label title(Proposal proposal) {
		Label label = new Label("Underskrift for " + proposal.getCar());
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
