package presentation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Proposal;
import styles.ButtonWithStyle;
import styles.GridPaneCenter;
import styles.StyleClass;
import styles.VBoxWithStyle;

public class SignProposalScreen {
	
	private StyleClass style = new StyleClass();

	public void signProposalUI(Proposal proposal) {
		VBoxWithStyle vbox = new VBoxWithStyle(title(proposal), buttons());
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}
	
	
	//////////////////////////////
	// Buttons
	//////////////////////////////

	private HBox buttons() {
		HBox hbox = new HBox(backButton(), csvButton());
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setBorder(new Border(new BorderStroke(Color.web(style.defaultHoverColor()), BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, new BorderWidths(7, 0, 0, 0))));

		return hbox;
	}

	private GridPane csvButton() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);

		ButtonWithStyle button = new ButtonWithStyle("CSV", grid, 0, 1);
		button.setOnAction(e -> {
			

		});

		return grid;
	}

	private GridPane backButton() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);

		ButtonWithStyle button = new ButtonWithStyle("Tilbage", grid, 0, 1);
		button.setOnAction(e -> {
			new NewPropsalScreen().newProposalUI();
		});

		return grid;
	}

	//////////////////////////////
	// Label Title
	//////////////////////////////

	private Label title(Proposal proposal) {
		Label label = new Label("Sign Proposal for " + proposal.getProposalId());
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
