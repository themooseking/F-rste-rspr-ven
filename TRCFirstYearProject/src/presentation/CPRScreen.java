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

	public void cprUI() {
		VBoxWithStyle vbox = new VBoxWithStyle(title(), cprTextfield(), continueButton());
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, 1800, 980);
		sceneSetup(scene);
	}

	private GridPane cprTextfield() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(30));

		TextFieldWithStyle textfield = new TextFieldWithStyle("CPR-Number", grid, 0, 0);

//		textfield.textProperty().addListener(new ChangeListener<String>() {
//			int prev = textfield.getLength();

//			@Override
//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//
//				textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
//					@Override
//					public void handle(KeyEvent keyEvent) {
//						if (keyEvent.getCode() == KeyCode.BACK_SPACE && prev == 5 && prev < 8) {
//							textfield.setText(textfield.getText().substring(0, 5));
//							textfield.end();
//							prev = textfield.getLength();
//						}
//					}
//				});
//				
//				if (prev != 6) {
//					prev = textfield.getLength();
//				} else if (prev == 6) {
//					prev = 5;
//				}
//				System.out.println(prev);
//
//				if (textfield.getLength() == 6 && prev != 7) {
//					textfield.setText(textfield.getText() + "-");
//				} else if (!newValue.matches("\\d*") && prev < 7) {
//					textfield.setText(newValue.replaceAll("[^\\d]", ""));
//				}
//
//			}
//		});

//		if (prev != 6) {
//			prev = textfield.getLength();
//		} else if (prev == 6) {
//			prev = 5;
//		}
//		System.out.println(prev);
//
//		if (textfield.getLength() == 6 && prev != 7) {
//			textfield.setText(textfield.getText() + "-");
//		} else if (!newValue.matches("\\d*") && prev < 7) {
//			textfield.setText(newValue.replaceAll("[^\\d]", ""));
//		}

		textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
			int tfl = 1;

			@Override
			public void handle(KeyEvent keyEvent) {

				tfl = textfield.getLength();
				System.out.println(tfl);
				
				if (keyEvent.getCode() == KeyCode.ENTER) {
					System.out.println("Hej");
				}

				if (keyEvent.getCode().isDigitKey()) {
					if (tfl == 6) {
						textfield.setText(textfield.getText() + "-");
						textfield.end();
					} else if (tfl > 8) {
						String s = String.format("%d-%d", textfield.getText().toString());
						System.out.println(s);
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
							textfield.setText(newValue.replaceAll("[^\\d\\-]", ""));
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
