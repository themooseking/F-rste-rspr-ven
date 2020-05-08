package presentation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import styles.ButtonWithStyle;
import styles.GridPaneCenter;
import styles.StyleClass;
import styles.TextFieldWithStyle;
import styles.VBoxWithStyle;

public class CPRScreen {
	
	private StyleClass style = new StyleClass();
	
	private TextFieldWithStyle textfield;

	public void cprUI() {
		VBoxWithStyle vbox = new VBoxWithStyle(title(), cprTextfield(), continueButton());
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	private GridPane cprTextfield() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(30));

		textfield = new TextFieldWithStyle("CPR-Number", grid, 0, 0);

		textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
			int tfl = 1;

			@Override
			public void handle(KeyEvent keyEvent) {

				tfl = textfield.getLength();
				System.out.println(tfl);
				textfield.end();

				if (keyEvent.getCode() == KeyCode.ENTER) {
					System.out.println("Hej");
				}

				if (keyEvent.getCode().isDigitKey()) {
					if (tfl == 6) {
						textfield.setText(textfield.getText() + "-");
						textfield.end();
					}
				}

				if (keyEvent.getCode() == KeyCode.BACK_SPACE && tfl == 7) {
					textfield.setText(textfield.getText().substring(0, 6));
					textfield.end();
				}

				textfield.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (!newValue.matches("\\d*")) {
							if (tfl < 6) {
								textfield.setText(newValue.replaceAll("[^\\d]", ""));
							}
						}
					}
				});
			}
		});

		return grid;
	}

	private GridPane continueButton() {
		GridPaneCenter grid = new GridPaneCenter();

		ButtonWithStyle button = new ButtonWithStyle("Continue", grid, 0, 1);
		button.setOnAction(e -> {
			new ProposalOverview().proposalOverviewUI(textfield.getText());
		});

		return grid;
	}

	//////////////////////////////
	// Label Title
	//////////////////////////////

	private Label title() {
		Label label = new Label("Customer CPR");
		label.setFont(Font.loadFont("file:resources/fonts/FerroRosso.ttf", 120));
		label.setTextFill(Color.web(new StyleClass().defaultTextColor()));
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
